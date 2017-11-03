package client;

import common.Reservation;

public class Model {

    private Reservation[] allReservations;

    public Reservation getReservation(Reservation r) {
        for(Reservation res: allReservations) {
            if(r.equals(res)) {
                return res;
            }
        }
        return null;
    }

    public void updateReservation(Reservation[] reservations) {
        Reservation old_ = reservations[0];
        Reservation new_ = reservations[1];

        for(int i = 0; i < allReservations.length; i++) {
            if(allReservations[i].equals(old_)) {
                allReservations[i] = new_;
            }
        }
    }

    public Reservation[] getAllReservations() {
        return allReservations;
    }

    public void setAllReservations(Reservation[] allReservations) {
        this.allReservations = allReservations;
    }
}
