package view;

import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

import java.util.Collection;
import java.util.Scanner; // Added import for Scanner

public interface View {
    String getInputString(String str);
    public boolean getYesNoInputString(String str);
    public void displayInfo(String str);
    public void displaySuccess(String str);
    public void displayWarning(String str);
    public void displayError(String str);
    public void displayException(String str);
    public void displayDivider();
    public void displayFAQ(FAQ faq, boolean bool);
    public void displayFAQSection(FAQSection faqSection, boolean bool);
    public void displayInquiry(Inquiry inquiry);
    public void displaySearchResults(Collection<PageSearchResult> pageSearchResultCollection);
    // Default method to get input from the user with a prompt, using Scanner
    default String getInput(String prompt) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(prompt);
            return scanner.nextLine();
        }
    }
    // Default method to display a failure message to the user
    default void displayFailure(String message) {
        // Default implementation to display failure message
        System.out.println("Failure: " + message); // Example implementation
    }
}