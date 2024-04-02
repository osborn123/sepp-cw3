package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a section of a Frequently Asked Questions (FAQ) list.
 * Each section can contain a list of questions (as FAQItem objects) and can be nested within other sections.
 */
public class FAQSection {
    private String topic; // The title or main topic of this FAQ section.
    private FAQSection parent; // The parent section, null if this is a top-level section.
    private Collection<FAQItem> items; // The list of FAQ items (questions and answers) in this section.
    private Collection<FAQSection> subsections; // Nested subsections within this FAQ section.

    /**
     * Constructs an FAQSection with a specified topic.
     *
     * @param topic The topic or title of the FAQ section.
     */
    public FAQSection(String topic) {
        this.topic = topic;
        this.items = new ArrayList<>();
        this.subsections = new ArrayList<>();
        this.parent = null; // Initially, there is no parent until this section is added as a subsection.
    }

    /**
     * Adds a subsection to this FAQ section. Automatically sets this section as the parent of the added subsection.
     *
     * @param section The FAQSection to be added as a subsection.
     */
    public void addSubsection(FAQSection section) {
        section.setParent(this);
        subsections.add(section);
    }

    /**
     * Sets the parent of this FAQ section. This is typically called internally when this section is added as a subsection.
     *
     * @param parent The parent FAQSection.
     */
    public void setParent(FAQSection parent) {
        this.parent = parent;
    }

    /**
     * Adds an FAQ item (question and answer) to this section.
     *
     * @param question The question to add.
     * @param answer The answer to the question.
     */
    public void addItem(String question, String answer) {
        items.add(new FAQItem(question, answer));
    }

    // Standard getters for the class properties.
    public String getTopic() { return topic; }
    public Collection<FAQItem> getItems() { return items; }
    public Collection<FAQSection> getSubsections() { return subsections; }
    public FAQSection getParent() { return parent; }

    /**
     * Generates a string representation of this FAQ section, including its topic, items, and any subsections.
     * This is particularly useful for debugging or displaying the structure in a console application.
     *
     * @return A string representation of the FAQ section.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(topic).append("\n");
        if (!items.isEmpty()) {
            sb.append("Items:\n");
            for (FAQItem item : items) {
                sb.append("  ").append(item).append("\n");
            }
        }
        if (!subsections.isEmpty()) {
            sb.append("Subsections:\n");
            for (FAQSection section : subsections) {
                // Recursive toString call to include subsections' information.
                sb.append("  ").append(section).append("\n");
            }
        }
        return sb.toString();
    }
}
