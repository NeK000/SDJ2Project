package client;

import client.GUI.MainGuiWindow;
import common.DateHandler;
import common.Price;
import common.Request;
import common.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HotelController {

    private Model model;
    private Client client;
    MainGuiWindow mg;

    public HotelController(MainGuiWindow mg) {
        this.mg = mg;
        model = new Model(mg);
        client = new Client("localhost", 6789);
    }


    public double getTotalPrice(Reservation r) {
        Calendar cal = new GregorianCalendar(r.getArrival().getCheckInDate().getYear(),
                r.getArrival().getCheckInDate().getMonth() - 1,
                r.getArrival().getCheckInDate().getDay());
        Calendar cal2 = new GregorianCalendar(r.getDeparture().getCheckOutDate().getYear(),
                r.getDeparture().getCheckOutDate().getMonth() - 1,
                r.getDeparture().getCheckOutDate().getDay());
        int total = cal2.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
        double totalPrice = new Price().getRoomPrice(r.getRoomType()) * total;
        return totalPrice;
    }

    /**
     * Create reservation method. Used to create reservation and save it in the system.
     *
     * @param reservation takes specific reservation.
     */
    public void createReservation(Reservation reservation) {
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

    public void checkIn(Reservation reservation, int roomNumber) {
        Reservation temp = reservation.copy();
        reservation.setRoomNumber(roomNumber);
        Reservation[] param = new Reservation[]{temp, reservation};
        client.sendRequest(new Request("checkin", param, true), model);

    }

    public void checkOut(Reservation r) {
        client.sendRequest(new Request("checkout", r), model);
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

    public ArrayList<Reservation> getReservationsForDateInterval(DateHandler arrival, DateHandler departure) {
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
        return temp;
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
