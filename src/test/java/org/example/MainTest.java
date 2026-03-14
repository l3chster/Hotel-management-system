package org.example;

import commandFactory.*;
import Models.Hotel;
import map.MyMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CommandRegistryTest {

    private CommandRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CommandRegistry();
    }

    @Test
    void testRegisterAndCreateCommand() {
        // Rejestracja komendy
        registry.registerCommand("view", View.class);

        // Utworzenie komendy
        Command command = registry.createCommand("view");

        assertNotNull(command);
        assertInstanceOf(View.class, command);
    }

    @Test
    void testRegisterMultipleCommands() {
        registry.registerCommand("checkin", Checkin.class);
        registry.registerCommand("checkout", Checkout.class);
        registry.registerCommand("view", View.class);
        registry.registerCommand("list", List.class);
        registry.registerCommand("prices", Prices.class);

        // Sprawdzenie, czy wszystkie komendy można utworzyć
        assertNotNull(registry.createCommand("checkin"));
        assertNotNull(registry.createCommand("checkout"));
        assertNotNull(registry.createCommand("view"));
        assertNotNull(registry.createCommand("list"));
        assertNotNull(registry.createCommand("prices"));

        // Sprawdzenie typów
        assertInstanceOf(Checkin.class, registry.createCommand("checkin"));
        assertInstanceOf(Checkout.class, registry.createCommand("checkout"));
        assertInstanceOf(View.class, registry.createCommand("view"));
        assertInstanceOf(List.class, registry.createCommand("list"));
        assertInstanceOf(Prices.class, registry.createCommand("prices"));
    }

    @Test
    void testCreateUnknownCommand() {
        registry.registerCommand("view", View.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registry.createCommand("unknown");
        });

        assertTrue(exception.getMessage().contains("Unknown command"));
        assertTrue(exception.getMessage().contains("unknown"));
    }

    @Test
    void testOverwriteCommand() {
        registry.registerCommand("test", View.class);
        Command command1 = registry.createCommand("test");
        assertInstanceOf(View.class, command1);

        // Nadpisanie tej samej nazwy inną komendą
        registry.registerCommand("test", List.class);
        Command command2 = registry.createCommand("test");
        assertInstanceOf(List.class, command2);
    }

    @Test
    void testCreateMultipleInstances() {
        registry.registerCommand("view", View.class);

        Command command1 = registry.createCommand("view");
        Command command2 = registry.createCommand("view");

        assertNotNull(command1);
        assertNotNull(command2);
        assertNotSame(command1, command2); // Różne instancje
        assertInstanceOf(View.class, command1);
        assertInstanceOf(View.class, command2);
    }

    @Test
    void testCommandNameCaseSensitivity() {
        registry.registerCommand("view", View.class);

        // Lowercase działa
        assertDoesNotThrow(() -> registry.createCommand("view"));

        // Uppercase nie działa (bo rejestrujemy jako "view")
        assertThrows(IllegalArgumentException.class, () -> {
            registry.createCommand("VIEW");
        });
    }

    @Test
    @DisplayName("Test setHotel")
    void testCommandSetHotel() {
        registry.registerCommand("view", View.class);
        Command command = registry.createCommand("view");

        MyMap<String, Models.Room> roomMap = new MyMap<>();
        Hotel hotel = new Hotel(roomMap, 3, 10);
    }
}