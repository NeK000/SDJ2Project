package client;


import server.Request;
import server.Reservation;
import server.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client implements Serializable {

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(String url, int port) {
        try {
            clientSocket = new Socket(url, port);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendRequest(Request request) {
        try {
            out.writeObject(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveResponse() {
        Response response;

        try {
            while (true) {

                response = (Response) in.readObject();
                System.out.println("response");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        Request request = new Request("bla", new Reservation());
        Client c = new Client("10.152.204.38", 6789);
        c.sendRequest(request);
        c.receiveResponse();
    }
}