package server;

import common.Reservation;

import java.util.ArrayList;

public interface IFileAdapter {

    ArrayList<Reservation> getAll();

    void createReservation(Reservation reservation);

    void updateReservation(Reservation old, Reservation newOne);

    void checkIn(Reservation old);

    void checkOut(Reservation old);

    ArrayList<Reservation> getInHouseGuests();

    ArrayList<Reservation> getPast();
}
