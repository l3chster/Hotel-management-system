package Models;

import map.MyMap;

/**
 * The Hotel class represents the overall hotel structure.
 * It stores the number of floors, rooms per floor, and a map of all rooms.
 * Provides access to hotel configuration and room data through getter methods.
 */

public class Hotel {
    final int floors;
    final int rooms_on_floor;
    private final MyMap<String, Room> roomsMap;


    public Hotel(MyMap<String, Room> Rmap, int x, int y ) {
        this.roomsMap = new MyMap<>();
        this.floors = x;
        this.rooms_on_floor = y;

        for (String key : Rmap.keys()) {
            this.roomsMap.put(key, Rmap.get(key));
        }

    }
    public int getFloors() {
        return floors;
    }
    public int getRooms_on_floor()
    {
        return rooms_on_floor;
    }
    public MyMap<String, Room> get_map()
    {
        return roomsMap;
    }


}