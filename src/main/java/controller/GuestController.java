package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.TextUserInterface;
import view.View;

public class GuestController extends Controller{
    public GuestController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
    }
    public void login(){
        TextUserInterface tui = new TextUserInterface();
        String username = tui.getInputString("Enter your username: ");
        String password = tui.getInputString("Enter your password: ");
        String response = as.login(username, password);
        if(response.length() == 2){
            as.
        }
        System.out.println(response);
    }
}
