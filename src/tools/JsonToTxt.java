package tools;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JsonToTxt {
    // Convert timestamp to readable UTC date
    private static String timestampConverter(String stringTimestamp){
        long longTimeStamp = Long.parseLong(stringTimestamp);
        Date date = new Date(longTimeStamp * 1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcString = sdf.format(date);

        return utcString;
    }
    // Format JsonElement to String lines in object detailed information file
    private static void writeJsonElementToFile(JsonObject jsonObject, String key, BufferedWriter writer)
            throws IOException {
        if (jsonObject.has(key)){
            writer.write(key + ": " + jsonObject.get(key) + "\n");
        }else{
            writer.write(key + ": No data\n");
        }
    }

    // Get overall report from json report -> Gen a visualizing table in PDF report later
    public static void getOverall(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            // Detailed results of different antivirus tools
            JsonObject resultsObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes")
                    .getAsJsonObject("last_analysis_results");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                // Write engine name, result category and specific result to file
                for (String engineName : resultsObject.keySet()) {
                    JsonObject engineObject = resultsObject.getAsJsonObject(engineName);
                    JsonElement resultElement = engineObject.get("result");
                    JsonElement categoryElement = engineObject.get("category");
                    String resultValue = resultElement.toString();
                    String categoryValue = categoryElement.toString();
                    writer.write("Engine name@@ " + engineName + ", Category@@ " + categoryValue + ", Result@@ " + resultValue + "\n");
                }

                System.out.println("TXT Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n) {
            // Raise exception when some object isn't active or json report doesn't contain necessary attributes
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    // Get specific information of file object
    public static void getFileInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");

            JsonArray tridArray = attributesObject.getAsJsonArray("trid");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writeJsonElementToFile(attributesObject,  "reputation", writer);

                if (attributesObject.has("last_analysis_date")){
                    writer.write("last_analysis_date: " +
                            timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                }
                if (attributesObject.has("last_submission_date")) {
                    writer.write("last_submission_date: " +
                            timestampConverter(attributesObject.get("last_submission_date").toString()) + "\n");
                }
                if (attributesObject.has("last_modification_date")) {
                    writer.write("last_modification_date: " +
                            timestampConverter(attributesObject.get("last_modification_date").toString()) + "\n");
                }

                writeJsonElementToFile(attributesObject,  "type_tags", writer);
                writeJsonElementToFile(attributesObject,  "names", writer);
                writeJsonElementToFile(attributesObject,  "size", writer);
                writeJsonElementToFile(attributesObject,  "sha256", writer);
                writeJsonElementToFile(attributesObject,  "sha1", writer);
                writeJsonElementToFile(attributesObject,  "md5", writer);

                if (attributesObject.has("trid")){
                    writer.write("trid:\n");
                    for (JsonElement element : tridArray){
                        JsonObject object = element.getAsJsonObject();
                        writer.write("\t");
                        writeJsonElementToFile(object, "file_type", writer);
                        writer.write("\t");
                        writeJsonElementToFile(object, "probability", writer);
                    }
                }

                System.out.println("TXT Info Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n){
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    // Get specific information of URL object
    public static void getUrlInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writeJsonElementToFile(attributesObject,  "reputation", writer);

                if (attributesObject.has("last_analysis_date")){
                    writer.write("last_analysis_date: " +
                            timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                }
                if (attributesObject.has("last_submission_date")) {
                    writer.write("last_submission_date: " +
                            timestampConverter(attributesObject.get("last_submission_date").toString()) + "\n");
                }
                if (attributesObject.has("last_modification_date")) {
                    writer.write("last_modification_date: " +
                            timestampConverter(attributesObject.get("last_modification_date").toString()) + "\n");
                }

                if (attributesObject.has("categories")){
                    JsonObject categoriesObject = attributesObject.getAsJsonObject("categories");
                    writer.write("Categories:" + "\n");
                    for (String category : categoriesObject.keySet()){
                        writer.write("\t");
                        writeJsonElementToFile(categoriesObject, category, writer);
                    }
                }

                writeJsonElementToFile(attributesObject,  "last_http_response_content_sha256", writer);
                writeJsonElementToFile(attributesObject,  "last_http_response_code", writer);
                writeJsonElementToFile(attributesObject,  "last_final_url", writer);
                writeJsonElementToFile(attributesObject,  "last_http_response_content_length", writer);
                writeJsonElementToFile(attributesObject,  "redirection_chain", writer);

                System.out.println("TXT Info Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n){
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    // Get specific information of domain object
    public static void getDomainInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writeJsonElementToFile(attributesObject,  "reputation", writer);

                if (attributesObject.has("last_analysis_date")){
                    writer.write("last_analysis_date: " +
                            timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                }
                if (attributesObject.has("last_submission_date")) {
                    writer.write("last_submission_date: " +
                            timestampConverter(attributesObject.get("last_submission_date").toString()) + "\n");
                }
                if (attributesObject.has("last_modification_date")) {
                    writer.write("last_modification_date: " +
                            timestampConverter(attributesObject.get("last_modification_date").toString()) + "\n");
                }

                if (attributesObject.has("categories")){
                    JsonObject categoriesObject = attributesObject.getAsJsonObject("categories");
                    writer.write("Categories:" + "\n");
                    for (String category : categoriesObject.keySet()){
                        writer.write("\t");
                        writeJsonElementToFile(categoriesObject, category, writer);
                    }
                }

                writer.write("Popularity rank: See more details if necessary in json_report/DomainReport.json\n");
                writer.write("last_dns_records: See more details if necessary in json_report/DomainReport.json\n");

                if (attributesObject.has("whois")){
                    writer.write("whois:\n");
                    String fullWhois = attributesObject.get("whois").getAsString();
                    String[] whoisLines = fullWhois.split("\\n");
                    for (String line : whoisLines){
                        writer.write("\t");
                        writer.write(line + "\n");
                    }
                }
                if (attributesObject.has("whois_date")){
                    writer.write("whois_date: " +
                            timestampConverter(attributesObject.get("whois_date").toString()) + "\n");
                }

                writeJsonElementToFile(attributesObject,"tags",writer);

                System.out.println("TXT Info Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n){
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    // Get specific information of IP object
    public static void getIpInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writeJsonElementToFile(attributesObject,  "reputation", writer);

                if (attributesObject.has("last_analysis_date")){
                    writer.write("last_analysis_date: " +
                            timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                }
                if (attributesObject.has("last_submission_date")) {
                    writer.write("last_submission_date: " +
                            timestampConverter(attributesObject.get("last_submission_date").toString()) + "\n");
                }
                if (attributesObject.has("last_modification_date")) {
                    writer.write("last_modification_date: " +
                            timestampConverter(attributesObject.get("last_modification_date").toString()) + "\n");
                }

                if (attributesObject.has("whois")){
                    writer.write("whois:\n");
                    String fullWhois = attributesObject.get("whois").getAsString();
                    String[] whoisLines = fullWhois.split("\\n");
                    for (String line : whoisLines){
                        writer.write("\t");
                        writer.write(line + "\n");
                    }
                }
                if (attributesObject.has("whois_date")){
                    writer.write("whois_date: " +
                            timestampConverter(attributesObject.get("whois_date").toString()) + "\n");
                }

                writeJsonElementToFile(attributesObject,"tags",writer);
                writeJsonElementToFile(attributesObject, "network", writer);
                writeJsonElementToFile(attributesObject, "asn", writer);
                writeJsonElementToFile(attributesObject, "as_owner", writer);
                writeJsonElementToFile(attributesObject, "regional_internet_registry", writer);
                writeJsonElementToFile(attributesObject,  "country", writer);
                writeJsonElementToFile(attributesObject,  "continent", writer);

                System.out.println("TXT Info Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n){
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }
}