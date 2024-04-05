package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Guest;
import model.SharedContext;
import view.View;


// This class is responsible for handling the user's requests after they have logged in.
public class AuthenticatedUserController extends Controller{
    public AuthenticatedUserController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }
    // This method is responsible for handling the user's request to logout.

    public void logout() {
        view.displaySuccess("Logout successful!");
        sc.setCurrentUser(new Guest());

    }
}
