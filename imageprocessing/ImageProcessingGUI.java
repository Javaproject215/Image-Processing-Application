
package com.mycompany.imageprocessing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessingGUI extends JFrame {
    private JLabel imageLabel;
    private BufferedImage currentImage;
    private ImageEditor imageEditor;

    public ImageProcessingGUI() {
    this.setTitle("Image Processing Application");
    this.setSize(600, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.getContentPane().setBackground(new Color(0x123456));

    // Configure image label
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    imageLabel.setPreferredSize(new Dimension(400, 400));
    imageLabel.setOpaque(false); // Make the label transparent

    // Configure JScrollPane
    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.getViewport().setOpaque(false); // Make viewport transparent
    scrollPane.setOpaque(false); // Make scroll pane transparent
    scrollPane.setBackground(new Color(0x123456)); // Match background color

    // Add the scroll pane
    add(scrollPane, BorderLayout.CENTER);

    // Configure button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.setBackground(new Color(0x345678)); // Set a distinct background for the panel

    JButton loadButton = new JButton("Load Image");
    loadButton.addActionListener(new LoadImageAction());

    JButton grayscaleButton = new JButton("Grayscale");
    grayscaleButton.addActionListener(new ApplyFilterAction("grayscale"));

    JButton rotateButton = new JButton("Rotate 90°");
    rotateButton.addActionListener(new ApplyTransformationAction("rotate"));

    JButton saveButton = new JButton("Save Image");
    saveButton.addActionListener(new SaveImageAction());

    buttonPanel.add(loadButton);
    buttonPanel.add(grayscaleButton);
    buttonPanel.add(rotateButton);
    buttonPanel.add(saveButton);

    add(buttonPanel, BorderLayout.SOUTH);

    // Display the frame
    setVisible(true);
}


    // Inner classes for handling button actions
    private class LoadImageAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                currentImage = ImageIOHandler.loadImage(file.getAbsolutePath());
                if (currentImage != null) {
                // Resize the image to 400dp (or pixels)
                int targetSize = 400;
                int width = currentImage.getWidth();
                int height = currentImage.getHeight();
                if (width > targetSize || height > targetSize) {
                    float aspectRatio = (float) width / height;
                    if (aspectRatio > 1) {
                        // Landscape image
                        currentImage = resizeImage(currentImage, targetSize, (int) (targetSize / aspectRatio));
                    } else {
                        // Portrait or square image
                        currentImage = resizeImage(currentImage, (int) (targetSize * aspectRatio), targetSize);
                    }
                }

                imageEditor = new ImageEditor(currentImage);
                imageLabel.setIcon(new ImageIcon(currentImage));
                pack();
            } else {
                JOptionPane.showMessageDialog(null, "Error loading image.");
            }
            }
        }
    }

    private class ApplyFilterAction implements ActionListener {
        private String filterType;

        public ApplyFilterAction(String filterType) {
            this.filterType = filterType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageEditor != null) {
                imageEditor.applyFilter(filterType);
                currentImage = imageEditor.getImage();
                imageLabel.setIcon(new ImageIcon(currentImage));
            } else {
                JOptionPane.showMessageDialog(null, "Please load an image first.");
            }
        }
    }

   private class ApplyTransformationAction implements ActionListener {
    private String transformationType;

    public ApplyTransformationAction(String transformationType) {
        this.transformationType = transformationType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (imageEditor != null) {
            imageEditor.applyTransformation(transformationType);
            currentImage = imageEditor.getImage();

            // Resize image to fit within 400x400 after rotation
            int targetSize = 400;
            int width = currentImage.getWidth();
            int height = currentImage.getHeight();

            if (width > targetSize || height > targetSize) {
                float aspectRatio = (float) width / height;
                if (aspectRatio > 1) {
                    // Landscape image
                    currentImage = resizeImage(currentImage, targetSize, (int) (targetSize / aspectRatio));
                } else {
                    // Portrait or square image
                    currentImage = resizeImage(currentImage, (int) (targetSize * aspectRatio), targetSize);
                }
            }

            imageLabel.setIcon(new ImageIcon(currentImage));
            
            // Pack the frame and center it without changing the window size
            pack();
            setResizable(false); // Prevent window from resizing
            setLocationRelativeTo(null); // Center the window on the screen
        } else {
            JOptionPane.showMessageDialog(null, "Please load an image first.");
        }
    }
}

    private class SaveImageAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentImage != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    ImageIOHandler.saveImage(currentImage, fileToSave.getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "Image saved successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No image to save.");
            }
        }
    }
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
    Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

   Graphics2D g2d = resizedImage.createGraphics();
    g2d.drawImage(scaledImage, 0, 0, null);
    g2d.dispose();

    return resizedImage;
}
}