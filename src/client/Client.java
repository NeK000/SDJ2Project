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
            System.out.println("Connecting ...");
            clientSocket = new Socket(url, port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("              Connected");
    }

    public void sendRequest(Request request) {
        try {
            System.out.println("Sending....");
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            out.writeObject(request);
            System.out.println("Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveResponse() {
        Response response;

        try {
            while (true) {
                in = new ObjectInputStream(clientSocket.getInputStream());
                response = (Response) in.readObject();
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        Request request = new Request("INSERT", new Reservation());
        Client c = new Client("10.152.204.38", 6789);
        c.sendRequest(request);
        c.receiveResponse();
    }
}