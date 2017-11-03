package client;

import common.FakeGuest;
import common.Guest;
import common.Name;
import common.Reservation;

import java.util.Arrays;

public class View2 {

    private HotelController controller;

    public View2() {
        controller = new HotelController();
    }

    public static void main(String[] args) {
        View2 v = new View2();
        v.getAllReservations();
    }

    public void getAllReservations() {
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
        System.out.println("we got - > " + Arrays.toString(all));


    }
}
