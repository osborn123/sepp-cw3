package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.SharedContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import view.View;
// This class is responsible for handling the user's requests before they have logged in.
public class GuestController extends Controller{
    public GuestController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
    }
    // This method is responsible for handling the user's request to login.
    public void login(){
        String email = view.getInputString("Enter your username: ");
        String password = view.getInputString("Enter your password: ");

        String response = as.login(email, password);
        JSONObject json = (JSONObject) JSONValue.parse(response);
        String err = (String)json.get("error");
        // If the response is empty, display an error message and prompt the user to login again.
        if(json.isEmpty()){
            view.displayError(err);
            login();
        }
        // If the response is not empty, create a new AuthenticatedUser object and set it as the current user.
        else{
            String role = (String)json.get("role");
            try {
                AuthenticatedUser currentUser = new AuthenticatedUser(email, role);
                view.displaySuccess("successfully logged in");
                sc.setCurrentUser(currentUser);
            }
            // If the role is not valid, display an error message and prompt the user to login again.
            catch (IllegalArgumentException e){
                view.displayException(String.valueOf(e));
            }
        }
    }
}
