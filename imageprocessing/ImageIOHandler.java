
package com.mycompany.imageprocessing;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageIOHandler {
    public static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }

    public static void saveImage(BufferedImage img, String filePath) {
        try {
            ImageIO.write(img, "jpg", new File(filePath));
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }
}