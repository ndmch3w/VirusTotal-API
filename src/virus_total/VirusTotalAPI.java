package virus_total;

import org.json.JSONException;
import report_tools.JsonToTxt;
import report_tools.TxtToPDF;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VirusTotalAPI {
    public static void main(String[] args) throws Exception {
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
                UploadFile upload = new UploadFile(key.getApiKey(), "Malware test files/thitracnghiem.seb");
                String responseUploadFile = upload.getResponseUploadFile();

                FileReport report = new FileReport(key.getApiKey());
                System.out.println(report.getFileReport(responseUploadFile));
                FileWriter wr1 = new FileWriter("Json_Report/FileReport.json");
                wr1.write(report.getFileReport(responseUploadFile));
                wr1.close();

                JsonToTxt jsonToTxt1 = new JsonToTxt("Json_Report/FileReport.json", "Results_txt/FileReport.txt");
                jsonToTxt1.FileReportToExamine();

                TxtToPDF txtToPDF1 = new TxtToPDF("Results_txt/FileReport.txt", "Results_pdf/FileReport.pdf");
                txtToPDF1.convert();
                break;
            case "2":
                ScanUrl scanUrl = new ScanUrl(key.getApiKey(), "mp3raid.com/music/krizz_kaliko.html");
                String responseScanUrl = scanUrl.getResponse();
                FileWriter wr2 = new FileWriter("Json_Report/UrlReport.json");
                wr2.write(responseScanUrl);
                wr2.close();

                JsonToTxt jsonToTxt2 = new JsonToTxt("Json_Report/UrlReport.json", "Results_txt/UrlReport.txt");
                jsonToTxt2.UrlReportToExamine();

                TxtToPDF txtToPDF2 = new TxtToPDF("Results_txt/UrlReport.txt", "Results_pdf/UrlReport.pdf");
                txtToPDF2.convert();
                break;
            case "3":
                ScanDomain scanDomain = new ScanDomain(key.getApiKey(), "www.xfer.com");
                String responseScanDomain = scanDomain.getResponse();
                FileWriter wr3 = new FileWriter("Json_Report/DomainReport.json");
                wr3.write(responseScanDomain);
                wr3.close();

                JsonToTxt jsonToTxt3 = new JsonToTxt("Json_Report/DomainReport.json", "Results_txt/DomainReport.txt");
                jsonToTxt3.DomainReportToExamine();

                TxtToPDF txtToPDF3 = new TxtToPDF("Results_txt/DomainReport.txt", "Results_pdf/DomainReport.pdf");
                txtToPDF3.convert();
                break;
            case "4":
                ScanIp scanIp = new ScanIp(key.getApiKey(), "93.174.89.224");
                String responseScanIp = scanIp.getResponse();
                FileWriter wr4 = new FileWriter("Json_Report/IpReport.json");
                wr4.write(responseScanIp);
                wr4.close();

                JsonToTxt jsonToTxt4 = new JsonToTxt("Json_Report/IpReport.json", "Results_txt/IpReport.txt");
                jsonToTxt4.IpReportToExamine();

                TxtToPDF txtToPDF4 = new TxtToPDF("Results_txt/IpReport.txt", "Results_pdf/IpReport.pdf");
                txtToPDF4.convert();
                break;
            default:
                break;
        }
    }
}
