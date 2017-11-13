package client;

import common.DateHandler;
import common.Reservation;

import java.util.ArrayList;


//ToDO  :  the model should trigger an update to the GUI each time a update is made
public class Model {

    private Reservation[] reservations;
    private Reservation[] inHouse;
    private Reservation[] pastReservations;
    private MainGuiWindow mainGuiWindow;

    public Model(MainGuiWindow mg) {
        this.mainGuiWindow = mg;
    }

    public Reservation getReservation(Reservation r) {
        for (Reservation res : reservations) {
            if (r.equals(res)) {
                return res;
            }
        }
        return null;
    }

    private void replace(Reservation[] arr, Reservation old_, Reservation new_) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(old_)) {
                arr[i] = new_;
                break;
            }
        }
    }

    public void createReservation(Reservation reservation) {
        addToArray(reservations, reservation, "reservations");
        mainGuiWindow.refresh();
    }

    public void updateReservation(Reservation[] reservations_) {

        Reservation old_ = reservations_[0];
        Reservation new_ = reservations_[1];

        System.out.println("updating: " + old_.getGuest() + " -> " + new_.getGuest());

        if (indexOf(reservations, old_) >= 0) {
            replace(reservations, old_, new_);
        }

        if (indexOf(inHouse, old_) >= 0) {
            replace(reservations, old_, new_);
        }

        if (indexOf(pastReservations, old_) >= 0) {
            replace(reservations, old_, new_);
        }
        mainGuiWindow.refresh();
    }

    public void checkIn(Reservation reservation) {
        removeReservation(reservations, reservation, "reservations");
        addToArray(inHouse, reservation, "inHouse");
        mainGuiWindow.refresh();
    }

    public void checkOut(Reservation reservation) {
        for (Reservation r : inHouse) {
            if (r.equals(reservation))
                checkOutUpdate(reservation);
        }
        mainGuiWindow.refresh();
    }

    public void checkOutUpdate(Reservation r) {

        removeReservation(inHouse, r, "inHouse");
        addToArray(pastReservations, r, "pastReservations");
    }

    public ArrayList<Reservation> getDeparturesForToday() {
        ArrayList<Reservation> departures = new ArrayList<>();
        DateHandler d = new DateHandler(1, 1, 1);
        for (Reservation r : inHouse) {
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

    public void removeReservation(Reservation[] arr, Reservation r, String name) {
        Reservation[] collect = new Reservation[arr.length - 1];
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals(r)) {
                collect[count] = arr[i];
                count++;
            }
        }

        if (name.equals("inHouse")) {
            inHouse = collect;
        } else if (name.equals("pastReservations")) {
            pastReservations = collect;
        } else {
            reservations = collect;
        }

    }

    public int indexOf(Reservation[] arr, Reservation r) {

        for (int i = 0; i < arr.length; i++) {
            if (r.equals(arr[i]))
                return i;

        }

        return -1;
    }

    public void addToArray(Reservation[] arr, Reservation r, String name) {
        Reservation[] a = new Reservation[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            a[i] = arr[i];
        }
        a[a.length - 1] = r;
        if (name.equals("inHouse")) {
            inHouse = a;
        } else if (name.equals("pastReservations")) {
            pastReservations = a;
        } else {
            reservations = a;
        }
    }

}
