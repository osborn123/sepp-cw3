package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

import java.util.Collection;

abstract class Controller {
    protected Controller(SharedContext sc, View view, AuthenticationService as, EmailService es){

    }
    protected <T> int selectfromMenu(Collection<T> collection, String str){
        return 0;
    }
}
