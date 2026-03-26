package tfg.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

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
