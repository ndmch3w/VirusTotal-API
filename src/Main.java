import tools.GenGraph;
import tools.JsonToCsv;
import tools.JsonToTxt;
import tools.TxtToPDF;
import scanner.ScanDomain;
import scanner.ScanFile;
import scanner.ScanIp;
import scanner.ScanUrl;

import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input your VirusTotal API key for more options:");
        String apiKey = sc.next();

        System.out.println("Choose your option:");
        System.out.println("1. Upload and Scan file (<650mb)");
        System.out.println("2. Scan URL");
        System.out.println("3. Scan domain name");
        System.out.println("4. Scan IP address");
        System.out.println("0. Exit");

        System.out.println("Type in your selection (e.g. '1'):");
        String choice = sc.next();



        switch (choice){
            case "1":
                // ("Malware test files/wildfire-test-apk-file.apk");
                String filePath = "C:\\Users\\DELL\\Downloads\\ideaIC-2023.1.1.exe";

                ScanFile fileScanner = new ScanFile(apiKey, filePath);
                String responseScanFile = fileScanner.getResponse();

                FileWriter wr1 = new FileWriter("Json_Report/FileReport.json");
                wr1.write(responseScanFile);
                wr1.close();

                JsonToTxt.convert("Json_Report/FileReport.json", "Results_txt/FileReport.txt");

                JsonToCsv.convert("Json_Report/FileReport.json", "Results_csv/FileReport.csv");

                GenGraph.generate("Results_csv/FileReport.csv", "Charts/FileChart.png");

                TxtToPDF.convert("Results_txt/FileReport.txt", "Results_pdf/FileReport.pdf",
                        "Charts/FileChart.png");
                break;
            case "2":
                String url = "hxxp://www.malwaredomainlist.com/";

                ScanUrl urlScanner = new ScanUrl(apiKey, url);
                String responseScanUrl = urlScanner.getResponse();

                FileWriter wr2 = new FileWriter("Json_Report/UrlReport.json");
                wr2.write(responseScanUrl);
                wr2.close();

                JsonToTxt.convert("Json_Report/UrlReport.json", "Results_txt/UrlReport.txt");

                JsonToCsv.convert("Json_Report/UrlReport.json", "Results_csv/UrlReport.csv");

                GenGraph.generate("Results_csv/UrlReport.csv", "Charts/UrlChart.png");

                TxtToPDF.convert("Results_txt/UrlReport.txt", "Results_pdf/UrlReport.pdf",
                        "Charts/UrlChart.png");
                break;
            case "3":
                String domain = "malwaredomainlist.com";

                ScanDomain domainScanner = new ScanDomain(apiKey, domain);
                String responseScanDomain = domainScanner.getResponse();

                FileWriter wr3 = new FileWriter("Json_Report/DomainReport.json");
                wr3.write(responseScanDomain);
                wr3.close();

                JsonToTxt.convert("Json_Report/DomainReport.json", "Results_txt/DomainReport.txt");

                JsonToCsv.convert("Json_Report/DomainReport.json", "Results_csv/DomainReport.csv");

                GenGraph.generate("Results_csv/DomainReport.csv", "Charts/DomainChart.png");

                TxtToPDF.convert("Results_txt/DomainReport.txt", "Results_pdf/DomainReport.pdf",
                        "Charts/DomainChart.png");
                break;
            case "4":
                String ipAddress = "93.174.89.224";

                ScanIp ipScanner = new ScanIp(apiKey, ipAddress);
                String responseScanIp = ipScanner.getResponse();

                FileWriter wr4 = new FileWriter("Json_Report/IpReport.json");
                wr4.write(responseScanIp);
                wr4.close();

                JsonToTxt.convert("Json_Report/IpReport.json", "Results_txt/IpReport.txt");

                JsonToCsv.convert("Json_Report/IpReport.json", "Results_csv/IpReport.csv");

                GenGraph.generate("Results_csv/IpReport.csv", "Charts/IpChart.png");

                TxtToPDF.convert("Results_txt/IpReport.txt", "Results_pdf/IpReport.pdf",
                        "Charts/IpChart.png");
                break;
            default:
                break;
        }
    }
}
