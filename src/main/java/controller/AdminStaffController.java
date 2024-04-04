package controller;

import external.AuthenticationService;
import external.EmailService;
import external.MockEmailService;
import model.*;
import view.View;


import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;

public class AdminStaffController extends StaffController{
    public AdminStaffController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);

    }

    public String readFileContent(String filePath) {
        try {
            // Attempt to obtain a URL to the specified resource file.
            URL resourceUrl = getClass().getClassLoader().getResource(filePath);

            // If the resource URL is null, the resource file does not exist in the classpath.
            if (resourceUrl == null) {
                throw new IOException("Resource file not found: " + filePath);
            }

            // Convert the resource URL to a Path object, necessary for reading the file content.
            Path resourcePath = Paths.get(resourceUrl.toURI());

            // Read the entire content of the file into a String using UTF-8 encoding.
            // This operation is performed in a single step for simplicity.
            return Files.readString(resourcePath, StandardCharsets.UTF_8);

        } catch (IOException | URISyntaxException e) {
            // An IOException is thrown if the file cannot be read (including if the file does not exist).
            // A URISyntaxException is thrown if the URL to the resource file is not formatted correctly.

            // Print an error message to standard error, including the exception's message to assist with troubleshooting.
            System.err.println("Error reading file: " + e.getMessage());

            // Return null to indicate that an error occurred and the content could not be read.
            return null;
        }
    }

    public void addPage(){
        String title = view.getInputString("Enter page title:");

        String content = null; // Initialize content to null to start the content acquisition process

        // Keep asking for the content file path until valid content is obtained or the user decides to stop trying
        while (content == null) {
            // Ask the user for the file path where the page content is located
            String contentPath = view.getInputString("Enter page content file path (e.g., file.txt):");

            // Attempt to read the page content from the provided file path
            content = readFileContent(contentPath);

            // If the content is still null after attempting to read the file, it indicates an error in reading the file
            if (content == null) {
                // Prompt the user to decide whether to try reading from another file path due to the error
                boolean tryAgain = view.getYesNoInputString("Error reading file. Would you like to try another file path? (Yes/No)");

                // If the user decides not to try again, exit the method early
                if (!tryAgain) {
                    return;
                }
            }
            Boolean isPrivate = view.getYesNoInputString("Should this page be private?");

// Retrieve a map of already available pages
            Map<String, Page> availablePages = sc.getPages();

// Check if the page title already exists in the available pages
            if (availablePages.containsKey(title)) {
                // Ask the user whether to overwrite the existing page
                boolean overwrite = view.getYesNoInputString("Page " + title + " already exists. Overwrite with new page?");
                if (!overwrite) {
                    // If the user decides not to overwrite, cancel the operation
                    view.displayInfo("Cancelled adding new page.");
                    return; // Early return to stop executing the rest of the method
                }
            }

// Create a new Page object with the title, content, and privacy status
            Page newPage = new Page(title, content, isPrivate);

// Add the new page to the shared context
            sc.addPage(newPage);

// Retrieve the email of the currently authenticated user
            String senderEmail = ((AuthenticatedUser)(sc.getCurrentUser())).getEmail();

// Attempt to send an email notification about the new page
            int status = es.sendEmail(senderEmail, SharedContext.getAdminStaffEmail(), newPage.getTitle(), newPage.getContent());

// Check the status of the email send operation and display appropriate feedback
            if (status == es.STATUS_SUCCESS){
                // If the email was sent successfully, notify the user of the successful addition
                view.displaySuccess("Added page " + title + " and email notification sent.");
            } else {
                // If the email send operation failed, notify the user that the page was added but the email was not sent
                view.displayWarning("Added page " + title + " but failed to send email notification!");
            }



        }

    }


    public void manageFAQ() {

        // Retrieve the FAQ from the shared context
        FAQ faq = sc.getFAQ();
        view.displayInfo("Welcome!");
        User currentUser = sc.getCurrentUser();

        if (currentUser != null && currentUser instanceof AuthenticatedUser) {
            String userEmail = ((AuthenticatedUser) currentUser).getEmail();
        }

        // Display the FAQ management options
        FAQSection currentSection = null;
        int optionNo = 0;
        while (!(currentSection == null && optionNo == -1)) {
            if (!faq.getSections().isEmpty()) {
                if (currentSection == null) {

                    view.displayFAQ(faq,true);
                    view.displayInfo("[-1] to return to main menu ");
                } else {
                    view.displayFAQSection(currentSection,true);
                    if (currentSection.getParent() == null) {
                        view.displayInfo("[-1] to return to main menu");
                    } else {

                        view.displayInfo("[-1] to return to " + currentSection.getParent().getTopic());
                    }
                }

                if (currentSection != null) {
                    view.displayInfo("[-2] to add a new question-answer pair");
                } else {
                    view.displayInfo("[-2] to add a new question-answer pair in a new section");
                }
            }
            else {

                view.displayInfo("\nCurrently, there is no question-answer pair for this.\n");
                view.displayInfo("[-1] to return to main menu");
                view.displayInfo("[-2] to add a new question-answer pair in a new section");
            }
            // Prompt the user for an option
            try {
                optionNo = Integer.parseInt(view.getInputString("Please choose an option from -1,-2, 0 (refresh page) "));

                switch (optionNo) {
                    case -1:
                        if (currentSection != null) {
                            if (currentSection.getParent() != null) {
                                currentSection = currentSection.getParent();
                                optionNo = 0;
                            } else {
                                // user wants to return to menu.
                                currentSection = null;
                            }
                        }
                        break;

                    case -2:
                        String question = view.getInputString("Enter question: ");
                        String answer = view.getInputString("Enter answer: ");

                        if (!question.isEmpty() && !answer.isEmpty()) {
                            if (currentSection == null) {
                                String newSection = view.getInputString("Enter name of new section to create: ");
                                if (!newSection.isEmpty()) {
                                    FAQSection actualSection = faq.getSections().get(newSection);
                                    if (actualSection != null) {

                                        view.displayWarning("A section with that name already exists. The question has been added to that section.");

                                        actualSection.addItem(question, answer);

                                        Collection<String> subscribers = sc.usersSubscribedToFAQTopic(newSection);
                                        if (!subscribers.isEmpty()) {
                                            for (String subscriberEmail : subscribers) {

                                                es.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, subscriberEmail, "Update on"+ question, answer);
                                            }
                                        } else{
                                            view.displayInfo("Updated!");
                                        }
                                    }

                                    else {
// Create a new section and add the question to it
                                        Map<String, String> items = new HashMap<>();
                                        items.put(question, answer);
                                        faq.addSectionItems(newSection, items);
                                        view.displayInfo("The question has been added to the new section.");
                                    }
                                } else {
                                    view.displayInfo("The section name must not be empty. Cancelling operation.");
                                }
                            } else {
// Ask the user if they want to add the question to a new subsection
                                boolean createNewSection = view.getYesNoInputString("Would you like to add the question to a new subsection?");
                                if (createNewSection) {

                                    String newSection = view.getInputString("Enter the name of the new subsection. ");
                                    if (!newSection.isEmpty()) {

                                        FAQSection subsection = null;
                                        for (FAQSection sub : currentSection.getSubsections()) {
                                            if (sub.getTopic().equals(newSection)) {
                                                subsection = sub;
                                                break;
                                            }
                                        }
// Check if the subsection already exists
                                        if (subsection != null) {

                                            view.displayWarning("A subsection with that name already exists. The question is added these.");
                                            subsection.addItem(question, answer);
                                        } else {

                                            FAQSection newSubsection = new FAQSection(newSection);
                                            newSubsection.addItem(question, answer);
                                            currentSection.addSubsection(newSubsection);
                                            view.displayInfo("The question is added to the new subsection.");
                                        }
                                    } else {
                                        view.displayInfo("The subsection name must not be empty. Cancelling operation...");
                                    }
                                } else {
// Add the question to the current section
                                    currentSection.addItem(question, answer);
                                    view.displayInfo("The question has just been added to the current section.");

                                }
                            }
                        } else {
                            view.displayInfo("The question and answer must not be empty. Cancelling operation...");
                        }
                        break;

                    default:
                        if (currentSection != null) {
                            List<FAQSection> subsectionsList = new ArrayList<>(currentSection.getSubsections());
                            if (currentSection.getSubsections().size() > optionNo) {

                                currentSection = subsectionsList.get(optionNo);
                            } else {
                                view.displayInfo("Invalid option: " + optionNo);
                            }
                        } else {
                            List<FAQSection> sections = new ArrayList<>(faq.getSections().values());
                            if (sections.size() > optionNo) {
                                currentSection = sections.get(optionNo);
                            } else {
                                view.displayInfo("Invalid option: " + optionNo);
                            }
                        }
                        break;
                }

            }//end of try
            catch (NumberFormatException e){
                view.displayInfo("Invalid option " + optionNo +".");
                view.displayInfo("Please enter a number");
            }
        }
    }
    private void notifySubscribers(String sectionName, String question, String answer) {
        Collection<String> subscribers = sc.usersSubscribedToFAQTopic(sectionName);
        if (subscribers != null && !subscribers.isEmpty()) {
            for (String subscriberEmail : subscribers) {
                es.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, subscriberEmail, "Update on " + question, answer);
            }
            view.displayInfo("Subscribers notified.");
        }
    }


    private void addFAQItem(FAQSection section) {
        // Retrieve the FAQ from the shared context
        FAQ faq = sc.getFAQ();

        // Inform the user about the addition process
        view.displayInfo("Adding a new FAQ item. Please provide the necessary details.");

        // Prompt for the question and answer
        String question = view.getInputString("Enter the question:");
        String answer = view.getInputString("Enter the answer:");

        // Validate the input
        if (question.isEmpty() || answer.isEmpty()) {
            view.displayError("Both question and answer must be provided. Operation cancelled.");
            return;
        }

        // Prompt for the section name
        String sectionName = view.getInputString("Enter the name of the section for this FAQ item:");

        // Attempt to retrieve the section by name
        section = faq.getSections().get(sectionName);
        if (section == null) {
            // If the section doesn't exist, ask if the user wants to create a new one
            boolean createNewSection = view.getYesNoInputString("The section does not exist. Would you like to create it?");
            if (createNewSection) {
                // Create a new section and add the FAQ item
                Map<String, String> items = new HashMap<>();
                items.put(question, answer);
                faq.addSectionItems(sectionName, items);
                view.displaySuccess("New section created and FAQ item added successfully to: " + sectionName);
            } else {
                // Operation cancelled by the user
                view.displayInfo("Operation cancelled.");
                return;
            }
        } else {
            // Add the item to the existing section
            section.addItem(question, answer);
            view.displaySuccess("FAQ item added successfully to section: " + sectionName);
        }
    }

    /**
     * Displays all available pages to the user and allows them to view a specific page,
     * add a new page, or exit the viewing interface.
     */
    public void viewAllPages() {
        Map<String, Page> availablePages = sc.getPages();

        // Check if there are any pages to display
        if (availablePages.isEmpty()) {
            view.displayInfo("No pages currently available.");
            return;
        }

        List<String> titles = new ArrayList<>(availablePages.keySet());

        // Loop until the user decides to exit
        while (true) {
            // Display each available page title with an index
            for (int i = 0; i < titles.size(); i++) {
                view.displayInfo(i + ": " + titles.get(i));
            }
            // Provide options for adding a new page or exiting
            view.displayInfo(titles.size() + ": Add a new page");
            view.displayInfo((titles.size() + 1) + ": Exit");

            try {
                // Prompt the user for their choice
                int choice = Integer.parseInt(view.getInputString("Select a page to view, add a new page, or exit:"));

                if (choice == titles.size()) { // Option to add a new page
                    addPage();
                    // Optionally, break if you want to return to the menu after adding a page, or continue to refresh the list
                    continue; // Refresh the list of pages in case a new page was added
                } else if (choice == titles.size() + 1) { // Option to exit
                    break;
                } else if (choice >= 0 && choice < titles.size()) { // A valid page selection
                    Page selectedPage = availablePages.get(titles.get(choice));
                    // Display the selected page's title and content
                    view.displayInfo("Title: " + selectedPage.getTitle());
                    view.displayInfo("Content: " + selectedPage.getContent());
                    view.displayDivider();
                } else {
                    // If the user's choice doesn't match any valid option
                    view.displayError("Invalid selection. Please enter a number between 0 and " + (titles.size() + 1) + ".");
                }
            } catch (NumberFormatException e) {
                // Handle cases where the input is not an integer
                view.displayError("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Allows the user to manage inquiries by listing all available inquiries,
     * then providing options to view, respond to, or redirect an inquiry, or cancel the operation.
     */
    public void manageInquiries() {
        ArrayList<Inquiry> inquiries = sc.getInquiries();

        // Check if there are any inquiries to manage
        if (inquiries.isEmpty()) {
            view.displayInfo("No unanswered inquiries at this time.");
            return;
        }

        // Display titles of all inquiries
        Collection<String> inquiryTitles = getInquiryTitles(inquiries);
        int index = 0;
        for (String title : inquiryTitles) {
            view.displayInfo(index + ": " + title);
            index++;
        }

        // Get user selection for which inquiry to manage
        int chosenIndex = getUserSelection(inquiries.size());

        // If user selects valid inquiry, display it and provide options for next steps
        if (chosenIndex != -1) {
            Inquiry selectedInquiry = inquiries.get(chosenIndex);
            view.displayInquiry(selectedInquiry);

            manageSelectedInquiry(selectedInquiry);
        }
    }

    /**
     * Prompts the user for a selection and validates it.
     *
     * @param size The number of available inquiries.
     * @return The index of the selected inquiry or -1 if the user cancels.
     */
    private int getUserSelection(int size) {
        int chosenIndex;
        while (true) {
            try {
                chosenIndex = Integer.parseInt(view.getInputString("Enter the number of the inquiry to view, or -1 to cancel: "));
                if (chosenIndex == -1 || (chosenIndex >= 0 && chosenIndex < size)) {
                    return chosenIndex;
                } else {
                    view.displayError("Invalid selection. Please enter a number between 0 and " + (size - 1) + " or -1 to cancel.");
                }
            } catch (NumberFormatException e) {
                view.displayError("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Handles the selected inquiry by allowing the user to respond, redirect, or cancel.
     *
     * @param inquiry The selected inquiry to manage.
     */
    private void manageSelectedInquiry(Inquiry inquiry) {
        int action;
        while (true) {
            try {
                action = Integer.parseInt(view.getInputString("Enter 1 to respond, 2 to redirect, or -1 to cancel: "));
                switch (action) {
                    case 1:
                        respondToInquiry(inquiry);
                        return; // Exit after action is performed
                    case 2:
                        redirectInquiry(inquiry);
                        return; // Exit after action is performed
                    case -1:
                        return; // Exit if user chooses to cancel
                    default:
                        view.displayError("Invalid action. Please enter 1, 2, or -1.");
                        break; // Invalid action, ask again
                }
            } catch (NumberFormatException e) {
                view.displayError("Invalid input. Please enter a number.");
            }
        }
    }

    private void redirectInquiry(Inquiry inquiry){// Prompt the user to enter the email address to which the inquiry should be redirected
        String receiverEmail = view.getInputString("What email would you like to redirect to?");

        // Update the inquiry's assigned handler to the new email address
        inquiry.setAssignedTo(receiverEmail);

        // Compose and send a notification email to the new handler
        int status = es.sendEmail(
                SharedContext.ADMIN_STAFF_EMAIL, // From the admin staff's email
                receiverEmail, // To the new handler's email
                "Inquiry: " + inquiry.getSubject() + " sent by " + inquiry.getInquirerEmail(), // Email subject
                "Please log in to the Self Service Portal to review the inquiry" // Email body
        );

        // Check the status of the email send operation and display a message accordingly
        if (status == EmailService.STATUS_SUCCESS) {
            // If the email was successfully sent, inform the user
            view.displaySuccess("Inquiry successfully redirected and email notification sent.");
        } else {
            // If there was an issue sending the email, inform the user
            view.displayWarning("Failed to redirect the inquiry due to an email sending error.");
        }
    }

}

