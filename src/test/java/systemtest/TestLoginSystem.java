package systemtest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import controller.AuthenticatedUserController;
import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.Guest;
import model.SharedContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.View;

public class TestLoginSystem {

    private SharedContext sharedContext;
    private View view;
    private AuthenticationService authenticationService;
    private EmailService emailService;
    private AuthenticatedUserController controller;

    @BeforeEach
    void setUp() {
        // Initialize the shared context and mock services
        sharedContext = new SharedContext();
        view = mock(View.class);
        authenticationService = mock(AuthenticationService.class);
        emailService = mock(EmailService.class);

        // Create an instance of AuthenticatedUserController with mocked dependencies
        controller = new AuthenticatedUserController(sharedContext, view, authenticationService, emailService);
    }

    @Test
    @DisplayName("Test Logout Success - Correct username and password")
    void testLogoutSuccess() {
        // Simulate a user being logged in
        AuthenticatedUser user = new AuthenticatedUser("user@example.com", "Student");
        sharedContext.setCurrentUser(user);

        // Perform the logout operation
        controller.logout();

        // Verify that the current user is now a Guest (indicating successful logout)
        assertTrue(sharedContext.getCurrentUser() instanceof Guest);

        // Verify that the success message is displayed
        verify(view).displaySuccess("Logout successful!");
    }
}