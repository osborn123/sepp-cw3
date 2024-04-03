package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.SharedContext;
import model.User;
import view.View;

import java.io.IOException;
import java.util.List;

public class MenuController extends Controller {
    // enums
    public enum GuestMainMenuOption {
        LOGIN,
        CONSULT_FAQ,
        SEARCH_PAGES,
        CONTACT_STAFF
    }

    public enum StudentMainMenuOption {
        LOGOUT,
        CONSULT_FAQ,
        SEARCH_PAGES,
        CONTACT_STAFF
    }

    public enum TeachingStaffMainMenuOption {
        LOGOUT,
        MANAGE_RECEIVED_QUERIES
    }

    public enum AdminStaffMainMenuOption {
        LOGOUT,
        MANAGE_QUERIES,
        ADD_PAGE,
        SEE_ALL_PAGES,
        MANAGE_FAQ
    }



    private InquirerController inquirerController;
    private AdminStaffController adminStaffController;
    private TeachingStaffController teachingStaffController;
    private GuestController guestController;
    private AuthenticatedUserController authenticatedUserController;
    private StaffController staffController;

    public MenuController(SharedContext sharedContext, View view, AuthenticationService authenticationService,
                          EmailService emailService){
        super(sharedContext, view, authenticationService, emailService);
        inquirerController = new InquirerController(sharedContext, view, authenticationService, emailService);
        adminStaffController = new AdminStaffController(sharedContext, view, authenticationService, emailService);
        teachingStaffController = new TeachingStaffController(sharedContext, view, authenticationService, emailService);
        guestController = new GuestController(sharedContext, view, authenticationService, emailService);
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authenticationService,
                emailService);
    }


    public void mainMenu() throws IOException {
        User currentUser = sc.getCurrentUser();
        boolean res = true; // A flag to determine whether to return to the main menu

        if (currentUser instanceof model.Guest) {
            // Handle the guest main menu options
            res = handleGuestMainMenu();
        } else if (currentUser instanceof AuthenticatedUser) {
            // Cast the current user to AuthenticatedUser for role checking
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) currentUser;
            String role = authenticatedUser.getRole();

            // Handle main menu options based on the user's role
            if ("Student".equals(role)) {
                res = handleStudentMainMenu();
            } else if ("TeachingStaff".equals(role)) {
                res = handleTeachingStaffMainMenu();
            } else if ("AdminStaff".equals(role)) {
                res = handleAdminStaffMainMenu();
            } else {
                // Display an error if the role is unrecognized
                view.displayError("Unknown role: " + role);
            }
        } else {
            // Display an error if the user type is unrecognized
            view.displayError("Unknown user type: " + currentUser.getClass().getName());
        }

        // Recursively call mainMenu() if the flag is set to true, allowing the user to return to the main menu
        if (res) {
            mainMenu();
        }
    }

    private boolean handleGuestMainMenu() throws IOException {
        // Display guest main menu options and capture the selected option
        int selectedOption = guestController.selectFromMenu(
                List.of(GuestMainMenuOption.values()),
                "Please select an option from the above list: "
        );

        // Process the selected option
        switch (selectedOption) {
            case -1:
                // Exit
                return false;
            case 0:
                // Login
                guestController.login();
                break;
            case 1:
                // Consult FAQ
                inquirerController.consultFAQ();
                break;
            case 2:
                // Search pages
                inquirerController.searchPages();
                break;
            case 3:
                // Contact staff
                inquirerController.contactStaff();
                break;
            default:
                // Handle unknown option
                view.displayError("Unknown option: " + selectedOption);
                break;
        }
        return true;
    }

    private boolean handleStudentMainMenu() throws IOException {
        int selectedOption = inquirerController.selectFromMenu(List.of(StudentMainMenuOption.values()), "Please select an option from the above list: ");
        switch (selectedOption) {
            case -1: return false; // Exit
            case 0: authenticatedUserController.logout(); break; // Logout
            case 1: inquirerController.consultFAQ(); break; // Consult FAQ
            case 2: inquirerController.searchPages(); break; // Search pages
            case 3: inquirerController.contactStaff(); break; // Contact staff
            default: view.displayError("Unknown option: " + selectedOption); break; // Error handling
        }
        return true;
    }

    private boolean handleTeachingStaffMainMenu() {
        int selectedOption = teachingStaffController.selectFromMenu(List.of(TeachingStaffMainMenuOption.values()), "Please select an option from the above list: ");
        switch (selectedOption) {
            case -1: return false; // Exit
            case 0: authenticatedUserController.logout(); break; // Logout
            case 1: teachingStaffController.manageReceivedInquiries(); break; // Manage received inquiries
            default: view.displayError("Unknown option: " + selectedOption); break; // Error handling
        }
        return true;
    }
    private boolean handleAdminStaffMainMenu() {
        int selectedOption = adminStaffController.selectFromMenu(List.of(AdminStaffMainMenuOption.values()), "Please select an option from the above list: ");
        switch (selectedOption) {
            case -1: return false; // Exit
            case 0: authenticatedUserController.logout(); break; // Logout
            case 1: adminStaffController.manageInquiries(); break; // Manage received inquiries
            case 2: adminStaffController.addPage(); break; // Add page
            case 3: adminStaffController.viewAllPages(); break; // See all pages
            case 4: adminStaffController.manageFAQ(); break; // Manage FAQ
            default: view.displayError("Unknown option: " + selectedOption); break; // Error handling
        }
        return true;
    }

}
