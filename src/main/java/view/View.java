package view;
public interface View {
    String getInputString(String str);
    public boolean getYesNoInputString(String str);
    public void displayInfo(String str);
    public void displaySuccess(String str);
    public void displayWarning(String str);
    public void displayError(String str);
    public void displayException(String str);
    public void displayDivider();
    public void displayFAQ(boolean bool); //FAQ
    public void displayFAQSection(boolean bool);//FAQSection
    public void displayInquiry();//@arg Inquiry
    public void displaySearchResults(); //Collection<PagesSearchResult>
}
