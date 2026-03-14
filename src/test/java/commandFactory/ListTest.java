package commandFactory;

import Models.Hotel;
import Models.Reservation;
import Models.Room;
import map.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    private List list;
    private Hotel hotel;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        list = new List();
        MyMap<String, Room> roomsMap = new MyMap<>();
        hotel = new Hotel(roomsMap, 2, 5);
        list.hotel = hotel;

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testExecute_DisplayMultipleRoomsWithReservations() {

        Room room1 = new Room("Single room", 100.0f, 1);
        ArrayList<String> guests1 = new ArrayList<>();
        guests1.add("Friend 1");
        Reservation reservation1 = new Reservation("John Doe", guests1, "2023-12-01", "2023-12-05");
        room1.addReservation(reservation1);

        Room room2 = new Room("Double room", 200.0f, 2);
        ArrayList<String> guests2 = new ArrayList<>();
        guests2.add("Wife");
        guests2.add("Child");
        Reservation reservation2 = new Reservation("Robert Smith", guests2, "2023-12-10", "2023-12-15");
        room2.addReservation(reservation2);

        hotel.get_map().put("101", room1);
        hotel.get_map().put("102", room2);


        list.execute();


        String output = outputStream.toString();

        assertTrue(output.contains("Description: Single room"));
        assertTrue(output.contains("Price: 100.0"));
        assertTrue(output.contains("Name of main guest: John Doe"));
        assertTrue(output.contains("Check in: 2023-12-01"));
        assertTrue(output.contains("Check out: 2023-12-05"));
        assertTrue(output.contains("Friend 1"));


        assertTrue(output.contains("Description: Double room"));
        assertTrue(output.contains("Price: 200.0"));
        assertTrue(output.contains("Name of main guest: Robert Smith"));
        assertTrue(output.contains("Check in: 2023-12-10"));
        assertTrue(output.contains("Check out: 2023-12-15"));
        assertTrue(output.contains("Wife"));
        assertTrue(output.contains("Child"));


        assertTrue(output.contains("------------------------------------------------------"));
    }

    @Test
    void testExecute_DisplayRoomsWithEmptyReservations() {

        Room room1 = new Room("Single room", 100.0f, 1);
        Reservation emptyReservation1 = new Reservation("null", new ArrayList<>(), "null", "null");
        room1.addReservation(emptyReservation1);

        Room room2 = new Room("Double room", 200.0f, 2);
        Reservation emptyReservation2 = new Reservation("null", new ArrayList<>(), "null", "null");
        room2.addReservation(emptyReservation2);

        hotel.get_map().put("101", room1);
        hotel.get_map().put("102", room2);


        list.execute();

        String output = outputStream.toString();


        assertTrue(output.contains("Description: Single room"));
        assertTrue(output.contains("Price: 100.0"));
        assertTrue(output.contains("Description: Double room"));
        assertTrue(output.contains("Price: 200.0"));


        assertTrue(output.contains("There are no guests that booked this room."));


        assertFalse(output.contains("Name of main guest:"));
        assertFalse(output.contains("Check in:"));
        assertFalse(output.contains("Check out:"));
        assertFalse(output.contains("Other guests:"));
    }

    @Test
    void testExecute_DisplayMixedReservations() {

        Room room = new Room("Suite", 300.0f, 3);



        ArrayList<String> guests = new ArrayList<>();
        guests.add("Business Partner");
        Reservation validReservation = new Reservation("Michael Brown", guests, "2023-12-20", "2023-12-25");
        Reservation validReservation2 = new Reservation("Michael Bron", guests, "2024-12-20", "2024-12-20");

        room.addReservation(validReservation2);
        room.addReservation(validReservation);

        hotel.get_map().put("201", room);

        list.execute();

        String output = outputStream.toString();

        assertTrue(output.contains("Description: Suite"));
        assertTrue(output.contains("Price: 300.0"));


        assertTrue(output.contains("Name of main guest: Michael Brown"));
        assertTrue(output.contains("Check in: 2023-12-20"));
        assertTrue(output.contains("Check out: 2023-12-25"));
        assertTrue(output.contains("Business Partner"));

        assertTrue(output.contains("------------------------------------------------------"));
    }
}