package commandFactory;

import Models.Hotel;
import Models.Reservation;
import Models.Room;
import map.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveTest {

    private Save save;
    private Hotel hotel;

    @TempDir              // new option in junit 5 which creates new folder for tests
    Path tempDir;

    @BeforeEach     // before each test we need to have all necessary fields
    void setUp() {
        save = new Save();
        MyMap<String, Room> roomsMap = new MyMap<>();
        hotel = new Hotel(roomsMap, 3, 16);
        save.hotel = hotel;
    }

    @Test
    void testExecute_SaveMultipleRoomsWithReservations() throws IOException {

        File testFile = tempDir.resolve("test_result.txt").toFile();  // add temporary file to folder

        save = new Save() {  // we will implement the same function but now we are tranfering data to temp directory
            @Override
            public void execute() {
                MyMap<String, Room> rmap = hotel.get_map();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
                    writer.write(hotel.getFloors() + ",");
                    writer.write(hotel.getRooms_on_floor() + "");
                    writer.newLine();

                    for (String key : rmap.keys()) {
                        StringBuilder line = new StringBuilder();
                        int price = (int)rmap.get(key).getPrice();
                        int people = rmap.get(key).getHow_many_people();

                        line.append(key).append(",");
                        line.append(rmap.get(key).getDescription()).append(",");
                        line.append(price).append(",");

                        for (Reservation x : rmap.get(key).getReservations()) {
                            StringBuilder cust_line = new StringBuilder();
                            cust_line.append(x.getName()).append(",");
                            cust_line.append(x.getCheck_in()).append(",");
                            cust_line.append(x.getCheck_out()).append(",");
                            cust_line.append(people).append(",");
                            for (String person : x.getOther_guests()) {
                                cust_line.append(person).append(",");
                            }
                            String result = line + cust_line.toString();
                            result = result.replaceAll(",$", "");
                            result = result.trim();
                            writer.write(result);
                            writer.newLine();
                        }
                    }
                    System.out.println("Data has been changed.");
                } catch (IOException e) {
                    System.out.println("We've got an error during writing into file: " + e.getMessage());
                }
            }
        };
        save.hotel = hotel;

        Room room1 = new Room("jednoosobowy", 130.0f, 1);
        Reservation reservation1 = new Reservation("null", new ArrayList<>(), "null", "null");
        room1.addReservation(reservation1);

        Room room2 = new Room("dwuosobowy", 220.0f, 2);
        ArrayList<String> guests2 = new ArrayList<>();
        guests2.add("Błażej Pieczko");
        Reservation reservation2 = new Reservation("Jan Kowalski", guests2, "2025-11-02", "2025-11-05");
        room2.addReservation(reservation2);

        ArrayList<String> guests3 = new ArrayList<>();
        guests3.add("Konrad Kołodziej");
        Reservation reservation3 = new Reservation("Andrzej Nowak", guests3, "2025-11-07", "2025-11-10");
        room2.addReservation(reservation3);

        Room room3 = new Room("deluxe", 500.0f, 4);
        Reservation reservation4 = new Reservation("Anna Nowak", new ArrayList<>(), "2025-11-03", "2025-11-06");
        room3.addReservation(reservation4);

        hotel.get_map().put("101", room1);
        hotel.get_map().put("102", room2);
        hotel.get_map().put("104", room3);


        save.execute();

        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            String firstLine = reader.readLine();
            assertEquals("3,16", firstLine);

            String line101 = reader.readLine();
            assertEquals("101,jednoosobowy,130,null,null,null,1", line101);

            String line102_1 = reader.readLine();
            assertEquals("102,dwuosobowy,220,Jan Kowalski,2025-11-02,2025-11-05,2,Błażej Pieczko", line102_1);

            String line102_2 = reader.readLine();
            assertEquals("102,dwuosobowy,220,Andrzej Nowak,2025-11-07,2025-11-10,2,Konrad Kołodziej", line102_2);

            String line104 = reader.readLine();
            assertEquals("104,deluxe,500,Anna Nowak,2025-11-03,2025-11-06,4", line104);
        }
    }

    @Test
    void testExecute_SaveRoomWithMultipleGuests() throws IOException {
        File testFile = tempDir.resolve("test_multiple_guests.txt").toFile();

        save = new Save() {
            @Override
            public void execute() {
                MyMap<String, Room> rmap = hotel.get_map();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
                    writer.write(hotel.getFloors() + ",");
                    writer.write(hotel.getRooms_on_floor() + "");
                    writer.newLine();

                    for (String key : rmap.keys()) {
                        StringBuilder line = new StringBuilder();
                        int price = (int)rmap.get(key).getPrice();
                        int people = rmap.get(key).getHow_many_people();

                        line.append(key).append(",");
                        line.append(rmap.get(key).getDescription()).append(",");
                        line.append(price).append(",");

                        for (Reservation x : rmap.get(key).getReservations()) {
                            StringBuilder cust_line = new StringBuilder();
                            cust_line.append(x.getName()).append(",");
                            cust_line.append(x.getCheck_in()).append(",");
                            cust_line.append(x.getCheck_out()).append(",");
                            cust_line.append(people).append(",");
                            for (String person : x.getOther_guests()) {
                                cust_line.append(person).append(",");
                            }
                            String result = line + cust_line.toString();
                            result = result.replaceAll(",$", "");
                            result = result.trim();
                            writer.write(result);
                            writer.newLine();
                        }
                    }
                    System.out.println("Data has been changed.");
                } catch (IOException e) {
                    System.out.println("We've got an error during writing into file: " + e.getMessage());
                }
            }
        };
        save.hotel = hotel;

        Room room = new Room("deluxe", 600.0f, 4);
        ArrayList<String> guests = new ArrayList<>();
        guests.add("Gość 1");
        guests.add("Gość 2");
        guests.add("Gość 3");
        Reservation reservation = new Reservation("Główny Gość", guests, "2025-11-10", "2025-11-15");
        room.addReservation(reservation);

        hotel.get_map().put("112", room);

        save.execute();

        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            reader.readLine(); // Pomijamy pierwszą linię z info o hotelu

            String roomLine = reader.readLine();
            assertEquals("112,deluxe,600,Główny Gość,2025-11-10,2025-11-15,4,Gość 1,Gość 2,Gość 3", roomLine);
        }
    }

    @Test
    void testExecute_SaveEmptyHotel() throws IOException {
        File testFile = tempDir.resolve("test_empty.txt").toFile();

        save = new Save() {
            @Override
            public void execute() {
                MyMap<String, Room> rmap = hotel.get_map();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
                    writer.write(hotel.getFloors() + ",");
                    writer.write(hotel.getRooms_on_floor() + "");
                    writer.newLine();

                    for (String key : rmap.keys()) {
                        StringBuilder line = new StringBuilder();
                        int price = (int)rmap.get(key).getPrice();
                        int people = rmap.get(key).getHow_many_people();

                        line.append(key).append(",");
                        line.append(rmap.get(key).getDescription()).append(",");
                        line.append(price).append(",");

                        for (Reservation x : rmap.get(key).getReservations()) {
                            StringBuilder cust_line = new StringBuilder();
                            cust_line.append(x.getName()).append(",");
                            cust_line.append(x.getCheck_in()).append(",");
                            cust_line.append(x.getCheck_out()).append(",");
                            cust_line.append(people).append(",");
                            for (String person : x.getOther_guests()) {
                                cust_line.append(person).append(",");
                            }
                            String result = line + cust_line.toString();
                            result = result.replaceAll(",$", "");
                            result = result.trim();
                            writer.write(result);
                            writer.newLine();
                        }
                    }
                    System.out.println("Data has been changed.");
                } catch (IOException e) {
                    System.out.println("We've got an error during writing into file: " + e.getMessage());
                }
            }
        };
        save.hotel = hotel;
        save.execute();

        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            String firstLine = reader.readLine();
            assertEquals("3,16", firstLine);

            assertNull(reader.readLine());
        }
    }
}