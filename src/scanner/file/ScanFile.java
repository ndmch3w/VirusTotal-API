package scanner.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class ScanFile {
    private String apiKey;
    private String filePath;
    public ScanFile(String apiKey, String filePath){
        this.apiKey = apiKey;
        this.filePath = filePath;
    }

    public String getFileReport() throws IOException, NoSuchAlgorithmException {
        String url = "https://www.virustotal.com/api/v3/files/" + FileHash.hash(filePath);
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
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
            String res = "Error: " + responseCode;
            connection.disconnect();
            return res;
        }
    }
}
