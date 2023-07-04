package tools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.io.*;

public class JsonToTxt {
    public static void convert(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        // Reputation results
        JsonObject attributesObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes");

        JsonElement reputationElement = attributesObject.get("reputation");

        // Detail results of different antivirus tools
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("last_analysis_results");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
            String reputation = reputationElement.toString();
            writer.write("Reputation@@ " + reputation + "\n");

            for (String engineName : resultsObject.keySet()) {
                JsonObject engineObject = resultsObject.getAsJsonObject(engineName);
                JsonElement resultElement = engineObject.get("result");
                JsonElement categoryElement = engineObject.get("category");
                String resultValue = resultElement.toString();
                String categoryValue = categoryElement.toString();
                writer.write("Engine name@@ " + engineName + ", Category@@ " + categoryValue + ", Result@@ " + resultValue + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
