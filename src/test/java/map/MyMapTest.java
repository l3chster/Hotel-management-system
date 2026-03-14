package map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import Models.Room;
import java.util.*;

@DisplayName("Essential tests for MyMap with Room objects")
class MyMapTest {

    private MyMap<String, Room> roomMap;
    private Room room101;
    private Room room102;
    private Room room103;

    @BeforeEach
    void setUp() {
        roomMap = new MyMap<>();

        // Set rooms
        room101 = new Room("jednoosobowy", 130.0f, 1);
        room102 = new Room("dwuosobowy", 220.0f, 2);
        room103 = new Room("deluxe", 500.0f, 4);
    }

    // Adding rooms to map
    @Test
    @DisplayName("Add rooms to map and retrieve them")
    void testAddAndRetrieveRooms() {

        roomMap.put("101", room101);
        roomMap.put("102", room102);
        roomMap.put("103", room103);

        assertEquals(3, roomMap.size());
        assertEquals(room101, roomMap.get("101"));
        assertEquals(room102, roomMap.get("102"));
        assertEquals(room103, roomMap.get("103"));

        assertEquals("jednoosobowy", roomMap.get("101").getDescription());
        assertEquals(220.0f, roomMap.get("102").getPrice());
        assertEquals(4, roomMap.get("103").getHow_many_people());
    }


    // Removing a room
    @Test
    @DisplayName("Remove room from map")
    void testRemoveRoom() {
        // Arrange
        roomMap.put("101", room101);
        roomMap.put("102", room102);
        roomMap.put("103", room103);

        // Act
        Room removedRoom = roomMap.remove("102");

        // Assert
        assertEquals(room102, removedRoom);
        assertNull(roomMap.get("102"), "Room 102 should be removed");
        assertEquals(2, roomMap.size());
        assertNotNull(roomMap.get("101"), "Room 101 should still exist");
        assertNotNull(roomMap.get("103"), "Room 103 should still exist");
    }

    // Searching room by its number
    @Test
    @DisplayName("Search for rooms by room number")
    void testSearchRoomByNumber() {
        // Arrange
        roomMap.put("101", room101);
        roomMap.put("102", room102);
        roomMap.put("201", new Room("jednoosobowy", 125.0f, 1));

        // Assert - rooms that exist
        assertTrue(roomMap.containsKey("101"));
        assertTrue(roomMap.containsKey("102"));
        assertTrue(roomMap.containsKey("201"));

        // Assert - rooms that dont exist
        assertFalse(roomMap.containsKey("999"));
        assertFalse(roomMap.containsKey("103"));

        // Assert - should return null
        assertNull(roomMap.get("999"));
    }

    // testing numbers of rooms
    @Test
    @DisplayName("6. Get all room numbers and verify order")
    void testGetAllRoomNumbers() {
        // Arrange
        roomMap.put("101", room101);
        roomMap.put("102", room102);
        roomMap.put("103", new Room("jednoosobowy", 125.0f, 1));
        roomMap.put("104", room103);

        // Act
        List<String> roomNumbers = roomMap.keys();

        // Assert
        assertEquals(4, roomNumbers.size());
        assertTrue(roomNumbers.contains("101"));
        assertTrue(roomNumbers.contains("102"));
        assertTrue(roomNumbers.contains("103"));
        assertTrue(roomNumbers.contains("104"));

        // Check insertion order
        assertEquals("101", roomNumbers.get(0));
        assertEquals("102", roomNumbers.get(1));
        assertEquals("103", roomNumbers.get(2));
        assertEquals("104", roomNumbers.get(3));
    }

    // Clearing map of rooms
    @Test
    @DisplayName("Clear all rooms from hotel")
    void testClearAllRooms() {

        roomMap.put("101", room101);
        roomMap.put("102", room102);
        roomMap.put("103", room103);
        assertEquals(3, roomMap.size());


        roomMap.clear();

        assertEquals(0, roomMap.size());
        assertTrue(roomMap.isEmpty());
        assertNull(roomMap.get("101"));
        assertNull(roomMap.get("102"));
        assertNull(roomMap.get("103"));
    }


    // Handling operations on null map
    @Test
    @DisplayName("Handle empty hotel (no rooms)")
    void testEmptyHotel() {
        // Assert - nowy hotel jest pusty
        assertTrue(roomMap.isEmpty());
        assertEquals(0, roomMap.size());

        // Assert - operations on empty map
        assertNull(roomMap.get("101"));
        assertNull(roomMap.remove("101"));
        assertFalse(roomMap.containsKey("101"));

        // Assert - keys() returns empty list
        List<String> keys = roomMap.keys();
        assertNotNull(keys);
        assertEquals(0, keys.size());
    }


}