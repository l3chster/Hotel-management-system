package org.example;

import java.util.Scanner;
import commandFactory.Prices;
import commandFactory.View;
import commandFactory.Checkin;
import commandFactory.Checkout;
import commandFactory.List;
import commandFactory.Command;
import commandFactory.CommandRegistry;
import Models.Hotel;
import commandFactory.Save;
import commandFactory.Exit;

import static java.lang.Integer.parseInt;

/**
 * The Main class serves as the entry point for the hotel management system.
 * It initializes the application by loading hotel data from a CSV file,
 * creating a Hotel object, and registering all available commands in the
 * CommandRegistry.
 *
 * Main responsibilities:
 * - Reads hotel and room data using CsvReader and constructs the Hotel object.
 * - Registers commands such as checkin, checkout, view, list, prices, save,
 *   and exit, enabling user interaction with the system.
 * - Continuously prompts the user for input via the console, interpreting
 *   commands and executing the corresponding actions.
 * - Provides error handling for invalid commands, ensuring smooth user
 *   experience.
 *
 * This class ties together the core components of the system (Hotel, commands,
 * and registry) and maintains the main program loop, making it the central
 * controller of the application.
 */


public class Main {
    public static void main(String[] args) {
        CsvReader file = new CsvReader("./src/main/resources/data.txt");

        Hotel hotel = new Hotel(file.get_map(), parseInt(file.get_hotel_info()[0]), parseInt(file.get_hotel_info()[1]));


        CommandRegistry commandFactory = new CommandRegistry();
        commandFactory.registerCommand("checkin", Checkin.class);
        commandFactory.registerCommand("checkout", Checkout.class);
        commandFactory.registerCommand("view", View.class);
        commandFactory.registerCommand("list", List.class);
        commandFactory.registerCommand("prices", Prices.class);
        commandFactory.registerCommand("save", Save.class);
        commandFactory.registerCommand("exit", Exit.class);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter the command - valid commands are: view, list, checkin, checkout, prices, save, exit: ");
            System.out.println();
            String cmd = scanner.nextLine();
            Command command = commandFactory.createCommand(cmd.toLowerCase());
            command.setHotel(hotel);
            if (command == null) {
                System.err.println("No such command, please try again...");
                continue;
            }
            command.execute();
        }

    }
}
