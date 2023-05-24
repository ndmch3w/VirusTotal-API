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
        System.out.println("3. Scan domain name");
        System.out.println("4. Scan IP address");
        System.out.println("0. Exit");

        System.out.println("Type in your selection (e.g. '1'):");
        String choice = sc.next();



        switch (choice){
            case "1":
                UploadFile upload = new UploadFile(key.getApiKey(), "wildfire-test-apk-file.apk");
                String responseUploadFile = upload.getResponseUploadFile();

                FileReport report = new FileReport(key.getApiKey());
                // System.out.println(report.getFileReport(responseUploadFile));
                FileWriter wr1 = new FileWriter("FileReport.json");
                wr1.write(report.getFileReport(responseUploadFile));
                wr1.close();

                /*JsonToCsv converter = new JsonToCsv("FileReport.json", "FileReport.csv");
                converter.Execute();*/
                break;
            case "2":
                ScanUrl scanUrl = new ScanUrl(key.getApiKey(), "https://kenh14.vn");
                String responseScanUrl = scanUrl.getResponse();
                FileWriter wr2 = new FileWriter("UrlReport.json");
                wr2.write(responseScanUrl);
                wr2.close();
                break;
            case "3":
                ScanDomain scanDomain = new ScanDomain(key.getApiKey(), "www.xfer.com");
                String responseScanDomain = scanDomain.getResponse();
                FileWriter wr3 = new FileWriter("DomainReport.json");
                wr3.write(responseScanDomain);
                wr3.close();
                break;
            case "4":
                ScanIp scanIp = new ScanIp(key.getApiKey(), "93.174.89.224");
                String responseScanIp = scanIp.getResponse();
                FileWriter wr4 = new FileWriter("IpReport.json");
                wr4.write(responseScanIp);
                wr4.close();
                break;
            default:
                break;
        }
    }
}
