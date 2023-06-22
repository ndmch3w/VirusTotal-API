package scanner.file;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class ScanBigFile {
    public static String getUploadURL(String apiKey) throws IOException {
        String url = "https://www.virustotal.com/api/v3/files/upload_url";

        URL apiUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            String jsonElement = jsonObject.get("data").toString();

            return jsonElement.substring(1, jsonElement.length() - 1);
        } else {
            connection.disconnect();
            return ("Request failed with response code: " + responseCode);
        }
    }

    public static String getAnalysisID(String apiKey, String uploadURL, String filePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();

        String boundary = "----Boundary" + System.currentTimeMillis();

        URL apiUrl = new URL(uploadURL);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

        outputStream.writeBytes("--" + boundary + "\r\n");
        outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
        outputStream.writeBytes("Content-Type: application/octet-stream\r\n\r\n");

        BufferedInputStream fileInputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
        byte[] chunk = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(chunk)) > 0) {
            outputStream.write(chunk, 0, bytesRead);
        }

        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("--" + boundary + "--\r\n");
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject dataObject = jsonObject.getAsJsonObject("data");
            JsonElement idElement = dataObject.get("id");

            String id = idElement.toString();

            return id.substring(1, id.length() - 1);
        } else {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            StringBuilder errorResponse = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();
            connection.disconnect();

            String errorResponseBody = errorResponse.toString();
            return (errorResponseBody);
        }
    }

    public static String getFileHash(String apiKey, String analysisID) throws IOException {
        String url = "https://www.virustotal.com/api/v3/analyses/" + analysisID;

        URL apiUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("x-apikey", apiKey);
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject fileInfoObject = jsonObject
                    .getAsJsonObject("meta")
                    .getAsJsonObject("file_info");
            JsonElement md5HashElement = fileInfoObject.get("md5");
            String md5Hash = md5HashElement.toString();

            return md5Hash.substring(1, md5Hash.length() - 1);
        } else {
            connection.disconnect();
            return ("Request failed with response code: " + responseCode);
        }
    }

    public static String getResponse(String apiKey, String fileHash) throws IOException {
        String url = "https://www.virustotal.com/api/v3/files/" + fileHash;
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
            connection.disconnect();

            String res = response.toString();
            return res;
        } else {
            connection.disconnect();

            String res = "Error: " + responseCode;
            return res;
        }
    }
}

