package model;
import java.util.ArrayList;
import java.util.Collection;
public class FAQSection {
    private String topic; // The topic or title of the FAQ section
    private Collection<FAQItem> items; // A collection of FAQ items within this section
    private Collection<FAQSection> subsections; // Subsections of this FAQ section
    private FAQSection parent; // The parent section of this FAQ section, if any

    /**
     * Constructs an FAQSection with the specified topic.
     * Initializes empty collections for FAQ items and subsections.
     *
     * @param topic The topic or title of the FAQ section.
     */
    public FAQSection(String topic) {
        this.topic = topic;
        this.items = new ArrayList<>();
        this.subsections = new ArrayList<>();
        this.parent = null; // Initially, this section has no parent
    }

    /**
     * Adds a subsection to this FAQ section.
     * Sets this section as the parent of the added subsection.
     *
     * @param section The subsection to add.
     */
    public void addSubsection(FAQSection section) {
        section.setParent(this); // Set this section as the parent
        subsections.add(section);
    }

    /**
     * Sets the parent FAQ section of this section.
     * This is typically called when this section is added as a subsection of another.
     *
     * @param parent The parent FAQ section.
     */
    public void setParent(FAQSection parent) {
        this.parent = parent;
    }

    /**
     * Adds an FAQ item to this section.
     *
     * @param question The question for the FAQ item.
     * @param answer The answer for the FAQ item.
     */
    public void addItem(String question, String answer) {
        items.add(new FAQItem(question, answer));
    }

    // Getters for accessing private fields
    public String getTopic() { return topic; }
    public Collection<FAQItem> getItems() { return items; }
    public Collection<FAQSection> getSubsections() { return subsections; }
    public FAQSection getParent() { return parent; }

    /**
     * Returns a string representation of the FAQ section, including its topic, FAQ items,
     * and any subsections. Items and subsections are indented for better readability.
     *
     * @return A string representation of the FAQ section.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(topic).append("\n");

        // Append items with indentation
        if (!items.isEmpty()) {
            sb.append("Items:\n");
            for (FAQItem item : items) {
                sb.append("  ").append(item).append("\n");
            }
        }

        // Append subsections with indentation
        if (!subsections.isEmpty()) {
            sb.append("Subsections:\n");
            for (FAQSection section : subsections) {
                // Recursively calls toString on subsections for a nested structure
                sb.append("  ").append(section).append("\n");
            }
        }
        return sb.toString();
    }
}