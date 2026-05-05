package tfg.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DeleteTaskServiceSymflowerTest {
	@Test
	public void deleteTaskService1() {
		DeleteTaskService d = new DeleteTaskService();
		String taskId = null;
		String expected = "You must select a task to delete";
		String actual = d.deleteTaskService(taskId);

		assertEquals(expected, actual);
	}
}
