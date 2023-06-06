package report_tools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class JsonToCsv{
    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader("Json_Report/UrlReport.json");

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        JsonObject attributes = jsonObject.getAsJsonObject("data").getAsJsonObject("attributes");
        JsonObject lastAnalysisResults = attributes.getAsJsonObject("last_analysis_results");

        // Create CSV file and write header
        String csvFilePath = "output.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write("tool_name,category,result\n");

            // Iterate over last analysis results and write data to CSV
            for (String toolName : lastAnalysisResults.keySet()) {
                JsonObject toolData = lastAnalysisResults.getAsJsonObject(toolName);
                String category = toolData.get("category").getAsString();
                String result = toolData.get("result").getAsString();
                writer.write(toolName + "," + category + "," + result + "\n");
        }
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while creating/writing to the CSV file: " + e.getMessage());
        }
        }
}
