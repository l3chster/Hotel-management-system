package commandFactory;
import Models.Reservation;
import Models.Room;
import map.MyMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * The Checkin class handles the process of registering a guest into a hotel room.
 * It interacts with the user via console input to collect reservation details such as
 * room number, check-in date, number of nights, and guest names.
 * It verifies room availability and updates the reservation data accordingly.
 * This class extends the Command interface and is part of the commandFactory package.
 */


public class Checkin extends Command {


    @Override
    public void execute() {
        MyMap<String, Room> Rmap = hotel.get_map();

        System.out.println("Please enter the number of room you want to check in: ");
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();

        if (Rmap.get(number) != null)  {

            System.out.println("Do you want to set check_in_date (if you skip it the current date will be set)");
            System.out.println("Type Y if you want to set a date: ");

            LocalDate checkinDate = null;
            if (scanner.nextLine().equalsIgnoreCase("Y")) {

                boolean validDate = false;

                while (!validDate) {
                    try {
                        System.out.println("Set check in date in format year-month-day (YYYY-MM-DD): ");
                        String input = scanner.nextLine();
                        checkinDate = LocalDate.parse(input);
                        validDate = true; // Jeśli nie było błędu, data jest OK
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Please use YYYY-MM-DD (e.g., 2025-12-31)");
                        return;
                    }
                }

            }
            else {
                checkinDate = LocalDate.now();
            }

            System.out.println("For how many nights");
            String text = scanner.nextLine();

            int nights;
            try {
                nights = Integer.parseInt(text);
                if (nights <= 0) {
                    System.out.println("You entered a negative or 0 number of days. ");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("You did not entered a number. ");
                return;
            }

            LocalDate checkoutDate = checkinDate.plusDays(nights);

            /// ////////////////////////////////////////////////////////////
            String main_guest = null;
            ArrayList<String> guests  = new ArrayList<>();

            if (!Rmap.get(number).isTaken() || !overlaps(checkinDate, checkoutDate, Rmap, number))
            {
                System.out.println("Enter first and last name of main guest: ");
                main_guest = scanner.nextLine();

                int how_many_left = Rmap.get(number).getHow_many_people() - 1;
                System.out.println("You can enter max " + how_many_left + " more other guests. ");
                System.out.print("Type how many guests you want to register: ");
                text = scanner.nextLine();

                int counter;
                try {
                    counter = Integer.parseInt(text);
                    if (counter < 0 || counter > how_many_left ) {
                        System.out.println("You typed wrong number of  guests. ");
                        return;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("You did not entered a number. ");
                    return;
                }

                guests = new ArrayList<>();
                for (int i=0; i< counter; i++) {
                    System.out.println("Type guest name and surname: ");
                    guests.add(scanner.nextLine());
                }
            }

            /// /////////////////////////////////////////////////////////////
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


            if (!Rmap.get(number).isTaken())
            {
                // There should be only one room with null values
                for (Reservation res : Rmap.get(number).getReservations())
                {
                    res.setMain_guest(main_guest);
                    res.setOther_guests(guests);
                    res.setCheckin(checkinDate.format(formatter));
                    res.setCheckout(checkoutDate.format(formatter));

                }

                Rmap.get(number).setTaken(true);
            }
            else if (!overlaps(checkinDate, checkoutDate, Rmap, number) )
            {
                String d1 = checkinDate.format(formatter);
                String d2 = checkoutDate.format(formatter);
                Reservation new_res = new Reservation(main_guest, guests, d1, d2);
                Rmap.get(number).addReservation(new_res);

                // I do not have to set_taken because some date is taken already
            }
            else
            {
                System.out.println("We're sorry. The room is occupied on this date. ");
                System.out.println("Try to search for something different. ");
                return;
            }


            System.out.println("Thank you. You've been registered.");
            System.out.println("Your check in date: " + checkinDate.format(formatter));
            System.out.println("Your checkout date: " + checkoutDate.format(formatter));
            System.out.println("Please remember to register at the reception between 11am and 2pm. :)");



        }
        else
        {
            System.out.println("Error: you typed incorrect number or room is already taken");
        }

    }

    public static boolean overlaps(LocalDate startDate, LocalDate endDate, MyMap<String, Room> Rmap, String number)
    {
        for (Reservation res : Rmap.get(number).getReservations())
        {
            LocalDate beginning = LocalDate.parse(res.getCheck_in());
            LocalDate end =  LocalDate.parse(res.getCheck_out());

            if ( !end.isBefore(startDate) && !endDate.isBefore(beginning) )
                return true;  // returns true if overlaps
        }

        return false; // returns false if it does not overlap
    }
}