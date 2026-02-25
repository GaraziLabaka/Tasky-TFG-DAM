package tfg.LoginWindow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public void login() {
    String mailLogin = mailLoginField.getText();
    String passwordLogin = passwordLoginField.getText();

    try {
       
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tasky", "root", "root");
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, mailLogin);
       ps.setString(2, passwordLogin);

       ResultSet rs = ps.executeQuery();
       while(rs.next()) {
        String mail = rs.getString("email");
        System.out.println(mail);
        String password = rs.getString("password");
        System.out.println(password);
        if (mail.equals(mailLogin) && password.equals(passwordLogin)) {
            loginStatus.setText("You successfully logged in!");
            // TODO change view to task screen
        } else loginStatus.setText("Invalid password or email");
       }


    } catch (Exception e) {
        loginStatus.setText("Invalid password or email");
    }
}
}
