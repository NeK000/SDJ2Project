package client;

import common.DateHandler;
import common.Reservation;

import java.util.ArrayList;
import java.util.Arrays;


//ToDO  :  the model should trigger an update to the GUI each time a update is made
public class Model {

    private Reservation[] reservations;
    private Reservation[] inHouse;
    private Reservation[] pastReservations;

    public Reservation getReservation(Reservation r) {
        for (Reservation res : reservations) {
            if (r.equals(res)) {
                return res;
            }
        }
        return null;
    }

    private void replace(Reservation[] arr, Reservation old_, Reservation new_) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].equals(old_)) {
                arr[i] = new_;
                break;
            }
        }
    }

    public void updateReservation(Reservation[] reservations_) {
        Reservation old_ = reservations_[0];
        Reservation new_ = reservations_[1];

        if(indexOf(reservations, old_) >= 0) {
            replace(reservations, old_, new_);
        }

        if(indexOf(inHouse, old_) >= 0) {
            replace(reservations, old_, new_);
        }

        if(indexOf(pastReservations, old_) >= 0) {
            replace(reservations, old_, new_);
        }

//        for (int i = 0; i < this.reservations.length; i++) {
//            if (this.reservations[i].equals(old_)) {
//                this.reservations[i] = new_;
//            }
//        }
    }

//    public void checkIn(Reservation reservation) {
//        addFromReservationsToInHouse(reservation);
//    }

    public void checkIn(Reservation reservation) {

//        ArrayList<Reservation> _reservations = new ArrayList<>();
//        ArrayList<Reservation> _inHouse = new ArrayList<>();
//
//        for (int i = 0; i < reservations.length; i++) {
//            if (!reservations[i].equals(reservation)) {
//                _reservations.add(reservations[i]);
//            }
//        }
//
//        _inHouse.addAll(Arrays.asList(reservations));
//
//        _inHouse.add(reservation);
//
//        Reservation[] res = new Reservation[_reservations.size()];
//        Reservation[] in = new Reservation[_inHouse.size()];
//
//        res = _reservations.toArray(res);
//        in = _inHouse.toArray(in);
//
//
//        reservations = res;
//        inHouse = in;
        removeReservation(reservations, reservation);
        addToArray(inHouse, reservation);

    }

    public void checkOut(Reservation reservation) {
        for (Reservation r : inHouse) {
            if (r.equals(reservation))
                checkOutUpdate(reservation);
        }
    }

    public void checkOutUpdate(Reservation r) {

        removeReservation(inHouse, r);
        addToArray(pastReservations, r);
    }

    public ArrayList<Reservation> getDeparturesForToday() {
        ArrayList<Reservation> departures = new ArrayList<>();
        DateHandler d = new DateHandler(1, 1, 1);
        for (Reservation r: inHouse) {
            if (r.getDeparture().getCheckOutDate().equals(d.currentDate())) {
                departures.add(r);
            }
        }
        return departures;
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

    public void removeReservation(Reservation[] arr, Reservation r) {
        Reservation[] collect = new Reservation[arr.length - 1];

        int index = indexOf(arr, r);
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (i != index) {
                collect[count] = arr[i];
                count++;

            }

        }

        arr = collect;

    }

    public int indexOf(Reservation[] arr, Reservation r) {

        for (int i = 0; i < arr.length; i++) {
            if (r.equals(arr[i]))
                return i;

        }

        return -1;
    }

    public void addToArray(Reservation[] arr, Reservation r) {
        Reservation[] a = new Reservation[arr.length + 1];
        for(int i = 0; i < arr.length; i++) {
            a[i] = arr[i];
        }
        a[a.length - 1] = r;
        arr = a;
    }

}
