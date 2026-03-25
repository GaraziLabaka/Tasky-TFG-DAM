package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CompleteTaskServiceTest {
	@Test
	public void completeTaskService1() {
		CompleteTaskService c = new CompleteTaskService();
		String taskId = null;
		String expected = "You must select a task to complete";
		String actual = c.completeTaskService(taskId);

		assertEquals(expected, actual);
	}
}
