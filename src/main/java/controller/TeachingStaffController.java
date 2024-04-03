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


    protected TeachingStaffController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }
    public void manageReceivedInquiries() {
        // Retrieve the titles of all inquiries assigned to the user
        Collection<String> inquiryTitles = getInquiryTitles(sc.getInquiries());

        // Check if there are any inquiries to manage
        if (inquiryTitles.isEmpty()) {
            view.displayInfo("No inquiries to manage.");
            return; // Exit if there are no inquiries
        }

        // Prompt the user to select an inquiry from the list
        int selectedOption = selectFromMenu(inquiryTitles, "Please select an inquiry to manage: ");

        // Handle the case where the user chooses to exit without making a selection
        if (selectedOption == -1) {
            return; // Exit the method
        }

        // Retrieve the selected inquiry based on the user's choice
        // Note: This approach assumes the order of inquiries in the collection matches the displayed list
        Inquiry selectedInquiry = new ArrayList<>(sc.getInquiries()).get(selectedOption);

        // Prompt the user to respond to the selected inquiry
        respondToInquiry(selectedInquiry);
    }


}