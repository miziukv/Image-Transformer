/*
 * Course: CSC1120
 * Summer 2024
 * Lab 5 - Functional Image Transformer
 * Name: Vlad Miziuk
 * Created: 6/18/2024
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * ImageTransformer class that extends the Application class and sets up a primary
 * stage with a user interface defined in an FXML file. The class loads the file
 * from sceneBuilder.fxml which sets the layout and controls of the application.
 * In case a failure occurs while loading the file, it displays a useful error message
 * in an alert box.
 * The class runs the JavaFX program and displays the main window.
 */
public class ImageTransformer extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                    "sceneBuilder.fxml")));
            stage.setTitle("Image Transform");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
