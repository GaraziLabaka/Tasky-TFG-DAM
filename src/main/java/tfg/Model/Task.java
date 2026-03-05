package tfg.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

    @Entity
    @Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String dateAdded;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    @Column(nullable = false)
    private boolean completed;
    @ManyToOne(User.class, optional=false)
    @JoinColumn(referencedColumnName="id", unique=true, nullable=false)
    private Long user_id;

     public Task() {
       
    }

    public Task(String dateAdded, String title, String content, String category, boolean completed) {
        this.category = Category.valueOf(category.toUpperCase());
        this.completed = completed;
        this.content = content;
        this.dateAdded = dateAdded;
        this.id = id;
        this.title = title;
    }

    public Task(String dateAdded, String title, String content, String category, boolean completed, Long user_id) {
        this.category = Category.valueOf(category.toUpperCase());
        this.completed = completed;
        this.content = content;
        this.dateAdded = dateAdded;
        this.id = id;
        this.title = title;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }


}
