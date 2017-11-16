package server;
import common.Response;

import java.io.IOException;

public interface OurObservable {
    void addObserver(OurObserver addClient);

    void removeObserver(OurObserver oldClient);

    void updateAll(Response resp) throws IOException;

}
