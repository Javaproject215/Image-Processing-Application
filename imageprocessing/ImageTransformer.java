
package com.mycompany.imageprocessing;


import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTransformer {
    public static BufferedImage rotateImage(BufferedImage img, int angle) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, img.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }

}
