package commandFactory;

import Models.Hotel;
import Models.Reservation;
import Models.Room;
import map.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    private Checkout checkout;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        checkout = new Checkout();
        MyMap<String, Room> roomsMap = new MyMap<>();
        hotel = new Hotel(roomsMap, 3, 10);
        checkout.hotel = hotel;
    }

    @Test
    void testExecute_RoomNotFound() {    // if somebody wants to check out from room that not exist

        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Room room = new Room("Double room", 200.0f, 2);
        room.setTaken(true);
        hotel.get_map().put("101", room);

        checkout.execute();

        assertTrue(room.isTaken());
    }

    @Test
    void testExecute_RoomAlreadyEmpty() {  // checks the situation when somebody tries to (hipothetically check out from empty room)
        String input = "101\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Room room = new Room("Double room", 200.0f, 2);
        room.setTaken(false);
        hotel.get_map().put("101", room);

        checkout.execute();

        assertFalse(room.isTaken());
    }

    @Test
    void testExecute_WrongGuestName() {   // checks situation when somebody gives wrong main name
        String input = "101\nWrong Name\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Room room = new Room("Double room", 200.0f, 2);
        room.setTaken(true);
        Reservation reservation = new Reservation("John Doe", new ArrayList<>(),
                "2024-01-01", "2024-01-03");
        room.addReservation(reservation);

        hotel.get_map().put("101", room);

        checkout.execute();

        assertTrue(room.isTaken());
        assertEquals(1, room.getReservations().size());
    }
}