package tfg.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LoginServiceSymflowerTest {
	@Test
	public void loginService1() {
		LoginService l = new LoginService();
		String mail = null;
		String password = null;
		String expected = "You must enter data to log in";
		String actual = l.loginService(mail, password);

		assertEquals(expected, actual);
	}
}
