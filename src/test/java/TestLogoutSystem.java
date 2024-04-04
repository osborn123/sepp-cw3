import controller.AuthenticatedUserController;
import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.Guest;
import model.SharedContext;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestLogoutSystem {

    private SharedContext sharedContext;
    private AuthenticatedUserController authenticatedUserController;
    private View view;
    private AuthenticationService mockAuthenticationService;
    private EmailService mockEmailService;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        sharedContext = new SharedContext();
        view = mock(View.class);
        mockAuthenticationService = new MockAuthenticationService();
        mockEmailService = new MockEmailService();
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, mockAuthenticationService, mockEmailService);
    }

    @Test
    @DisplayName("Test forced logout due to inactivity")
    public void testForcedLogoutDueToInactivity() throws IOException {
        // Simulating that the user is forced to logout due to inactivity
        authenticatedUserController.forceLogoutDueToInactivity();

        // Verify that a message is displayed regarding the forced logout
        verify(view).displayInfo("You have been logged out due to inactivity.");

        // Check that the current user in the shared context is now an instance of Guest
        assertTrue(sharedContext.getCurrentUser() instanceof Guest);
    }

    @Test
    @DisplayName("Test logout with unsaved changes")
    public void testLogoutWithUnsavedChanges() throws IOException {
        // Assuming that the 'hasUnsavedChanges()' method returns true when there are unsaved changes
        when(authenticatedUserController.hasUnsavedChanges()).thenReturn(true);

        // Simulate the user confirming they want to logout even with unsaved changes
        when(view.getYesNoInputs("You have unsaved changes. Are you sure you want to logout?")).thenReturn(true);

        // Perform the logout action
        authenticatedUserController.logout();

        // Verify that a warning message is shown to the user about the unsaved changes
        verify(view).displayWarning("You logged out with unsaved changes.");

        // Check that the current user in the shared context is now an instance of Guest
        assertTrue(sharedContext.getCurrentUser() instanceof Guest);
    }

    // Additional logout test cases would follow the same pattern...
}
