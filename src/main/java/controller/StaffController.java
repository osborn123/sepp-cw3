package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.Inquiry;
import model.SharedContext;
import view.View;

import java.util.ArrayList;
import java.util.Collection;


public class StaffController extends Controller{
    public StaffController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }

    protected Collection<String> getInquiryTitles(Collection<Inquiry> inquiries) {
        ArrayList<String> inquiryTitles = new ArrayList<>();
        for (Inquiry inquiry : inquiries) {
            // Add the subject of each inquiry to the list of titles
            inquiryTitles.add(inquiry.getSubject());
        }
        return inquiryTitles;
    }
    protected void respondToInquiry(Inquiry inquiry) {
        // Prompt the user for a response to the inquiry
        String answer = view.getInputString("What is your response to the inquiry?");

        // Retrieve the role and email of the currently authenticated user
        AuthenticatedUser currentUser = (AuthenticatedUser) sc.getCurrentUser();
        String role = currentUser.getRole();
        String senderEmail = currentUser.getEmail();

        // Send an email with the response to the inquirer's email
        int status = es.sendEmail(
                senderEmail,
                inquiry.getInquirerEmail(),
                "Subject: Answer to " + inquiry.getSubject() + " inquiry",
                "Content: " + answer
        );

        // Check the status of the email send operation
        if (status == EmailService.STATUS_SUCCESS) {
            // If the email was successfully sent, notify the user and remove the inquiry from the context
            view.displaySuccess("Email Sent. Inquiry answered!");
            sc.getInquiries().remove(inquiry);
        } else {
            // If the email was not successfully sent, display a warning
            view.displayWarning("Failed to send the email. Please try again later.");
        }
    }
}
