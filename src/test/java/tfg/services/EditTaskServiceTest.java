package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EditTaskServiceTest {
	@Test
	public void editTaskService1() {
		EditTaskService e = new EditTaskService();
		String taskId = null;
		String expected = "You must select a task to edit";
		String actual = e.editTaskService(taskId);

		assertEquals(expected, actual);
	}
}
