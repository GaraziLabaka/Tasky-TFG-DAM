package tfg.services;

public class SignUpService {
public String signUpService(String mail, String name, String password) {
    if (mail == null || mail.isBlank() || name == null || name.isBlank() || password == null || password.isBlank()) {
        return ("You must enter data to sign up");
    }
    return "Signing up...";
}
}
