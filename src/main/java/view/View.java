package view;

// import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

import java.util.Collection;
import java.util.Scanner; // Added import for Scanner

public interface View {
    // Default method to get input from the user with a prompt, using Scanner
    public default String getInputString(String prompt) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(prompt);
            return scanner.nextLine();
        }
    }

    public boolean getYesNoInputString(String str);

    public default void displayInfo(String info) {
        System.out.println(info);
    }

    public default void displaySuccess(String str) {
        System.out.println("Success: " + str);
    }

    // Default method to display a warning message to the user
    public default void displayWarning(String message) {
        // Default implementation to display failure message
        System.out.println("Failure: " + message);
    }

    public default void displayError(String str){
        System.out.println("Error: " + str);
    }

    public void displayException(String str);
    public void displayDivider();

    public default void displayFAQ(FAQSection faq, boolean isGuest) { // It was supposed to be FAQ faq, but getTopic and isPrivate are methods in FAQSection
        if (faq == null) {
            System.out.println("No FAQ available.");
            return;
        }
        System.out.println("FAQ Topic: " + faq.getTopic());
        if (isGuest && faq.isPrivate()) {
            System.out.println("This FAQ is private. Please log in to view the full content.");
        } else {
            // System.out.println(faq.getContent());
        }
    }

    public default void displayFAQSection(FAQSection section, boolean isGuest) {
        System.out.println("Topic: " + section.getTopic());
        if (isGuest && section.isPrivate()) {
            System.out.println("This section is private. Please log in to view.");
        } else {
            // System.out.println(section.getContent());
            // write method to display the content into FAQSection
        }
    }

    //Displays an inquiry to the user
    public default void displayInquiry(Inquiry inquiry) {
        System.out.println("Inquiry from: " + inquiry.getInquirerEmail());
        System.out.println("Subject: " + inquiry.getSubject());
        System.out.println("Content: " + inquiry.getContent());
        System.out.println("Receipent: " + inquiry.getAssignedTo());
    }

    public void displaySearchResults(Collection<PageSearchResult> pageSearchResultCollection);
}