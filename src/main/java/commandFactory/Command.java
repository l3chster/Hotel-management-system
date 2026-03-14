package commandFactory;
import Models.Hotel;


public abstract class Command {

    protected Hotel hotel;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public Hotel  getHotel()
    {
        return hotel;
    }

    public abstract void execute();
}