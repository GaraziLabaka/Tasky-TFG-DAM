package tfg.Model;

import java.time.LocalDate;

public class Task {
    private int id;
    private LocalDate dateAdded;
    private String title;
    private String content;
    private Category category;
    private boolean completed;

    public Task(int id, LocalDate dateAdded, String title, String content, Category category, boolean completed) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.title = title;
        this.content = content;
        this.category = category;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
