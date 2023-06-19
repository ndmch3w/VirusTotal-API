package converter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;


import java.io.*;

public class JsonToTxt {
    private String jsonFilePath;
    private String txtFilePath;

    public JsonToTxt(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
    }
    public JsonToTxt(String jsonFile, String csvFile){
        this.jsonFilePath = jsonFile;
        this.txtFilePath = csvFile;
    }
    public void normalExamine() throws IOException{
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        // Overall results
        JsonObject statsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("last_analysis_stats");

        // Detail results of different antivirus tools
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("last_analysis_results");

        BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));
        for (String toolName : resultsObject.keySet()) {
            JsonObject toolObject = resultsObject.getAsJsonObject(toolName);
            JsonElement resultElement = toolObject.get("result");
            JsonElement categoryElement = toolObject.get("category");
            String resultValue = resultElement.toString();
            String categoryValue = categoryElement.toString();
            writer.write("Tool@@ " + toolName + ", Category@@ " + categoryValue + ", Result@@ " + resultValue + "\n");
        }
        writer.close();
    }

    public void FileReportToExamine() throws IOException, JSONException {
        this.normalExamine();
    }
    public void UrlReportToExamine() throws IOException, JSONException {
        this.normalExamine();
    }
    public void DomainReportToExamine() throws IOException, JSONException {
        this.normalExamine();
    }
    public void IpReportToExamine() throws IOException, JSONException {
        this.normalExamine();
    }
}
