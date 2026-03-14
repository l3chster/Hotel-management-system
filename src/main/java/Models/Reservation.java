package Models;
import java.util.ArrayList;

/**
 * The Reservation class represents a booking made by a guest in the hotel system.
 * It stores information about the main guest, additional guests, and the reservation dates.
 *
 * Main responsibilities:
 * - Holds the name of the main guest and a list of other guests sharing the room.
 * - Stores check-in and check-out dates as strings.
 * - Provides getter and setter methods to access and update reservation details.
 *
 * This class is used by the hotel management system to track room occupancy
 * and guest information. It ensures that each reservation is clearly defined
 * and can be modified when necessary.
 */

public class Reservation {
    private String mainGuest;
    private ArrayList<String> otherGuests;
    private String check_in;
    private String check_out;

    public Reservation(String mainGuest, ArrayList<String> otherGuests, String checkin, String checkout) {
        this.mainGuest = mainGuest;
        this.otherGuests = otherGuests;
        this.check_in = checkin;
        this.check_out = checkout;
    }

    public String getName() {
        return mainGuest;
    }
    public String getCheck_in() {
        return check_in;
    }
    public String getCheck_out() {
        return check_out;
    }

    public ArrayList<String> getOther_guests()
    {
        return otherGuests;
    }
    public void setOther_guests(ArrayList<String> new_guests)
    {
        otherGuests = new_guests;
    }
    public void setMain_guest(String main_g)
    {
        mainGuest =  main_g;
    }
    public void setCheckin(String date)
    {
        check_in  = date;
    }
    public void setCheckout(String date)
    {
        check_out  = date;
    }

}
