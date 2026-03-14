package commandFactory;
import Models.Room;
import map.MyMap;

/**
 * The Prices class is responsible for displaying the pricing information
 * of all rooms in the hotel. It extends the Command class and is part of
 * the commandFactory package.
 *
 * Main responsibilities:
 * - Iterates through all rooms stored in the hotel's map.
 * - Prints the room number, description, and price per night.
 * - Provides a quick overview of the hotel's pricing structure for staff
 *   or guests using the system.
 *
 * This command is useful when users want to compare room options and
 * costs before making a reservation. It offers a simple and clear way
 * to access essential financial information about the hotel rooms.
 */

public class Prices extends Command {
    public void execute() {
        MyMap<String, Room> Rmap = hotel.get_map();

        for (String key : Rmap.keys()) {
            System.out.print("Number: " + key);
            System.out.print(", Description: " + Rmap.get(key).getDescription());
            System.out.println(", Price: " + Rmap.get(key).getPrice());
        }

    }
}
