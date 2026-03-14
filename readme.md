# Hotel Management System in Java

## System Description

The system manages a hotel with a configurable number of floors and rooms per floor.

Each room has:
- a number (e.g. `101` – first room on the first floor)
- a short description (regarding the room's standard)
- a price per night
- the name of the primary guest (to whom the room is registered)
- check-in and check-out dates
- capacity (maximum number of guests)
- guest details (if currently occupied)

### Hotel Configuration

Data is loaded from and saved to a file. The first record in `data.txt` contains the number of floors and the number of rooms per floor.

---

## Available Commands

The program uses a text-based interface. Supported commands (case-insensitive):

| Command    | Description |
|------------|-------------|
| `prices`   | Lists all rooms with their price per night |
| `view`     | Displays detailed information about a specified room |
| `checkin`  | Registers a guest in a room (guest details, dates, length of stay) |
| `checkout` | Checks out a guest and calculates the total cost of the stay |
| `list`     | Lists all rooms with occupancy status and guest information |
| `save`     | Saves the current hotel state to `data.txt` |
| `exit`     | Exits the program |

---

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── commandFactory/
│   │   │   │   ├── Checkin.java
│   │   │   │   ├── Checkout.java
│   │   │   │   ├── Command.java
│   │   │   │   ├── CommandRegistry.java
│   │   │   │   ├── Exit.java
│   │   │   │   ├── List.java
│   │   │   │   ├── Prices.java
│   │   │   │   ├── Save.java
│   │   │   │   └── View.java
│   │   │   ├── map/
│   │   │   │   └── MyMap.java
│   │   │   ├── Models/
│   │   │   │   ├── Hotel.java
│   │   │   │   ├── Reservation.java
│   │   │   │   └── Room.java
│   │   │   └── org.example/
│   │   │       ├── CsvReader.java
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── data.txt
│
└── test/
    └── java/
        ├── commandFactory/   # Command class tests
        ├── map/              # MyMap class tests
        └── org.example/     # Main program tests
```

---

## Tests

The project includes unit tests covering:
- `MyMap` operations: adding, retrieving, removing entries, and listing keys
- Hotel operations (check-in, check-out, pricing, etc.)
- `Main` class behavior and file loading via `CsvReader`

---

## Running the Application

1. **Compile** the project:
   ```bash
   javac Main.java
   ```

2. **Run** the program:
   ```bash
   java Main
   ```

3. **Enter commands** as described in the [Available Commands](#available-commands) section.

---

## Notes

- The program runs in text mode (console interface).
- Commands are **case-insensitive**.
- If invalid input is provided (e.g. a non-existent room or attempting to check into an occupied room), an appropriate error message is displayed.
- If data is entered in an incorrect format, the program throws a corresponding error.
