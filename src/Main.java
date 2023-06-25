import tools.GenGraph;
import tools.JsonToCsv;
import tools.JsonToTxt;
import tools.TxtToPDF;
import scanner.domain.ScanDomain;
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
        System.out.println("1. Upload and Scan file (<650mb)");
        System.out.println("2. Scan URL");
        System.out.println("3. Scan domain name");
        System.out.println("4. Scan IP address");
        System.out.println("0. Exit");

        System.out.println("Type in your selection (e.g. '1'):");
        String choice = sc.next();



        switch (choice){
            case "1":
                //String responseScanFile = ScanFile.getResponse(apiKey, "Malware test files/wildfire-test-apk-file.apk");
                String responseScanFile = ScanFile.getResponse(apiKey, "C:\\Users\\DELL\\Downloads\\ideaIC-2023.1.1.exe");

                FileWriter wr1 = new FileWriter("Json_Report/FileReport.json");
                wr1.write(responseScanFile);
                wr1.close();

                JsonToTxt.convert("Json_Report/FileReport.json", "Results_txt/FileReport.txt");

                TxtToPDF.convert("Results_txt/FileReport.txt", "Results_pdf/FileReport.pdf");

                JsonToCsv.convert("Json_Report/FileReport.json", "Results_csv/FileReport.csv");

                GenGraph.generate("Results_csv/FileReport.csv", "Charts/FileChart.png");
                break;
            case "2":
                String responseScanUrl = ScanUrl.getResponse(apiKey, "hxxp://www.malwaredomainlist.com/");
                FileWriter wr2 = new FileWriter("Json_Report/UrlReport.json");
                wr2.write(responseScanUrl);
                wr2.close();

                JsonToTxt.convert("Json_Report/UrlReport.json", "Results_txt/UrlReport.txt");

                TxtToPDF.convert("Results_txt/UrlReport.txt", "Results_pdf/UrlReport.pdf");

                JsonToCsv.convert("Json_Report/UrlReport.json", "Results_csv/UrlReport.csv");
                break;
            case "3":
                String responseScanDomain = ScanDomain.getResponse(apiKey, "malwaredomainlist.com");

                FileWriter wr3 = new FileWriter("Json_Report/DomainReport.json");
                wr3.write(responseScanDomain);
                wr3.close();

                JsonToTxt.convert("Json_Report/DomainReport.json", "Results_txt/DomainReport.txt");

                TxtToPDF.convert("Results_txt/DomainReport.txt", "Results_pdf/DomainReport.pdf");

                JsonToCsv.convert("Json_Report/DomainReport.json", "Results_csv/DomainReport.csv");
                break;
            case "4":
                String responseScanIp = ScanIp.getResponse(apiKey, "93.174.89.224");

                FileWriter wr4 = new FileWriter("Json_Report/IpReport.json");
                wr4.write(responseScanIp);
                wr4.close();

                JsonToTxt.convert("Json_Report/IpReport.json", "Results_txt/IpReport.txt");

                TxtToPDF.convert("Results_txt/IpReport.txt", "Results_pdf/IpReport.pdf");

                JsonToCsv.convert("Json_Report/IpReport.json", "Results_csv/IpReport.csv");
                break;
            default:
                break;
        }
    }
}
