package virus_total;

import java.io.IOException;
import java.util.Scanner;

public class VirusTotalAPI {
    public static void main(String[] args) throws IOException    {
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
                UploadFile upload = new UploadFile(key.getApiKey(), "wildfire-test-apk-file.apk");
                String responseUploadFile = upload.getResponseUploadFile();
                FileReport report = new FileReport(key.getApiKey());
                System.out.println(report.getFileReport(responseUploadFile));
                break;
            default:
                break;
        }
    }
}
