package scanner.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Base64;

public class ScanUrl {
    private String apiKey;
    private String urlToScan;

    public ScanUrl(String apiKey, String urlToScan){
        this.apiKey = apiKey;
        this.urlToScan = urlToScan;
    }

    private static String getUrlId(String urlToScan){
        String url = urlToScan;
        byte[] urlBytes = url.getBytes();

        String encodedUrl = Base64.getUrlEncoder().withoutPadding().encodeToString(urlBytes);
        encodedUrl = encodedUrl.replaceAll("=", "");

        return encodedUrl;
    }

    public static String getResponse(String apiKey, String urlToScan) throws IOException {
        //System.out.println(getUrlId());
        String url = "https://www.virustotal.com/api/v3/urls/" + getUrlId(urlToScan);
        URL apiUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);

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

            String res = errorResponse.toString();
            connection.disconnect();
            return res;
        }
    }
}
