package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

public class GuestController extends Controller{
    public GuestController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
    }
    public void login(){

    }
}
