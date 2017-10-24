package server;

import java.io.Serializable;

public class Request implements Serializable {
    private String request;
    private Reservation parameter;

    public Request(String request, Reservation reservation) {
        this.request = request;
        this.parameter = reservation;
    }

    public String getType() {
        return request;
    }

    public Reservation getParameter() {
        return parameter;
    }
}
