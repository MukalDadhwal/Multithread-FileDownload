

import java.io.*;
import java.net.*;

class SingleThreadDownloader {

    public static void main(String[] args) throws InterruptedException {
        final String fileURL = args[0];
        final String fileName = args[0].substring(args[0].lastIndexOf('/')+1);

        Runnable r = () -> {
            try {
                URL urlObj = new URL(fileURL);

                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

                conn.setRequestMethod("GET");
                BufferedReader in;
                FileWriter fw;
                try ( // Reading response
                        InputStream inputStream = conn.getInputStream()) {
                    in = new BufferedReader(new InputStreamReader(inputStream));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                
                    fw = new FileWriter(fileName);
                    while ((inputLine = in.readLine()) != null) {
                        fw.write(inputLine + '\n');
                        response.append(inputLine);
                    }
                    in.close();
                }
                in.close();
                fw.close();

            } catch (MalformedURLException e) {
                System.out.println("Url cannot be parsed...");
            } catch (SocketException e) {
                System.out.println("Error reading input stream" + e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Connection error has occured...");
            }
        };

        long start = System.nanoTime();

        Thread thread = new Thread(r);

        thread.start();

        while (thread.isAlive()) {
            // System.out.println("downloading...");
        }

        long end = System.nanoTime();

        long executionTime = end - start;

        // Optionally, convert nanoseconds to milliseconds
        double executionTimeInMilliseconds = executionTime / 1_000_000.0;

        // System.out.println("Execution time in nanoseconds: " + executionTime);
        System.out.println("Execution time in milliseconds: " + executionTimeInMilliseconds);
        System.out.println("Execution time in seconds: " + executionTimeInMilliseconds / 1000);

    }
}
