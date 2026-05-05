package tfg.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompleteTaskServiceSymflowerTest {
	@Test
	public void completeTaskService1() {
		CompleteTaskService c = new CompleteTaskService();
		String taskId = null;
		String expected = "You must select a task to complete";
		String actual = c.completeTaskService(taskId);

		assertEquals(expected, actual);
	}
}
