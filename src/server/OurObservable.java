package server;


import common.Reservation;
import common.Response;

import java.io.IOException;

public interface OurObservable {
    public void addObserver(Connection addClient);

    public void updateAll(Response resp) throws IOException;

    public void updateAll(Reservation old, Reservation newOne) throws IOException;

    public void addToInHouse(Reservation reservation, Reservation newForCheckIn) throws IOException;

    public void addToPastReservations(Reservation reservation) throws IOException;

}
