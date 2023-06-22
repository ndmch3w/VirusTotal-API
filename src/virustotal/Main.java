package virustotal;

import tools.JsonToCsv;
import tools.JsonToTxt;
import tools.TxtToPDF;
import scanner.domain.ScanDomain;
import scanner.file.ScanBigFile;
import scanner.file.ScanFile;
import scanner.ip.ScanIp;
import scanner.url.ScanUrl;

import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input your VirusTotal API key for more options:");
        String apiKey = sc.next();

        System.out.println("Choose your option:");
        System.out.println("1. Upload and Scan file");
        System.out.println("2. Scan URL");
        System.out.println("3. Scan domain name");
        System.out.println("4. Scan IP address");
        System.out.println("5. Scan large file (>32mb)");
        System.out.println("0. Exit");

        System.out.println("Type in your selection (e.g. '1'):");
        String choice = sc.next();



        switch (choice){
            case "1":
                String responseScanFile = ScanFile.getResponse(apiKey, "Malware test files/wildfire-test-apk-file.apk");

                FileWriter wr1 = new FileWriter("Json_Report/FileReport.json");
                wr1.write(responseScanFile);
                wr1.close();

                JsonToTxt.convert("Json_Report/FileReport.json", "Results_txt/FileReport.txt");

                TxtToPDF.convert("Results_txt/FileReport.txt", "Results_pdf/FileReport.pdf");

                JsonToCsv.convert("Json_Report/FileReport.json", "Results_csv/FileReport.csv");
                break;
            case "2":
                ScanUrl scanUrl = new ScanUrl(apiKey, "mp3raid.com/music/krizz_kaliko.html");
                String responseScanUrl = scanUrl.getResponse();
                FileWriter wr2 = new FileWriter("Json_Report/UrlReport.json");
                wr2.write(responseScanUrl);
                wr2.close();

                JsonToTxt.convert("Json_Report/UrlReport.json", "Results_txt/UrlReport.txt");

                TxtToPDF.convert("Results_txt/UrlReport.txt", "Results_pdf/UrlReport.pdf");

                JsonToCsv.convert("Json_Report/UrlReport.json", "Results_csv/UrlReport.csv");
                break;
            case "3":
                ScanDomain scanDomain = new ScanDomain(apiKey, "www.xfer.com");
                String responseScanDomain = scanDomain.getResponse();
                FileWriter wr3 = new FileWriter("Json_Report/DomainReport.json");
                wr3.write(responseScanDomain);
                wr3.close();

                JsonToTxt.convert("Json_Report/DomainReport.json", "Results_txt/DomainReport.txt");

                TxtToPDF.convert("Results_txt/DomainReport.txt", "Results_pdf/DomainReport.pdf");

                JsonToCsv.convert("Json_Report/DomainReport.json", "Results_csv/DomainReport.csv");
                break;
            case "4":
                ScanIp scanIp = new ScanIp(apiKey, "93.174.89.224");
                String responseScanIp = scanIp.getResponse();
                FileWriter wr4 = new FileWriter("Json_Report/IpReport.json");
                wr4.write(responseScanIp);
                wr4.close();

                JsonToTxt.convert("Json_Report/IpReport.json", "Results_txt/IpReport.txt");

                TxtToPDF.convert("Results_txt/IpReport.txt", "Results_pdf/IpReport.pdf");

                JsonToCsv.convert("Json_Report/IpReport.json", "Results_csv/IpReport.csv");
                break;
            case "5":
                String uploadURL = ScanBigFile.getUploadURL(apiKey);

                String analysisID= ScanBigFile.getAnalysisID(apiKey, uploadURL,
                        "C:\\Users\\DELL\\Downloads\\jdk-17_windows-x64_bin.exe");

                String md5Hash = ScanBigFile.getFileHash(apiKey, analysisID);

                String responseScanBigFile = ScanBigFile.getResponse(apiKey, md5Hash);

                FileWriter wr5 = new FileWriter("Json_Report/BigFileReport.json");
                wr5.write(responseScanBigFile);
                wr5.close();

                JsonToTxt.convert("Json_Report/BigFileReport.json", "Results_txt/BigFileReport.txt");

                TxtToPDF.convert("Results_txt/BigFileReport.txt", "Results_pdf/BigFileReport.pdf");

                JsonToCsv.convert("Json_Report/BigFileReport.json", "Results_csv/BigFileReport.csv");
                break;
            default:
                break;
        }
    }
}
