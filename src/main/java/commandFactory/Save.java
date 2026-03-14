package commandFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import Models.Reservation;
import Models.Room;
import map.MyMap;

/**
 * The Save class is responsible for exporting the current state of the hotel
 * into a text file. It extends the Command class and is part of the
 * commandFactory package.
 *
 * Main responsibilities:
 * - Retrieves all room and reservation data from the hotel's map.
 * - Writes hotel configuration (number of floors and rooms per floor) to the file.
 * - Iterates through each room and its reservations, saving details such as:
 *   room number, description, price, main guest, check-in and check-out dates,
 *   capacity, and additional guests.
 * - Ensures that data is formatted consistently in CSV-like structure.
 * - Handles potential I/O exceptions gracefully, informing the user if an error occurs.
 *
 * This command provides a way to persist the hotel's current state, allowing
 * administrators to back up or transfer reservation data. It demonstrates
 * file handling in Java using BufferedWriter and StringBuilder for efficient
 * output operations.
 */


public class Save extends Command {

    @Override
    public void execute() {

        MyMap<String, Room> rmap = hotel.get_map();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/resources/data.txt"))) {

            writer.write(hotel.getFloors() + ",");
            writer.write(hotel.getRooms_on_floor() + "");
            writer.newLine();

            for (String key : rmap.keys()) {
                ///
                StringBuilder line = new StringBuilder();
                int price = (int)rmap.get(key).getPrice();
                int people = rmap.get(key).getHow_many_people();

                line.append(key).append(",");
                line.append(rmap.get(key).getDescription()).append(",");
                line.append(price).append(",");


                for (Reservation x : rmap.get(key).getReservations())
                {
                    StringBuilder cust_line = new StringBuilder();

                    cust_line.append(x.getName()).append(",");
                    cust_line.append(x.getCheck_in()).append(",");
                    cust_line.append(x.getCheck_out()).append(",");
                    cust_line.append(people).append(",");
                    for (String person : x.getOther_guests())
                    {
                        cust_line.append(person).append(",");
                    }
                    String result = line + cust_line.toString();
                    result = result.replaceAll(",$", "");
                    result = result.trim();
                    writer.write(result);
                    writer.newLine();
                }

            }

            System.out.println("Data has been changed.");
        } catch (IOException e) {
            System.out.println("We've got an error during writing into file: " + e.getMessage());
        }
    }
}






