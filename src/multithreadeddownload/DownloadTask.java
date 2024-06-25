

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

class DownloadTask implements Runnable {

    private final String fileUrl;
    private final String outputFile;
    private final int start;
    private final int end;

    public DownloadTask(String fileUrl, String outputFile, int start, int end) {
        this.fileUrl = fileUrl;
        this.outputFile = outputFile;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
            try (InputStream inputStream = connection.getInputStream(); RandomAccessFile outputFileStream = new RandomAccessFile(outputFile, "rw")) {

                outputFileStream.seek(start);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputFileStream.write(buffer, 0, bytesRead);
                }

            }
            connection.disconnect();
        } catch (IOException e) {
            System.err.println("Something went wrong...");
            System.out.println(e.getMessage());
        }
    }
}
