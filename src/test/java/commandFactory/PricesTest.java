package commandFactory;

import Models.Hotel;
import Models.Room;
import map.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PricesTest {

    private Prices prices;
    private Hotel hotel;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        prices = new Prices();
        MyMap<String, Room> roomsMap = new MyMap<>();
        hotel = new Hotel(roomsMap, 2, 5);
        prices.hotel = hotel;

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testExecute_DisplayMultipleRoomsWithPrices() {

        Room room1 = new Room("Single room", 150.0f, 1);
        Room room2 = new Room("Double room", 250.0f, 2);
        Room room3 = new Room("Suite", 450.0f, 4);

        hotel.get_map().put("101", room1);
        hotel.get_map().put("102", room2);
        hotel.get_map().put("201", room3);

        prices.execute();

        String output = outputStream.toString();

        assertTrue(output.contains("Number: 101"));
        assertTrue(output.contains("Description: Single room"));
        assertTrue(output.contains("Price: 150.0"));

        assertTrue(output.contains("Number: 102"));
        assertTrue(output.contains("Description: Double room"));
        assertTrue(output.contains("Price: 250.0"));

        assertTrue(output.contains("Number: 201"));
        assertTrue(output.contains("Description: Suite"));
        assertTrue(output.contains("Price: 450.0"));
    }

    @Test
    void testExecute_DisplayEmptyHotel() {

        prices.execute();


        String output = outputStream.toString();
        assertEquals("", output.trim());
    }
}