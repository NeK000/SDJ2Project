package server;

import java.io.*;
import java.util.ArrayList;



public class MyFileIO {
    /**
     * Write multiple objects to file
     *
     * @param fileName takes file name.
     * @param objs     takes multiple objects.
     * @throws FileNotFoundException when a file is not found
     * @throws IOException           Signals that an I/O exception of some sort has occurred. This class is the general class of
     *                               exceptions produced by failed or interrupted I/O operations.
     */
    public void writeToFile(String fileName, Object[] objs) throws IOException {
        ObjectOutputStream writeToFile = null;

        try {
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            writeToFile = new ObjectOutputStream(fileOutStream);

            for (int i = 0; i < objs.length; i++) {
                writeToFile.writeObject(objs[i]);
            }
        } finally {
            if (writeToFile != null) {
                try {
                    writeToFile.close();
                } catch (IOException e) {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }
    }

    /**
     * Read array from file
     *
     * @param fileName takes file name
     * @return ArrayList returns the whole list as an array list.
     * @throws FileNotFoundException  when a file is not found
     * @throws IOException            Signals that an I/O exception of some sort has occurred. This class is the general class of
     *                                exceptions produced by failed or interrupted I/O operations.
     * @throws ClassNotFoundException when the object is not in the same class
     */
    public Object[] readArrayFromFile(String fileName) throws IOException, ClassNotFoundException {
        ArrayList<Object> objs = new ArrayList<>();

        ObjectInputStream readFromFile = null;
        try {
            FileInputStream fileInStream = new FileInputStream(fileName);
            readFromFile = new ObjectInputStream(fileInStream);
            while (true) {
                try {
                    objs.add(readFromFile.readObject());
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (EOFException e) {
            System.out.println("I think the file " + fileName + " was empty");
            return new Object[0];
        } finally {
            if (readFromFile != null) {
                try {
                    readFromFile.close();
                } catch (IOException e) {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }

        return objs.toArray();
    }
}
