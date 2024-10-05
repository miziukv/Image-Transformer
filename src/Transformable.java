/*
 * Course: CSC1120
 * Summer 2024
 * Lab 5 - Functional Image Transformer
 * Name: Vlad Miziuk
 * Created: 6/18/2024
 */

import javafx.scene.paint.Color;

/**
 * Functional interface for transforming the color of the pixel in an image.
 * The interface is intended for use of image processing operations. Implementation
 * of this interface should define how the color transformation is applied.
 */
public interface Transformable {
    /**
     * Applies a color transformation at the given location (x, y).
     * @param x the x-coordinate of the pixel in the image.
     * @param y the y-coordinate of the pixel in the image.
     * @param color the current color of the pixel.
     * @return the new color of the pixel after apply the transformation.
     */
    Color apply(int x, int y, Color color);
}
