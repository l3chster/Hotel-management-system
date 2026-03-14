package commandFactory;
import map.MyMap;


/**
 * The CommandRegistry class acts as a central registry and factory
 * for all available commands in the hotel management system.
 * It uses a custom MyMap implementation to associate command names
 * (strings) with their corresponding command classes.
 *
 * Main responsibilities:
 * - Provides a method to register commands by name and class type.
 * - Allows dynamic creation of command instances based on user input.
 * - Ensures that only known and registered commands can be executed.
 * - Throws clear exceptions when an unknown command is requested
 *   or when instantiation fails.
 *
 * This class demonstrates the Factory design pattern, enabling
 * flexible and extensible command management. By decoupling command
 * creation from execution, it makes the system easier to maintain
 * and extend with new commands in the future.
 */

public class CommandRegistry {

    private final MyMap<String, Class<? extends Command>> commandMap = new MyMap<>();

    // Register commands in the factory
    public void registerCommand(String name, Class<? extends Command> commandClass) {
        commandMap.put(name, commandClass);
    }
    // Create a command instance based on the string
    public Command createCommand(String commandName) {
        Class<? extends Command> commandClass = commandMap.get(commandName);
        if (commandClass == null) {
            throw new IllegalArgumentException("Unknown command: " + commandName);
        }
        try {
            return commandClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create command instance", e);
        }
    }
}
