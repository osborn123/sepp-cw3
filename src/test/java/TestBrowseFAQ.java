import controller.AdminStaffController;
import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.AuthenticatedUser;
import model.FAQSection;
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

public class TestBrowseFAQ {
    private SharedContext sharedContext;
    private AdminStaffController adminStaffController;
    private View view;
    private AuthenticationService authenticationService;
    private EmailService emailService;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        sharedContext = new SharedContext();
        view = mock(TextUserInterface.class);
        authenticationService = new MockAuthenticationService();
        emailService = new MockEmailService();
        adminStaffController = new AdminStaffController(sharedContext, view, authenticationService, emailService);

        authenticatedUser = new AuthenticatedUser("authed@email.com", "AdminStaff");
        sharedContext.setCurrentUser(authenticatedUser);
        FAQSection section1 = new FAQSection("Topic 1");
        section1.addItem("Question 1", "Answer 1");
        section1.addItem("Question 2", "Answer 2");
        FAQSection section2 = new FAQSection("Topic 2");
        FAQSection section3 = new FAQSection("Topic 3");
        section1.addSubsection(section3);

        sharedContext.getFAQ().getSections().get(section1);
        sharedContext.getFAQ().getSections().get(section2);
    }
    @Test
    @DisplayName("Test navigating to a sub sub-section and back")
    public void testNavigateToSubsection() {
        when(view.getInput(anyString()))
                .thenReturn("0", "0", "-1");

        adminStaffController.manageFAQ();
        verify(view).displayFAQsection(sharedContext.getFAQ().getSections().get(0).getSubsections().getClass());
    }
}

