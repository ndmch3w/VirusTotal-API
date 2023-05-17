package virus_total;

import org.json.JSONException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VirusTotalAPI {
    public static void main(String[] args) throws IOException, JSONException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input your VirusTotal API key for more options:");
        APIKey key = new APIKey();
        key.setApiKey(sc.next());

        System.out.println("Choose your option:");
        System.out.println("1. Upload and Scan file");
        System.out.println("2. Scan URL");
        System.out.println("0. Exit");

        System.out.println("Type in your selection (e.g. '1'):");
        String choice = sc.next();



        switch (choice){
            case "1":
                /*UploadFile upload = new UploadFile(key.getApiKey(), "wildfire-test-apk-file.apk");
                String responseUploadFile = upload.getResponseUploadFile();

                FileReport report = new FileReport(key.getApiKey());
                // System.out.println(report.getFileReport(responseUploadFile));
                /*FileWriter wr = new FileWriter("FileReport.json");
                wr.write(report.getFileReport(responseUploadFile));
                wr.close();*/

                JsonToCsv converter = new JsonToCsv("FileReport.json", "FileReport.csv");
                converter.Execute();
                break;
            default:
                break;
        }
    }
}
