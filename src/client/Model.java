package client;

import client.GUI.MainGuiWindow;
import client.linkedList.LinkedList;
import common.DateHandler;
import common.Reservation;

import java.util.ArrayList;


//ToDO  :  the model should trigger an update to the GUI each time a update is made
public class Model {

    private LinkedList<Reservation> reservations;
    private LinkedList<Reservation> inHouse;
    private LinkedList<Reservation> pastReservations;
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

    public void createReservation(Reservation reservation) {
        reservations.add(reservation);
        mainGuiWindow.refresh();
    }

    public void updateReservation(Reservation[] reservations_) {

        Reservation old_ = reservations_[0];
        Reservation new_ = reservations_[1];

        System.out.println("updating: " + old_.getGuest() + " -> " + new_.getGuest());

        if (reservations.contains(old_)) {
            reservations.remove(old_);
            reservations.add(new_);
        }

        if (inHouse.contains(old_)) {
            inHouse.remove(old_);
            inHouse.add(new_);
        }

        if (pastReservations.contains(old_)) {
            pastReservations.remove(old_);
            pastReservations.add(new_);
        }
        mainGuiWindow.refresh();
    }

    public void checkIn(Reservation[] reservation) {
        reservations.remove(reservation[0]);
        inHouse.add(reservation[1]);
        mainGuiWindow.refresh();
    }

    public void checkOut(Reservation reservation) {
        inHouse.remove(reservation);
        pastReservations.add(reservation);
        mainGuiWindow.refresh();
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
        return listToArr(reservations);
    }

    public void setReservations(Reservation[] reservations) {
        this.reservations = new LinkedList<>();
        for (Reservation r : reservations) {
            this.reservations.add(r);
        }
    }

    public Reservation[] getInHouse() {
        return listToArr(inHouse);
    }

    public void setInHouse(Reservation[] inHouse) {
        this.inHouse = new LinkedList<>();
        for (Reservation r : inHouse) {
            this.inHouse.add(r);
        }
    }

    public Reservation[] getPastReservations() {
        return listToArr(pastReservations);
    }

    public void setPastReservations(Reservation[] pastReservations) {
        this.pastReservations = new LinkedList<>();
        for (Reservation r : pastReservations) {
            this.pastReservations.add(r);
        }
    }

    private Reservation[] listToArr(LinkedList<Reservation> list) {
        if (list == null) {
            return null;
        }
        Reservation[] arr = new Reservation[list.size()];
        int count = 0;
        for (Reservation r : list) {
            arr[count] = r;
            count++;
        }
        return arr;
    }

}
