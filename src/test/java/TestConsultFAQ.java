import model.FAQ;
import model.FAQSection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class TestConsultFAQ {

    private FAQ faq;

    @BeforeEach
    void setUp() {
        faq = new FAQ();

        // Creating FAQ sections and items
        Map<String, String> generalQuestions = new HashMap<>();
        generalQuestions.put("What is FAQ?", "FAQ stands for Frequently Asked Questions.");
        generalQuestions.put("How to use FAQ?", "Simply navigate to the section of interest and click on a question.");

        Map<String, String> technicalQuestions = new HashMap<>();
        technicalQuestions.put("What is Java?", "Java is a programming language and computing platform.");
        technicalQuestions.put("What is JUnit?", "JUnit is a unit testing framework for the Java programming language.");

        faq.addSectionItems("General", generalQuestions);
        faq.addSectionItems("Technical", technicalQuestions);
    }

    @Test
    @DisplayName("Test consulting FAQ")
    void testConsultFAQ() {
        // Simulate consulting the FAQ
        Map<String, FAQSection> sections = faq.getSections();

        // Assert that the FAQ contains the expected sections
        assertTrue(sections.containsKey("General"));
        assertTrue(sections.containsKey("Technical"));

        // Assert that the "General" section contains the expected items
        FAQSection generalSection = sections.get("General");
        assertNotNull(generalSection);
        assertEquals(2, generalSection.getItems().size());

        // Assert specific FAQ item in "General" section
        boolean foundFAQItem = generalSection.getItems().stream()
                .anyMatch(item -> item.getQuestion().equals("What is FAQ?") && item.getAnswer().equals("FAQ stands for Frequently Asked Questions."));
        assertTrue(foundFAQItem);

        // Assert that the "Technical" section contains specific FAQ item
        FAQSection technicalSection = sections.get("Technical");
        assertNotNull(technicalSection);
        assertEquals(2, technicalSection.getItems().size());

        foundFAQItem = technicalSection.getItems().stream()
                .anyMatch(item -> item.getQuestion().equals("What is Java?") && item.getAnswer().equals("Java is a programming language and computing platform."));
        assertTrue(foundFAQItem);
    }
}