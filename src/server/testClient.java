package server;

import common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class testClient {
    private Socket socket;
    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;

    public testClient() {
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 6789);
            System.out.println(1);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(3);
            inFromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println(2);

            Thread.sleep(1000);
            Reservation first = new Reservation(new Guest(new Name("steven", "george", "someGuy"), 1234278901, new Address("Romania", "SomeCity", "8700", "someStreet"), "Romanian",
                ("31, 10, 1988")), new Arrival(new DateHandler(01, 11, 2017)), new Departure(new DateHandler(02, 11, 2017)), "king sized", true, true,
                true);
            outToServer.writeObject(new Request("create reservation", first));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testClient tc = new testClient();

    }


}
