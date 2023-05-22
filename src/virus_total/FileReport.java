package virus_total;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

public class FileReport {
    private String apiKey;
    public FileReport(String apiKey){
        this.apiKey = apiKey;
    }
    public class AnalysisResponse {
        @SerializedName("data")
        private AnalysisData data;
        public AnalysisData getData() {
            return data;
        }
    }

    public class AnalysisData {
        private String id;
        public String getId() {
            return id;
        }
    }
    private String getId(String jsonResponse) {
        Gson gson = new Gson();
        AnalysisResponse gsonResponse = gson.fromJson(jsonResponse, AnalysisResponse.class);
        String id = gsonResponse.getData().getId();

        if (id.contains("==")) {
            id = id.replace("==", "%3D%3D");
        }
        return id;
    }
    public String getFileReport(String jsonResponse) throws IOException {
        String url = "https://www.virustotal.com/api/v3/analyses/" + getId(jsonResponse);
        System.out.println(url);
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
