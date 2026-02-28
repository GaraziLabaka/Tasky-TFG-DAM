package tfg.TaskWindow;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tfg/View/loginWindow.fxml"));	
		root = loader.load();	
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
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

    public void switchDocs(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tfg/View/docsWindow.fxml"));	
		root = loader.load();	
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    public void search() {

    }

    public void notifyTask() {

    }
}
