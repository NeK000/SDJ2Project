package server;

import common.Reservation;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class FileAdapter implements Serializable, IFileAdapter {
    private MyFileIO fileIO = new MyFileIO();
    private String currentReservations;
    private String pastReservations;
    private String inHouseReservations;

    public FileAdapter(String reservations, String pastReservations, String inHouseReservations) {
        this.currentReservations = reservations;
        this.pastReservations = pastReservations;
        this.inHouseReservations = inHouseReservations;
    }

    @Override
    public ArrayList<Reservation> getAll() {
        return getAllFromFile(currentReservations);
    }

    @Override
    public void createReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        Object[] read = null;
        try {
            read = fileIO.readArrayFromFile(currentReservations);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object[] newValues = new Object[read.length + 1];
        for (int i = 0; i < read.length; i++) {
            newValues[i] = read[i];
        }
        newValues[newValues.length - 1] = reservation;
        try {
            fileIO.writeToFile(currentReservations, newValues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateReservation(Reservation old, Reservation newOne) {
        if (old == null || newOne == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        boolean check = false;
        ArrayList<Reservation> current = getAll();
        ArrayList<Reservation> inHouse = getInHouseGuests();
        for (Reservation item : current
                ) {
            if (item.equals(old)) {
                removeSingleObjectFromFile(currentReservations, old);
                appendToFile(currentReservations, newOne);
                check = true;
            }
        }
        if (!check) {
            for (Reservation item : inHouse
                    ) {
                if (item.equals(old))
                    removeSingleObjectFromFile(inHouseReservations, old);
                appendToFile(inHouseReservations, newOne);
            }
        }
    }


    @Override
    public void checkIn(Reservation old, Reservation newOne) {
        if (old == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        appendToFile(inHouseReservations, newOne);
        System.out.println(newOne.getRoomNumber());
        removeSingleObjectFromFile(currentReservations, old);
    }

    @Override
    public void checkOut(Reservation old) {
        if (old == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        removeSingleObjectFromFile(inHouseReservations, old);
        appendToFile(pastReservations, old);
    }

    @Override
    public ArrayList<Reservation> getInHouseGuests() {
        return getAllFromFile(inHouseReservations);
    }

    @Override
    public ArrayList<Reservation> getPast() {
        return getAllFromFile(pastReservations);
    }

    private void removeSingleObjectFromFile(String fileName, Reservation reservation) {
        Object[] read = null;
        try {
            read = fileIO.readArrayFromFile(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (read.length == 0) {
            throw new IllegalStateException("File empty");
        }

        ArrayList<Object> lessValues = new ArrayList<>();
        for (int i = 0; i < read.length; i++) {
            if (!(read[i].equals(reservation))) {

                lessValues.add(read[i]);
            }
        }
        Object[] temp = new Object[lessValues.size()];
        lessValues.toArray(temp);
        try {
            fileIO.writeToFile(fileName, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Reservation> getAllFromFile(String filename) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        Object[] fg = null;
        try {
            fg = fileIO.readArrayFromFile(filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < fg.length; i++) {
            reservations.add((Reservation) fg[i]);
        }
        return reservations;
    }

    private void appendToFile(String fileName, Reservation reservation) {

        Object[] read = null;
        try {
            read = fileIO.readArrayFromFile(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object[] newValues = new Object[read.length + 1];
        for (int i = 0; i < read.length; i++) {
            newValues[i] = read[i];
        }
        newValues[newValues.length - 1] = reservation;
        try {
            fileIO.writeToFile(fileName, newValues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}