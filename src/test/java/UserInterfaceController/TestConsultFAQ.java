package UserInterfaceController;

import controller.InquirerController;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class TestConsultFAQ {
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
    @DisplayName("Test consulting faq as guest and subscribing to topic")
    public void testConsultFAQSubscribeAsGuest() {
        String email = "test@gmail.com";
        when(view.getInput(anyString()))
                .thenReturn("0", "-2", email, "-1");
        inquirerController.consultFAQ();
        verify(view).displaySuccess(contains("Successfully registered " + email + " for updates on"));
    }

    @Test
    @DisplayName("Test consulting faq as guest and unsubscribing to topic")
    public void testConsultFAQUnsubscribeAsGuest() {
        String email = "test@gmail.com";
        when(view.getInput(anyString()))
                .thenReturn("0", "-2", email, "-3", email, "-1");
        inquirerController.consultFAQ();
        verify(view).displaySuccess(contains("Successfully unregistered " + email + " for updates on"));
    }

    @Test
    @DisplayName("Test consulting faq as logged in user and subscribing to topic")
    public void testConsultFAQSubscribeAsUser() {
        AuthenticatedUser user = new AuthenticatedUser("test@email.com", "Student");
        sharedContext.setCurrentUser(user);
        when(view.getInput(anyString()))
                .thenReturn("0", "-2", "-1");
        inquirerController.consultFAQ();
        verify(view).displaySuccess(contains("Successfully registered " + user.getEmail() + " for updates on"));
    }

    @Test
    @DisplayName("Test consulting faq as logged in user and unsubscribing to topic")
    public void testConsultFAQUnsubscribeAsUser() {
        AuthenticatedUser user = new AuthenticatedUser("test@email.com", "Student");
        sharedContext.setCurrentUser(user);
        when(view.getInput(anyString()))
                .thenReturn("0", "-2", "-2", "-1");
        inquirerController.consultFAQ();
        verify(view).displaySuccess(contains("Successfully unregistered " + user.getEmail() + " for updates on"));
    }

    @Test
    @DisplayName("Test navigating through to subsections")
    public void testConsultFAQSubsections() {
        when(view.getInput(anyString()))
                .thenReturn("0" , "-1");
        inquirerController.consultFAQ();
        verify(view).displayFAQsection(argThat(faqSection -> faqSection.getTypeName().equals("Topic 1")));
    }

    @Test
    @DisplayName("Test consulting FAQ sub section and going back main FAQ")
    public void testConsultFAQBack() {
        when(view.getInput(anyString()))
                .thenReturn("0", "-1");
        inquirerController.consultFAQ();
        verify(view, times(2)).displayFAQ(argThat(faq -> faq.getSections().size() == 2));
    }

}
