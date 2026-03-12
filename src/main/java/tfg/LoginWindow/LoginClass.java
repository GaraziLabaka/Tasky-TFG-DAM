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

public class LoginClass {
@FXML
TextField mailSignField, mailLoginField, nameSignField;
@FXML
PasswordField passwordLoginField, passwordSignField;
@FXML
Button loginBtn, signupBtn;
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

    if (mail == null || mail.isEmpty() || name == null || name.isEmpty() || password == null || password.isEmpty()) {
        signupStatus.setText("You must enter data to register");
        return;
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

    if (mailLogin == null || mailLogin.isEmpty() || passwordLogin == null || passwordLogin.isEmpty()) {
        loginStatus.setText("You must enter data to login");
        return;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tfg/View/taskWindow.fxml"));	
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
    

