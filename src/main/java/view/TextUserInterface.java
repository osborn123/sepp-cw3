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

    }

    @Override
    public void displaySuccess(String str) {
        System.out.println(str);
    }

    @Override
    public void displayWarning(String str) {
        System.out.println(str);
    }

    @Override
    public void displayError(String str) {
        System.out.println(str);
    }

    @Override
    public void displayException(String e) {
        System.out.println(e);
    }

    @Override
    public void displayDivider() {

    }

    @Override
    public void displayFAQ(FAQ faq, boolean bool) {

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
