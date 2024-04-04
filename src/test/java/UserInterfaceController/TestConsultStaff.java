package UserInterfaceController;

import controller.InquirerController;
import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.AuthenticatedUser;
import model.SharedContext;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;
import view.View;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class TestConsultStaff {
    private SharedContext sharedContext;
    private InquirerController inquirerController;
    private View view;
    private AuthenticationService authenticationService;
    private EmailService emailService;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        sharedContext = new SharedContext();
        view = mock(TextUserInterface.class);
        authenticationService = new MockAuthenticationService();
        emailService = new MockEmailService();
        inquirerController = new InquirerController(sharedContext, view, authenticationService, emailService);
    }




    @Test
    @DisplayName("Test contacting staff as student with short message but he regretted halfway")
    public void testConsultAdminAsStudentRegret() {
        AuthenticatedUser user = new AuthenticatedUser("test@email.com", "Student");
        sharedContext.setCurrentUser(user);
        String subject = "Inquiry Subject";
        String message = "mes"; // more than 20 characters
        when(view.getInput(anyString())).thenReturn(subject, message);
        when(view.getYesNoInputs(anyString())).thenReturn(false);
        inquirerController.contactStaff();
        verify(view, never()).displayInfo("Your inquiry " + subject + " has been sent! Someone should be in touch via email soon!");
    }

    @Test
    @DisplayName("Test contacting staff as student but he want to exit in the process of writing the message")
    public void testConsultAdminAsStudentExit() {
        AuthenticatedUser user = new AuthenticatedUser("t@e.com", "Student");
        sharedContext.setCurrentUser(user);
        String subject = "Inquiry Subject";
        String message = "message";
        when(view.getInput(anyString())).thenReturn("-1");
        inquirerController.contactStaff();
        // verify that the user go back to main menu
        verify(view, never()).displayInfo(contains("Your inquiry"));
    }

    @Test
    @DisplayName("Test contacting staff as guest but he want to exit in the process of writing the message")
    public void testConsultAdminAsGuestExit() {
        String email = "t@e.com";
        String subject = "Inquiry Subject";
        String message = "message";
        when(view.getInput(anyString())).thenReturn(email, subject, message, "-1");
        inquirerController.contactStaff();
        verify(view, never()).displayInfo(contains("Your inquiry"));

    }

}