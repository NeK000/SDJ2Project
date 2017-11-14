//package server;
//
//import common.Reservation;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//
///**
// * A class  which later on will be used to save a object from the HotelManager with the help of MyFileIO.
// *
// * @author Nikolay D Nikolav, Yusuf A Farah, Radu G Orleanu, Catalin Udrea
// * @version 1.0
// */
//public class FileAdapter implements Serializable {
//    private MyFileIO fileIO = new MyFileIO();
//
//
//    /**
//     * Writing object to file.
//     *
//     * @param fileName takes file name.
//     * @param object   takes object.
//     */
//
//    public void writeToFileObj(String fileName, Object object) {
//        try {
//            fileIO.writeToFile(fileName, object);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Reading object from file.
//     *
//     * @param fileName takes file name.
//     * @return object
//     */
//    public Object readFromFileObj(String fileName) {
//        Object read = null;
//        try {
//            read = fileIO.readObjectFromFile(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return read;
//    }
//
//    /**
//     * Reading from a file.
//     *
//     * @param fileName takes file name.
//     * @return reservations, an array list containing all guests.
//     */
//    public ArrayList<Reservation> getAll(String fileName) {
//        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
//        Object[] fg = null;
//        try {
//            fg = fileIO.readArrayFromFile(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < fg.length; i++) {
//            reservations.add((Reservation) fg[i]);
//        }
//        return reservations;
//    }
//
//    /**
//     * Append to a file.
//     *
//     * @param fileName    takes file name.
//     * @param reservation takes specific reservation
//     */
//    public void createReservation(String fileName, Reservation reservation) {
//
//        Object[] read = null;
//        try {
//            read = fileIO.readArrayFromFile(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Object[] newValues;
//        if (read == null) {
//            newValues = new Object[1];
//            newValues[0] = reservation;
//        } else {
//            newValues = new Object[read.length + 1];
//            for (int i = 0; i < read.length; i++) {
//                newValues[i] = read[i];
//            }
//            newValues[read.length - 1] = reservation;
//        }
//        try {
//            fileIO.writeToFile(fileName, newValues);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Remove single reservation from file
//     *
//     * @param fileName    takes file name.
//     * @param reservation takes specific reservation.
//     */
//    public void removeSingleObjectFromFile(String fileName, Reservation reservation) {
//        Object[] read = null;
//        try {
//            read = fileIO.readArrayFromFile(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        ArrayList<Object> lessValues = new ArrayList<Object>();
//        for (int i = 0; i < read.length; i++) {
//            if (!(read[i].equals(reservation))) {
//                lessValues.add(read[i]);
//            }
//        }
//        Object[] temp = new Object[lessValues.size()];
//        lessValues.toArray(temp);
//        try {
//            fileIO.writeToFile(fileName, temp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void updateReservation(Reservation old, Reservation newOne) {
//        removeSingleObjectFromFile("reservations.bin", old);
//        writeToFileObj("reservations.bin", newOne);
//    }
////    public static void main(String[] args) {
////        FileAdapter james = new FileAdapter();
////        Reservation first = new Reservation(new Guest(new Name("steven", "george", "someGuy"), 1234278901, new Address("Romania", "SomeCity", "8700", "someStreet"), "Romanian",
////                ("31, 10, 1988")), new Arrival(new DateHandler(01, 11, 2017)), new Departure(new DateHandler(02, 11, 2017)), "king sized", true, true,
////                true);
////        james.writeToFileObj("pastReservations.bin", first);
////        Reservation[] a = new Reservation[10];
////        a[0] = (Reservation)james.readFromFileObj("pastReservations.bin");
////        System.out.println(Arrays.toString(a));
////    }
//
//}
package server;

import common.Reservation;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
//        Reservation[] temp = readArray(currentReservations);
//
//        for (int i = 0; i < temp.length; i++) {
//            if (temp[i].equals(old)) {
//                check = true;
//                removeSingleObjectFromFile(currentReservations, old);
//                writeToFileObj(currentReservations, newOne);
//                break;
//            }
//        }
//        if (!check) {
//            temp = (Reservation[]) readFromFileObj(inHouseReservations);
//            for (int i = 0; i < temp.length; i++) {
//                if (temp[i].equals(old)) {
//                    removeSingleObjectFromFile(inHouseReservations, old);
//                    writeToFileObj(inHouseReservations, newOne);
//                    break;
//                }
//            }
//        }
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
        //        createReservation(inHouseReservations, old);
    }

    @Override
    public void checkOut(Reservation old) {
        if (old == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        removeSingleObjectFromFile(inHouseReservations, old);
//        createReservation(pastReservations, old);
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

    private Reservation[] readArray(String filename) {
        Object[] temps = new Object[0];
        try {
            temps = fileIO.readArrayFromFile(currentReservations);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Reservation[] temp = new Reservation[temps.length];
        for (int i = 0; i < temps.length; i++) {
            temp[0] = (Reservation) temps[0];
        }
        return temp;
    }

    private void removeSingleObjectFromFile(String fileName, Reservation reservation) {
        Object[] read = null;
        try {
            read = fileIO.readArrayFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (read.length == 0) {
            throw new IllegalStateException("File empty");
        }

        ArrayList<Object> lessValues = new ArrayList<Object>();
        for (int i = 0; i < read.length; i++) {
//            Reservation temp = (Reservation) read[i];
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
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        Object[] fg = null;
        try {
            fg = fileIO.readArrayFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < fg.length; i++) {
            reservations.add((Reservation) fg[i]);
        }
        return reservations;
    }

//    private void writeToFileObj(String fileName, Object object) {
//        try {
//            fileIO.writeToFile(fileName, object);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void appendToFile(String fileName, Reservation reservation) {

        Object[] read = null;
        try {
            read = fileIO.readArrayFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    private Object readFromFileObj(String fileName) {
        Object read = null;
        try {
            read = fileIO.readObjectFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return read;
    }


}