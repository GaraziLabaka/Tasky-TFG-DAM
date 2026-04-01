package tfg;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Image img;

    @Override
    public void start(Stage stage) throws IOException {
        img = new Image("/static/images/logo.png");
        scene = new Scene(loadFXML("loginWindow"), 640, 480);
        stage.setTitle("Tasky");
        stage.getIcons().add(img);
        String css = this.getClass().getResource("/static/CSS/styles.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/tfg/View/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}