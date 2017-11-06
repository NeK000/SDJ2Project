package client;

import common.Reservation;

import java.util.ArrayList;
import java.util.Arrays;


//ToDO  :  the model should trigger an update to the GUI each time a update is made
public class Model {

    private Reservation[] reservations;
    private Reservation[] inHouse;
    private Reservation[] pastReservations;

    public Reservation getReservation(Reservation r) {
        for(Reservation res: reservations) {
            if(r.equals(res)) {
                return res;
            }
        }
        return null;
    }

    public void updateReservation(Reservation[] reservations) {
        Reservation old_ = reservations[0];
        Reservation new_ = reservations[1];

        for(int i = 0; i < this.reservations.length; i++) {
            if(this.reservations[i].equals(old_)) {
                this.reservations[i] = new_;
            }
        }
    }

//    public void checkIn(Reservation reservation) {
//        addFromReservationsToInHouse(reservation);
//    }

    public void checkIn(Reservation reservation) {

        ArrayList<Reservation> _reservations = new ArrayList<>();
        ArrayList<Reservation> _inHouse = new ArrayList<>();

        for(int i = 0; i < reservations.length; i++) {
            if(!reservations[i].equals(reservation)) {
                _reservations.add(reservations[i]);
            }
        }

        _inHouse.addAll(Arrays.asList(reservations));

        _inHouse.add(reservation);

        Reservation[] res = new Reservation[_reservations.size()];
        Reservation[] in = new Reservation[_inHouse.size()];

        res = _reservations.toArray(res);
        in = _inHouse.toArray(in);


        reservations = res;
        inHouse = in;

    }

    public Reservation[] getReservations() {
        return reservations;
    }

    public void setReservations(Reservation[] reservations) {
        this.reservations = reservations;
    }

    public Reservation[] getInHouse() {
        return inHouse;
    }

    public void setInHouse(Reservation[] inHouse) {
        this.inHouse = inHouse;
    }

    public Reservation[] getPastReservations() {
        return pastReservations;
    }

    public void setPastReservations(Reservation[] pastReservations) {
        this.pastReservations = pastReservations;
    }
}
