package report_tools;

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

    public void FileReportToExamine() throws IOException, JSONException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        // Overall results
        JsonObject statsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("stats");
        System.out.println("Overall report (new Json): " + statsObject.toString());

        // Detail results of different antivirus tools
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("results");
        System.out.println("Detailed report (new Json): " + resultsObject.toString());

        BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));

        for (String toolName : resultsObject.keySet()) {
            JsonObject toolObject = resultsObject.getAsJsonObject(toolName);
            JsonElement resultElement = toolObject.get("result");
            String resultValue = resultElement.toString();
            writer.write("Tool: " + toolName + ", Result: " + resultValue + "\n");
        }

        writer.close();
    }
    public void UrlReportToExamine() throws IOException, JSONException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        // Detail results of different antivirus tools
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("last_analysis_results");
        System.out.println("Detailed report (new Json): " + resultsObject.toString());

        BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));
        for (String toolName : resultsObject.keySet()) {
            JsonObject toolObject = resultsObject.getAsJsonObject(toolName);
            JsonElement resultElement = toolObject.get("result");
            String resultValue = resultElement.toString();
            writer.write("Tool: " + toolName + ", Result: " + resultValue + "\n");
        }
        writer.close();
    }
    public void DomainReportToExamine() throws IOException, JSONException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        // Detail results of different antivirus tools
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("last_analysis_results");
        System.out.println("Detailed report (new Json): " + resultsObject.toString());

        BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));
        for (String toolName : resultsObject.keySet()) {
            JsonObject toolObject = resultsObject.getAsJsonObject(toolName);
            JsonElement resultElement = toolObject.get("result");
            String resultValue = resultElement.toString();
            writer.write("Tool: " + toolName + ", Result: " + resultValue + "\n");
        }
        writer.close();
    }
    public void IpReportToExamine() throws IOException, JSONException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        // Detail results of different antivirus tools
        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("last_analysis_results");
        System.out.println("Detailed report (new Json): " + resultsObject.toString());

        BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));
        for (String toolName : resultsObject.keySet()) {
            JsonObject toolObject = resultsObject.getAsJsonObject(toolName);
            JsonElement resultElement = toolObject.get("result");
            String resultValue = resultElement.toString();
            writer.write("Tool: " + toolName + ", Result: " + resultValue + "\n");
        }
        writer.close();
    }
    public static void main(String[] args) throws IOException, JSONException {
        JsonToTxt test = new JsonToTxt("Json_Report/IpReport.json", "Results_txt/IpReport.txt");
        test.IpReportToExamine();
    }
}
