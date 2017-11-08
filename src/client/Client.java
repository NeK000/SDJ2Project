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
        try {
            System.out.println("Sending...." + request);
            if (out == null) {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            }

            out.writeObject(request);
            System.out.println("Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (in == null) {
            new Thread(() -> {
//

                // TODO MODEL IS PASSED AS NULL -- solved


                try {
                    in = new ObjectInputStream(clientSocket.getInputStream());
                    while (true) {
                        Response response;
                        response = (Response) in.readObject();

                        System.out.println("response from server: " + response.getResponse());

                        if (response.getResponse().toLowerCase().equals("update reservation")) {
                            model.updateReservation(response.getAllParameters());
                        }

                        if (response.getResponse().toLowerCase().equals("allreservations")) {
                            System.out.println("received from server " + response);
                            model.setReservations(response.getAllParameters());
                        }

                        if(response.getResponse().toLowerCase().equals("inhouse")) {
                            System.out.println("received from server for inhouse " + response);
                            model.setInHouse(response.getAllParameters());
                        }

                        if(response.getResponse().toLowerCase().equals("past")) {
                            System.out.println("received from server " + response);
                            model.setPastReservations(response.getAllParameters());
                        }

                        if(response.getResponse().toLowerCase().equals("checkin")) {
                            System.out.println("received from server " + response);
                            model.checkIn(response.getParameter());

                        }

                        if(response.getResponse().toLowerCase().equals("checkout")) {
                            System.out.println("received from server " + response);
                            model.checkOut(response.getParameter());

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
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
}