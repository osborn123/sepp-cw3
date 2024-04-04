package view;

import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

import java.util.Collection;

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


    boolean getYesNoInputs(String message);
}
