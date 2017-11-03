package server;

import common.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class Server implements OurObservable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int PORT = 6789;
    //    ToDo: verify if fileAdapter works as static
    private static FileAdapter fileAdapter = new FileAdapter();

    public static ArrayList<Connection> clientList;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientList = new ArrayList<>();
    }

    public synchronized static void createReservation(Reservation reservation) {
        System.out.println(reservation.toString());
        fileAdapter.createReservation("reservations.bin", reservation);

    }

    public synchronized static ArrayList<Reservation> getAll() {
        return fileAdapter.getAll("reservations.bin");
    }

    public synchronized static void updateReservation(Reservation old, Reservation newOne) {
//        System.out.println(old.toString());
//        System.out.println(newOne.toString());
        fileAdapter.updateReservation(old, newOne);
    }

    private void startServer() {
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
    public void updateAll(Reservation reservation) throws IOException {
        System.out.println("Sending updates");
        for (Connection item : clientList
                ) {
            new Thread(() -> {
                try {
                    System.out.println(item.toString());
                    item.getOutputStream().writeObject(new Response("create reservation", reservation));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void updateAll(Reservation old, Reservation newOne) throws IOException {
        Reservation[] res = {old, newOne};
        System.out.println("Sending updates");
        for (Connection item : clientList
                ) {
            new Thread(() -> {
                try {
                    item.getOutputStream().writeObject(new Response("update reservation", res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        Server newServer = new Server();
        newServer.startServer();
    }
}
