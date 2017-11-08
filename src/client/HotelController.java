package client;

import common.DateHandler;
import common.Price;
import common.Request;
import common.Reservation;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A class containing methods which later on will be used from our GUI.
 *
 * @author Nikolay D Nikolav, Yusuf A Farah, Radu G Orleanu, Catalin Udrea
 * @version 1.0
 */


public class HotelController implements Serializable {

    private Price price;
    private Model model;
    private Client client;

    public HotelController() {
        model = new Model();
        //"10.152.204.9"
        client = new Client("10.152.204.58", 6789);
    }
//    private common.Price price;
//    private FileAdapter fileAdapter;
//
//    /**
//     * No-argument constructor. Used to initialize client.HotelController.
//     */
//    public client.HotelController() {
//        fileAdapter = new FileAdapter();
//        this.price = new common.Price();
//    }
//
//    /**
//     * Check in method. Used to check in a reservation.
//     *
//     * @param reservation takes specific reservation.
//     * @param roomNumber  takes specific room number.
//     */
//    public void checkIn(common.Reservation reservation, int roomNumber) {
//        fileAdapter.removeSingleObjectFromFile("reservations.bin", reservation);
//        reservation.setRoomNumber(roomNumber);
//        fileAdapter.appendToFile("inHouseGuests.bin", reservation);
//    }
//
//    /**
//     * Check out method. Used to check out person.
//     *
//     * @param reservation takes specific reservation.
//     */
//
//    public void checkOut(common.Reservation reservation) {
//        fileAdapter.removeSingleObjectFromFile("inHouseGuests.bin", reservation);
//        fileAdapter.appendToFile("pastReservations.bin", reservation);
//    }
//
//
//    /**
//     * Total price method. Used to calculate the price upon check out.
//     *
//     * @param reservation takes specific reservation.
//     * @param discount    takes discount so calculates the price upon check out.
//     * @return price common.Price with the discount.
//     */
    public String getTotalPrice(common.Reservation reservation, double discount) {

        Calendar cal = new GregorianCalendar(reservation.getArrival().getCheckInDate().getYear(),
                reservation.getArrival().getCheckInDate().getMonth() - 1,
                reservation.getArrival().getCheckInDate().getDay());
        Calendar cal2 = new GregorianCalendar(reservation.getDeparture().getCheckOutDate().getYear(),
                reservation.getDeparture().getCheckOutDate().getMonth() - 1,
                reservation.getDeparture().getCheckOutDate().getDay());
        int total = cal2.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
        double totalPrice = price.getRoomPrice(reservation.getRoomType()) * total;
        if (discount == 0) {
            return String.valueOf(totalPrice);
        }
        double finalPrice = Math.round((discount * totalPrice) / 100);
        return String.valueOf(totalPrice - finalPrice);
    }

    /**
     * Create reservation method. Used to create reservation and save it in the system.
     *
     * @param reservation takes specific reservation.
     */
    public void createReservation(Reservation reservation) {


        // toDO: should call the client
        client.sendRequest(new Request("create reservation", reservation), model);
    }

    // used for reservations.bin
    public Reservation[] getAllReservations() {
        if (model.getReservations() == null) {
            Request r = new Request("get all", null);
            client.sendRequest(r, model);
        }

        return model.getReservations();
    }

    // used for inHouse.bin
    public Reservation[] getInHouse() {
        if (model.getInHouse() == null) {
            Request r = new Request("inhouse", null);
            client.sendRequest(r, model);
        }

        return model.getInHouse();
    }

    // used for pastReservations.bin
    public Reservation[] getPastReservations() {
        if (model.getPastReservations() == null) {
            Request r = new Request("past", null);
            client.sendRequest(r, model);
        }

        return model.getPastReservations();
    }

    public void checkIn(Reservation reservation) {
        client.sendRequest(new Request("checkin", reservation), model);
    }

    public Reservation getReservation(Reservation r) {
        return model.getReservation(r);
    }

