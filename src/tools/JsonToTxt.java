package tools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JsonToTxt {
    private static String timestampConverter(String stringTimestamp){
        long longTimeStamp = Long.parseLong(stringTimestamp);
        Date date = new Date(longTimeStamp * 1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcString = sdf.format(date);

        return utcString;
    }
    private static void writeJsonElementToFile(JsonObject jsonObject, String key, BufferedWriter writer) throws IOException {
        if (jsonObject.has(key)){
            writer.write(key + ": " + jsonObject.get(key) + "\n");
        }else{
            writer.write(key + ": No data");
        }
    }
    public static void convert(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            // Detail results of different antivirus tools
            JsonObject resultsObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes")
                    .getAsJsonObject("last_analysis_results");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
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
            // catch này để catch trường hợp không tìm được object nên gson không đọc được file json
            // sau này có thể tối ưu để không lặp code
        } catch (NullPointerException n) {
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    public static void getFileInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writer.write("last_analysis_date: " +
                        timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                writeJsonElementToFile(attributesObject,  "reputation", writer);
                writeJsonElementToFile(attributesObject,  "type_tags", writer);
                writeJsonElementToFile(attributesObject,  "names", writer);
                writeJsonElementToFile(attributesObject,  "size", writer);
                writeJsonElementToFile(attributesObject,  "sha256", writer);
                writeJsonElementToFile(attributesObject,  "sha1", writer);
                writeJsonElementToFile(attributesObject,  "md5", writer);
                System.out.println("TXT Info Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n){
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    public static void getUrlInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");

            JsonObject categoriesObject = attributesObject.getAsJsonObject("categories");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writer.write("last_analysis_date: " +
                        timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                writeJsonElementToFile(attributesObject,  "reputation", writer);
                writer.write("Categories:" + "\n");
                for (String category : categoriesObject.keySet()){
                    writer.write("\t");
                    writeJsonElementToFile(categoriesObject, category, writer);
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

    public static void getDomainInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");
            JsonObject categoriesObject = attributesObject.getAsJsonObject("categories");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writer.write("last_analysis_date: " +
                        timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                writeJsonElementToFile(attributesObject,  "reputation", writer);
                writer.write("Categories:" + "\n");
                for (String category : categoriesObject.keySet()){
                    writer.write("\t");
                    writeJsonElementToFile(categoriesObject, category, writer);
                }
                writeJsonElementToFile(attributesObject,  "whois", writer);
                writeJsonElementToFile(attributesObject,  "registrar", writer);
                writeJsonElementToFile(attributesObject,  "jarm", writer);

                System.out.println("TXT Info Report is created successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException n){
            System.out.println("Error occurred while reading json file: " + n.getMessage());
            System.out.println("Please check again if the object exists");
        }
    }

    public static void getIpInfo(String jsonFilePath, String txtFilePath) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(jsonFilePath);

        JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

        try {
            JsonObject attributesObject = jsonObject
                    .getAsJsonObject("data")
                    .getAsJsonObject("attributes");
            JsonObject categoriesObject = attributesObject.getAsJsonObject("categories");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
                writer.write("last_analysis_date: " +
                        timestampConverter(attributesObject.get("last_analysis_date").toString()) + "\n");
                writeJsonElementToFile(attributesObject,  "reputation", writer);
                writeJsonElementToFile(attributesObject,  "regional_internet_registry", writer);
                writeJsonElementToFile(attributesObject,  "whois", writer);
                writeJsonElementToFile(attributesObject,  "country", writer);
                writeJsonElementToFile(attributesObject,  "tag", writer);

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