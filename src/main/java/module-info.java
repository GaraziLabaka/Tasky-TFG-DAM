    module tfg {
    requires transitive javafx.controls;
    requires  javafx.fxml;
    requires java.naming;

    // Hibernate
    requires org.hibernate.orm.core;

    // Jakarta Persistence API
    requires jakarta.persistence;
    requires javafx.web;
    requires java.sql;

    requires jbcrypt;
    requires org.simplejavamail.core;
    requires com.mailjet.api;
    requires org.json;
    requires java.prefs;
    requires javafx.graphics;
   
    


    // opens folders to JavaFX
    opens tfg to javafx.fxml, org.testfx.junit5; 
    opens tfg.LoginWindow to javafx.fxml, org.testfx;
    opens tfg.TaskWindow to javafx.fxml, org.testfx;
    
    
    // opens folders to Hibernate
    opens tfg.model to org.hibernate.orm.core;
    

    exports tfg;
    exports tfg.LoginWindow;
    exports tfg.model;
}
