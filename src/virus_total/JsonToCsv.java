package virus_total;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;

public class JsonToCsv {
    private String jsonStringPath;
    private String csvPath;

    public JsonToCsv(String jsonStringPath, String csvPath){
        this.jsonStringPath = jsonStringPath;
        this.csvPath = csvPath;
    }
    public void setJsonStringPath(String jsonStringPath){
        this.jsonStringPath = jsonStringPath;
    }
    public void setCsvPath(String csvPath){
        this.csvPath = csvPath;
    }
    private String readJsonFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
    private void writeCsvFile(String filePath, JSONArray jsonArray) throws IOException, JSONException{
        try (FileWriter writer = new FileWriter(filePath)) {
            JSONObject firstObject = jsonArray.getJSONObject(0);
            StringBuilder header = new StringBuilder();
            StringBuilder row = new StringBuilder();

            // Write header row
            Iterator<String> keysIterator1 = firstObject.keys();
            while (keysIterator1.hasNext()) {
                String key = keysIterator1.next();
                if (header.length() != 0) {
                    header.append(",");
                }
                header.append(key);
            }
            writer.write(header.toString());
            writer.write(System.lineSeparator());

            // Write data rows
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator<String> keysIterator2 = jsonObject.keys();
                while (keysIterator2.hasNext()) {
                    String key = keysIterator2.next();
                    if (row.length() != 0) {
                        row.append(",");
                    }
                    row.append(jsonObject.get(key));
                }
                writer.write(row.toString());
                writer.write(System.lineSeparator());
            }
        }
    }
    public void Execute() throws IOException, JSONException{
        String jsonContent = readJsonFile(jsonStringPath);
        JSONObject jsonObject = new JSONObject(jsonContent);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        writeCsvFile(csvPath, jsonArray);
    }

}
