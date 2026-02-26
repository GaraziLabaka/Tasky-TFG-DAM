package tfg.LoginWindow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import tfg.Model.User;

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

    User user = new User(name, mail, password);

    try (EntityManager entityManager = Persistence.createEntityManagerFactory("tasky").createEntityManager()) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        signupStatus.setText("User registered successfully!");

    } catch (Exception e) {
        signupStatus.setText("Error registering user.");
    }
}

public void login(ActionEvent event) {
    String mailLogin = mailLoginField.getText();
    String passwordLogin = passwordLoginField.getText();

    try {
       
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tasky", "root", "root");
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, mailLogin);
       ps.setString(2, passwordLogin);

       ResultSet rs = ps.executeQuery();
       // if credentials are correct (found in the db), change view to task screen
       if(rs.next()) {
        String mail = rs.getString("email");
        String password = rs.getString("password");
            loginStatus.setText("You successfully logged in!");
            
            switchTask(event);

        } else loginStatus.setText("Invalid password or email");
    } catch (Exception e) {
        e.printStackTrace();
        loginStatus.setText("Invalid password or email");
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
}
