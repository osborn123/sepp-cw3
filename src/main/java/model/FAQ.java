package model;



import java.util.HashMap;
import java.util.Map;

public class FAQ {
    private Map<String, FAQSection> sections;
    public FAQ() {
        this.sections = new HashMap<>();
    }

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
