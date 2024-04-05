package systemtest;

import model.Inquiry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;


public class TestInquirySystem {

    private Inquiry inquiry;
    private final String testSubject = "Test Subject";
    private final String testContent = "This is the content of the inquiry.";
    private final String testEmail = "inquirer@example.com";
    private final String testAssignedStaff = "staff@example.com";


    @BeforeEach
    public void setUp() {
        // Initialize the inquiry with some test data before each test
        inquiry = new Inquiry(testSubject, testContent, testEmail);
    }

    @Test
    @DisplayName("Test Inquiry Creation and Details Retrieval")
    public void testInquiryCreation() {
        // Check that the inquiry has been created with the correct details
        assertEquals(testSubject, inquiry.getSubject());
        assertEquals(testContent, inquiry.getContent());
        assertEquals(testEmail, inquiry.getInquirerEmail());
        assertNotNull(inquiry.getCreatedAt()); // We assume a getter for createdAt
        assertNull(inquiry.getAssignedTo()); // No staff assigned initially
    }

    @Test
    @DisplayName("Test Inquiry Assigning to Staff")
    public void testAssignInquiryToStaff() {
        // Assign the inquiry to a staff member and test
        inquiry.setAssignedTo(testAssignedStaff);
        assertEquals(testAssignedStaff, inquiry.getAssignedTo());
    }

    // Other tests can be added to test additional functionalities and interactions

    @AfterEach
    public void tearDown() {
        // Clean up resources after tests, if necessary
        inquiry = null;
    }
}
