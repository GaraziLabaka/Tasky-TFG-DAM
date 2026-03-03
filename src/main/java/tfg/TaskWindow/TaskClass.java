package tfg.TaskWindow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.EntityManager;
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

    private String[] category = {"Chores", "Hobby", "Work", "Self-care"};
    // static entity manager
    private static final EntityManager emf =
        Persistence.createEntityManagerFactory("tasky").createEntityManager();
    
    private Stage stage;
    private Parent root;
    private Scene scene;
		
	public void initialize() {
		// fill combobox
		taskCategory.getItems().addAll(category);
		taskCategory.setOnAction(this::getCategory);

        // sets values for columns of the class
        tableDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableCompleted.setCellValueFactory(new PropertyValueFactory<>("completed"));

		// TODO: Load data from db

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

    // delete items from the GUI
        try {
			// check if content is empty. If not, proceed with the else block
			if (taskTable == null || taskTable.getSelectionModel().getSelectedItem() == null) {
				infoLabel.setText("Select an entry from the table");
			} else {
				// select the item from the table
				Task item = taskTable.getSelectionModel().getSelectedItem();
				// remove the selected item
				taskTable.getItems().remove(item);

                // delete items from DB
                Connection con = 
                DriverManager.getConnection("jdbc:mysql://localhost/tasky", "root", "root");
                String deleteQuery = "DELETE * FROM tasks WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(deleteQuery);
                ps.setInt(1, item.getId());
                ps.executeUpdate();

			}
		} catch (Exception e) {
			infoLabel.setText("Select an item from the table");
		}
	}
    

    public void editTask() {

    }

    public void saveTask() {

    }

    public void completeTask() {

        deleteTask();
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
        emf.getTransaction().begin();
        emf.persist(t);
        emf.getTransaction().commit();

        infoLabel.setText("Task added successfully!");

    } catch (Exception e) {
        infoLabel.setText("Error registering task.");
    }
    }

    public void loadFromDB() throws SQLException {
        String loadQuery = "SELECT * FROM tasks";
        Connection con = 
        DriverManager.getConnection("jdbc:mysql://localhost/tasky", "root", "root");
        PreparedStatement ps = con.prepareStatement(loadQuery);
        ResultSet rs = ps.executeQuery(loadQuery);
    }


}
