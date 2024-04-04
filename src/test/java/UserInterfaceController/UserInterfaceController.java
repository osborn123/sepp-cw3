package UserInterfaceController;


import model.SharedContext;
import view.View;

public class UserInterfaceController {
    private SharedContext sharedContext;
    private View view;

    public UserInterfaceController(SharedContext sharedContext, View view) {
        this.sharedContext = sharedContext;
        this.view = view;
    }


}
