package tfg.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import tfg.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
