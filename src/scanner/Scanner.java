package scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Scanner {
    protected String apiKey;
    protected String endpointUrl;

    protected Scanner(String apiKey, String endpointUrl) {
        this.apiKey = apiKey;
        this.endpointUrl = endpointUrl;
    }

    public String getResponse() throws IOException {
            URL apiUrl = new URL(endpointUrl);
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
