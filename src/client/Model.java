package client;

import common.Reservation;

public class Model {

    private Reservation[] allReservations;

    public Reservation[] getAllReservations() {
        return allReservations;
    }

    public void setAllReservations(Reservation[] allReservations) {
        this.allReservations = allReservations;
    }
}
