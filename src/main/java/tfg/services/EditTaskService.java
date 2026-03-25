package tfg.services;

public class EditTaskService {
public String editTaskService(String taskId) {
    if (taskId == null || taskId.isBlank()) {
        return ("You must select a task to edit");
    }
    return "Task edited successfully";
}
}
