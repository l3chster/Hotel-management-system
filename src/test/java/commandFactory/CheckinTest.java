package commandFactory;

import Models.Hotel;
import Models.Reservation;
import Models.Room;
import map.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckinTest {

    private Checkin checkin;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        checkin = new Checkin();
        MyMap<String, Room> roomsMap = new MyMap<>();
        hotel = new Hotel(roomsMap, 3, 10);
        checkin.hotel = hotel;
    }

    @Test
    void testExecute_SuccessfulCheckinEmptyRoom() {   // check in to empty room
        Room room = new Room("Double room", 200.0f, 3);
        Reservation emptyReservation = new Reservation("null", new ArrayList<>(), "null", "null");
        room.addReservation(emptyReservation);
        room.setTaken(false);

        hotel.get_map().put("101", room);

        String input = "101\nN\n2\nJohn Doe\n1\nJane Smith\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkin.execute();

        assertEquals(1, room.getReservations().size());
        Reservation reservation = room.getReservations().get(0);
        assertEquals("John Doe", reservation.getName());
        assertEquals(1, reservation.getOther_guests().size());
        assertEquals("Jane Smith", reservation.getOther_guests().get(0));
        assertEquals(LocalDate.now().toString(), reservation.getCheck_in());
        assertEquals(LocalDate.now().plusDays(2).toString(), reservation.getCheck_out());
        assertTrue(room.isTaken());
    }

    @Test
    void testExecute_AddReservationToTakenRoomWithoutOverlap() {  // check in without overlapping dates

        String input = "101\nN\n2\nCharlie Davis\n1\nDiana Evans\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Room room = new Room("Deluxe", 300.0f, 4);
        room.setTaken(true);

        ArrayList<String> existingGuests = new ArrayList<>();
        Reservation existingReservation = new Reservation("Existing Guest", existingGuests,
                LocalDate.now().minusDays(5).toString(), LocalDate.now().minusDays(2).toString());
        room.addReservation(existingReservation);

        hotel.get_map().put("101", room);


        checkin.execute();

        assertEquals(2, room.getReservations().size());

        Reservation newReservation = room.getReservations().get(1);
        assertEquals("Charlie Davis", newReservation.getName());
        assertEquals(1, newReservation.getOther_guests().size());
        assertEquals("Diana Evans", newReservation.getOther_guests().get(0));
    }

    @Test
    void testExecute_RoomAlreadyTakenWithOverlappingDates() {    // check in with overlapping dates

        String input = "101\nN\n5\nBob Wilson\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Room room = new Room("Double room", 200.0f, 2);
        room.setTaken(true);

        ArrayList<String> existingGuests = new ArrayList<>();
        Reservation existingReservation = new Reservation("Existing Guest", existingGuests,
                LocalDate.now().toString(), LocalDate.now().plusDays(3).toString());
        room.addReservation(existingReservation);

        hotel.get_map().put("101", room);


        checkin.execute();

        assertEquals(1, room.getReservations().size(), "Should not add new reservation due to date overlap");
        assertEquals("Existing Guest", room.getReservations().get(0).getName(), "Original reservation should remain");
    }

    @Test
    void testExecute_RoomNotFound() {  // checks situation when somebody tries to get into unknown room

        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));


        Room room = new Room("Double room", 200.0f, 2);
        Reservation emptyReservation = new Reservation("null", new ArrayList<>(), "null", "null");
        room.addReservation(emptyReservation);
        room.setTaken(false);

        hotel.get_map().put("101", room);


        checkin.execute();

        assertFalse(room.isTaken(), "Room 101 should remain not taken");
        assertEquals(1, room.getReservations().size(), "Room 101 should still have one reservation");
        assertEquals("null", room.getReservations().get(0).getName(), "Reservation should remain unchanged");
    }


}