import external.MockAuthenticationService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMockAuthenticationService {

    private MockAuthenticationService authService;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize the authentication service before each test
        authService = new MockAuthenticationService();
    }

    @Test
    @DisplayName("Testing correct password and correct username for success")
    void testLoginSuccess() throws Exception {
        // "JackTheRipper" with password "catch me if u can" exists in the MockUserDataGroups4.json
        String result = authService.login("JackTheRipper", "catch me if u can");
        JSONParser parser = new JSONParser();
        JSONObject resultObj = (JSONObject) parser.parse(result);
        Assertions.assertEquals("JackTheRipper", resultObj.get("username"), "Login should succeed with correct username and password");
    }

    @Test
    @DisplayName("Testing correct username and incorrect password for failure")
    void testLoginFailureIncorrectPassword() throws Exception {
        String result = authService.login("JackTheRipper", "wrongPassword");
        JSONParser parser = new JSONParser();
        JSONObject resultObj = (JSONObject) parser.parse(result);
        Assertions.assertFalse(resultObj.containsKey("error"), "Login should fail with incorrect password");
    }

    @Test
    @DisplayName("Testing incorrect username and correct password for failure")
    void testLoginFailureIncorrectUsername() throws Exception {
        // Assuming "nonExistingUser" does not exist in the MockUserDataGroups4.json
        String result = authService.login("nonExistingUser", "anyPassword");
        JSONParser parser = new JSONParser();
        JSONObject resultObj = (JSONObject) parser.parse(result);
        Assertions.assertFalse(resultObj.containsKey("error"), "Login should fail with incorrect username");
    }
}