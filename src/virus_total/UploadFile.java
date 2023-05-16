package virus_total;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class UploadFile {
    private String apiKey;
    private String filePath;

    public UploadFile(String apiKey, String filePath){
        this.apiKey = apiKey;
        this.filePath = filePath;
    }
    public String getResponseUploadFile() throws IOException {
        String url = "https://www.virustotal.com/api/v3/files";

        File file = new File(filePath);

        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);
        connection.setDoOutput(true);

        String boundary = "---------------------------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        boundary = "--" + boundary;

        OutputStream outputStream = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

        // Write file part
        writer.append(boundary).append("\r\n");
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName()).append("\"\r\n");
        writer.append("Content-Type: application/pdf\r\n\r\n");
        writer.flush();

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        // End of multipart/form-data
        writer.append("\r\n").append(boundary).append("--\r\n");
        writer.flush();
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Successful request
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String res = response.toString();
            connection.disconnect();
            return res;
        } else {
            // Error occurred
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            StringBuilder errorResponse = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();

            System.out.println("Error response: " + errorResponse.toString());
            connection.disconnect();
            return null;
        }
    }
}