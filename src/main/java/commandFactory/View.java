package commandFactory;
import Models.Room;
import Models.Reservation;
import map.MyMap;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The View class allows users to inspect detailed information about a specific hotel room.
 * It extends the Command class and is part of the commandFactory package.
 *
 * Main responsibilities:
 * - Prompts the user to enter a room number.
 * - Displays the room's description and price.
 * - Iterates through all reservations associated with the room.
 * - Prints details of each reservation including:
 *   - Main guest name
 *   - Check-in and check-out dates
 *   - Additional guests registered for the room
 * - If no valid reservation exists, informs the user that the room is not booked.
 * - Handles invalid room numbers gracefully by displaying an error message.
 *
 * This command is useful for reception staff or administrators to quickly
 * check the occupancy and reservation details of a specific room in the hotel.
 */

public class View extends Command {

    public void execute() {
        MyMap<String, Room> Rmap = hotel.get_map();

        System.out.println("Please enter the number of room you want to view: ");
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();

        if (Rmap.get(number) != null)  {
            String empty = "null";

            System.out.println("Description: " + Rmap.get(number).getDescription());
            System.out.println("Price: " + Rmap.get(number).getPrice());
            System.out.println();
            for (Reservation reservation : Rmap.get(number).getReservations() ) {
                String name = reservation.getName();
                String check_in = reservation.getCheck_in();
                String check_out = reservation.getCheck_out();
                ArrayList<String> o_guests = reservation.getOther_guests();

                if (!name.equals(empty) && !check_in.equals(empty) && !check_out.equals(empty)) {
                    System.out.println("Name of main guest: " + name + "; ");
                    System.out.println("Check in: " + check_in + "; ");
                    System.out.println("Check out: " + check_out + "; ");
                    System.out.println("Other guests: ");
                    for (String guest : o_guests) {
                        System.out.print(guest + "   ");
                    }
                }
                System.out.println();

            }

            }
            else
            {
                System.out.println("Sorry, you entered wrong number of the room.");
            }
    }

}


