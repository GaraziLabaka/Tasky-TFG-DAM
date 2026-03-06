package tfg.TaskWindow;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tfg.model.Category;
import tfg.model.SessionUser;
import tfg.model.Task;
import tfg.model.User;

public class TaskClass {

    @FXML
    private TextField taskTitle;
    @FXML
    private DatePicker taskDate;
    @FXML
    private ComboBox<String> taskCategory;
    @FXML
    private TextArea taskContent;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> tableDate, tableTitle, tableContent;
    @FXML
    private TableColumn<Task, Boolean> tableCompleted;
    @FXML
    private TableColumn<Task, Category> tableCategory;
    @FXML
    private Button addTask, deleteTask, editTask, saveTask, completeTask, notifyTask, docs, logout;
    @FXML
    private Label infoLabel;

    private final String[] category = {"WORK", "HOBBIES", "SELF_CARE", "CHORES"};
    // static entity manager factory
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("tasky");
    
    private Stage stage;
    private Parent root;
    private Scene scene;
		
	public void initialize() throws SQLException {
		// fill combobox
		taskCategory.getItems().addAll(category);
		taskCategory.setOnAction(this::getCategory);

        // sets values for columns of the class
        tableDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableCompleted.setCellValueFactory(new PropertyValueFactory<>("completed"));

		// Load data from db
        loadFromDB();
    }

    public void getCategory(ActionEvent event) {
		
		String taskCat = taskCategory.getValue();
	}

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tfg/View/loginWindow.fxml"));	
		root = loader.load();	
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

 public void addTask() {
   
    String tasksTitle = taskTitle.getText();
    String tasksContent = taskContent.getText();
    String tasksCategory = taskCategory.getSelectionModel().getSelectedItem();

    LocalDate date = taskDate.getValue();

    if (tasksTitle == null || tasksTitle.isEmpty() ||
        tasksContent == null || tasksContent.isEmpty() ||
        date == null ||
        tasksCategory == null || tasksCategory.isEmpty()) {

        infoLabel.setText("Title, date, category and content must not be empty");
        return;
    }

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = date.format(format);

    Task t = new Task(formattedDate, tasksTitle, tasksContent, tasksCategory, false);

    // Assign user to the created task
    t.setUser(SessionUser.getCurrentUser());
// add task to db
     addToDB(t);
     // add task to the GUI
    taskTable.getItems().add(t);

    taskTitle.clear();
    taskContent.clear();
    infoLabel.setText("Task added successfully");

   
}

    public void deleteTask() {

        // delete items from DB first, to avoid conflicts

        try {
            // select the item from the table
                Task item = taskTable.getSelectionModel().getSelectedItem();
            // check if content is empty. If not, proceed with the else block
            if (item == null) {
                infoLabel.setText("Select an entry from the table");
            } else {
                    try (EntityManager em = emf.createEntityManager()) {
                        em.getTransaction().begin();
                        
                        Task selectedTask = em.find(Task.class, item.getId());
                        if (selectedTask != null) {
                            em.remove(selectedTask);
                            em.getTransaction().commit();
                            
                        
                            // remove the selected item from the GUI
                            if (item == null) {
                            infoLabel.setText("Select an entry from the table");
                            } else {
                            taskTable.getItems().remove(item);
                            infoLabel.setText("Task deleted successfully");
                            }

                            em.close();
                }}

                        }
            
        } catch (Exception e) {
            infoLabel.setText("Error deleting task from the table");
        }
    }
    

    public void editTask() {
                // select the item from the table
                Task item = taskTable.getSelectionModel().getSelectedItem();
        try {
            // edit items in the GUI
			if (item == null) {
				infoLabel.setText("Add a task first");
            } else {
                // load the fields
                taskTitle.setText(item.getTitle());
                taskContent.setText(item.getContent());
                taskCategory.setValue(item.getCategory().toString());
                // formats and parses date to add it to the field
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate parsedDate = LocalDate.parse(item.getDateAdded(), format);
                taskDate.setValue(parsedDate);
            }
		} catch (Exception e) {
			infoLabel.setText("Add a task first");
		}

	}
    

    public void saveTask() {
        // gets selected item
    Task item = taskTable.getSelectionModel().getSelectedItem();

    if (item == null) {
        infoLabel.setText("Select a task from the table");
        return;
    }

    try (EntityManager em = emf.createEntityManager()) {
        em.getTransaction().begin();
        // find the task in the db
        Task taskDB = em.find(Task.class, item.getId());

        if (taskDB != null) {

            // get the new values from the GUI
            String newTitle = taskTitle.getText();
            String newContent = taskContent.getText();
            String newCategory = taskCategory.getValue();
            LocalDate newDate = taskDate.getValue();

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // update new content in db
            taskDB.setTitle(newTitle);
            taskDB.setContent(newContent);
            taskDB.setCategory(Category.valueOf(newCategory));
            taskDB.setDateAdded(newDate.format(format));

            // save changes
            em.merge(taskDB);
            em.getTransaction().commit();
        }
    } catch (Exception e) {
        e.printStackTrace();
        infoLabel.setText("Error saving task");
        return;
    }

    // refresh GUI
    loadFromDB();
    infoLabel.setText("Task updated successfully");
}

    public void completeTask() {
    // Update database status

    try {
        //Select item data
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
    // Create entity manager
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    // find task in table
    Task taskFromDB = em.find(Task.class, selectedTask.getId());
    // set task completed
    taskFromDB.setCompleted(true);
    // commit
    em.getTransaction().commit();
    
    // update GUI
    selectedTask.setCompleted(true);
    
    // refresh to update GUI, reload from DB
    taskTable.getItems().clear();
    loadFromDB();
    infoLabel.setText("You completed a task!");
    } catch (Exception e) {
        e.printStackTrace();
        infoLabel.setText("Something went wrong. Complete your task IRL :)");
    }
    
    }

    public void switchDocs(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tfg/View/docsWindow.fxml"));	
		root = loader.load();	
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }


    public void notifyTask() {

    }

    public void addToDB(Task t) {

        try  {
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();
            }

        infoLabel.setText("Task added successfully!");
    } catch (Exception e) {
        infoLabel.setText("Error registering task.");
    }
    }

    public void loadFromDB() {

        // clears table before loading to avoid clutter
        taskTable.getItems().clear();
    try (EntityManager em = emf.createEntityManager()) {
        
        em.getTransaction().begin();
        //gets current logged user
        User current = SessionUser.getCurrentUser();

        // creates an array of tasks
        ArrayList<Task> tasksFromDB = new ArrayList<>(
            // query. Selects all tasks that belong to the current user
            em.createQuery("SELECT t FROM Task t WHERE t.user.id = :uid", Task.class)
              .setParameter("uid", current.getId())
              .getResultList()
        );
        // adds tasks from the db to the GUI
        taskTable.getItems().addAll(tasksFromDB);
        em.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        infoLabel.setText("No items in database");
    }
}




}
