package tfg.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AddTaskServiceSymflowerTest {
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

	@Test
	public void addTaskService2() {
		AddTaskService a = new AddTaskService();
		String title = "";
		String description = null;
		String category = null;
		String date = null;
		String expected = "Title, date, category and content must not be empty";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}

	@Test
	public void addTaskService3() {
		AddTaskService a = new AddTaskService();
		String title = "A";
		String description = null;
		String category = null;
		String date = null;
		String expected = "Title, date, category and content must not be empty";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}

	@Test
	public void addTaskService4() {
		AddTaskService a = new AddTaskService();
		String title = "A";
		String description = "";
		String category = null;
		String date = null;
		String expected = "Title, date, category and content must not be empty";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}

	@Test
	public void addTaskService5() {
		AddTaskService a = new AddTaskService();
		String title = "A";
		String description = "A";
		String category = null;
		String date = null;
		String expected = "Title, date, category and content must not be empty";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}

	@Test
	public void addTaskService6() {
		AddTaskService a = new AddTaskService();
		String title = "A";
		String description = "A";
		String category = null;
		String date = "";
		String expected = "Title, date, category and content must not be empty";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}

	@Test
	public void addTaskService7() {
		AddTaskService a = new AddTaskService();
		String title = "A";
		String description = "A";
		String category = "A";
		String date = "";
		String expected = "Task added successfully";
		String actual = a.addTaskService(title, description, category, date);

		assertEquals(expected, actual);
	}
}
