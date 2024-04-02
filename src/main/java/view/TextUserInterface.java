package view;

import model.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

/**
 * Implements a text-based user interface for interacting with the user through the console.
 * This class handles input and output operations, displaying information, warnings, errors,
 * and taking user inputs in various forms.
 */
public class TextUserInterface implements View {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user with a message and returns the input string.
     *
     * @param message The message to display to the user.
     * @return The input string from the user.
     */
    @Override
    public String getInputString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Prompts the user with a Yes/No question and returns the user's response as a boolean.
     *
     * @param message The Yes/No question to display to the user.
     * @return true if the user answers Yes, false if the user answers No.
     */
    @Override
    public boolean getYesNoInputString(String message) {
        while (true) {
            System.out.println(message + " (Yes/No)");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("yes")) {
                return true;
            } else if (input.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Input is invalid, please enter 'Yes' or 'No'.");
            }
        }
    }

    /**
     * Displays informational messages to the user.
     *
     * @param message The informational message to be displayed.
     */
    @Override
    public void displayInfo(String message) {
        System.out.println(message);
    }

    /**
     * Displays success messages to the user.
     *
     * @param message The success message to be displayed.
     */
    @Override
    public void displaySuccess(String message) {
        System.out.println("Success: " + message);
    }

    /**
     * Displays warning messages to the user.
     *
     * @param message The warning message to be displayed.
     */
    @Override
    public void displayWarning(String message) {
        System.out.println("Warning: " + message);
    }

    /**
     * Displays error messages to the user.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void displayError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays exceptions to the user.
     *
     * @param message The exception message to be displayed.
     */
    @Override
    public void displayException(String message) {
        System.out.println(message);
    }

    /**
     * Displays a divider line in the console to visually separate different sections.
     */
    @Override
    public void displayDivider() {
        System.out.println("-----------------------------------------");
    }

    /**
     * Displays FAQ information. If expanded is true, it also pauses execution to allow the user
     * to read the FAQ content before continuing.
     *
     * @param faq The FAQ to be displayed.
     * @param expanded If true, pause execution after displaying the FAQ.
     */
    public void displayFAQ(FAQ faq, boolean expanded) {
        System.out.println("FAQ sections: ");
        System.out.println();

        for (FAQSection section : faq.getSections().values()) {
            displayFAQSection(section, expanded);
        }

        if (expanded) {
            pauseExecution();
        }
    }

    /**
     * Pauses the program execution, waiting for the user to press Enter to continue.
     */
    private void pauseExecution() {
        System.out.println("Press Enter to continue...");
        try {
            // System.in.read() waits for any character, not just Enter.
            // To strictly wait for Enter, consider reading until a newline is encountered.
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursively displays an FAQ section and its items. If expanded is true,
     * it displays items and subsections within each section.
     *
     * @param faqSection The FAQ section to display.
     * @param expanded If true, display the section's items and subsections.
     */
    public void displayFAQSection(FAQSection faqSection, boolean expanded) {
        System.out.println("- " + faqSection.getTopic());

        if (expanded) {
            for (FAQItem item : faqSection.getItems()) {
                System.out.println("Q: " + item.getQuestion());
                System.out.println("A: " + item.getAnswer());
                System.out.println();
            }

            for (FAQSection subsection : faqSection.getSubsections()) {
                displayFAQSection(subsection, expanded);
            }
        }
    }

    /**
     * Displays an inquiry's details to the user.
     *
     * @param inquiry The inquiry to display.
     */
    @Override
    public void displayInquiry(Inquiry inquiry) {
        System.out.println("Inquiry from: " + inquiry.getInquirerEmail());
        System.out.println("Subject: " + inquiry.getSubject());
        System.out.println("Content: " + inquiry.getContent());
        System.out.println("Recipient: " + inquiry.getAssignedTo());
    }

    /**
     * Displays a collection of search results to the user.
     *
     * @param pageSearchResults The search results to display.
     */
    @Override
    public void displaySearchResults(Collection<PageSearchResult> pageSearchResults) {
        if (pageSearchResults.isEmpty()) {
            System.out.println("No result found...");
        } else {
            System.out.println("Search Results:");
            for (PageSearchResult result : pageSearchResults) {
                System.out.println(result.getFormattedContent());
            }
        }
    }
}
