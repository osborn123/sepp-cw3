package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Page;
import model.PageSearch;
import model.SharedContext;
import view.View;

import java.util.ArrayList;
import java.util.Collection;

public class AdminStaffController extends StaffController{
    public AdminStaffController(SharedContext sc, View view, AuthenticationService as, EmailService es){
        super(sc, view, as, es);
    }
    public void addPage(){
        String title = view.getInputString("Enter page title: ");
        String content = view.getInputString("Enter page content: ");
        boolean isPrivate = view.getYesNoInputString("Should this be private? ");
        //Todo: Finish this off, don't know about the function "getPages"
        Page newPage = new Page(title,content,isPrivate);
        Collection pageCollection = new ArrayList();
        pageCollection.add(newPage);
        PageSearch(pageCollection);
    }
    public void manageFAQ(){

    }
    private void addFAQItem(){

    }
    public void viewAllPages(){

    }
    public void manageInquiries(){

    }
    private void redirectInquiry(){

    }
}
