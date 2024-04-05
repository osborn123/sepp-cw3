import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import controller.AuthenticatedUserController;
import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.Guest;
import model.SharedContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.View;

public class TestLogoutSystem {

    private AuthenticatedUserController controller;
    private SharedContext sharedContext;
    private View mockView;
    private AuthenticationService mockAuthenticationService;
    private EmailService mockEmailService;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        mockView = mock(View.class);
        mockAuthenticationService = mock(AuthenticationService.class);
        mockEmailService = mock(EmailService.class);
        sharedContext = new SharedContext();
        
        // Assume an authenticated user is already logged in
        AuthenticatedUser loggedInUser = new AuthenticatedUser("user@example.com", "Student");
        sharedContext.setCurrentUser(loggedInUser);

        // Initialize the controller with mocked dependencies
        controller = new AuthenticatedUserController(sharedContext, mockView, mockAuthenticationService, mockEmailService);
    }

    @Test
    void testLogoutSuccess() {
        // Perform the logout action
        controller.logout();

        // Verify that the success message was displayed
        verify(mockView).displaySuccess("Logout successful!");

        // Assert that the current user is now a Guest, indicating they are logged out
        assertTrue(sharedContext.getCurrentUser() instanceof Guest, "Current user should be a Guest after logout.");
    }
}
