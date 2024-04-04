import controller.GuestController;
import external.AuthenticationService;
import external.EmailService;
import external.MockEmailService;
import model.SharedContext;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestLoginSystem {
    private SharedContext sharedContext;
    private TextUserInterface view; // Changed to TextUserInterface since mock() was called on this
    private AuthenticationService authenticationService;
    private EmailService emailService;

    private GuestController guestController;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        sharedContext = new SharedContext();
        view = mock(TextUserInterface.class);
        authenticationService = mock(AuthenticationService.class); // Mocking, instead of using a real instance
        emailService = new MockEmailService(); // Assuming this is fine to use as is
        guestController = new GuestController(sharedContext, view, authenticationService, emailService);
    }

    @Test
    @DisplayName("Test successful login")
    public void testSuccessfulLogin() {
        String username = "JackTheRipper";
        String password = "catch me if u can";

        when(view.getInput(anyString())).thenReturn(username, password);
        when(authenticationService.authenticate(username, password)).thenReturn(true); // Assuming this method exists

        guestController.login();

        verify(view).displaySuccess(contains("You have successfully logged in as " + username));
    }

    @Test
    @DisplayName("Test login with incorrect password")
    public void testLoginWithInvalidCredentials() {
        String username = "JackTheRipper";
        String password = "wrong password";

        when(view.getInput(anyString())).thenReturn(username, password);
        when(authenticationService.authenticate(username, password)).thenReturn(true); // Assuming this method exists

        guestController.login();

        verify(view).displayError(contains("Wrong username or password"));
    }

    @Test
    @DisplayName("Test login with empty username")
    public void testLoginWithEmptyUsername() {
        String username = "";
        String password = "catch me if u can";

        when(view.getInput(anyString())).thenReturn(username, password);
        when(authenticationService.authenticate(username, password)).thenReturn(false); // Assuming this method exists

        guestController.login();

        verify(view).displayError(contains("Wrong username or password"));
    }

    @Test
    @DisplayName("Test login with incorrect username")
    public void testLoginWithIncorrectUsername() {
        String incorrectUsername = "WrongName";
        String password = "catch me if u can";

        when(view.getInput(anyString())).thenReturn(incorrectUsername, password);
        when(authenticationService.authenticate(incorrectUsername, password)).thenReturn(false); // Assuming this method exists

        guestController.login();

        verify(view).displayError(contains("Wrong username or password"));
    }

    @Test
    @DisplayName("Test login with locked account")
    public void testLoginWithLockedAccount() {
        String username = "LockedUser";
        String password = "password123";

        when(view.getInput(anyString())).thenReturn(username, password);
        when(authenticationService.authenticate(username, password)).thenReturn(false); // Mocking a failed authentication due to account lock

        guestController.login();

        verify(view).displayError(contains("Your account is locked"));
    }

    @Test
    @DisplayName("Test login with expired credentials")
    public void testLoginWithExpiredCredentials() {
        String username = "ExpiredUser";
        String password = "oldPassword";

        when(view.getInput(anyString())).thenReturn(username, password);
        when(authenticationService.authenticate(username, password)).thenReturn(false); // Mocking a failed authentication due to expired credentials

        guestController.login();

        verify(view).displayError(contains("Your credentials have expired"));
    }


}




