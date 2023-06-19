package scanner.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

public class ScanDomain {
    private String apiKey;
    private String domain;

    public ScanDomain(String apiKey, String domain){
        this.apiKey = apiKey;
        this.domain = domain;
    }

    public String getResponse() throws IOException {
        String url = "https://www.virustotal.com/api/v3/domains/" + domain;
        URL apiUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            String res = response.toString();
            connection.disconnect();
            return res;
        }else{
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            StringBuilder errorResponse = new StringBuilder();
            while ((line = errorReader.readLine()) != null){
                errorResponse.append(line);
            }
            errorReader.close();

            String res = errorResponse.toString();
            connection.disconnect();
            return res;
        }
    }
}
