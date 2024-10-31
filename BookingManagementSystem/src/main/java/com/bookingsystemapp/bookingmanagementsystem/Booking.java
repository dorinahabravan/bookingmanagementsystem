package com.bookingsystemapp.bookingmanagementsystem;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Dorina
 */

//  Booking class to store booking information
 public class Booking {

    // variables to store booking details
    private String customerName;
    private String contactNumber;
    private String email;  // New email field
    private Date arrivalDate;
    private int numDays;
    private int accommodationChoice;
    private double subTotal;

    // Constructor to initialize booking details and using them for further development
    public Booking(String customerName, String contactNumber, String email, Date arrivalDate, int numDays, int accommodationChoice, double subTotal) {
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.arrivalDate = arrivalDate;
        this.numDays = numDays;
        this.accommodationChoice = accommodationChoice;
        this.subTotal = subTotal;
    }

    // Getter and Setter methods for booking details
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public int getAccommodationChoice() {
        return accommodationChoice;
    }

    public void setAccommodationChoice(int accommodationChoice) {
        this.accommodationChoice = accommodationChoice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    // Method to calculate the leaving date based on arrival date and number of days which is used then to confirm the details
    public Date getLeavingDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(arrivalDate);
        calendar.add(Calendar.DATE, numDays);
        return calendar.getTime();
    }
    


    //  method to represent the Booking object as a string
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Customer Name: " + customerName + ", Contact Number: " + contactNumber + ", Email: " + email
                + ", Arrival Date: " + dateFormat.format(arrivalDate) + ", Number of Days: " + numDays
                + ", Accommodation Choice: " + VacayStarApp2.ACCOMMODATIONS[accommodationChoice] + ", Subtotal: £" + subTotal;
    }

}