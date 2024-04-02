package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.Inquiry;
import model.SharedContext;
import view.View;

import java.util.ArrayList;
import java.util.Collection;

public class TeachingStaffController extends StaffController {


    public TeachingStaffController(SharedContext sharedContext, View view,
                           AuthenticationService authenticationService,
                           EmailService emailService) {
        super(sharedContext, view, authenticationService, emailService);
    }


    protected Collection<String> getInquiryTitles(Collection<Inquiry> inquiries) {
        ArrayList<String> inquiryTitles = new ArrayList<>();
        for (Inquiry inquiry : inquiries) {
            inquiryTitles.add(inquiry.getSubject());
        }
        return inquiryTitles;
    }


    protected void respondToInquiry(Inquiry inquiry) {
        // Prompt the staff member for their response to the inquiry
        String answer = view.getInputString("What is your response to the inquiry?");

        // Retrieve the current user's role and email for use in the email response
        AuthenticatedUser currentUser = (AuthenticatedUser) sc.getCurrentUser();
        String role = currentUser.getRole();
        String senderEmail = currentUser.getEmail();

        // Attempt to send the email response
        int status = es.sendEmail(
                senderEmail, inquiry.getInquirerEmail(),
                "Subject: Answer to " + inquiry.getSubject() + " inquiry",
                "Content: " + answer);

        // Check if the email was successfully sent and provide feedback accordingly
        if (status == EmailService.STATUS_SUCCESS) {
            view.displaySuccess("Email Sent. Inquiry answered!");
            sc.getInquiries().remove(inquiry); // Remove the inquiry upon successful response
        } else {
            view.displayWarning("Failed to send the email. Please try again later.");
        }
    }
}