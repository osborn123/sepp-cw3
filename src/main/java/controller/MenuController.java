package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.TextUserInterface;
import view.View;

public class MenuController extends Controller {
    public MenuController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }

    public void mainMenu(){
        String welcomeStr = "Welcome to the kiosk!\nPress 1 to login\nPress 2 to consult the FAQ\nPress 3 to consult the webpages\nPress 4 to consult a member of staff";
        TextUserInterface tui = new TextUserInterface();
        String inp = tui.getInputString(welcomeStr);
        if (inp.equals("1")){
            GuestController controller = new GuestController(sc,view,as,es);
            controller.login();
        } else if (inp.equals("2")) {

        } else if (inp.equals("3")) {

        } else if (inp.equals("4")) {

        } else{
            tui.displayError("Please enter 1, 2, 3 or 4");
            mainMenu();
        }
    }
    private boolean handleGuestMainMenu(){
        return true;
    }
    private boolean handleStudentMainMenu() {
        return true;
    }
    private boolean handleTeachingStaffMainMenu(){
        return true;
    }
    private boolean handleAdminStaffMainMenu(){
        return true;
    }

    enum GuestMainMenuOption{LOGIN, CONSULT_FAQ, SEARCH_PAGES, CONTACT_STAFF}
    enum StudentMainMenuOption{LOGIN, CONSULT_FAQ, SEARCH_PAGES, CONTACT_STAFF}
    enum TeachingStaffMainMenuOption{LOGOUT, MANAGE_RECEIVED_QUERIES}
    enum AdminStaffMainMenuOption{LOGOUT, MANAGE_QUERIES, ADD_PAGE, SEE_ALL_PAGES, MANAGE_FAQ}

}
