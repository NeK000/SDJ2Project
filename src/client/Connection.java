package client;

import common.FakeGuest;
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
                newRequest = (Request) inFromClient.readObject();
                System.out.println(newRequest);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            assert newRequest != null;
            if (newRequest.getType().equals("get all")) {
                FakeGuest f = new FakeGuest();
                Reservation[] r = new Reservation[2];
                r[0] = (f.makeNewReservation());
                r[1] = (f.makeNewReservation());
                System.out.println("Inserting into database");
                for (Runnable asd : Server.clientList) {
                    Connection c = (Connection) asd;
                    try {
                        c.getOutputStream().writeObject(new Response("allReservations", r));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public ObjectOutputStream getOutputStream() {
        return outToClient;
    }
}
