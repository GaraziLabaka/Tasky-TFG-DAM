package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
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
