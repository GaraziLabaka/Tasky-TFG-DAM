package tfg.services;

public class LoginService {
// The service is plain java, so it can be tested without the need of any fxml element
    public String loginService(String mail, String password) {
    if (mail == null || mail.isBlank() || password == null || password.isBlank()) {
        return ("You must enter data to log in");
    }
    return "Logging in...";
}
}
