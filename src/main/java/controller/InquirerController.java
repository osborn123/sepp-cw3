package controller;

import external.AuthenticationService;
import external.EmailService;
import model.*;
import view.View;

import java.io.IOException;
import java.util.*;

import org.apache.lucene.queryparser.classic.ParseException;
public class InquirerController extends Controller{

//    private SharedContext sc;
//    private View view;
//    private AuthenticationService as;
//    private EmailService es;

    public InquirerController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
//        //Assigning to class-level fields
//        this.sc = sc;
//        this.view = view;
//        this.as = as;
//        this.es = es;
    }
    public void consultFAQ() {
        view.displayDivider();
        // Display the welcome message
        view.displayInfo("Welcome to FAQ!");
        view.displayDivider(); // Display the welcome message
        FAQSection currentSection = null;
        int optionNo = 0;
        User currentUser = sc.getCurrentUser();
        String userEmail = null;// Initialize userEmail to null

        if (currentUser instanceof AuthenticatedUser) {// Check if the current user is authenticated
            userEmail = ((AuthenticatedUser) currentUser).getEmail();
        }
        while (currentSection != null || optionNo != -1) {// Loop until the user chooses to exit
            if (currentSection == null) {
                FAQ faq = sc.getFAQ();
                view.displayFAQ(faq,true);
                view.displayInfo("[-1] to return to main menu");
            } else {// Display the current section and options
                view.displayFAQSection(currentSection,true);
                FAQSection parent = currentSection.getParent();
                if (parent == null) {// Display the main menu
                    view.displayInfo("[-1] to return to FAQ");
                } else {// Display the parent topic
                    String topic = parent.getTopic();
                    view.displayInfo("[-1] to return to " + topic);
                }
                if (userEmail == null) {// Check if the user is not authenticated
                    view.displayInfo("[-2] to request updates for this topic");
                    view.displayInfo("[-3] to stop receiving updates for this topic");
                } else {// Check if the user is authenticated
                    String topic = currentSection.getTopic();
                    Collection<String> subscribers = sc.usersSubscribedToFAQTopic(topic);
// Display the option to stop receiving updates
                    if (subscribers != null && subscribers.contains(userEmail)) {
                        view.displayInfo("[-2] to stop receiving updates for this topic");
                    } else {// Display the option to request updates
                        view.displayInfo("[-2] to request updates for this topic");
                    }
                }
            }// Prompt the user for input
            view.displayInfo("Enter Topic number to get into the topic: ");
            String input = view.getInputString("Please choose an option");
            try {// Parse the input as an integer
                optionNo = Integer.parseInt(input);
                ArrayList<FAQSection> sections = null;
// Check if the input is a valid option
                if (optionNo != -1 && optionNo != -2 && optionNo != -3) {
                    if (currentSection == null) {
                        FAQ faq = sc.getFAQ();
// Get the sections of the FAQ
                        sections = new ArrayList<>(faq.getSections().values());
                    } else {
// Get the subsections of the current section
                        sections = (ArrayList<FAQSection>) currentSection.getSubsections();

                    }// Check if the option is out of bounds
                    if (optionNo < 0 || optionNo >= sections.size()) {
                        view.displayError("Invalid option " + optionNo);
                    } else {
// Get the selected section
                        currentSection = sections.get(optionNo);
                    }
                }// Check if the user chose to return to the main menu
                if (currentSection != null) {
                    String topic = currentSection.getTopic();
                    if (userEmail == null && optionNo == -2) {
// Request updates for the topic
                        requestFAQUpdates(null, topic);
                    }
                    if (userEmail == null && optionNo == -3) {
                        stopFAQUpdates(null, topic);
                    }// Check if the user is authenticated
                    if (userEmail != null && optionNo == -2) {
                        Collection<String> subscribers = sc.usersSubscribedToFAQTopic(topic);
                        userEmail = ((AuthenticatedUser) currentUser).getEmail();
                        if (subscribers.contains(userEmail)) {// Stop receiving updates for the topic
                            stopFAQUpdates(userEmail, topic);
                        } else {
                            requestFAQUpdates(userEmail, topic);
                        }
                    }
                    if (optionNo == -1) {
//
                        FAQSection parent = currentSection.getParent();
                        optionNo = 0;
                        currentSection = parent;
                    }
                }// Catch any exceptions thrown during parsing
            } catch (NumberFormatException e) {
                view.displayError("Invalid option " + optionNo);
            }
        }
    }



    public void searchPages() {
        // Prompt the user for a search query
        String searchQuery = view.getInputString("Enter your search query: ");

        // Initialize the availablePages map to ensure it's not null
        Map<String, Page> availablePages = new HashMap<>(sc.getPages());

        // Check the current user's access rights
        User currentUser = sc.getCurrentUser();

        // If the current user is a guest or not logged in, filter out private pages
        if (currentUser instanceof Guest || currentUser == null) {
            filterPrivatePages(availablePages);
        }

        // Attempt to perform the search operation
        try {
            PageSearch search = new PageSearch(availablePages);
            Collection<PageSearchResult> results = search.search(searchQuery);

            // Display the search results, ensuring there are at most 4 results due to internal logic
            view.displayDivider();
            view.displaySearchResults(results);
            view.displayDivider();
        } catch (IOException ioException) {
            view.displayException("IO Error during search: " + ioException.getMessage());
        } catch (ParseException parseException) {
            view.displayException("Parse Error during search: " + parseException.getMessage());
        }
    }

    /**
     * Filters out private pages from a map of pages, modifying the original map.
     *
     * @param pages The map of pages to filter.
     */
    private void filterPrivatePages(Map<String, Page> pages) {
        // Use Iterator to safely remove entries while iterating
        for (Iterator<Map.Entry<String, Page>> it = pages.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Page> entry = it.next();
            if (entry.getValue().isPrivate()) {
                it.remove(); // Remove private pages from the list
            }
        }

    }

    public void contactStaff(){
        String subject = view.getInputString("What is the subject of your inquiry?");
        String content = view.getInputString("What is the content of your inquiry");
        String email = null;
        // Check if the current user is authenticated
        if (sc.getCurrentUser() instanceof AuthenticatedUser) {
            // Cast the user to AuthenticatedUser to access email
            AuthenticatedUser currentUser = (AuthenticatedUser) sc.getCurrentUser();
            email = currentUser.getEmail();
        } else {
            // If not authenticated, prompt the user to enter their email
            email = view.getInputString("Enter your email:");
        }

// Create a new Inquiry object with the provided subject, content, and email
        Inquiry inquiry = new Inquiry(subject, content, email);

// Add the inquiry to the shared context
        sc.getInquiries().add(inquiry);

// Notify the user that the inquiry has been sent
        view.displaySuccess("Inquiry sent");

// Attempt to send an email notification about the new inquiry
        int status = es.sendEmail(
                email,
                SharedContext.getAdminStaffEmail(),
                "New Inquiry subject: " + inquiry.getSubject(),
                "Please login to the self-service portal to review inquiry\nInquiry: " + inquiry.getContent()
        );

// Check the result of the email sending attempt
        if (status == EmailService.STATUS_SUCCESS) {
            // If the email was successfully sent, notify the user
            view.displaySuccess("Inquiry Sent " + inquiry.getSubject());
        } else {
            // If there was an issue sending the email, warn the user
            view.displayWarning("Added Inquiry " + inquiry.getSubject() + " but failed to send email notification!");
        }
    }
    public void requestFAQUpdates(String userEmail, String topic){
        // Prompts user for email if not provided
        if (userEmail == null){
            userEmail = view.getInputString("Please enter your email: ");
        }
        
        // Checks if user is authenticated before proceeding
        if (sc.getCurrentUser() == null) {
            view.displayError("User must be authenticated to register for updates.");
            return;
        }
        
            // Attempts to register user for updates on a specific topic
        boolean success = sc.registerForFAQUpdates(userEmail, topic);
        if (success){
            view.displaySuccess("Successfully registered" + userEmail + "for updates on " + topic);
        }
        else{
            view.displayError("Failed to register" + userEmail + "for updates on " + topic + ". Perhaps this email is already registered?");
        }

    }

    public void stopFAQUpdates(String userEmail, String topic) {
        // Checks if userEmail is null and prompts for it if so
        if (userEmail == null) {
            userEmail = view.getInputString("Please enter your email address");
        }
        
        // Attempts to unregister user for updates on a specific topic
        boolean success = sc.unregisterForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully unregistered " + userEmail + " for updates on " + topic);
        } else {
            view.displayError("Failed to unregister " + userEmail + " for updates on " + topic + ". Perhaps this email was not registered?");
        }
    }
}
