/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookingsystemapp.bookingmanagementsystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Dorina
 */
// Define the BookingManager class to manage bookings
public class BookingManager {

    // ArrayList to store booking objects
    private ArrayList<Booking> bookings;

    // Constructor to initialize the ArrayList
    public BookingManager() {
        this.bookings = new ArrayList<>();
    }

    // Method to add a booking to the ArrayList
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    // Method to remove a booking from the ArrayList
    public void removeBooking(int index) {
        if (index >= 0 && index < bookings.size()) {
            bookings.remove(index);
        } else {
            System.out.println("Invalid booking index.");
        }
    }

    // Method to update a booking in the ArrayList
    public void updateBooking(int index, Booking booking) {
        if (index >= 0 && index < bookings.size()) {
            bookings.set(index, booking);
        } else {
            System.out.println("Invalid booking index.");
        }
    }

    // Method to get all bookings
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    // Method to save bookings to a CSV file
    public void saveBookingsToCsv(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (Booking booking : bookings) {
            writer.append(booking.toString());
            writer.append('\n');
        }
        writer.flush();
        writer.close();
    }
    
    

    // Method to view existing bookings
    public void viewExistingBookings(JList<String> bookingList) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Booking booking : bookings) {
            model.addElement(booking.toString());
        }
        bookingList.setModel(model);
    }
    
    
    
}
