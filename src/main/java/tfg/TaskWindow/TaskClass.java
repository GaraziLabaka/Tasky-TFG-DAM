package tfg.TaskWindow;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskClass {
    @FXML
    private TextField taskTitle;
    @FXML
    private DatePicker taskDate;
    @FXML
    private ComboBox taskCategory;
    @FXML
    private TextArea taskContent;
    @FXML
    private TableView taskTable;
    @FXML
    private Button addTask, deleteTask, editTask, saveTask, completeTask, notifyTask, docs, logout, search;

    private Stage stage;
    private Parent root;
    private Scene scene;

    public void logout() {
        
    }

    public void addTask() {

    }

    public void deleteTask() {

    }

    public void editTask() {

    }

    public void saveTask() {

    }

    public void completeTask() {

    }

    public void switchDocs() {

    }

    public void search() {

    }

    public void notifyTask() {

    }
}
