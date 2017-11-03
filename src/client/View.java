package client;

import common.FakeGuest;
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
        controller.createReservation(r);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        controller.getAllReservations();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
        common.Reservation[] all = controller.getAllReservations();

        System.out.println("we got - > " + Arrays.toString(all));
    }
}
