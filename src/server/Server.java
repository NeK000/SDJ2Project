package server;

import common.Reservation;
import common.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements OurObservable {
    private ServerSocket serverSocket;
    private final int PORT = 6789;
    //    ToDo: verify if fileAdapter works as static
    private static IFileAdapter fileAdapter;

    public static ArrayList<OurObserver> clientList;

    public Server() {
        fileAdapter = new FileAdapter("reservations.bin", "pastReservations.bin", "inHouseGuests.bin");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientList = new ArrayList<>();
    }

    public synchronized void createReservation(Reservation reservation) {
        System.out.println(reservation.toString());
        fileAdapter.createReservation(reservation);
        try {
            updateAll(new Response("create reservation", reservation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static ArrayList<Reservation> getAll() {
        return fileAdapter.getAll();
    }

    public synchronized static void updateReservation(Reservation old, Reservation newOne) {
        fileAdapter.updateReservation(old, newOne);
    }

    public void startServer() {
        System.out.println("Server starting ...");
        while (true) {
            try {
                Connection newClient = new Connection(serverSocket.accept(), this);
                addObserver(newClient);
                new Thread(newClient).start();
                System.out.println("Client connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void addObserver(Connection addClient) {
        clientList.add(addClient);
    }


    @Override
    public void updateAll(Response resp) throws IOException {
        for (OurObserver item : clientList) {
            item.update(resp);
        }
    }

    @Override
    public void updateAll(Reservation old, Reservation newOne) throws IOException {
        Reservation[] res = {old, newOne};
        System.out.println("Sending updates");
        updateAll(new Response("update reservation", res));
    }

    @Override
    public synchronized void addToInHouse(Reservation reservation, Reservation newForCheckIn) throws IOException {
        fileAdapter.checkIn(reservation, newForCheckIn);
        Reservation[] reservations = {reservation, newForCheckIn};
        updateAll(new Response("checkin", reservations));
    }

    @Override
    public void addToPastReservations(Reservation reservation) throws IOException {
        fileAdapter.checkOut(reservation);
        updateAll(new Response("checkout", reservation));
    }

    public static ArrayList<Reservation> getAllInHouseGuests() {
        return fileAdapter.getInHouseGuests();
    }

    public static ArrayList<Reservation> getPastReservations() {
        return fileAdapter.getPast();
    }


}
