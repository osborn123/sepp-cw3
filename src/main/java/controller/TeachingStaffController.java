package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

public class TeachingStaffController extends StaffController{
    public TeachingStaffController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view,as, es);

    }
    public void manageReceivedInquiries(){

    }
}
