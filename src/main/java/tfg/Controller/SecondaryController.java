package tfg.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import tfg.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}