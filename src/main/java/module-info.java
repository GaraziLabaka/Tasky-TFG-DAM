module tfg {
    requires javafx.controls;
    requires javafx.fxml;
// used to make the root folder visible to javafx
    opens tfg to javafx.graphics, javafx.fxml;
    exports tfg;
// used to make the controller folder visible to javafx
    opens tfg.Controller to javafx.fxml;
    exports tfg.Controller;
}
