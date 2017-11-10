//package client;
//
//import common.Reservation;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class HotelControllerTest {
//    HotelController h;
//
//    @Before
//    public void setup() {
//        h = new HotelController();
//    }
//
//    @Test
//    public void testController() {
//
//        h.getAllReservations();
//
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Reservation[] r = h.getAllReservations();
//
//        assertTrue(r.length >= 1);
//    }
//
//}