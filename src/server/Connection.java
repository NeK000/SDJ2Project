package server;

import common.Request;
import common.Reservation;
import common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable, OurObserver {
    private Server server;
    private Socket socket;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
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
                try {
                    Server.createReservation(newRequest.getParameter());
                    updateOne(newRequest.getParameter());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            if (newRequest.getType().equalsIgnoreCase("Update reservation")) {
                try {
                    Server.updateReservation(newRequest.getReservations()[0], newRequest.getReservations()[1]);
                    updateAll(newRequest.getReservations()[0], newRequest.getReservations()[1]);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }


    @Override
    public void updateAll(Reservation old, Reservation newOne) throws IOException {
        server.updateAll(old, newOne);
    }

    @Override
    public void updateOne(Reservation reservation) throws IOException {
        server.updateAll(reservation);
    }
}
