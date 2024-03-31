package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

import java.util.Collection;

abstract class Controller {
    public SharedContext sc;
    public View view;
    public EmailService es;
    public AuthenticationService as;

    protected Controller(SharedContext sc, View view, AuthenticationService as, EmailService es){
        this.sc = sc;
        this.view = view;
        this.as = as;
        this.es = es;
    }
    protected <T> int selectfromMenu(Collection<T> collection, String str){
        return 0;
    }
}
