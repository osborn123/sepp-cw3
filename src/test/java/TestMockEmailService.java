import external.MockEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class TestMockEmailService {

    MockEmailService emailService = new MockEmailService();

    // Add @DisplayName
    @Test
    @DisplayName("Test send email with valid emails")
    public void testSendEmailWithValidEmails() {
        int status = emailService.sendEmail("sender@example.com", "recipient@example.com", "Subject", "Content");
        Assertions.assertEquals(MockEmailService.STATUS_SUCCESS, status);
    }

    @Test
    @DisplayName("Test send email with invalid sender email")
    public void testSendEmailWithInvalidSenderEmail() {
        int status = emailService.sendEmail("invalid_sender", "recipient@example.com", "Subject", "Content");
        Assertions.assertEquals(MockEmailService.STATUS_INVALID_SENDER_EMAIL, status);
    }

    @Test
    @DisplayName("Test send email with invalid recipient email")
    public void testSendEmailWithInvalidRecipientEmail() {
        int status = emailService.sendEmail("sender@example.com", "invalid_recipient", "Subject", "Content");
        Assertions.assertEquals(MockEmailService.STATUS_INVALID_RECIPIENT_EMAIL, status);
    }

    @Test
    @DisplayName("Test send email with null sender email")
    public void testSendEmailWithNullSenderEmail() {
        int status = emailService.sendEmail(null, "recipient@example.com", "Subject", "Content");
        Assertions.assertEquals(MockEmailService.STATUS_INVALID_SENDER_EMAIL, status);
    }

    @Test
    @DisplayName("Test send email with null recipient email")
    public void testSendEmailWithNullRecipientEmail() {
        int status = emailService.sendEmail("sender@example.com", null, "Subject", "Content");
        Assertions.assertEquals(MockEmailService.STATUS_INVALID_RECIPIENT_EMAIL, status);
    }
}
