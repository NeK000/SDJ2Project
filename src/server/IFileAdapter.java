package server;

import common.Reservation;

import java.util.ArrayList;

public interface IFileAdapter {


    void writeToFileObj(String fileName, Object object);

    Object readFromFileObj(String fileName);
    
    ArrayList<Reservation> getAll(String fileName);

    void createReservation(String fileName, Reservation reservation);

    void removeSingleObjectFromFile(String fileName, Reservation reservation);

    void updateReservation(Reservation old, Reservation newOne);

    void checkIn(Reservation old);

    void checkOut(Reservation old);

    ArrayList<Reservation> getInHouseGuests();

    ArrayList<Reservation> getPast();
}
