package tfg.services;

public class DeleteTaskService {
public String deleteTaskService(String taskId) {
    if (taskId == null || taskId.isBlank()) {
        return ("You must select a task to delete");
    }
    return "Task deleted successfully";
}
}