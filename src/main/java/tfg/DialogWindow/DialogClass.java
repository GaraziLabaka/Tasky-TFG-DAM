package tfg.DialogWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DialogClass {
    @FXML
    Button saveNumberDays;
    @FXML
    TextField numberDays;
    @FXML
    Label infoLabel;

    public int saveNumberDays() {
        String numberDaysText = numberDays.getText();
        int days = 0;
        if (!numberDaysText.isBlank() && numberDaysText.matches("^[0-9]+$")) {
            days = Integer.parseInt(numberDaysText);
            infoLabel.setText("Notifications will be sent every " + days + " days.");
        } else {
            infoLabel.setText("Please enter a valid number of days.");
        }

        return days;
}


}
