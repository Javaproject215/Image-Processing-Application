
package com.mycompany.imageprocessing;

import java.awt.image.BufferedImage;

public class ImageEditor {
    private BufferedImage image;

    public ImageEditor(BufferedImage image) {
        this.image = image;
    }

    

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void applyFilter(String filterType) {
        switch (filterType.toLowerCase()) {
            case "grayscale":
                image = ImageFilter.applyGrayscale(image);
                break;
            // Add more filters as needed
        }
    }

    public void applyTransformation(String transformationType) {
        switch (transformationType.toLowerCase()) {
            case "rotate":
                image = ImageTransformer.rotateImage(image, 90);
                break;
            // Add more transformations as needed
        }
    }
}
