package tfg.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SignUpServiceSymflowerTest {
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
