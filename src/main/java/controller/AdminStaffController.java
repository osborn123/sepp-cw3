package controller;

import external.AuthenticationService;
import external.EmailService;
import external.MockEmailService;
import model.AuthenticatedUser;
import model.Inquiry;
import model.Page;
import model.SharedContext;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public void manageFAQ(){

    }
    private void addFAQItem(){

    }
    public void viewAllPages(){

    }
    public void manageInquiries(){

    }
    private void redirectInquiry(){

    }
}
