package commandFactory;


/**
 * The Exit class represents the command used to terminate the hotel
 * management system. It extends the Command class and provides a
 * simple implementation of the execute() method.
 *
 * Main responsibilities:
 * - Displays a farewell message to the user.
 * - Calls System.exit(0) to immediately stop the program execution.
 *
 * This command is typically invoked when the user chooses to leave
 * the application. It ensures a clean and predictable shutdown of
 * the system, making it clear to the user that the program has ended.
 */

public class Exit extends Command {
    @Override
    public void execute() {
        System.out.println("Thank you for using Hotel Management System. Goodbye!");
        System.exit(0);  // ← ZAMYKA CAŁY PROGRAM
    }
}

