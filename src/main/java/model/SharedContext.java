package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SharedContext {
    public ArrayList<Inquiry> getInquiries() {
        return inquiries;
    }
    private ArrayList<Inquiry> inquiries = new ArrayList<>();
    public static String ADMIN_STAFF_EMAIL = "admins@hindeburg.ac.uk";
    public static String getAdminStaffEmail() {
        return ADMIN_STAFF_EMAIL;
    }

    private User currentUser = new Guest();
    private Map<String, Collection<String>> faqTopicsUpdateSubscribers;
    private Map<String, Page> availablePages = new HashMap<>();

    private FAQ faq;
    public SharedContext() {
        faqTopicsUpdateSubscribers = new HashMap<>();
        User currentUser = new Guest();
    }
    public void addPage(Page page){

    }
    public boolean registerForFAQUpdates(String userEmail, String topic){

        return true;
    }    public boolean unregisterForFAQUpdates(String userEmail, String topic){
        // Check if the topic exists in the map
        if (faqTopicsUpdateSubscribers.containsKey(topic)) {
            Collection<String> subscribers = faqTopicsUpdateSubscribers.get(topic);
            // Attempt to remove the userEmail from the subscribers of the topic
            boolean removed = subscribers.remove(userEmail);
            // If the userEmail was successfully removed, return true
            if (removed) {
                // If the subscribers list is now empty, remove the topic entry
                if (subscribers.isEmpty()) {
                    faqTopicsUpdateSubscribers.remove(topic);
                }
                return true;
            }
        }
        // If the topic does not exist or the userEmail was not found, return false
        return false;
    }
    public User getCurrentUser() {
        // Return the current user, either Guest or AuthenticatedUser
        if (currentUser instanceof Guest){
            return currentUser;
        }
        return currentUser;
    }
    public Collection<String> usersSubscribedToFAQTopic(String topic){
        // Implementation for retrieving users subscribed to a FAQ topic
        return null;
    }
    public void setCurrentUser(User user){
        currentUser = user;
    }
    public Map<String, Page> getPages(){ return availablePages; }
    
    public FAQ getFAQ() {
        // Return the FAQ object
        return new FAQ();
    }

    public void setFAQ(FAQ faq) {
        this.faq = faq;
    }
}