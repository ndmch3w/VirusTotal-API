package tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

public class JsonToCsv{
    // Convert results in json report to csv file -> gen graph later
    public static void convert(String jsonFilePath, String csvFilePath) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        JsonObject attributes = jsonObject.getAsJsonObject("data").getAsJsonObject("attributes");
        JsonObject lastAnalysisResults = attributes.getAsJsonObject("last_analysis_results");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write("tool_name,category,result\n");

            // Iterate over last analysis results and write data to CSV
            for (String toolName : lastAnalysisResults.keySet()) {
                JsonObject toolData = lastAnalysisResults.getAsJsonObject(toolName);
                String category = toolData.get("category").toString();
                String result = toolData.get("result").toString();
                writer.write(toolName + "," + category + "," + result + "\n");
            }
            System.out.println("CSV Report file is created successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while creating/writing to the CSV file: " + e.getMessage());
        }
    }

}
