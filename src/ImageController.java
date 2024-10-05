/*
 * Course: CSC1120
 * Summer 2024
 * Lab 5 - Functional Image Transformer
 * Name: Vlad Miziuk
 * Created: 6/18/2024
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Controller class for handling image operations in a JavaFX application. The class
 * provides functionalities such as to open, save, and apply various effects to images such
 * as grayscale, invert, brighten, darken, desaturate, saturate, and others.
 * Error handling is integrated to alert users about issues during file operations or image
 * modifications.
 */
public class ImageController {

    @FXML
    private ImageView imageView;
    private final FileChooser fileChooser = new FileChooser();
    private Image image;

    @FXML
    private void open() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                image = ImageIO.read(file.toPath());
                imageView.setImage(image);
            } catch (IOException | IllegalArgumentException e) {
                errorAlert("Error Loading Image", "Failed to load the image. "
                        + (e.getMessage() != null ? e.getMessage() : ""));
            } catch (ArrayIndexOutOfBoundsException e) {
                errorAlert("Error Loading Image", "Failed to load the image. " +
                        "Incorrect dimensions provided.");
            }
        }
    }

    @FXML
    private void save() {
        if (image != null) {
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    ImageIO.write(file.toPath(), image);
                    image = null;
                } catch (IOException | IllegalArgumentException e) {
                    errorAlert("Error Saving Image", "Failed to save the image.");
                }
            }
        } else {
            errorAlert("Error Saving Image", "No image was opened.");
        }
    }

    @FXML
    private void grayscale() {
        Image grayImage = transformImage(image, (x, y, color) -> color.grayscale());
        imageView.setImage(grayImage);
        image = grayImage;
    }

    @FXML
    private void invert() {
        Image invertedImage = transformImage(image, (x, y, color) -> color.invert());
        imageView.setImage(invertedImage);
        image = invertedImage;
    }

    @FXML
    private void brighten() {
        Image brigherImage = transformImage(image, (x, y, color) -> color.brighter());
        imageView.setImage(brigherImage);
        image = brigherImage;
    }

    @FXML
    private void darken() {
        Image darkerImage = transformImage(image, (x, y, color) -> color.darker());
        imageView.setImage(darkerImage);
        image = darkerImage;
    }

    @FXML
    private void desaturate() {
        Image desaturatedImage = transformImage(image, (x, y, color) -> color.desaturate());
        imageView.setImage(desaturatedImage);
        image = desaturatedImage;
    }

    @FXML
    private void saturate() {
        Image saturatedImage = transformImage(image, (x, y, color) -> color.saturate());
        imageView.setImage(saturatedImage);
        image = saturatedImage;
    }

    @FXML
    private void red() {
        Image redImage = transformImage(image, (x, y, color) ->
                new Color(color.getRed(), 0, 0, color.getOpacity()));
        imageView.setImage(redImage);
        image = redImage;
    }

    @FXML
    private void redGray() {
        Image redGrayImage = transformImage(image, (x, y, color) -> getColorRedGray(y, color));
        imageView.setImage(redGrayImage);
        image = redGrayImage;
    }

    @FXML
    private void blue() {
        Image redImage = transformImage(image, (x, y, color) ->
                new Color(0, 0, color.getBlue(), color.getOpacity()));
        imageView.setImage(redImage);
        image = redImage;
    }

    @FXML
    private void mirror() {
        Image mirroredImage = transformImage(image, (x, y, color) -> getMirroredImage(x, y));
        imageView.setImage(mirroredImage);
        image = mirroredImage;
    }

    @FXML
    private void widen() {
        if (image != null) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage modifiedImage = new WritableImage(width, height);
            PixelReader reader = image.getPixelReader();
            PixelWriter writer = modifiedImage.getPixelWriter();

            int quarter = width / 4;

            for (int y = 0; y < height; y++) {
                int writeX = 0;
                for (int x = quarter; x < quarter * 3; x++) {
                    if (writeX < width - 1) {
                        Color color = reader.getColor(x, y);
                        writer.setColor(writeX++, y, color);
                        writer.setColor(writeX++, y, color);
                    }
                }
            }
            imageView.setImage(modifiedImage);
            image = modifiedImage;
        }
    }

    private void errorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Image transformImage(Image image, Transformable transform) {
        if (image != null) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage modifiedImage = new WritableImage(width, height);
            PixelReader reader = image.getPixelReader();
            PixelWriter writer = modifiedImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    writer.setColor(x, y, transform.apply(x, y, reader.getColor(x, y)));
                }
            }
            return modifiedImage;
        }
        return null;
    }

    private static Color getColorRedGray(int y, Color color) {
        if (y % 2 == 0) {
            return new Color(color.getRed(), 0, 0, color.getOpacity());
        } else {
            return color.grayscale();
        }
    }

    private Color getMirroredImage(int x, int y) {
        int mirroredX = (int) image.getWidth() - x - 1;
        return image.getPixelReader().getColor(mirroredX, y);
    }
}