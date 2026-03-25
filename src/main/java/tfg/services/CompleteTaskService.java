package tfg.services;

public class CompleteTaskService {
public String completeTaskService(String taskId) {
    if (taskId == null || taskId.isBlank()) {
        return ("You must select a task to complete");
    }
    return "You completed a task!";
}
}