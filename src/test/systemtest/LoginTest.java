
import controller.GuestController;
import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.TextUserInterface;
import view.View;
import static org.mockito.Mockito.*;

public class LoginTest {
    @Mock
    private AuthenticationService auth;
    @Mock
    private TextUserInterface textUserInterface;
    @Mock
    private View view;

    @InjectMocks
    private GuestController guestController;

    private SharedContext sc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sc = new SharedContext();
        guestController = new GuestController(sc, textUserInterface, auth, mock(EmailService.class));
    }

    @Test
    void testLoginWithWrongPassword() {
        // Arrange
        String usernameInput = "gt";
        String passwordInput = "wrongpassword";
        String errorMessage = "Invalid username or password";
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("error", errorMessage);
        when(textUserInterface.getInputString("Enter your username: ")).thenReturn(usernameInput);
        when(textUserInterface.getInputString("Enter your password: ")).thenReturn(passwordInput);
        when(auth.login(usernameInput, passwordInput)).thenReturn(errorResponse.toJSONString());

        // Act
        guestController.login();

        // Assert
        verify(textUserInterface).displayError(errorMessage);
        //assertNull(guestController.getSharedContext().getCurrentUser());
    }
    @Test
    void testGuestLoginWithWrongUsername() {
        // Arrange
        String usernameInput = "wrongusername";
        String passwordInput = "password";
        String errorMessage = "Invalid username or password";
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("error", errorMessage);
        when(textUserInterface.getInputString("Enter your username: ")).thenReturn(usernameInput);
        when(textUserInterface.getInputString("Enter your password: ")).thenReturn(passwordInput);
        when(auth.login(usernameInput, passwordInput)).thenReturn(errorResponse.toJSONString());

        // Act
        guestController.login();

        // Assert
        verify(textUserInterface).displayError(errorMessage);
        //assertNull(guestController.getSharedContext().getCurrentUser());
    }
    @Test
    void testLoginWithCorrectUsernameAndPassword() {
        // Arrange
        String usernameInput = "correctusername";
        String passwordInput = "correctpassword";
        JSONObject successResponse = new JSONObject();
        successResponse.put("username", "test@gam.ci");
        when(textUserInterface.getInputString("Enter your username: ")).thenReturn(usernameInput);
        when(textUserInterface.getInputString("Enter your password: ")).thenReturn(passwordInput);
        when(auth.login(usernameInput, passwordInput)).thenReturn(successResponse.toJSONString());

        // Act
        guestController.login();

        // Assert
        verify(textUserInterface, never()).displayError(anyString());
        //verify(textUserInterface).displaySuccess("You have successfully logged in as " + usernameInput + " (a)");
//        verify(textUserInterface).displayError("Server response does not contain email information.");
        //assertEquals(usernameInput, guestController.getSharedContext().getCurrentUser());
        //assertNotNull(guestController.getSharedContext().getCurrentUser());
    }
}


