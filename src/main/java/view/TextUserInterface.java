package view;

import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

import java.util.Collection;
import java.util.Scanner;

public class TextUserInterface implements View {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String getInputString(String str) {
        System.out.println(str);
        return scanner.nextLine();
    }

    @Override
    public boolean getYesNoInputString(String str) {
        System.out.println(str);
        String inp = scanner.nextLine();
        if(inp.equals("yes") || inp.equals("Y")|| inp.equals("y")) {
            return true;
        } else if (inp.equals("No") || inp.equals("N")|| inp.equals("n")) {
            return false;
        }
        System.out.println("Valid inputs are only 'Yes','Y','y','No','N','n'");
        return getYesNoInputString(str);
    }

    @Override
    public void displayInfo(String str) {
        System.out.println(str);
    }

    @Override
    public void displaySuccess(String str) {
        System.out.println("Success: " + str);
    }

    @Override
    public void displayWarning(String str) {
        System.out.println("Warning: " + str);
    }

    @Override
    public void displayError(String str) {
        System.out.println("Error: " + str);
    }

    @Override
    public void displayException(String e) {
        System.out.println(e);
    }

    @Override
    public void displayDivider() {

    }
    @Override
    public void displayFAQ(FAQ faq, boolean bool) { // It was supposed to be FAQ faq, but getTopic and isPrivate are methods in FAQSection
//        if (faq == null) {
//            System.out.println("No FAQ available.");
//            return;
//        }
//        System.out.println("FAQ Topic: " + faq .getTopic());
//        if (bool && faq.isPrivate()) {
//            System.out.println("This FAQ is private. Please log in to view the full content.");
//        } else {
//            // System.out.println(faq.getContent());
//        }
    }

    @Override
    public void displayFAQSection(FAQSection section, boolean isGuest) {
        System.out.println("Topic: " + section.getTopic());
        if (isGuest && section.isPrivate()) {
            System.out.println("This section is private. Please log in to view.");
        } else {
            // System.out.println(section.getContent());
            // write method to display the content into FAQSection
        }
    }


    @Override
    //Displays an inquiry to the user
    public void displayInquiry(Inquiry inquiry) {
        System.out.println("Inquiry from: " + inquiry.getInquirerEmail());
        System.out.println("Subject: " + inquiry.getSubject());
        System.out.println("Content: " + inquiry.getContent());
        System.out.println("Receipent: " + inquiry.getAssignedTo());
    }


    @Override
    public void displaySearchResults(Collection<PageSearchResult> pageSearchResultCollection) {

    }
}
