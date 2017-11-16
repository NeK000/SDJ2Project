package server;


import common.Reservation;
import common.Response;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Observer;

public interface OurObservable {
    public void addObserver(OurObserver addClient);

    public void removeObserver(OurObserver oldClient);

    public void updateAll(Response resp) throws IOException;

}
