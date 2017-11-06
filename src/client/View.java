package client;

import common.FakeGuest;
import common.Guest;
import common.Name;
import common.Reservation;

import java.util.Arrays;

public class View {

    private HotelController controller;

    public View() {
        controller = new HotelController();
    }

    public static void main(String[] args) {
        View v = new View();
        v.getAllReservations();
    }

    public void getAllReservations() {

        FakeGuest f = new FakeGuest();
        Reservation r = f.makeNewReservation();

        Reservation r2 = f.makeNewReservation();

        controller.createReservation(r);
//
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        controller.createReservation(r2);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
        controller.getAllReservations();
//
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
////
        common.Reservation[] all = controller.getAllReservations();
//
        Reservation res = all[0];
        Reservation resOriginal = f.makeNewReservation();


        Guest g = res.getGuest();
        g.setName(new Name("Mina", "", ""));

        res.setGuest(g);

        System.out.println("sending original and new " + resOriginal.getGuest().getName() + " " + res.getGuest().getName());

        controller.updateReservation(res, resOriginal);


//
        System.out.println("we got - > " + Arrays.toString(all));


    }
}
