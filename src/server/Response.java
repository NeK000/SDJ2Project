package server;

import java.io.Serializable;

public class Response implements Serializable {
    private String response;
    private Reservation parameter;
    private Reservation[] allParameters;

    public Response(String response, Reservation parameter) {
        this.response = response;
        this.parameter = parameter;
    }

    public String toString() {
        return response + " fags";
    }
}
