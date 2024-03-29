package view;

// import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

import java.util.Collection;
import java.util.Scanner;

public class TextUserInterface implements View {
    private Scanner scanner;

    public TextUserInterface() {
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public String getInputString(String str) {
        System.out.println(str);
        return scanner.nextLine();
    }

    @Override
    public boolean getYesNoInputString(String str) {
        return false;
    }

    @Override
    public void displayInfo(String str) {

    }

    @Override
    public void displaySuccess(String str) {

    }

    @Override
    public void displayWarning(String str) {

    }

    @Override
    public void displayError(String str) {

    }

    @Override
    public void displayException(String str) {

    }

    @Override
    public void displayDivider() {

    }

    @Override
    public void displayFAQ(FAQSection faq, boolean bool) { //Changed FAQ to FAQSection

    }

    @Override
    public void displayFAQSection(FAQSection faqSection, boolean bool) {

    }

    @Override
    public void displayInquiry(Inquiry inquiry) {

    }

    @Override
    public void displaySearchResults(Collection<PageSearchResult> pageSearchResultCollection) {

    }
}

