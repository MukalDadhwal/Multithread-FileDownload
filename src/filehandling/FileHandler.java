package multifiledownloader.src.filehandling;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;

public class FileHandler {
    final String url = "http://127.0.0.1:5500/";

    synchronized void downloadTextFile(String fileName) {
        try {
            URL urlObj = new URL(url + fileName);

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            conn.setRequestMethod("GET");
            // Reading response
            InputStream inputStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuilder response = new StringBuilder();
            FileWriter fw = new FileWriter("multifiledownloader/src/"+fileName);

            while ((inputLine = in.readLine()) != null) {
                fw.write(inputLine+'\n');
                response.append(inputLine);
            }
            in.close();

            inputStream.close();
            in.close();
            fw.close();

        } catch (MalformedURLException e) {
            System.out.println("Url cannot be parsed...");
        }

        catch (SocketException e) {
            System.out.println("Error reading input stream" + e.getMessage());
        }

        catch (IOException e) {
            System.out.println("Connection error has occured...");
        }
    }

    synchronized void downloadImageFile(String fileName) {
        try {
            URL urlObj = new URL(url + fileName);

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            conn.setRequestMethod("GET");
            // Reading response
            InputStream inputStream = conn.getInputStream();
            BufferedInputStream in = new BufferedInputStream(inputStream);
            FileOutputStream out = new FileOutputStream("multifiledownloader/src/"+fileName);

            byte[] buffer = new byte[1024]; // Create a buffer for reading data in chunks
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) { // Read the data into the buffer
                out.write(buffer, 0, bytesRead); // Write the data from the buffer to the file
            }

            inputStream.close();
            in.close();
            out.close();

        } catch (Exception e) {
            System.out.println("Something went wrong while downloading image file... " + e.getMessage());
        }
    }

    public synchronized void downloadFile(String fileName) {
        try {
            URL urlObj = new URL(url + fileName);

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("The response code: " + responseCode);

            if (responseCode == 200) {
                String contentType = conn.getHeaderField("Content-Type");

                switch (contentType.split("/")[0]) {
                    case "text":
                        downloadTextFile(fileName);
                        break;

                    default:
                        downloadImageFile(fileName);
                        break;
                }

                System.out.println("DOWNLOADING FILE " + fileName + "...\n");

            } else {
                // throw new HttpException("Response code is not 200");
                System.out.println("Response code not 200: " + responseCode);
            }

        } catch (IOException e) {
            System.out.println("Some went wrong while sending get request." + e.getMessage());

        }
    }

}
