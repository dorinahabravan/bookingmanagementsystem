package com.bookingsystemapp.bookingmanagementsystem;



import javax.mail.MessagingException;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

/**
 *
 * @author Dorina
 */

public class EmailConfirmation {

    private static final String FROM_EMAIL = "info@bookingsystemapp.com"; // The email generated in AWS service to be used an email for booking confirmation.
     private static final Region REGION = Region.EU_WEST_2; 


//The method will send the email to the customer using Amazon Web Services
 public static void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        SesClient sesClient = SesClient.builder()
                .region(REGION)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();

        try {
            SendEmailRequest request = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(toEmail).build())
                    .message(Message.builder()
                            .subject(Content.builder().data(subject).charset("UTF-8").build())
                            .body(Body.builder().text(Content.builder().data(body).charset("UTF-8").build()).build())
                            .build())
                    .source(FROM_EMAIL)
                    .build();

            sesClient.sendEmail(request);
            System.out.println("Email sent successfully!");//If the customer introduce the email then will get the confirmation emai with the booking details.
        } catch (SesException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.awsErrorDetails().errorMessage());
        } finally {
            sesClient.close();
        }
    }
}








































