package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SharedContext {
    public static String ADMIN_STAFF_EMAIL;
    private Map<String, Collection<String>> faqTopicsUpdateSubscribers;

    public SharedContext() {
        faqTopicsUpdateSubscribers = new HashMap<>();
    }

    public void addPage(Page page){
        
    }

    public boolean registerForFAQUpdates(String userEmail, String topic){
        
        return true; 
    }

    public boolean unregisterForFAQUpdates(String userEmail, String topic){
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

    public Collection<String> usersSubscribedToFAQTopic(String topic){
        // Implementation for retrieving users subscribed to a FAQ topic
        return null; 
    }
}
