package model;

import java.util.Collection;
import java.util.Map;

public class SharedContext {
    public static String ADMIN_STAFF_EMAIL;
    public User currentUser;
    private Map<String, Collection<String>> faqTopicsUpdateSubscribers;

    public SharedContext() {
        User currentUser = new Guest();
    }
    public void addPage(Page page){

    }
    public boolean registerForFAQUpdates(String str1, String str2){
        return true;
    }
    public boolean unregisterForFAQUpdates(String str1, String str2){
        return true;
    }
    public Collection<String> usersSubscribedToFAQTopic(String str1){
        return null;
    }
    public void setCurrentUser(User user){
        currentUser = user;
    }
    public Collection<Page> getPages(){
        return null;
    }

}
