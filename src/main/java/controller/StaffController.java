package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

public class StaffController extends Controller{
    public StaffController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc,view,as,es);

    }

    protected void getInquiryTitles(){

    }
    protected void respondToInquiry(){

    }
}
