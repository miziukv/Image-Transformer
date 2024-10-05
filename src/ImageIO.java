/*
 * Course: CSC1120
 * Summer 2024
 * Lab 5 - Functional Image Transformer
 * Name: Vlad Miziuk
 * Created: 6/18/2024
 */

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is an ImageIO class that handles the reading and the writing of image files in formats
 * such as .msoe, .bmsoe and .png. It includes methods for converting hexadecimal color values
 * to ARGB and vice versa. This class is designed to facilitate image processing in GUI.
 */
public class ImageIO {

    private static final int BIT_MASK = 0x00FFFFFF;
    private static final int COLOR_LENGTH = 7;
    private static final int COLOR_BASE = 16;

    /**
     * Converts from hexadecimal color format to an ARGB integer value.
     * @param color a six digit hexadecimal value representing the RGB of a color prefixed with "#"
     * @return The integer ARGB value corresponding to the color specified
     *
     * @throws NumberFormatException if the specified string does not represent a
     * 6-digit hexadecimal value
     */
    public static int hexColorToArgb(String color) {
        if (color == null || color.length() != COLOR_LENGTH || color.charAt(0) != '#') {
            throw new NumberFormatException("Invalid color: " + color);
        }
        return Integer.parseUnsignedInt("ff" + color.substring(1), COLOR_BASE);
    }

    /**
     * Returns string representing a six-digit hexadecimal color prefixed with "#".
     * @param argb a integer value representing an ARGB color
     * @return a six-digit hexadecimal color
     */
    public static String argbToHexColor(int argb) {
        return String.format("#%06X", argb & BIT_MASK);
    }
    private static Image readMSOE(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String firstLine = reader.readLine();

            if (firstLine == null) {
                throw new IllegalArgumentException("Input file is empty.");
            }

            if (!firstLine.equals("MSOE")) {
                throw new IllegalArgumentException("Not an MSOE file.");
            }

            String[] parts = reader.readLine().split("\\s+");
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);

            WritableImage image = new WritableImage(width, height);
            PixelWriter writer = image.getPixelWriter();

            for (int y = 0; y < height; y++) {

                String colorLine = reader.readLine();
                if (colorLine == null) {
                    throw new IllegalArgumentException("Incorrect dimensions provided.");
                }
                String[] color = colorLine.split("\\s+");
                for (int x = 0; x < width; x++) {
                    writer.setArgb(x, y, hexColorToArgb(color[x]));
                }
            }
            return image;

        }
    }

    private static void writeMSOE(Path path, Image image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image file is empty.");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            writer.write("MSOE\n");
            writer.write((int) image.getWidth() + " " + (int) image.getHeight() + "\n");

            PixelReader reader = image.getPixelReader();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    String hex = argbToHexColor(reader.getArgb(x, y));
                    writer.write(hex + " ");
                }
                writer.write("\n");
            }
        }
    }

    private static Image readBMSOE(Path path) throws IOException {
        try (DataInputStream in = new DataInputStream(Files.newInputStream(path))) {

            int width = in.readInt();
            int height = in.readInt();

            WritableImage image = new WritableImage(width, height);
            PixelWriter writer = image.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    writer.setArgb(x, y, in.readInt());
                }
            }
            if (in.available() > 0) {
                throw new IllegalArgumentException("Incorrect dimensions provided.");
            }
            return image;
        }
    }

    private static void writeBMSOE(Path path, Image image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image file is empty.");
        }

        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(path))) {
            out.writeInt((int) image.getWidth());
            out.writeInt((int) image.getHeight());

            PixelReader reader = image.getPixelReader();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    out.writeInt(reader.getArgb(x, y));
                }
            }

        }
    }

    private static Image readPNG(Path path) throws IOException {
        return new Image(new FileInputStream(String.valueOf(path)));
    }

    private static void writePNG(Path path, Image image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image file is empty.");
        }

        try (OutputStream out = Files.newOutputStream(path)) {
            java.awt.image.BufferedImage bImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image,
                    null);
            javax.imageio.ImageIO.write(bImage, "PNG", out);
        }
    }

    /**
     * Takes in a path and checks whether its hexadecimal, binary, or png based on its
     * extension and reads the file by calling appropriate helper methods. Stores the values
     * in an image object.
     * @param path path of the file to be read.
     * @return an Image object containing the image data
     * @throws IllegalArgumentException if file extension is neither .msoe, .bmsoe., nor .png
     * @throws IOException is failed to read the file
     */
    public static Image read(Path path) throws IOException {
        String fileName = path.getFileName().toString();

        if (fileName.endsWith(".msoe")) {
            return readMSOE(path);
        } else if (fileName.endsWith(".bmsoe")) {
            return readBMSOE(path);
        } else if (fileName.endsWith(".png")){
            return readPNG(path);
        } else {
            throw new IllegalArgumentException("Unsupported file extension.");
        }
    }

    /**
     * Takes in a path and an Image object created after reading the input file.
     * Checks the file extension and determines the appropriate helper method to call
     * (writeMSOE, writeBMSOE, or writePNG).
     * @param path path of the file where it will be written.
     * @param image an Image object to write to file.
     * @throws IOException if failed to write to file
     */
    public static void write(Path path, Image image) throws IOException {
        String fileName = path.getFileName().toString();

        if (fileName.endsWith(".msoe")) {
            writeMSOE(path, image);
        } else if (fileName.endsWith(".bmsoe")) {
            writeBMSOE(path, image);
        } else if (fileName.endsWith(".png")) {
            writePNG(path, image);
        } else {
            throw new IOException("Failed to write to file.");
        }
    }
}

