package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Guest;
import model.SharedContext;
import view.View;

import java.util.ArrayList;
import java.util.List;


public class AuthenticatedUserController extends Controller{
    // This could be a list of changes, a flag, or any other way you track changes.
    private List<ModifiableEntity> entities;

    private SharedContext sharedContext;
    private View view;
    public AuthenticatedUserController(SharedContext sc, View view, AuthenticationService as, EmailService es) {
        super(sc, view, as, es);
        this.sharedContext = sharedContext;
        this.view = view;
        this.entities = new ArrayList<>();
    }
    // This method is responsible for handling the user's request to logout.

    public void logout() {
        view.displaySuccess("Logout successful!");
        sc.setCurrentUser(new Guest());

    }
    public void forceLogoutDueToInactivity() {
        // Logic for handling forced logout due to inactivity
        // Set the current user to a guest (non-authenticated user)
        sharedContext.setCurrentUser(new Guest());

        // Display a message to the user
        view.displayInfo("You have been logged out due to inactivity.");
    }

    // Method to check if there are any unsaved changes.
    public boolean hasUnsavedChanges() {
        // Loop through all entities and check if any have been modified.
        for (ModifiableEntity entity : entities) {
            if (entity.isModified()) {
                return true; // Return true as soon as one modified entity is found.
            }
        }
        return false; // If no modified entities are found, return false.
    }


}

