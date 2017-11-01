package client;

import common.Request;
import common.Response;

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

    public void sendRequest(Request request, Model model) {
        System.out.println("sendRequest called");


        try {
            System.out.println("Sending...." + request);
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            out.writeObject(request);
            System.out.println("Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                Response response;
                in = new ObjectInputStream(clientSocket.getInputStream());
                response = (Response) in.readObject();
                if (response.getResponse().equals("ALLRESERVATIONS")) {
                    model.setAllReservations(response.getAllParameters());
                }

                // ToDO: keep the connection opened or it will throw an error on the client
                Thread.sleep(10000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
}