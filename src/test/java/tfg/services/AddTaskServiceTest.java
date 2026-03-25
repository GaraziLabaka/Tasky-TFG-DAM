package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddTaskServiceTest {
	@Test
	public void addTaskService1() {
		AddTaskService a = new AddTaskService();
		String title = null;
		String description = null;
		String category = null;
		String date = null;
		String expected = "Title, date, category and content must not be empty";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}
}