    public void updateReservation(Reservation old_, Reservation new_) {
        Reservation[] param = new Reservation[]{old_, new_};
        Request req = new Request("update reservation", param, true);
        client.sendRequest(req, model);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Availability method. Used to check availability between dates.
     * <p>
     * //     * @param arrival   takes specific date for arraving.
     * //     * @param departure takes specific date for departure.
     *
     * @return String, returns specific numbers of available rooms by room type.
     */
    public String getAvailabilityFromDateInterval(DateHandler arrival, DateHandler departure) {
        int countSingleBedroomSuite = 0;
        int countTwoBedroomSuite = 0;
        int countThreeBedroomSuite = 0;
        int singleRoom = 0;
        int twinRoom = 0;
        int kingSizeRoom = 0;

        ArrayList<Reservation> temp = new ArrayList<>();
        ArrayList<Reservation> compare = new ArrayList<>();
        Reservation[] compareFutureReservations = model.getReservations();
        Reservation[] compareInHouseReservations = model.getInHouse();
        compare.addAll(Arrays.asList(compareFutureReservations));
        compare.addAll(Arrays.asList(compareInHouseReservations));

        for (int i = 0; i < compare.size(); i++) {
            if (!(compare.get(i).getDeparture().getCheckOutDate().isBefore(arrival))
                    && (compare.get(i).getArrival().getCheckInDate().isBefore(departure))) {
                temp.add(compare.get(i));
            }
        }

        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getRoomType().equals("single bedroom suite")) {
                countSingleBedroomSuite++;
            }
            if (temp.get(i).getRoomType().equals("double bedroom suite")) {
                countTwoBedroomSuite++;
            }
            if (temp.get(i).getRoomType().equals("three bedroom suite")) {
                countThreeBedroomSuite++;
            }
            if (temp.get(i).getRoomType().equals("single room")) {
                singleRoom++;
            }
            if (temp.get(i).getRoomType().equals("double room-twin beds")) {
                twinRoom++;
            }
            if (temp.get(i).getRoomType().equals("double room-kingsize")) {
                kingSizeRoom++;
            }
        }

        if (countSingleBedroomSuite <= 2) {
            countSingleBedroomSuite = 2 - countSingleBedroomSuite;
        } else {
            countSingleBedroomSuite = 0;
        }

        if (countTwoBedroomSuite <= 1) {
            countTwoBedroomSuite = 1 - countTwoBedroomSuite;
        } else {
            countTwoBedroomSuite = 0;
        }


        if (countThreeBedroomSuite <= 1) {
            countThreeBedroomSuite = 1 - countThreeBedroomSuite;
        } else {
            countThreeBedroomSuite = 0;
        }

        if (singleRoom <= 10) {
            singleRoom = 10 - singleRoom;
        } else {
            singleRoom = 0;
        }

        if (twinRoom <= 6) {
            twinRoom = 6 - twinRoom;
        } else {
            twinRoom = 0;
        }

        if (kingSizeRoom <= 22) {
            kingSizeRoom = 22 - kingSizeRoom;
        } else {
            kingSizeRoom = 0;
        }

        String str = "Single room: " + singleRoom +
                "\nDouble room-twin bed: " + twinRoom + "\nDouble room-kingsize bed: "
                + kingSizeRoom + "\nSingle suite: " + countSingleBedroomSuite + "\nDouble suite: "
                + countTwoBedroomSuite + "\nTriple Suite: " + countThreeBedroomSuite;
        return str;
    }

    public ArrayList<Reservation> getArrivalsForToday() {
        Reservation[] temp = model.getReservations();
        ArrayList<Reservation> forReturn = new ArrayList<>();
        DateHandler dateHandler = new DateHandler(1, 1, 1);
        for (Reservation item : temp
                ) {
            if (item.getArrival().getCheckInDate().equals(dateHandler.currentDate())) {
                forReturn.add(item);
            }
        }
        return forReturn;
    }

    public ArrayList<Reservation> getDeparturesForToday() {
        Reservation[] temp = model.getInHouse();
        ArrayList<Reservation> forReturn = new ArrayList<>();
        DateHandler dateHandler = new DateHandler(1, 1, 1);
        for (Reservation item : temp
                ) {
            if (item.getDeparture().getCheckOutDate().equals(dateHandler.currentDate())) {
                forReturn.add(item);
            }
        }
        return forReturn;
    }
}
