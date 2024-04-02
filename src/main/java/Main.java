import controller.MenuController;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.SharedContext;
import org.json.simple.parser.ParseException;
import view.TextUserInterface;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
        SharedContext sc = new SharedContext();
        TextUserInterface view = new TextUserInterface();
        MockAuthenticationService as = new MockAuthenticationService();
        MockEmailService es = new MockEmailService();
        MenuController mc = new MenuController(sc,view,as,es);
        mc.mainMenu();
    }
}
