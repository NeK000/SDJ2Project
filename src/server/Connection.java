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
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public Connection(Socket socket, Server server) {
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
            } catch (Exception e) {
                server.removeObserver(this);
            }
            if (newRequest.getType().equalsIgnoreCase("create reservation")) {
                server.createReservation(newRequest.getParameter());

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
                    server.updateReservation(newRequest.getReservations()[0], newRequest.getReservations()[1]);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (newRequest.getType().equalsIgnoreCase("checkin")) {
                try {
                    updateOnInHouse(newRequest.getReservations()[0], newRequest.getReservations()[1]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (newRequest.getType().equalsIgnoreCase("checkout")) {
                try {
                    server.addToPastReservations(newRequest.getParameter());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (newRequest.getType().equalsIgnoreCase("inhouse")) {
                try {
                    Reservation[] temp = new Reservation[Server.getAllInHouseGuests().size()];
                    temp = Server.getAllInHouseGuests().toArray(temp);
                    getOutputStream().writeObject(new Response("inhouse", temp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (newRequest.getType().equalsIgnoreCase("past")) {
                try {
                    Reservation[] temp = new Reservation[Server.getPastReservations().size()];
                    temp = Server.getPastReservations().toArray(temp);
                    getOutputStream().writeObject(new Response("past", temp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }

    public void updateOnInHouse(Reservation reservation, Reservation forCheckIn) throws IOException {
        server.addToInHouse(reservation, forCheckIn);
    }

    private OurObserver getMe() {
        return this;
    }

    public void writeObject(Response response) {
        new Thread(() -> {
            try {
                getOutputStream().writeObject(response);
            } catch (IOException e) {
                server.removeObserver(getMe());
            }

        }).start();
    }

    @Override
    public void update(Response asd) {
        writeObject(asd);
    }
}
