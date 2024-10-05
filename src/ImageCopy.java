/*
 * Course: CSC1120
 * Summer 2024
 * Lab 2 - Image Transformer
 * Name: Vlad Miziuk
 * Created: 5/24/2024
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * This is an ImageCopy class that utilizes JavaFX and makes a simple GUI that allows for users
 * to pass in an input file which will be copied to the user specified output file. The
 * program uses the ImageIO class and is able to process .msoe, .bmsoe, and .png files
 * in both directions. Useful status and/or error information is displayed.
 */
public class ImageCopy extends Application {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private TextField input1;
    private TextField input2;
    private Label warning;
    private ImageView imageView;
    @Override
    public void start(Stage stage) {
        input1 = new TextField();
        input2 = new TextField();
        warning = new Label("");
        imageView = new ImageView();

        Button copy = new Button("Copy");
        Pane pane1 = new HBox();
        Pane pane2 = new HBox();
        Pane pane3 = new HBox();

        pane1.getChildren().add(new Label("Input file"));
        pane1.getChildren().add(input1);
        pane2.getChildren().add(new Label("Output file"));
        pane2.getChildren().add(input2);
        pane3.getChildren().add(copy);

        Pane pane4 = new VBox();
        pane4.getChildren().addAll(pane1, pane2, pane3, warning, imageView);

        Scene scene = new Scene(pane4, WIDTH, HEIGHT);
        copy.setOnAction(this::copyPressed);
        stage.setTitle("ImageCopy");
        stage.setScene(scene);
        stage.show();
    }

    private void copyPressed(ActionEvent actionEvent) {
        String text1 = input1.getText();
        String text2 = input2.getText();
        try {
            Image image = ImageIO.read(Path.of(text1));
            ImageIO.write(Path.of(text2), image);
            imageView.setImage(image);
            warning.setStyle("-fx-text-fill: black;");
            warning.setText("Copied successfully.");

        } catch (IllegalArgumentException | NullPointerException |
                 ArrayIndexOutOfBoundsException | IOException e) {
            imageView.setImage(null);
            warning.setStyle("-fx-text-fill: red;");
            warning.setText(e.getMessage());
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
