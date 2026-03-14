package org.example;

import java.io.BufferedReader;  // efficient way to read file line by line
// it's better than FileReader which enables reading only sign by sign
import java.io.FileReader;
import java.io.IOException; // error handling (System out, System in)
import java.util.ArrayList;
import Models.Room;
import map.MyMap;
import Models.Reservation;

/**
 * The CsvReader class is responsible for loading hotel data from a CSV file
 * and converting it into objects used by the hotel management system.
 *
 * Main responsibilities:
 * - Reads the file line by line using BufferedReader for efficiency.
 * - Extracts hotel configuration (number of floors and rooms per floor) from
 *   the first line of the file.
 * - Processes subsequent lines to build Room objects and their associated
 *   Reservation records.
 * - Populates a custom MyMap structure with room numbers as keys and Room
 *   objects as values.
 * - Marks rooms as taken or available depending on reservation data.
 * - Handles file I/O exceptions gracefully by printing error messages.
 *
 * This class provides getter methods to access:
 * - Hotel information (floors and rooms per floor).
 * - The complete map of rooms with their reservations.
 *
 * Overall, CsvReader acts as a bridge between external CSV data and the
 * internal hotel management system, ensuring that the application starts
 * with accurate and structured information about rooms and reservations.
 */


public class CsvReader {

    private String[] hotel_info =  new String[0];
    MyMap<String, Room> roomMap = new MyMap<>();


    public CsvReader(String filePath) {

        ArrayList<String[]> elements = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) // wraps our object in buffor
        {
            String line;  // our temporary line to read

            line = reader.readLine(); // first line is about hotel info (number of floors and number of rooms on floor)
            hotel_info = line.split(",");


            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String[] temp = new String[parts.length];
                for (int i=0; i<parts.length; i++)
                {
                    temp[i] = parts[i];
                }
                elements.add(temp);
            }

            for (String[] data : elements)
            {
                ArrayList<String> guests = new ArrayList<>();
                for(int i =  7; i < data.length; i++)
                {
                    guests.add(data[i]);
                }

                Room r = roomMap.get(data[0]);
                if (r == null) {
                    r = new Room(data[1],Float.parseFloat(data[2]), Integer.parseInt(data[6]));
                    roomMap.put(data[0], r);
                }

                Reservation reservation = new Reservation(data[3], guests, data[4], data[5]);

                if ( !data[3].equals("null"))
                {
                    roomMap.get(data[0]).setTaken(true);
                }
                else {
                    roomMap.get(data[0]).setTaken(false);
                }

                roomMap.get(data[0]).addReservation(reservation);
            }


        } catch (IOException e) {
            System.out.println("We failed to read the file");
            System.out.println("Error: " + e.getMessage());
        }
    }
    public String[] get_hotel_info()
    {
        return hotel_info;   // how many floors and rooms on each floor
    }
    public  MyMap<String, Room> get_map()
    {
        return roomMap;
    }

}
