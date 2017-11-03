package server;

import common.Reservation;

import java.io.IOException;

public interface OurObserver {
    public void updateAll(Reservation old, Reservation newOne) throws IOException;

    public void updateOne(Reservation reservation) throws IOException;
}
