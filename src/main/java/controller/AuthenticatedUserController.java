package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Guest;
import model.SharedContext;
import view.View;

public class AuthenticatedUserController extends Controller{
    protected AuthenticatedUserController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
    }

    public void logout(){
        sc.setCurrentUser(new Guest());
    }
}
