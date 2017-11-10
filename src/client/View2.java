//package client;
//
//import common.FakeGuest;
//import common.Guest;
//import common.Name;
//import common.Reservation;
//
//import java.util.Arrays;
//
//public class View2 {
//
//    private HotelController controller;
//
//    public View2() {
//        controller = new HotelController();
//    }
//
//    public static void main(String[] args) {
//        View2 v = new View2();
//        v.getAllReservations();
//    }
//
//    public void getAllReservations() {
//
//        FakeGuest f = new FakeGuest();
////
//        controller.getAllReservations();
//        wait_(1);
//        controller.getInHouse();
////
//        wait_(1);
//////
//
////        common.Reservation[] inHouse = controller.getInHouse();
////
//        Reservation r1 = f.makeNewReservation();
//        Reservation r2 = f.makeNewReservation();
//        Reservation r3 = f.makeNewReservation();
//
//        Guest g = r1.getGuest();
//        g.setName(new Name("Haroldas","",""));
//
//        controller.createReservation(r1);
//        wait_(1);
//        controller.createReservation(r2);
//        wait_(1);
//        controller.createReservation(r3);
//        wait_(1);
//
//        common.Reservation[] allReservations = controller.getAllReservations();
//        System.out.println("Reservations: ");
//        System.out.println(Arrays.toString(allReservations));
//
//        controller.checkIn(r1);
//
//        common.Reservation[] inHouse = controller.getInHouse();
//        System.out.println("inHouse: ");
//        System.out.println(Arrays.toString(inHouse));
//
//        wait_(1);
//
//        allReservations = controller.getInHouse();
//        System.out.println("Reservations after checkIn: ");
//        System.out.println(Arrays.toString(allReservations));
//
//
//
//    }
//
//    public static void wait_(int seconds) {
//        try {
//            Thread.sleep(1000 * seconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
