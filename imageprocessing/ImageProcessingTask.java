
package com.mycompany.imageprocessing;


import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageProcessingTask {
    public static void processInParallel(Runnable task) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executor.submit(task);
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for task completion
        }
    }
}