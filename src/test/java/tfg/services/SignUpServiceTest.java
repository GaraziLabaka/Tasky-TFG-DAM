package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SignUpServiceTest {
	@Test
	public void signUpService1() {
		SignUpService s = new SignUpService();
		String mail = null;
		String name = null;
		String password = null;
		String expected = "You must enter data to sign up";
		String actual = s.signUpService(mail, name, password);

		assertEquals(expected, actual);
	}
}
