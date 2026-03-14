package org.example;

import Models.Room;
import Models.Reservation;
import map.MyMap;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    @TempDir
    Path tempDir;

    private String createTestFile(String content) throws IOException {
        Path file = tempDir.resolve("test.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
            writer.write(content);
        }
        return file.toString();
    }

    @Test
    @DisplayName("Test kompleksowego odczytu pliku CSV")
    void testCompleteCSVReading() throws IOException {
        String csvContent = "3,10\n" +
                "101,Standard,150.0,2024-01-15,2024-01-20,cash,2,Jan,Kowalski\n" +
                "102,Deluxe,250.0,null,null,null,4\n" +
                "103,Suite,400.0,2024-03-01,2024-03-05,card,3,Anna,Nowak,Piotr\n";

        String filePath = createTestFile(csvContent);
        CsvReader reader = new CsvReader(filePath);

        // Test informacji o hotelu
        String[] hotelInfo = reader.get_hotel_info();
        assertEquals(2, hotelInfo.length);
        assertEquals("3", hotelInfo[0]);
        assertEquals("10", hotelInfo[1]);

        // Test mapy pokoi
        MyMap<String, Room> roomMap = reader.get_map();
        assertEquals(3, roomMap.size());

        // Test pokoju z rezerwacją (zajęty)
        Room room101 = roomMap.get("101");
        assertNotNull(room101);
        assertEquals("Standard", room101.getDescription());
        assertEquals(150.0f, room101.getPrice(), 0.01);
        assertEquals(2, room101.getHow_many_people());
        assertTrue(room101.isTaken());
        assertEquals(1, room101.getReservations().size());

        // Test pokoju bez rezerwacji (wolny)
        Room room102 = roomMap.get("102");
        assertNotNull(room102);
        assertFalse(room102.isTaken());

        // Test rezerwacji z wieloma gośćmi
        Room room103 = roomMap.get("103");
        assertNotNull(room103);
        Reservation res = room103.getReservations().get(0);
        assertEquals(3, res.getOther_guests().size());
        assertEquals("2024-03-01", res.getName()); // mainGuest = data[3] = check-in date
        assertEquals("2024-03-05", res.getCheck_in()); // checkin = data[4] = check-out date
        assertEquals("card", res.getCheck_out()); // checkout = data[5] = payment method
    }

    @Test
    @DisplayName("Test wielu rezerwacji dla tego samego pokoju")
    void testMultipleReservationsForSameRoom() throws IOException {
        String csvContent = "2,8\n" +
                "101,Standard,150.0,2024-01-15,2024-01-20,cash,2,Jan,Kowalski\n" +
                "101,Standard,150.0,2024-02-10,2024-02-15,card,2,Anna,Nowak\n" +
                "101,Standard,150.0,2024-03-05,2024-03-10,transfer,2,Piotr,Zielinski\n";

        String filePath = createTestFile(csvContent);
        CsvReader reader = new CsvReader(filePath);

        MyMap<String, Room> roomMap = reader.get_map();
        Room room = roomMap.get("101");

        assertNotNull(room);
        assertEquals(3, room.getReservations().size());
        assertTrue(room.isTaken());

        // Z powodu błędu w CsvReader: mainGuest=data[3], checkin=data[4], checkout=data[5]
        ArrayList<Reservation> reservations = room.getReservations();
        assertEquals("2024-01-15", reservations.get(0).getName()); // mainGuest
        assertEquals("2024-02-10", reservations.get(1).getName());
        assertEquals("2024-03-05", reservations.get(2).getName());

        assertEquals("2024-01-20", reservations.get(0).getCheck_in()); // to jest data[4]
        assertEquals("2024-02-15", reservations.get(1).getCheck_in());
        assertEquals("2024-03-10", reservations.get(2).getCheck_in());

    }
}