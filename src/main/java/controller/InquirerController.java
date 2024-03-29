package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

public class InquirerController extends Controller{

    private SharedContext sc;
    private View view;
    private AuthenticationService as;
    private EmailService es;

    public InquirerController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
        //Assigning to class-level fields
        this.sc = sc;
        this.view = view;
        this.as = as;
        this.es = es;
    }
    public void consultFAQ(){

    }
    public void searchPages(){

    }
    public void contactStaff(){

    }
    public void requestFAQUpdates(String userEmail, String topic){
        // Prompts user for email if not provided
        if (userEmail == null){
            userEmail = view.getInputString("Please enter your email: ");
        }
        
        // Checks if user is authenticated before proceeding
        if (sc.getCurrentUser() == null) {
            view.displayError("User must be authenticated to register for updates.");
            return;
        }
        
            // Attempts to register user for updates on a specific topic
        boolean success = sc.registerForFAQUpdates(userEmail, topic);
        if (success){
            view.displaySuccess("Successfully registered" + userEmail + "for updates on " + topic);
        }
        else{
            view.displayError("Failed to register" + userEmail + "for updates on " + topic + ". Perhaps this email is already registered?");
        }

    }

    public void stopFAQUpdates(String userEmail, String topic) {
        // Checks if userEmail is null and prompts for it if so
        if (userEmail == null) {
            userEmail = view.getInputString("Please enter your email address");
        }
        
        // Attempts to unregister user for updates on a specific topic
        boolean success = sc.unregisterForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully unregistered " + userEmail + " for updates on " + topic);
        } else {
            view.displayError("Failed to unregister " + userEmail + " for updates on " + topic + ". Perhaps this email was not registered?");
        }
    }
}
