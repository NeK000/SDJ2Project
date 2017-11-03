package common;

import java.io.Serializable;

public class Request implements Serializable {
    private String request;
    private Reservation parameter;
    private Reservation[] reservations;

    public Request(String request, Reservation reservation) {
        this.request = request;
        this.parameter = reservation;
    }

    public Request(String request, Reservation[] reservations) {
        this.request = request;
        this.reservations = reservations;
    }

    public String getType() {
        return request;
    }

    public Reservation getParameter() {
        return parameter;
    }

    public Reservation[] getReservations() {
        return reservations;
    }

    public void setReservations(Reservation[] reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "common.Request{" +
                "request='" + request + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
