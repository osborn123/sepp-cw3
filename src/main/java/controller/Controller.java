package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

import java.util.Collection;

abstract class Controller {
    public SharedContext sc;
    protected MenuController mc;
    public View view;
    public EmailService es;
    public AuthenticationService as;

    protected Controller(SharedContext sc, View view, AuthenticationService as, EmailService es){
        this.sc = sc;
        this.view = view;
        this.as = as;
        this.es = es;
    }
    public void setMenuController(MenuController menuController) {
        this.mc = menuController;
    }
    protected <T> int selectFromMenu(Collection<T> menuItems, String prompt) {
        view.displayDivider();

        // Convert the collection to an array for easy access by index
        Object[] itemsArray = menuItems.toArray();
        int numMenuItems = itemsArray.length;

        // Display each menu item with an index
        for (int i = 0; i < numMenuItems; i++) {
            view.displayInfo(i + ". " + itemsArray[i].toString());
        }

        // Initialization outside the valid range to ensure the loop starts
        int selection = -2; // Start with a value that guarantees entry into the loop
        while (selection < 0 || selection >= numMenuItems) {
            // After the first invalid input, display an error message
            if (selection != -2) {
                view.displayError("Invalid selection. Please enter a number between 0 and " + (numMenuItems - 1) + ".");
            }

            try {
                selection = Integer.parseInt(view.getInputString(prompt));
            } catch (NumberFormatException e) {
                // Reset selection to ensure the loop continues after invalid input
                selection = -2;
            }
        }

        view.displayDivider();
        return selection;
    }

}
