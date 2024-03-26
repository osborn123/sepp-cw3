package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

public class MenuController extends Controller {
    protected MenuController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }

    public void mainMenu(){

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
