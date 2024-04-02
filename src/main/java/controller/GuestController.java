package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.SharedContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import view.View;

public class GuestController extends Controller{
    public GuestController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
    }
    public void login(){
        String email = view.getInputString("Enter your username: ");
        String password = view.getInputString("Enter your password: ");

        String response = as.login(email, password);
        JSONObject json = (JSONObject) JSONValue.parse(response);
        String err = (String)json.get("error");
        if(json.isEmpty()){
            view.displayError(err);
            login();
        }
        else{
            String role = (String)json.get("role");
            try {
                AuthenticatedUser currentUser = new AuthenticatedUser(email, role);
                view.displaySuccess("successfully logged in");
                sc.setCurrentUser(currentUser);
                MenuController mc = new MenuController(sc,view,as,es);
                if (role.equals("AdminStaff")){
                } else if (role.equals("TeachingStaff")) {
                }
                else{ //Already checked valid role
                    String inpString = "\nPress 1 to logout\nPress 2 to consult the FAQ\nPress 3 to consult the webpages\nPress 4 to consult a member of staff";
                    view.getInputString(inpString);
                }
            }
            catch (IllegalArgumentException e){
                view.displayException(String.valueOf(e));
            }
        }
    }
}
