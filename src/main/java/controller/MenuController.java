package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Guest;
import model.SharedContext;
import view.View;
public class MenuController extends Controller {
    public MenuController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }
    private void invalidNum(){
        view.displayError("Please enter a valid integer");
        mainMenu();
    }
    public void mainMenu(){
        String welcomeStr = "";
        if (handleGuestMainMenu()){
            welcomeStr += LOGIN_string + FAQ_string + WEBPAGE_string + CONTACT_string;
            String inp = view.getInputString(welcomeStr);
            if (inp.equals(GuestMainMenuOption.LOGIN.getString())) {
                GuestController controller = new GuestController(sc, view, as, es);
                controller.login();
            }
            else{
                InquirerController ic = new InquirerController(sc, view, as, es);
                if (inp.equals(GuestMainMenuOption.CONSULT_FAQ.getString())) {
                    ic.consultFAQ();
                } else if (inp.equals(GuestMainMenuOption.SEARCH_PAGES.getString())) {
                    ic.searchPages();
                } else if (inp.equals(GuestMainMenuOption.CONTACT_STAFF.getString())) {
                    ic.contactStaff();
                } else {
                    invalidNum();
                }
            }
        } else if (handleStudentMainMenu()){
            welcomeStr += LOGOUT_string + FAQ_string + WEBPAGE_string + CONTACT_string;
            String inp = view.getInputString(welcomeStr);
            if (inp.equals(StudentMainMenuOption.LOGOUT.getString())) {
                sc.setCurrentUser(new Guest());
            } else {
                InquirerController ic = new InquirerController(sc, view, as, es);
                if (inp.equals(StudentMainMenuOption.CONSULT_FAQ.getString())) {
                    ic.consultFAQ();
                }
                else if (inp.equals(StudentMainMenuOption.SEARCH_PAGES.getString())) {
                    ic.searchPages();
                }
                else  if (inp.equals(StudentMainMenuOption.CONTACT_STAFF.getString())) {
                    ic.contactStaff();
                }
                else{
                    invalidNum();
                }
            }
        } else if (handleAdminStaffMainMenu()) {
            welcomeStr += LOGOUT_string + MANAGEQUERIES_string + ADDPAGE_string + SEE_ALL_PAGES_string + MANAGEFAQ_string;
            String inp = view.getInputString(welcomeStr);
            if (inp.equals(AdminStaffMainMenuOption.LOGOUT.getString())) {
                sc.setCurrentUser(new Guest());
            }
            else{
                AdminStaffController asc = new AdminStaffController(sc,view,as,es);
                if (inp.equals(AdminStaffMainMenuOption.MANAGE_QUERIES.getString())) {
                    asc.manageInquiries();
                }
                else if(inp.equals(AdminStaffMainMenuOption.ADD_PAGE.getString())) {
                    asc.addPage();
                }
                else if (inp.equals(AdminStaffMainMenuOption.SEE_ALL_PAGES.getString())) {
                    asc.viewAllPages();
                }
                else if (inp.equals(AdminStaffMainMenuOption.MANAGE_FAQ.getString())) {
                    asc.manageFAQ();
                }
                else {
                    invalidNum();
                }
        }
        } else if (handleTeachingStaffMainMenu()) {
            welcomeStr += LOGOUT_string + MRQ_string;
            String inp = view.getInputString(welcomeStr);
            if (inp.equals(TeachingStaffMainMenuOption.LOGOUT.getString())) {
                sc.setCurrentUser(new Guest());
                mainMenu(); //Loop back with main menu for logged out user
            } else if (inp.equals(TeachingStaffMainMenuOption.MANAGE_RECEIVED_QUERIES.getString())) {
                TeachingStaffController tsc = new TeachingStaffController(sc,view,as,es);
                tsc.manageReceivedInquiries();
            }
            else{
                invalidNum();
            }
        }
        else {
            view.displayError("Unkown Role");
        }
        mainMenu();
    }
    private boolean handleGuestMainMenu(){
        if (sc.getCurrentUser() instanceof Guest){
            return true;
        }
        return false;
    }
    private boolean handleStudentMainMenu() {
            if (sc.getCurrentUser().getRole().equals("Student")) {
                return true;
            }
        return false;
    }
    private boolean handleTeachingStaffMainMenu(){
        if (sc.getCurrentUser().getRole().equals("TeachingStaff")){
            return true;
        }
        return false;
    }
    private boolean handleAdminStaffMainMenu(){
        if (sc.getCurrentUser().getRole().equals("AdminStaff")){
            return true;
        }
        return false;
    }
    private static final String LOGIN_string = "Press 1 to login\n";
    private static final String FAQ_string =  "Press 2 to consult the FAQ\n";
    private static final String WEBPAGE_string = "Press 3 to consult the webpages\n";
    private static final String CONTACT_string = "Press 4 to consult a member of staff";
    private static final String LOGOUT_string = "Press 1 to logout\n";
    private static final String MRQ_string = "Press 2 to manage received queries";
    private static final String MANAGEQUERIES_string = "Press 2 to manage queries\n";
    private static final String ADDPAGE_string = "Press 3 to add a page\n";
    private static final String SEE_ALL_PAGES_string = "Press 4 to see all pages\n";
    private static final String MANAGEFAQ_string = "Press 5 to manage FAQ";
    enum GuestMainMenuOption{LOGIN("1"), CONSULT_FAQ("2"), SEARCH_PAGES("3"), CONTACT_STAFF("4");
        private String text;
        private GuestMainMenuOption(String text) {
            this.text = text;
        }
        public String getString() { return text; }
    }
    enum StudentMainMenuOption{LOGOUT("1"), CONSULT_FAQ("2"), SEARCH_PAGES("3"), CONTACT_STAFF("4");
        private String text;
        StudentMainMenuOption(String text){
            this.text = text;
        }
        public String getString(){
            return text;
        }
    }
    enum TeachingStaffMainMenuOption{LOGOUT("1"), MANAGE_RECEIVED_QUERIES("2");
        private String text;
        TeachingStaffMainMenuOption(String text){
            this.text = text;
        }
        public String getString(){
            return text;
        }}
    enum AdminStaffMainMenuOption{LOGOUT("1"), MANAGE_QUERIES("2"), ADD_PAGE("3"), SEE_ALL_PAGES("4"), MANAGE_FAQ("5");
        private String text;
        AdminStaffMainMenuOption(String text){
            this.text = text;
        }
        public String getString(){
            return text;
        }}

}
