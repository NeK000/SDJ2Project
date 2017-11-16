package client.GUI;

import client.HotelController;
import common.DateHandler;
import common.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CheckAvailability {

    private HotelController hc;

    private JPanel controlPanel;
    private JTabbedPane parent;
    private JPanel left;
    private JPanel right;
    private JPanel down;
    private JTextField fromField;
    private JTextField toField;
    private JTextArea roomData;
    private JTextPane warnings;
    private JLabel label;
    private boolean error;
    private String[] titles = {"First name", "Middle name ", "Last name", "Country", "Arrival date", "Departure date", "Room type"};
    private DefaultTableModel dtm = new DefaultTableModel(titles, 0);
    private JTable roomDataWithReservations = new JTable(dtm);

    /**
     * No-argument constructor for initialing CheckAvailability
     */
    public CheckAvailability(JTabbedPane parent, HotelController hc) {
        this.hc = hc;
        this.parent = parent;
        prepareGUI();
    }

    /**
     * A method preparing the GUI for launch.
     */
    private void prepareGUI() {

        left = new JPanel();
        right = new JPanel();
        down = new JPanel();

        right.setPreferredSize(new Dimension(700, 400));
        left.setPreferredSize(new Dimension(700, 400));
        down.setPreferredSize(new Dimension(1350, 400));
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        prepareSearchWindow();
        controlPanel.add(left, BorderLayout.WEST);
        controlPanel.add(right, BorderLayout.EAST);
        controlPanel.add(down, BorderLayout.SOUTH);
    }

    /**
     * A method containing the design of the GUI
     */
    private void prepareSearchWindow() {

        roomData = new JTextArea(hc.getAvailabilityFromDateInterval(new DateHandler(1, 1, 2200),
                new DateHandler(1, 2, 2200)));
        JScrollPane listScroller = new JScrollPane(roomData);
        listScroller.setPreferredSize(new Dimension(700, 400));
        roomData.setEditable(false);
        JScrollPane listWithReservations = new JScrollPane(roomDataWithReservations);
        listWithReservations.setPreferredSize(new Dimension(1300, 390));
        right.add(listScroller);
        down.add(listWithReservations);

        fromField = new JTextField();
        fromField.setPreferredSize(new Dimension(100, 25));

        toField = new JTextField();
        toField.setPreferredSize(new Dimension(100, 25));
        toField.addKeyListener(new KeyPressEvent());
        fromField.addKeyListener(new KeyPressEvent());

        label = new JLabel("Arrival and Departure dates (dd/mm/YYYY) ");
        label.setLabelFor(toField);

        warnings = new JTextPane();
        warnings.setPreferredSize(new Dimension(300, 25));
        warnings.setEditable(false);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet asset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
        warnings.setCharacterAttributes(asset, false);

        left.add(label);
        left.add(fromField);
        left.add(toField);
        left.add(warnings);
    }

    /**
     * The method takes two date handlers and executes the getAvailabilityFromDate using them and displaying the resulting String s in the roomData JTextArea
     *
     * @param d1 DateHandler object representing arrival date
     * @param d2 DateHandler object representing departure date
     */

    private void displayRooms(DateHandler d1, DateHandler d2) {

        String s = hc.getAvailabilityFromDateInterval(d1, d2);
        roomData.setText(s);
        takeData(d1, d2);
        roomData.revalidate();
    }

    private void takeData(DateHandler d1, DateHandler d2) {
        ArrayList<Reservation> newRes = hc.getReservationsForDateInterval(d1, d2);
        Object[][] array = new Object[newRes.size()][7];
        for (int i = 0; i < newRes.size(); i++) {
            array[i][0] = newRes.get(i).getGuest().getName().getFirstName();
            array[i][1] = newRes.get(i).getGuest().getName().getMiddleName();
            array[i][2] = newRes.get(i).getGuest().getName().getLastName();
            array[i][3] = newRes.get(i).getGuest().getAddress().getCountry();
            array[i][4] = newRes.get(i).getArrival().getCheckInDate();
            array[i][5] = newRes.get(i).getDeparture().getCheckOutDate();
            array[i][6] = newRes.get(i).getRoomType();

        }
        dtm = new DefaultTableModel(array, titles);
        roomDataWithReservations.setModel(dtm);

    }

    /**
     * A class that listens to any activity regarding the keyboard
     */
    class KeyPressEvent implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        /**
         * A method to listen for key presses.
         *
         * @param e event representing the action event.
         */
        public void keyPressed(KeyEvent e) {
            if ((e.getSource().equals(toField) || e.getSource().equals(fromField)) && e.getKeyCode() == 10) {
                String[] str = fromField.getText().split("/");
                String[] str2 = toField.getText().split("/");
                error = true;
                if (!(isValidDate(fromField.getText()) && isValidDate(toField.getText()))) {
                    warnings.setText("Please use the format provided");
                    error = false;
                } else {
                    DateHandler d1 = new DateHandler(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
                    DateHandler d2 = new DateHandler(Integer.parseInt(str2[0]), Integer.parseInt(str2[1]), Integer.parseInt(str2[2]));
                    if (d1.isBefore(d2)) {
                        displayRooms(d1, d2);
                    } else {
                        error = false;

                        warnings.setText("Departure is before Arrival");
                        roomData.setText("Single room: " + 0 + "\nDouble room: " + 0
                                + "\nDouble room-twin bed: " + 0 + "\nDouble room-kingsize bed: "
                                + 0 + "\nSingle suite: " + 0 + "\nDouble suite: "
                                + 0 + "\nTriple Suite: " + 0);
                    }
                }
                if (error) {
                    warnings.setText("");
                }

            }
        }

        /**
         * Checks if the entered dates are in the correct format.
         *
         * @param str takes the date as a string
         * @return true or false. If dates are entered in the proper way, the method returns true , else it returns false.
         */

        public boolean isValidDate(String str) {
            String[] arr = str.split("/");
            if (arr[0].chars().allMatch(Character::isDigit) && arr[0].length() == 2) {
                if (arr[1].chars().allMatch(Character::isDigit) && arr[1].length() == 2) {
                    if (arr[2].chars().allMatch(Character::isDigit) && arr[2].length() == 4) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * A method that returns the controlPanel of this class as a JPanel.
     *
     * @return JPanel, controlPanel representing the main panel of this tab, where all GUI elements from check availability are contained
     */

    public JPanel getAvailabilityTab() {
        return controlPanel;
    }

}
