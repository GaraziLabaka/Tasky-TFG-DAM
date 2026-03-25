package tfg.services;

public class AddTaskService {
public String addTaskService(String title, String description, String category, String date) {
    if(title == null || title.isEmpty() ||
        description == null || description.isEmpty() ||
        date == null ||
        category == null || category.isEmpty()) {

        return "Title, date, category and content must not be empty";
    }
    return "Task added successfully";
}
}
