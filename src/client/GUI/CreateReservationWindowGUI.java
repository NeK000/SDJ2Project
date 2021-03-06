package client.GUI;

import client.HotelController;
import common.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateReservationWindowGUI {

    private HotelController hc;
    private JPanel reservationPanel, leftPanel, rightPanel, guestDataLabels, guestDataTextFields, guestDataCheckBoxes,
            leftPanelButtons, rightPanelButtons;
    private JLabel firstNameLabel, middleNameLabel, lastNameLabel, countryLabel, cityLabel, postCodeLabel, streetLabel,
            phoneNumberLabel, nationalityLabel, dateOfBirthLabel, arrivalLabel, departureLabel, roomTypeLabel,
            bookingInitiatorLabel, lateArrivalNoticeLabel, priorityGuestLabel;
    private JTextField firstName, middleName, lastName, country, city, postCode, street, phoneNumber, nationality,
            dateOfBirth, arrival, departure;
    private JCheckBox bookingInitiator, lateArrivalNotice, priorityGuest;
    private JScrollPane allGuestScroll;
    private JButton save, clear, choose, refresh, cancel, update, remove;
    private ArrayList<JLabel> allJLabels;
    private ArrayList<JTextField> allTextFields;
    private ArrayList<Reservation> allReservations;
    private MyButtonListener listener;
    private JComboBox<String> roomTypes;
    private KeyPressEvent presser;
    private JTable allGuests;
    private DefaultTableModel dtm;
    private String[] columnNames;
    private Object[][] resColumn;
    private ArrayList<Reservation> foundNames;
    private MyListSelectionListener tableSelect;
    private Reservation chosenReservation;
    private boolean isSearch = false;
    private JTabbedPane parent;

    /**
     * Constructor for initializing the required methods
     *
     * @param parent representing the JTabbedPane where the Create Reservation Tab will be in
     */

    public CreateReservationWindowGUI(JTabbedPane parent, HotelController hc) {

        this.hc = hc;
        if (this.getClass().getName().equals("client.GUI.Search")) {
            isSearch = true;
        }

        this.parent = parent;

        left();
        takeAllGuest();
        right();
        prepareGUI();
    }

    /**
     * A method used to prepare all the methods below for the GUI for launching together.
     * Prepares all listeners.
     * Prepares  the left and the right parts of the window.
     */

    public void prepareGUI() {
        listener = new MyButtonListener();
        presser = new KeyPressEvent();
        tableSelect = new MyListSelectionListener();
        left();
        right();
        reservationPanel = new JPanel();
        reservationPanel.setPreferredSize(new Dimension(1440, 960));
        reservationPanel.add(leftPanel, BorderLayout.WEST);
        reservationPanel.add(rightPanel, BorderLayout.EAST);
        bookingInitiator.doClick();
    }

    /**
     * A class listening for whenever the selection changes.
     */
    private class MyListSelectionListener implements ListSelectionListener {
        /**
         * A method used to select a specific reservation from a list.
         *
         * @param e the event that characterizes the change. This event e is not used in this case.
         */
        public void valueChanged(ListSelectionEvent e) {
            int a = allGuests.getSelectedRow();
            if (a >= 0) {
                chosenReservation = foundNames.get(a);
            }
        }

    }

    /**
     * A method used to check if the input data is in the correct format. Checks the data fields.
     *
     * @param str takes a string.
     * @return true or false. Returns true if the date format was typed properly in the text field, else it returns false.
     * @author Catalin Udrea
     * @version 1.0
     */
    public boolean isValidDate(String str) {
        String[] arr = str.split("/");
        if (arr[0].chars().allMatch(Character::isDigit) && (arr[0].length() == 2 || arr[0].length() == 1)) {
            if (arr[1].chars().allMatch(Character::isDigit) && (arr[1].length() == 2 || arr[1].length() == 1)) {
                if (arr[2].chars().allMatch(Character::isDigit) && arr[2].length() == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method used to check if the input data is in the correct format. Checks the phone number field.
     *
     * @param str takes a string.
     * @return true or false. Returns true if the phone number was typed properly in the text field, else it returns false.
     * @author Catalin Udrea
     * @version 1.0
     */
    public boolean isValidPhoneNumber(String str) {
        Long a;
        try {
            a = Long.parseLong(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * A method created for the clear button.
     * If the clear button is clicked, it will clear all fields except the booking initiator check box.
     * Check box for booking initiator is checked by default.
     */
    public void clear() {
        firstName.setText("");
        middleName.setText("");
        lastName.setText("");
        country.setText("");
        city.setText("");
        postCode.setText("");
        street.setText("");
        phoneNumber.setText("");
        nationality.setText("");
        dateOfBirth.setText("");
        arrival.setText("");
        departure.setText("");
        if (lateArrivalNotice.isSelected()) {
            lateArrivalNotice.doClick();
        }
        if (priorityGuest.isSelected()) {
            priorityGuest.doClick();
        }
    }

    /**
     * A class which listens for any activity regarding the buttons.
     */
    private class MyButtonListener implements ActionListener {

        /**
         * A method used to check if a button is pressed.
         *
         * @param e event representing the action event.
         */
        public void actionPerformed(ActionEvent e) {
            int a = 0;
            // Checks if button save is pressed and if all data is valid will create reservation and clear all the fields.
            if (e.getSource() == save || e.getSource() == update) {
                if (firstName.getText().length() < 2) {
                    firstNameLabel.setText("<html><font color='black'>First name </font> <font color='red'>" +
                            "should have at least 2 letters</font></html>");
                    a++;
                } else {
                    firstNameLabel.setText("First name");
                }
                // Arrival, departure is checked when pressing save
                if (!(isValidDate(arrival.getText()))) {
                    arrivalLabel.setText("<html><font color='black'>Arrival (dd/mm/yyyy) </font> <font color='red'>" +
                            "please use the format provided</font></html>");
                    a++;
                }
                if (!(isValidDate(departure.getText()))) {
                    departureLabel.setText("<html><font color='black'>Departure (dd/mm/yyyy) </font> <font color='red'>" +
                            "please use the format provided</font></html>");
                }
                if (!(isValidPhoneNumber(phoneNumber.getText()))) {
                    phoneNumberLabel.setText("<html><font color='black'>Phone number</font> <font color='red'>" +
                            "should be a valid phone number</font></html>");
                    a++;
                }
                if (a == 0 && !isSearch) {
                    reservationCreator();
                    clear();
                    parent.setSelectedIndex(0);
                }
                if (a == 0 && isSearch) {
                    updateReservation(chosenReservation);
                    firstNameLabel.setText("First name");
                    arrivalLabel.setText("Arrival (dd/mm/yyyy)");
                    departureLabel.setText("Departure (dd/mm/yyyy");
                    phoneNumberLabel.setText("Phone number");
                }


            }
            // Checks if button clear is pressed and clears all fields.
            else if (e.getSource() == clear) {
                clear();
            }
            // Checks if the refresh button is pressed. If pressed will refresh the list on the right side and give you fresh list with all reservations
            else if (e.getSource() == refresh) {
                takeAllGuest();
                createReservationTable(new ArrayList<Reservation>());
            }
            // Checks if the choose button is pressed.
            // If pressed uses the method valueChanged that gives you a specific reservation.
            // After that fills up the text fields with the value of that reservation.
            // Depending from which tab is pressed fills up different data.
            else if (e.getSource() == choose) {
                firstName.setText(chosenReservation.getGuest().getName().getFirstName());
                middleName.setText(chosenReservation.getGuest().getName().getMiddleName());
                lastName.setText(chosenReservation.getGuest().getName().getLastName());
                country.setText(chosenReservation.getGuest().getAddress().getCountry());
                city.setText(chosenReservation.getGuest().getAddress().getCity());
                postCode.setText(chosenReservation.getGuest().getAddress().getPostCode());
                street.setText(chosenReservation.getGuest().getAddress().getStreet());
                phoneNumber.setText(String.valueOf(chosenReservation.getGuest().getPhoneNumber()));
                nationality.setText(chosenReservation.getGuest().getNationality());
                dateOfBirth.setText(chosenReservation.getGuest().getDateOfBirth());
                roomTypes.setSelectedItem(chosenReservation.getRoomType());
                if (chosenReservation.isPriorityGuest()) {
                    priorityGuest.doClick();
                }
                if (isSearch) {
                    String arrDay = String.valueOf(chosenReservation.getArrival().getCheckInDate().getDay());
                    String arrMonth = String.valueOf(chosenReservation.getArrival().getCheckInDate().getMonth());
                    String arrYear = String.valueOf(chosenReservation.getArrival().getCheckInDate().getYear());
                    String depDay = String.valueOf(chosenReservation.getDeparture().getCheckOutDate().getDay());
                    String depMonth = String.valueOf(chosenReservation.getDeparture().getCheckOutDate().getMonth());
                    String depYear = String.valueOf(chosenReservation.getDeparture().getCheckOutDate().getYear());
                    arrival.setText(arrDay + "/" + arrMonth + "/" + arrYear);
                    departure.setText(depDay + "/" + depMonth + "/" + depYear);
                }
            }
            // Checks if the cancel button is pressed. If pressed return to the main window of the GUI.
            else if (e.getSource() == cancel) {
                int choice = JOptionPane.showConfirmDialog(null, "Do you really want to exit the create reservation window?", "Exit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    parent.setSelectedIndex(0);
                }
            }
        }

    }

    /**
     * A class that listens to any activity regarding the keyboard.
     */
    class KeyPressEvent implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
        }

        /**
         * On key released, if the first name in a reservation has all the letters that were typed inside the name
         * field, it will add that reservation to a list and display it
         *
         * @param e event representing the action event.
         */
        public void keyReleased(KeyEvent e) {
            foundNames = new ArrayList<Reservation>();
            createReservationTable(allReservations);

            for (int i = 0; i < resColumn.length; i++) {
                String fullName = String.valueOf(resColumn[i][0].toString().toLowerCase());
                if (fullName.contains(firstName.getText().toLowerCase())) {
                    foundNames.add(allReservations.get(i));
                }
            }
            createReservationTable(foundNames);
        }
    }

    /**
     * A method used to design the left side of the CreateReservation tab.
     */
    public void left() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(700, 650));

        save = new JButton("Save");
        save.addActionListener(listener);
        update = new JButton("Update");
        update.addActionListener(listener);
        remove = new JButton("Remove");
        remove.addActionListener(listener);
        clear = new JButton("Clear");
        clear.addActionListener(listener);

        guestDataLabels = new JPanel(new GridLayout(13, 1, 2, 2));
        guestDataLabels.setPreferredSize(new Dimension(250, 450));

        guestDataCheckBoxes = new JPanel(new GridLayout(3, 3, 2, 2));
        guestDataCheckBoxes.setPreferredSize(new Dimension(450, 150));

        guestDataTextFields = new JPanel(new GridLayout(13, 1, 2, 2));
        guestDataTextFields.setPreferredSize(new Dimension(200, 450));

        leftPanelButtons = new JPanel();
        leftPanelButtons.setPreferredSize(new Dimension(600, 100));

        allTextFields = new ArrayList<JTextField>();
        allJLabels = new ArrayList<JLabel>();
        String[] roomTypesForComboBox = {"single room", "double room-twin beds", "double room-kingsize",
                "single bedroom suite", "double bedroom suite", "three bedroom suite"};
        roomTypes = new JComboBox<String>(roomTypesForComboBox);


        allTextFields.add(firstName = new JTextField());
        allTextFields.add(middleName = new JTextField());
        allTextFields.add(lastName = new JTextField());
        allTextFields.add(country = new JTextField());
        allTextFields.add(city = new JTextField());
        allTextFields.add(postCode = new JTextField());
        allTextFields.add(street = new JTextField());
        allTextFields.add(phoneNumber = new JTextField());
        allTextFields.add(nationality = new JTextField());
        allTextFields.add(dateOfBirth = new JTextField());
        allTextFields.add(arrival = new JTextField());
        allTextFields.add(departure = new JTextField());

        firstName.addKeyListener(presser);
        phoneNumber.addKeyListener(presser);
        allJLabels.add(firstNameLabel = new JLabel("First name"));
        allJLabels.add(middleNameLabel = new JLabel("Middle name"));
        allJLabels.add(lastNameLabel = new JLabel("Last name"));
        allJLabels.add(countryLabel = new JLabel("Country"));
        allJLabels.add(cityLabel = new JLabel("City"));
        allJLabels.add(postCodeLabel = new JLabel("Post code"));
        allJLabels.add(streetLabel = new JLabel("Street"));
        allJLabels.add(phoneNumberLabel = new JLabel("Phone number"));
        allJLabels.add(nationalityLabel = new JLabel("Nationality"));
        allJLabels.add(dateOfBirthLabel = new JLabel("Date of birth (dd/mm/yyyy)"));
        allJLabels.add(arrivalLabel = new JLabel("Arrival (dd/mm/yyyy)"));
        allJLabels.add(departureLabel = new JLabel("Departure (dd/mm/yyyy)"));
        allJLabels.add(roomTypeLabel = new JLabel("Room type"));


        guestDataCheckBoxes.add(bookingInitiatorLabel = new JLabel("Booking initiator"));
        guestDataCheckBoxes.add(bookingInitiator = new JCheckBox());
        guestDataCheckBoxes.add(lateArrivalNoticeLabel = new JLabel("Late arrival notice"));
        guestDataCheckBoxes.add(lateArrivalNotice = new JCheckBox());
        guestDataCheckBoxes.add(priorityGuestLabel = new JLabel("Priority guest"));
        guestDataCheckBoxes.add(priorityGuest = new JCheckBox());

        for (int i = 0; i < allJLabels.size(); i++) {
            guestDataLabels.add(allJLabels.get(i));
        }

        for (int i = 0; i < allTextFields.size(); i++) {
            guestDataTextFields.add(allTextFields.get(i));
        }
        guestDataTextFields.add(roomTypes);

        if (!isSearch) {
            leftPanelButtons.add(save);
        } else {
            leftPanelButtons.add(update);
            leftPanelButtons.add(remove);
        }
        leftPanelButtons.add(clear);
        leftPanel.add(guestDataLabels);
        leftPanel.add(guestDataTextFields);
        leftPanel.add(guestDataCheckBoxes);
        leftPanel.add(leftPanelButtons);
    }

    /**
     * A method used to design the right side of the CreateReservation tab.
     */

    public void right() {

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(600, 650));

        rightPanelButtons = new JPanel();
        rightPanelButtons.setPreferredSize(new Dimension(600, 100));

        refresh = new JButton("Refresh");
        refresh.addActionListener(listener);

        choose = new JButton("Choose");
        choose.addActionListener(listener);

        cancel = new JButton("Cancel");
        cancel.addActionListener(listener);

        columnNames = new String[5];
        columnNames[0] = "First name";
        columnNames[1] = "Middle name";
        columnNames[2] = "Last name";
        columnNames[3] = "Country";
        columnNames[4] = "Phone number";
        dtm = new DefaultTableModel(columnNames, 0);

        allGuests = new JTable(dtm) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        allGuests.getSelectionModel().addListSelectionListener(tableSelect);
        allGuestScroll = new JScrollPane(allGuests);
        allGuestScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        allGuestScroll.setPreferredSize(new Dimension(600, 600));
        allGuests.revalidate();
        rightPanelButtons.add(refresh);
        rightPanelButtons.add(choose);
        rightPanelButtons.add(cancel);
        rightPanel.add(allGuestScroll);
        rightPanel.add(rightPanelButtons);

    }


    /**
     * A method used to get all the guests in an array list.
     */
    public void takeAllGuest() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.addAll(Arrays.asList(hc.getAllReservations()));

        ArrayList<Reservation> pastReservation = new ArrayList<>();
        pastReservation.addAll(Arrays.asList(hc.getPastReservations()));

        ArrayList<Reservation> inHouse = new ArrayList<>();
        inHouse.addAll(Arrays.asList(hc.getInHouse()));


        ArrayList<Reservation> temp = new ArrayList<>();
        allReservations = new ArrayList<>();

        temp.addAll(reservations);
        temp.addAll(inHouse);

        if (!isSearch) {
            temp.addAll(pastReservation);
        }

        for (int i = 0; i < temp.size(); i++) {
            allReservations.add(temp.get(i));
        }

    }

    /**
     * A method used to get the required data for the JTable.
     *
     * @param res represents a reservation array.
     */

    public void createReservationTable(ArrayList<Reservation> res) {
        resColumn = new Object[res.size()][5];

        for (int i = 0; i < res.size(); i++) {
            resColumn[i][0] = res.get(i).getGuest().getName().getFirstName();
            resColumn[i][1] = res.get(i).getGuest().getName().getMiddleName();
            resColumn[i][2] = res.get(i).getGuest().getName().getLastName();
            resColumn[i][3] = res.get(i).getGuest().getAddress().getCountry();
            resColumn[i][4] = res.get(i).getGuest().getPhoneNumber();
        }
        dtm = new DefaultTableModel(resColumn, columnNames);
        allGuests.setModel(dtm);
        allGuests.revalidate();
    }

    /**
     * A method used to create a reservation.
     */
    public void reservationCreator() {
        Arrival arr = new Arrival(new DateHandler(Integer.parseInt(arrival.getText().split("/")[0]),
                Integer.parseInt(arrival.getText().split("/")[1]),
                Integer.parseInt(arrival.getText().split("/")[2])));
        Departure dep = new Departure(new DateHandler(Integer.parseInt(departure.getText().split("/")[0]),
                Integer.parseInt(departure.getText().split("/")[1]),
                Integer.parseInt(departure.getText().split("/")[2])));
        Name name = new Name(firstName.getText(), middleName.getText(), lastName.getText());
        Address add = new Address(country.getText(), city.getText(), postCode.getText(), street.getText());
        Guest guest = new Guest(name, Long.parseLong(phoneNumber.getText()), add, nationality.getText(), dateOfBirth.getText());
        Reservation rs = new Reservation(guest, arr, dep, roomTypes.getSelectedItem().toString(), bookingInitiator.isSelected(),
                lateArrivalNotice.isSelected(), priorityGuest.isSelected());

        hc.createReservation(rs);
    }

    /**
     * A method used to update a specific reservation.
     *
     * @param r takes a specific reservation.
     */
    private void updateReservation(Reservation r) {
        String firstName_ = firstName.getText();
        String middleName_ = middleName.getText();
        String lastName_ = lastName.getText();
        String country_ = country.getText();
        String city_ = city.getText();
        String postCode_ = postCode.getText();
        String street_ = street.getText();
        String phoneNumber_ = phoneNumber.getText();
        String nationality_ = nationality.getText();
        String dateOfBirth_ = dateOfBirth.getText();
        String roomType_ = roomTypes.getSelectedItem().toString();
        String[] arrival_ = arrival.getText().split("/");
        String[] departure_ = departure.getText().split("/");
        boolean lateArraivalNotice_ = false;
        boolean priorityGuest_ = false;
        boolean bookingInitiator_ = false;
        if (lateArrivalNotice.isSelected()) {
            lateArraivalNotice_ = true;
        }
        if (priorityGuest.isSelected()) {
            priorityGuest_ = true;
        }
        if (bookingInitiator.isSelected()) {
            bookingInitiator_ = true;
        }

        Name name = new Name(firstName_, middleName_, lastName_);

        DateHandler arrDH = new DateHandler(Integer.parseInt(arrival_[0]),
                Integer.parseInt(arrival_[1]), Integer.parseInt(arrival_[2]));
        DateHandler depDH = new DateHandler(Integer.parseInt(departure_[0]),
                Integer.parseInt(departure_[1]), Integer.parseInt(departure_[2]));
        Arrival arr = new Arrival(arrDH);
        Departure dep = new Departure(depDH);
        Address address = new Address(country_, city_, postCode_, street_);

        Guest guest = new Guest(name, Long.parseLong(phoneNumber_), address, nationality_, dateOfBirth_);

        Reservation reservation = new Reservation(guest, arr, dep, roomType_, bookingInitiator_,
                lateArraivalNotice_, priorityGuest_);
        Reservation old = r;
        hc.updateReservation(r, reservation);

    }

    /**
     * A method that returns the reservation of this class as a JPanel.
     *
     * @return JPanel, reservationPanel representing the main panel of this tab, where all GUI elements from this class are contained
     */
    public JPanel getAvailabilityTab() {
        return reservationPanel;
    }

}