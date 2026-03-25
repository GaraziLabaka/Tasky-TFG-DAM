package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteTaskServiceTest {
	@Test
	public void deleteTaskService1() {
		DeleteTaskService d = new DeleteTaskService();
		String taskId = null;
		String expected = "You must select a task to delete";
		String actual = d.deleteTaskService(taskId);

		assertEquals(expected, actual);
	}
}
