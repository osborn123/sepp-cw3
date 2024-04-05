package model;



import java.util.HashMap;
import java.util.Map;
// Compare this snippet from sepp-cw3/src/main/java/model/FAQSection.java:
public class FAQ {
    private Map<String, FAQSection> sections;
    public FAQ() {
        this.sections = new HashMap<>();
    }

    // Compare this snippet from sepp-cw3/src/main/java/model/FAQSection.java:

    // Compare this snippet from sepp-cw3/src/main/java/model/FAQSection.java:

    public void addSectionItems(String topic, Map<String, String> items) {
        FAQSection section = new FAQSection(topic);
        for (Map.Entry<String, String> entry : items.entrySet()) {
            section.addItem(entry.getKey(), entry.getValue());
        }
        sections.put(topic, section);
    }


    public Map<String, FAQSection> getSections() {
        // NOT sure if this is correct, but i think i need a getter here - bw
        return sections;
    }


}
