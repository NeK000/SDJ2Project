package server;

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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (newRequest.getType().equals("INSERT")) {
                System.out.println("Inserting into database");
                for (Runnable asd : Server.clientList) {
                    Connection c = (Connection) asd;
                    try {
                        c.getOutputStream().writeObject(new Response("Fuck you", null));
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
