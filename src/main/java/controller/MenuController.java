package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;
public class MenuController extends Controller {
    public MenuController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }
    public void mainMenu(){
        String welcomeStr = "Welcome to the kiosk!\n";
        if (handleGuestMainMenu()){
            welcomeStr += LOGIN_string + FAQ_string + WEBPAGE_string + CONTACT_string;
            String inp = view.getInputString(welcomeStr);
            if (inp.equals(GuestMainMenuOption.LOGIN.getValue())){
                GuestController controller = new GuestController(sc, view, as, es);
                controller.login();
            } else if (inp.equals("2")) {

            } else if (inp.equals("3")) {

            } else if (inp.equals("4")) {

            } else{
                view.displayError("Please enter 1, 2, 3 or 4");
                mainMenu();
            }
}
    }
    private boolean handleGuestMainMenu(){
        if (sc.getCurrentUser().role.equals("")){
            return true;
        }
        return false;
    }
    private boolean handleStudentMainMenu() {
            if (sc.getCurrentUser().role.equals("Student")) {
                return true;
            }
        return false;
    }
    private boolean handleTeachingStaffMainMenu(){
        if (sc.getCurrentUser().role.equals("TeachingStaff")){
            return true;
        }
        return false;
    }
    private boolean handleAdminStaffMainMenu(){
        if (sc.getCurrentUser().role.equals("AdminStaff")){
            return true;
        }
        return false;
    }
    private static final String LOGIN_string = "Press 1 to login\n";
    private static final String FAQ_string =  "Press 2 to consult the FAQ\n";
    private static final String WEBPAGE_string = "Press 3 to consult the webpages\n";
    private static final String CONTACT_string = "Press 4 to consult a member of staff";
    private static final String LOGOUT_string = "Press 1 to logout\n";
    private static final String MRQ_string = "Press 2 to manage received queries\n";
    private static final String MANAGEQUERIES_string = "Press 2 to manage queries\n";
    private static final String ADDPAGE_string = "Press 3 to add a page\n";
    private static final String SEE_ALL_PAGES_string = "Press 4 to see all pages\n";
    private static final String MANAGEFAQ_string = "Press 5 to manage FAQ\n";
    enum GuestMainMenuOption{
        LOGIN("1"),
        CONSULT_FAQ("2");
        private String value;
        private GuestMainMenuOption(String value) {
            this.value = value;
        }

        public String getValue() { return value; }
    }
//    enum GuestMainMenuOption{LOGIN(LOGIN_string), CONSULT_FAQ(FAQ_string), SEARCH_PAGES(WEBPAGE_string), CONTACT_STAFF(CONTACT_string);
//        private String text;
//        GuestMainMenuOption(String text){
//            this.text = text;
//        }
//        public String getString(){
//            return text;
//        }
//    }
    enum StudentMainMenuOption{LOGIN(LOGIN_string), CONSULT_FAQ(WEBPAGE_string), SEARCH_PAGES(WEBPAGE_string), CONTACT_STAFF(CONTACT_string);
        private String text;
        StudentMainMenuOption(String text){
            this.text = text;
        }
        public String getString(){
            return text;
        }
    }
    enum TeachingStaffMainMenuOption{LOGOUT(LOGOUT_string), MANAGE_RECEIVED_QUERIES(MRQ_string);
        private String text;
        TeachingStaffMainMenuOption(String text){
            this.text = text;
        }
        public String getString(){
            return text;
        }}
    enum AdminStaffMainMenuOption{LOGOUT(LOGOUT_string), MANAGE_QUERIES(MANAGEQUERIES_string), ADD_PAGE(ADDPAGE_string), SEE_ALL_PAGES(SEE_ALL_PAGES_string), MANAGE_FAQ(MANAGEFAQ_string);
        private String text;
        AdminStaffMainMenuOption(String text){
            this.text = text;
        }
        public String getString(){
            return text;
        }}

}
