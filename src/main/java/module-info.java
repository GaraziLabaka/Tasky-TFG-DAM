    module tfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;

    // Hibernate
    requires org.hibernate.orm.core;

    // Jakarta Persistence API
    requires jakarta.persistence;
    requires javafx.web;
    
    // opens folders to JavaFX
    opens tfg to javafx.fxml;
    opens tfg.LoginWindow to javafx.fxml;
    // opens folders to Hibernate
    opens tfg.Model to org.hibernate.orm.core;  

    exports tfg;
    exports tfg.LoginWindow;
    exports tfg.Model;
}
