// Import necessary packages
package com.bookingsystemapp.bookingmanagementsystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.text.DecimalFormat;

/**
 *
 * @author Dorina
 */

// Main class to run the VacayStarApp with GUI
public class VacayStarApp2 extends javax.swing.JFrame {

    // List of accommodation choices
    public static final String[] ACCOMMODATIONS = {"Imperial Lodge", "Sunshine Apt.", "Standard Cabin", "Rustic Shed", "Classic Caravan"};

    // Constants for costings
    public static final double[] CARD_FEE = {4.00, 3.50, 3.00, 2.50, 2.00};//Card fee for calculation
    public static final double[] DISCOUNT_RATE = {0.10, 0.10, 0.05, 0.05, 0.05};//Discount rate for calculation
    public static final double[] DAILY_RATE = {350.00, 280.00, 200.00, 150.00, 90.00};//Daily rate for calculation
    public static final double VAT_RATE = 0.15; // VAT rate for calculations

    public static final String CSV_FILE_NAME = "vacaystar_bookings.csv"; // CSV file for saving bookings

    
    //The fields will be used for creatin the application
    private final BookingManager bookingManager;
    private final JTextField nameField;
    private final JTextField contactField;
    private final JTextField emailField;  
    private final JTextField arrivalDateField;
    private final JTextField numDaysField;
    private final JComboBox<String> accommodationBox;
    private JTextArea textArea;
    private final JDateChooser arrivalDateChooser;
    
    
    // Setting up the GUI
    public VacayStarApp2() {
        bookingManager = new BookingManager();

        setTitle("VacayStar Booking System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //  setting padding for the content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setContentPane(contentPane);

        //Here we set the background color
        getContentPane().setBackground(Color.WHITE);

        // Create components for the application
        nameField = new JTextField(20);
        contactField = new JTextField(20);
        emailField = new JTextField(20);  
        arrivalDateField = new JTextField(10);
        numDaysField = new JTextField(5);
        accommodationBox = new JComboBox<>(ACCOMMODATIONS);
        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Booking Details"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Border for text area

        // Create panels and layout
        arrivalDateChooser = new JDateChooser();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Booking Details")); // Border for input panel
        inputPanel.add(new JLabel("Customer Name:"));// Add customer name label
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Contact Number:"));// Add contact name label
        inputPanel.add(contactField);
        inputPanel.add(new JLabel("Email Address:"));  // Add email label
        inputPanel.add(emailField);  
        inputPanel.add(new JLabel("Arrival Date :"));// Add arrival date label
        inputPanel.add(arrivalDateChooser);
        inputPanel.add(new JLabel("Number of Days:"));// Add number of days label
        inputPanel.add(numDaysField);
        inputPanel.add(new JLabel("Accommodation:"));// Add accomodation label
        inputPanel.add(accommodationBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 
        JButton bookButton = new JButton("Create Booking");
        JButton viewButton = new JButton("View Bookings (Admin)");
        JButton exitButton = new JButton("Close");

        buttonPanel.add(bookButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(exitButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the components
        bookButton.addActionListener(e -> makeNewBooking());
        viewButton.addActionListener(e -> {
            if (isAdmin()) {
                showAdminInterface();
            } else {
                textArea.append("Invalid admin password.\n");
            }
        });
        exitButton.addActionListener(e -> System.exit(0));
    }

    // Method to create  a new booking
    private void makeNewBooking() {
        int accommodationChoice = accommodationBox.getSelectedIndex();
        Date arrivalDate = arrivalDateChooser.getDate();
        DecimalFormat df = new DecimalFormat("0.00");

        for (double fee : CARD_FEE) {
            String formattedFee = df.format(fee);
            System.out.println("Formatted Fee: " + formattedFee);
        }

        if (arrivalDate == null) {
            textArea.append("Booking cancelled. Invalid arrival date.\n");
            return;
        }
        int numDays;
        try {
            numDays = Integer.parseInt(numDaysField.getText());
            if (numDays <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textArea.append("Booking cancelled. Invalid number of days.\n");
            return;
        }
        double subTotal = calculateSubTotal(accommodationChoice, numDays);
        boolean discountApplied = false;

        // Check if discount was applied
        if (numDays >= 14) {
            discountApplied = true;
            
            
        }
        
        
        
        // Check if the subTotal has the right data type.
        String formattedSubTotal = df.format(subTotal);
        System.out.println("Formatted Subtotal: " + formattedSubTotal);

        String customerName = nameField.getText();
        String contactNumber = contactField.getText();
        String email = emailField.getText();  // Get email address

        // Create a new Booking.
        Booking newBooking = new Booking(customerName, contactNumber, email, arrivalDate, numDays,
                accommodationChoice, subTotal);
        bookingManager.addBooking(newBooking); // Add the new booking to the system.

        try {
            bookingManager.saveBookingsToCsv(CSV_FILE_NAME); // Save the bookings to CSV file

            // Send email confirmation to the customer
            String subject = "Booking Confirmation";
            String body = "Dear " + customerName + ",\n\nYour booking has been confirmed.\n\n"
                    + "Arrival Date: " + new SimpleDateFormat("dd/MM/yyyy").format(arrivalDate) + "\n"
                    + "Number of Days: " + numDays + "\n"
                    + "Accommodation: " + ACCOMMODATIONS[accommodationChoice] + "\n"
                    + "Subtotal: £" + formattedSubTotal + "\n\n";
            // The information for the discount
            if (discountApplied) {
                body += "Discount (14 Day): Applied\n";
            } else {
                body += "Discount (14 Day): Not Applicable\n";
            }

            // The information for the card fee
            body += "Card Fee: £" + String.format("%.2f", CARD_FEE[accommodationChoice]) + "\n\n"
                    + "Regards,\nVacayStar Team";

            try {
                EmailConfirmation.sendEmail(email, subject, body);
            } catch (MessagingException ex) {
                Logger.getLogger(VacayStarApp2.class.getName()).log(Level.SEVERE, null, ex);
            }
            textArea.append("Confirmation email sent successfully to " + email + "\n");

            textArea.append("Booking created and saved successfully!\n");
            showBookingDetails(newBooking); // Show booking details to the customer
        } catch (IOException e) {
            textArea.append("Error saving booking to file: " + e.getMessage() + "\n"); // if the saving fails the the message will be dispayed.
        }
    }


    // Method to calculate subtotal based on accommodation choice and number of days
    private double calculateSubTotal(int accommodationChoice, int numDays) {
        double cardFee = CARD_FEE[accommodationChoice];
        double dailyRate = 0; // Initialize daily rate

        if (numDays >= 14) {

            dailyRate = dailyRate * (1 - DISCOUNT_RATE[accommodationChoice]);

        }
        double subTotal = (dailyRate * numDays) + cardFee;
        double vat = subTotal * VAT_RATE;

        switch (accommodationChoice) {
            case 0: // Imperial Lodge
                dailyRate = 350;
                break;
            case 1: // Sunshine Apt.
                dailyRate = 280;
                break;
            case 2: // Standard Cabin
                dailyRate = 200;
                break;
            case 3: // Rustic Shed
                dailyRate = 150;
                break;
            case 4: // Classic Caravan
                dailyRate = 90;
                break;
            default:
                textArea.append("Invalid accommodation choice!\n"); // Display error message for invalid choice
        }
        return dailyRate * numDays + subTotal + vat; // Calculate, return subtotal with all the detaails and requirements applied
    }


    // The method checks the id the admin has the permissions to access the admin interface.
    private boolean isAdmin() {
        String password = JOptionPane.showInputDialog(this, "Enter admin password:");
        return "adminUser".equals(password); // Replace "admin123" with your actual admin password
    }

    // Method to show admin interface with the booking details
    private void showAdminInterface() {

        JFrame adminFrame = new JFrame("Admin Interface");
        adminFrame.setSize(600, 400);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLayout(new BorderLayout());

        JList<String> bookingList = new JList<>();
        bookingManager.viewExistingBookings(bookingList);

        JButton deleteButton = new JButton("Delete Booking");
        JButton editButton = new JButton("Edit Booking");

        deleteButton.addActionListener(e -> deleteBooking(bookingList));
        editButton.addActionListener(e -> editBooking(bookingList));

        JPanel adminButtonPanel = new JPanel();
        adminButtonPanel.add(deleteButton);
        adminButtonPanel.add(editButton);

        adminFrame.add(new JScrollPane(bookingList), BorderLayout.CENTER);
        adminFrame.add(adminButtonPanel, BorderLayout.SOUTH);

        adminFrame.setVisible(true);
    }

    // Method to delete the booking
    private void deleteBooking(JList<String> bookingList) {
        int selectedIndex = bookingList.getSelectedIndex();
        if (selectedIndex != -1) {
            bookingManager.removeBooking(selectedIndex);
            try {
                bookingManager.saveBookingsToCsv(CSV_FILE_NAME); // Save the  updated bookings to CSV file
                DefaultListModel<String> model = (DefaultListModel<String>) bookingList.getModel();
                model.remove(selectedIndex);
                bookingList.setModel(model);
                textArea.append("Booking deleted and saved successfully!\n");
            } catch (IOException e) {
                textArea.append("Error saving bookings to file: " + e.getMessage() + "\n"); // If the saving fails the message will be displayed.
            }
        } else {
            textArea.append("No booking selected to delete.\n");
        }
    }

    // Method will edit the booking
    private void editBooking(JList<String> bookingList) {
        int selectedIndex = bookingList.getSelectedIndex();
        if (selectedIndex != -1) {
            Booking booking = bookingManager.getBookings().get(selectedIndex);

            // Here the show edit will be displayed
            JTextField customerNameField = new JTextField(booking.getCustomerName());
            JTextField contactNumberField = new JTextField(booking.getContactNumber());
            JTextField arrivalDateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(booking.getArrivalDate()));
            JTextField numDaysField = new JTextField(String.valueOf(booking.getNumDays()));
            JComboBox<String> accommodationBox = new JComboBox<>(ACCOMMODATIONS);
            accommodationBox.setSelectedIndex(booking.getAccommodationChoice());

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Customer Name:"));
            panel.add(customerNameField);
            panel.add(new JLabel("Contact Number:"));
            panel.add(contactNumberField);
            panel.add(new JLabel("Arrival Date (dd/MM/yyyy):"));
            panel.add(arrivalDateField);
            panel.add(new JLabel("Number of Days:"));
            panel.add(numDaysField);
            panel.add(new JLabel("Accommodation:"));
            panel.add(accommodationBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Booking", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {

                // Update the customer details
                booking.setCustomerName(customerNameField.getText());
                booking.setContactNumber(contactNumberField.getText());
                try {
                    booking.setArrivalDate(new SimpleDateFormat("dd/MM/yyyy").parse(arrivalDateField.getText()));
                } catch (ParseException e) {
                    textArea.append("Invalid date format! Please use dd/MM/yyyy.\n");
                    return;
                }
                try {
                    booking.setNumDays(Integer.parseInt(numDaysField.getText()));
                } catch (NumberFormatException e) {
                    textArea.append("Invalid number of days.\n");
                    return;
                }
                booking.setAccommodationChoice(accommodationBox.getSelectedIndex());
                booking.setSubTotal(calculateSubTotal(booking.getAccommodationChoice(), booking.getNumDays()));

                // This method wil update and save the booking.
                bookingManager.updateBooking(selectedIndex, booking);
                try {
                    bookingManager.saveBookingsToCsv(CSV_FILE_NAME);
                    DefaultListModel<String> model = (DefaultListModel<String>) bookingList.getModel();
                    model.set(selectedIndex, booking.toString());
                    bookingList.setModel(model);
                    textArea.append("Booking updated and saved successfully!\n");
                } catch (IOException e) {
                    textArea.append("Error saving bookings to file: " + e.getMessage() + "\n");
                }
            }
        } else {
            textArea.append("No booking selected to edit.\n");
        }
    }

    // Method to show booking details to the customer in a new window with all booking details
    private void showBookingDetails(Booking booking) {
        StringBuilder bookingDetails = new StringBuilder();
        bookingDetails.append("Booking Details:\n");
        bookingDetails.append("Customer Name: ").append(booking.getCustomerName()).append("\n");
        bookingDetails.append("Contact Number: ").append(booking.getContactNumber()).append("\n");
        bookingDetails.append("Arrival Date: ").append(new SimpleDateFormat("dd/MM/yyyy").format(booking.getArrivalDate())).append("\n");
        bookingDetails.append("Number of Days: ").append(booking.getNumDays()).append("\n");
        bookingDetails.append("Accommodation: ").append(ACCOMMODATIONS[booking.getAccommodationChoice()]).append("\n");
        bookingDetails.append("Subtotal: £").append(String.format("%.2f", booking.getSubTotal())).append("\n");
        bookingDetails.append("Leaving Date: ").append(new SimpleDateFormat("dd/MM/yyyy").format(booking.getLeavingDate())).append("\n");
        bookingDetails.append("You will shortly receive an email with your booking confirmation.!");

//The message with the booking confirmation will be displayed.
        JOptionPane.showMessageDialog(this, bookingDetails.toString(), "Booking Details", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VacayStarApp2 app = new VacayStarApp2();
            app.setVisible(true);
        });
    }
}
