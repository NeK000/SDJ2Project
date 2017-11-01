package server;

import common.Request;
import common.Reservation;
import common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket socket;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            inFromClient = new ObjectInputStream(socket.getInputStream());
            outToClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            Request newRequest = null;
            try {
                Object temp = inFromClient.readObject();
                newRequest = (Request) temp;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

//            if (newRequest.getType().equals("INSERT")) {
//                System.out.println("Inserting into database");
//                for (Runnable asd : Server.clientList) {
//                    Connection c = (Connection) asd;
//                    try {
//                        c.getOutputStream().writeObject(new Response("Fuck you", null));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            if (newRequest.getType().equalsIgnoreCase("create reservation")) {
                Server.createReservation(newRequest.getParameter());
            }
            if (newRequest.getType().equalsIgnoreCase("get all")) {
                try {
                    Reservation[] temp = new Reservation[Server.getAll().size()];
                    temp = Server.getAll().toArray(temp);
                    getOutputStream().writeObject(new Response("ALLRESERVATIONS", temp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }
}
