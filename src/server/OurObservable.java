package server;


import common.Reservation;

import java.io.IOException;

public interface OurObservable {
    public void addObserver(Connection addClient);

    public void updateAll(Reservation reservation) throws IOException;

    public void updateAll(Reservation old, Reservation newOne) throws IOException;

}
