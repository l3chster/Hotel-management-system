package commandFactory;

import Models.Hotel;
import Models.Reservation;
import Models.Room;
import map.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {

    private View view;
    private Hotel hotel;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        view = new View();
        MyMap<String, Room> roomsMap = new MyMap<>();
        hotel = new Hotel(roomsMap, 3, 10);
        view.hotel = hotel;

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testExecute_ViewRoomWithReservations() {

        Room room = new Room("Double room", 200.0f, 2);

        ArrayList<String> guests1 = new ArrayList<>();
        guests1.add("Jane Smith");
        Reservation reservation1 = new Reservation("John Doe", guests1, "2023-12-01", "2023-12-05");

        ArrayList<String> guests2 = new ArrayList<>();
        guests2.add("Bob Wilson");
        Reservation reservation2 = new Reservation("Alice Johnson", guests2, "2023-12-10", "2023-12-15");

        room.addReservation(reservation1);
        room.addReservation(reservation2);

        hotel.get_map().put("101", room);

        String input = "101\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // When
        view.execute();

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("Description: Double room"));
        assertTrue(output.contains("Price: 200.0"));
        assertTrue(output.contains("Name of main guest: John Doe"));
        assertTrue(output.contains("Check in: 2023-12-01"));
        assertTrue(output.contains("Check out: 2023-12-05"));
        assertTrue(output.contains("Jane Smith"));
        assertTrue(output.contains("Name of main guest: Alice Johnson"));
        assertTrue(output.contains("Check in: 2023-12-10"));
        assertTrue(output.contains("Check out: 2023-12-15"));
        assertTrue(output.contains("Bob Wilson"));
    }

    @Test
    void testExecute_ViewRoomWithEmptyReservations() {

        Room room = new Room("Single room", 100.0f, 1);

        ArrayList<String> emptyGuests = new ArrayList<>();
        Reservation emptyReservation = new Reservation("null", emptyGuests, "null", "null");
        room.addReservation(emptyReservation);

        hotel.get_map().put("102", room);

        String input = "102\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        view.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Description: Single room"));
        assertTrue(output.contains("Price: 100.0"));
        assertFalse(output.contains("Name of main guest:"));
        assertFalse(output.contains("Check in:"));
        assertFalse(output.contains("Check out:"));
    }

    @Test
    void testExecute_ViewNonExistentRoom() {
        Room existingRoom = new Room("Suite", 300.0f, 3);
        hotel.get_map().put("201", existingRoom);

        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        view.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Sorry, you entered wrong number of the room."));
        assertFalse(output.contains("Description:"));
        assertFalse(output.contains("Price:"));
    }
}