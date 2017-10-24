package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int PORT = 6789;
    public static ArrayList<Runnable> clientList;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientList = new ArrayList<>();
    }

    public void startServer() {
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


}
