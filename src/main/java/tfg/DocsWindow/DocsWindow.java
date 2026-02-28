package tfg.DocsWindow;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DocsWindow {
    @FXML
    WebView web;
    @FXML
    Button returnBtn;

    private Parent root;
    private Scene scene;
    private Stage stage;


    public void returnToTask(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/tfg/View/taskWindow.fxml"));	
		root = loader.load();	
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

}
