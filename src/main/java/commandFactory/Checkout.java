package commandFactory;

import Models.Reservation;
import Models.Room;
import map.MyMap;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The Checkout class manages the process of checking a guest out of a hotel room.
 * It extends the Command class and is part of the commandFactory package.
 *
 * Main responsibilities:
 * - Prompts the user to enter the room number and the main guest's name.
 * - Searches for the corresponding reservation and removes it from the room's list.
 * - If no reservations remain, marks the room as available again and resets its state.
 * - Calculates the number of nights stayed.
 * - Displays a summary message including the duration of the stay, room description,
 *   and the total payment based on the room price.
 *
 * This class demonstrates input validation, reservation management, and simple
 * billing logic within the hotel management system.
 */

public class Checkout extends Command {

    @Override
    public void execute() {
        MyMap<String, Room> Rmap = hotel.get_map();

        System.out.println("Please enter the number of room you want to check out: ");
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();

        if (Rmap.get(number) != null && Rmap.get(number).isTaken())  {

            System.out.println("Enter first and last name of main guest: ");
            String main_guest = scanner.nextLine();

            LocalDate checkIn = null;
            LocalDate checkOut = null;
            boolean flag = true;

            ArrayList<Reservation> reservations = Rmap.get(number).getReservations();
            for (int i = reservations.size() - 1; i >= 0; i--) {
                Reservation res = reservations.get(i);
                if (res.getName().equals(main_guest)) {
                    checkIn = LocalDate.parse(res.getCheck_in());
                    checkOut = LocalDate.parse(res.getCheck_out());
                    reservations.remove(i);
                    flag = false;
                    break;
                }
            }

            if (flag) {
                System.out.println("You typed wrong first and last name of main guest.");
                return;
            }


            if (Rmap.get(number).getReservations().isEmpty()) {
                Rmap.get(number).setTaken(false);
                ArrayList<String> guests = new ArrayList<>();
                Reservation new_res = new Reservation("null", guests, "null", "null");
                Rmap.get(number).addReservation(new_res);
            }


            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            System.out.print("Thank you for coming. You spent here " + nights + " night(s) in ");
            System.out.println("'" + Rmap.get(number).getDescription() + "' room. ");
            System.out.println("Payment: " + nights * Rmap.get(number).getPrice());

        } else {
            System.out.println("Error: you typed incorrect number or room is empty");
        }

    }


}