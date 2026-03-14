package Models;
import java.util.ArrayList;

/**
 * The Room class represents a single hotel room and its associated data.
 * It stores descriptive information, pricing, capacity, and reservation details.
 *
 * Main responsibilities:
 * - Holds the room description, price per night, and maximum number of guests.
 * - Maintains a list of reservations linked to the room.
 * - Tracks whether the room is currently taken or available.
 * - Provides methods to access room details, add reservations, and update occupancy status.
 *
 * This class is central to the hotel management system, as it connects
 * guest reservations with the physical room data. It ensures that each
 * room can be managed individually while supporting multiple reservations
 * over time.
 */

public class Room {
    private final String description;
    private final float price;
    private final int how_many_people;  // how many people in the room
    ArrayList<Reservation> reservations;
    boolean taken; // is true if somebody ever took a room


    public Room(String x, float y, int z)
    {
        description=x;
        price=y;
        how_many_people=z;
        reservations=new ArrayList<>();
        taken = false;
    }

    public String getDescription() {
        return description;
    }
    public float getPrice()
    {
        return price;
    }

    public int getHow_many_people() {
        return how_many_people;
    }
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
    public void addReservation(Reservation reservation)
    {
        reservations.add(reservation);
    }
    public boolean  isTaken() {
        return taken;
    }
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}



