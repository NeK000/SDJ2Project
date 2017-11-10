package server;

import common.Reservation;
import common.Response;

import java.io.IOException;

public interface OurObserver {
    public void updateAll(Reservation old, Reservation newOne) throws IOException;

//    public void createResUpdate(Reservation reservation) throws IOException;

    public void updateOnInHouse(Reservation reservation) throws IOException;

    public void writeObject(Response response);

    public void update(Response asd);
}
