package tfg.services;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SaveTaskServiceTest {
	@Test
	public void saveTaskService1() {
		SaveTaskService s = new SaveTaskService();
		String taskId = null;
		String expected = "You must select a task to delete";
		String actual = s.saveTaskService(taskId);

		assertEquals(expected, actual);
	}
}
