package virus_total;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

import java.util.Iterator;

public class JsonToCsv {
    private String jsonFilePath;
    private String csvFilePath;

    public JsonToCsv(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
    }
    public JsonToCsv(String jsonFile, String csvFile){
        this.jsonFilePath = jsonFile;
        this.csvFilePath = csvFile;
    }

    public void FileReportToCsv() throws IOException{
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        JsonObject statsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("stats");
        System.out.println("Overall report (new Json): " + statsObject.toString());

        JsonObject resultsObject = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("attributes")
                .getAsJsonObject("results");
        System.out.println("Detailed report (new Json): " + resultsObject.toString());

    }
    public static void main(String[] args) throws IOException{
        JsonToCsv test = new JsonToCsv("FileReport.json");
        test.FileReportToCsv();
    }
}
