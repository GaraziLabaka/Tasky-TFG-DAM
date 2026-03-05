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
import tfg.Model.Category;
import tfg.Model.Task;

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

    System.out.println(tasksTitle + " " + tasksContent + " " + formattedDate + " " + tasksCategory);
    Task t = new Task(formattedDate, tasksTitle, tasksContent, tasksCategory, false);

    taskTable.getItems().add(t);

    taskTitle.clear();
    taskContent.clear();
    infoLabel.setText("Task added successfully");

    addToDB(t);
}

    public void deleteTask() {

        // delete items from DB first, to avoid conflicts

        try {
            // select the item from the table
                Task item = taskTable.getSelectionModel().getSelectedItem();
            // check if content is empty. If not, proceed with the else block
            if (taskTable == null || taskTable.getSelectionModel().getSelectedItem() == null) {
                infoLabel.setText("Select an entry from the table");
            } else {
                
                if (item == null) {
                    infoLabel.setText("Select an entry from the table");
                    
                } else {
                    try (EntityManager em = emf.createEntityManager()) {
                        em.getTransaction().begin();
                        
                        Task selectedTask = em.find(Task.class, item.getId());
                        if (selectedTask != null) {
                            em.remove(selectedTask);
                            em.getTransaction().commit();
                            em.close();

                            // remove the selected item from the GUI
                            if (taskTable == null || taskTable.getSelectionModel().getSelectedItem() == null) {
                            infoLabel.setText("Select an entry from the table");
                            } else {
                            taskTable.getItems().remove(item);
                            infoLabel.setText("Task deleted successfully");
                            }
                }

                        }
                    }
                }
            
        } catch (Exception e) {
            infoLabel.setText("Error deleting task from the table");
        }
    }
    

    public void editTask() {

    }

    public void saveTask() {

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
    try (EntityManager em = emf.createEntityManager()) {
        em.getTransaction().begin();

        ArrayList<Task> tasksFromDB = new ArrayList<>(
            em.createQuery("SELECT t FROM Task t", Task.class).getResultList()
        );
        taskTable.getItems().addAll(tasksFromDB);
        em.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error:" + e);
        infoLabel.setText("No items in database");
    }
}


}
