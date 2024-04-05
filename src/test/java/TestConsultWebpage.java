import model.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class TestConsultWebpage {

    @Test
    @DisplayName("Test Privacy Setting of Page")
    public void testPrivacySetting() {
        Page page = new Page("Test Title", "Test Content", false);
        Assertions.assertFalse(page.isPrivate()); // Initially not private
        page.setPrivate(true);
        Assertions.assertTrue(page.isPrivate()); // Now set to private
    }

    @Test
    @DisplayName("Test Content Retrieval of Page")
    public void testContentRetrieval() {
        String content = "This is a test content.";
        Page page = new Page("Test Title", content, false);
        Assertions.assertEquals(content, page.getContent());
    }

    @Test
    @DisplayName("Test Title Retrieval of Page")
    public void testTitleRetrieval() {
        String title = "Test Title";
        Page page = new Page(title, "Test Content", false);
        Assertions.assertEquals(title, page.getTitle());
    }
}
