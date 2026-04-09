package tfg.LoginWindow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tfg.model.SessionUser;
import tfg.model.User;
import tfg.services.LoginService;
import tfg.services.SignUpService;

public class LoginClass {
@FXML
TextField mailSignField;
@FXML TextField mailLoginField;
@FXML
TextField nameSignField;
@FXML
PasswordField passwordLoginField, passwordSignField;
@FXML
Button loginBtn, signupBtn, docsBtn;
@FXML
Label signupStatus, loginStatus;

private Parent root;
    private Stage stage;
    private Scene scene;



public void signup() {
    String mail = mailSignField.getText();
    String name = nameSignField.getText();
    String password = passwordSignField.getText();

    User user = new User(name, mail, hashPasswords());

    String validationMessage = new SignUpService().signUpService(mail, name, password);
    if (!validationMessage.equals("Signing up...")) {
        signupStatus.setText(validationMessage);
    } else {
        try (EntityManager entityManager = Persistence.createEntityManagerFactory("tasky").createEntityManager()) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

       signupStatus.setText("User registered successfully!");

    } catch (Exception e) {
        signupStatus.setText("Error registering user.");
    }
    }
    
}

public void login(ActionEvent event) {
    String mailLogin = mailLoginField.getText();
    String passwordLogin = passwordLoginField.getText();

    // Calling up the service to validate the data before registering the user in the database. Services allow to create unit tests seamlessly using Symflower.
// Symflower is a tool that generates unit tests for Java code, this way no fxml elements are needed to test the logic of the service, which is the one that validates the data before registering the user in the database.
    String validationMessage = new LoginService().loginService(mailLogin, passwordLogin);
    if (!validationMessage.equals("Logging in...")) {
        loginStatus.setText(validationMessage);
    } else {
        try {
       // check mail only because password changes every time you log in
        String query = "SELECT * FROM users WHERE email = ?";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tasky", "root", "root");
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, mailLogin);

       ResultSet rs = ps.executeQuery();
       // if password is correct (found in the db), change view to task screen
       
       if (rs.next()) {
        String DBPassword = rs.getString("password");
       
       if(BCrypt.checkpw(passwordLogin, DBPassword)) {
        User loggedUser = new User();
    loggedUser.setId(rs.getLong("id"));
    loggedUser.setName(rs.getString("name"));
    loggedUser.setEmail(rs.getString("email"));
    loggedUser.setPassword(DBPassword);

    
     // Keeps track of which user is logged in
    SessionUser.setCurrentUser(loggedUser);
    switchTask(event);
       } else {
        loginStatus.setText("Invalid mail or password");
       }      
           
       } else {
        loginStatus.setText("Something went wrong, try again");
       }
    } catch (Exception e) {
        e.printStackTrace();
        loginStatus.setText("Something went wrong, try again");
    }
    }
    
}
    
   private void switchTask(ActionEvent event) throws IOException{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/tfg/View/taskWindow.fxml"));	
		root = loader.load();	
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
        // loading css again because css isn't shared between views, it has to be loaded in every view that needs it
         // alternatively, load it manually in the stylesheets section of scene builder
		stage.setScene(scene);
		stage.show();
    }

    public void switchToDocs(ActionEvent event) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/tfg/View/docsWindow.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String hashPasswords() {
        String password = passwordSignField.getText();
        String hashedPassword = BCrypt.hashpw(password, String.valueOf(BCrypt.gensalt()));
        return hashedPassword;
    }
}