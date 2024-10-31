
VacayStar Booking System
A simple application for managing holiday bookings for VacayStar, designed to replace the existing paper and phone-call-based system. This system helps VacayStar streamline booking processes, calculate costs, save booking data, and manage booking history.
The VacayStar Booking System is a basic digital solution for managing holiday bookings. It allows users to select accommodation options, calculate costs (including VAT, card fees, and discounts), and save or view booking details. It is designed to streamline VacayStar’s booking process and provide a more user-friendly, reliable alternative to manual records.


The application includes the following functionality:

Booking Calculation:        Calculates subtotal based on the selected property and length of stay, applying discounts for extended bookings.
Accommodation Selection:    Allows the user to select from a range of accommodations.
Date Management:            Enables date input, affecting cost calculation.
Customer Information Input: Collects additional reservation details (e.g., name and contact number).
Booking Save Functionality: Saves booking information as a CSV file.
Admin View of Bookings:     Allows an admin user to view past bookings from saved CSV files.


Usage
Selecting a Property:          Choose a property type (Imperial Lodge, Sunshine Apt., etc.) from the available options.
Setting Dates:                 Enter the check-in and check-out dates to calculate the total cost.
Entering Customer Information: Fill in the customer's name and contact information.
Saving Bookings:               Save booking data to a CSV file for later reference.
Viewing Bookings (Admin):      Load past bookings to view customer details, dates, and calculated costs.



Cost Calculations
Cost calculations include several factors:

Daily Rate:                    Each property has a unique daily rate.
14-Day Discount:               A discount applies for bookings of 14 days or more.
Card Fee:                      A small fee added to each booking.
VAT:                           A 15% tax applied to the booking subtotal.

The following table provides an example of cost rates per accommodation type:


Card Fee	14 Day Discount*	Daily Rate	VAT
Imperial Lodge	£4.00	10%	£350	15%
Sunshine Apt.	£3.50	10%	£280	15%
Standard Cabin	£3.00	5%	£200	15%
Rustic Shed	£2.50	5%	£150	15%
Classic Caravan	£2.00	5%	£90	15%


Technologies Used
Programming Language:  Java
Data Storage: CSV file handling for booking information storage and retrieval.
