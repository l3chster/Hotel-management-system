package commandFactory;

import Models.Reservation;
import Models.Room;
import map.MyMap;
import java.util.ArrayList;


/**
 * The List class provides functionality to display all rooms in the hotel
 * along with their details and reservations. It extends the Command class
 * and is part of the commandFactory package.
 *
 * Main responsibilities:
 * - Iterates through all rooms stored in the hotel's map.
 * - Prints each room's description and price.
 * - Displays reservation details including the main guest, check-in and
 *   check-out dates, and any additional guests.
 * - If a room has no valid reservations, informs the user that the room
 *   is currently not booked.
 * - Separates room information with a clear divider for readability.
 *
 * This command is useful for administrators or reception staff to quickly
 * review the current occupancy and reservation status of the hotel.
 */

public class List extends Command {
    public void execute() {
        MyMap<String, Room> Rmap = hotel.get_map();
        String empty = "null";

        for (String key : Rmap.keys()){
        System.out.println("Description: " + Rmap.get(key).getDescription());
        System.out.println("Price: " + Rmap.get(key).getPrice());
        System.out.println();
        for (Reservation reservation : Rmap.get(key).getReservations() ) {
            String name = reservation.getName();
            String check_in = reservation.getCheck_in();
            String check_out = reservation.getCheck_out();
            ArrayList<String> o_guests = reservation.getOther_guests();

            if (!name.equals(empty) && !check_in.equals(empty) && !check_out.equals(empty)) {
                System.out.println("Name of main guest: " + name + "; ");
                System.out.println("Check in: " + check_in + "; ");
                System.out.println("Check out: " + check_out + "; ");
                System.out.print("Other guests: ");
                for (String guest : o_guests) {
                    System.out.print(guest + "   ");
                }
                System.out.println();
                System.out.println();
            }
            else
            {
                System.out.println("There are no guests that booked this room.");
            }

        }

        System.out.println("------------------------------------------------------");

        }

    }

}
