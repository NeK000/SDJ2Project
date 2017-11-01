package server;

import common.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int PORT = 6789;
    //    ToDo: verify if fileAdapter works as static
    private static FileAdapter fileAdapter = new FileAdapter();

    public static ArrayList<Runnable> clientList;

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

    public void startServer() {
        System.out.println("Server starting ...");
        while (true) {
            try {
                Connection newClient = new Connection(serverSocket.accept());
                clientList.add(newClient);
                new Thread(newClient).start();
                System.out.println("Client connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server newServer = new Server();
        newServer.startServer();
    }
}
