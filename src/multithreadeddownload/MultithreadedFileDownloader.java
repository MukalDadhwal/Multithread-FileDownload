

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultithreadedFileDownloader {

    public static void main(String[] args) throws IOException {

        final String fileURL = args[0];
        final int maxThreadCount = Integer.parseInt(args[1]);
        final String fileName = args[0].substring(args[0].lastIndexOf('/')+1);

        URL url = new URL(fileURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int fileSize = connection.getContentLength();
        connection.disconnect();

        long startTime = System.nanoTime(); // benchmarking the start time

        ExecutorService executor = Executors.newFixedThreadPool(maxThreadCount);
        int partSize = fileSize / maxThreadCount;

        for (int i = 0; i < maxThreadCount; i++) {
            int start = i * partSize;
            int end = (i == maxThreadCount - 1) ? fileSize : (start + partSize - 1);
            executor.execute(new DownloadTask(fileURL, fileName, start, end));
        }

        executor.shutdown(); // to let the excutor know that nothing new is going to be added in the task queuey
        try {
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                // Timeout occurred; force shutdown
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Handle interruption
            executor.shutdownNow();
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }

        long endTime = System.nanoTime(); // benchmarking the start time

        // Calculate the execution time in nanoseconds
        long executionTime = endTime - startTime;

        // Optionally, convert nanoseconds to milliseconds
        double executionTimeInMilliseconds = executionTime / 1_000_000.0;

        // System.out.println("Execution time in nanoseconds: " + executionTime);
        System.out.println("Execution time in milliseconds: " + executionTimeInMilliseconds);
        System.out.println("Execution time in seconds: " + executionTimeInMilliseconds / 1000);

    }

}
