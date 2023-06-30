package main;

import tools.GenGraph;
import tools.JsonToCsv;
import tools.JsonToTxt;
import tools.TxtToPDF;
import scanner.ScanDomain;
import scanner.ScanFile;
import scanner.ScanIp;
import scanner.ScanUrl;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
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



        switch (choice) {
            case "1":
                String filePath = "C:\\Users\\DELL\\Downloads\\ideaIC-2023.1.1.exe";

                ScanFile fileScanner = new ScanFile(apiKey, filePath);
                String responseScanFile = fileScanner.getResponse();
                String newFilePath1 = "Json_Report/FileReport.json";

                try (FileWriter wr1 = new FileWriter(newFilePath1)) {
                    wr1.write(responseScanFile);
                    wr1.close();
                    JsonToTxt.convert(newFilePath1, "Results_txt/FileReport.txt");
                    JsonToCsv.convert(newFilePath1, "Results_csv/FileReport.csv");
                    GenGraph.generate("Results_csv/FileReport.csv", "Charts/FileChart.png");
                    TxtToPDF.convert("Results_txt/FileReport.txt", "Results_pdf/FileReport.pdf",
                            "Charts/FileChart.png");
                } catch (Exception e) {
                    System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                }
                break;
            case "2":
                String url = "hxxp://www.malwaredomainlist.com/";

                ScanUrl urlScanner = new ScanUrl(apiKey, url);
                String responseScanUrl = urlScanner.getResponse();
                String newFilePath2 = "Json_Report/UrlReport.json";

                try(FileWriter wr2 = new FileWriter(newFilePath2)) {
                    wr2.write(responseScanUrl);
                    wr2.close();

                    JsonToTxt.convert(newFilePath2, "Results_txt/UrlReport.txt");

                    JsonToCsv.convert(newFilePath2, "Results_csv/UrlReport.csv");

                    GenGraph.generate("Results_csv/UrlReport.csv", "Charts/UrlChart.png");

                    TxtToPDF.convert("Results_txt/UrlReport.txt", "Results_pdf/UrlReport.pdf",
                            "Charts/UrlChart.png");
                } catch (Exception e){
                    System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                }
                break;
            case "3":
                String domain = "malwaredomainlist.com";

                ScanDomain domainScanner = new ScanDomain(apiKey, domain);
                String responseScanDomain = domainScanner.getResponse();
                String newFilePath3 = "Json_Report/DomainReport.json";
                try (FileWriter wr3 = new FileWriter(newFilePath3)) {
                    wr3.write(responseScanDomain);
                    wr3.close();

                    JsonToTxt.convert(newFilePath3, "Results_txt/DomainReport.txt");

                    JsonToCsv.convert(newFilePath3, "Results_csv/DomainReport.csv");

                    GenGraph.generate("Results_csv/DomainReport.csv", "Charts/DomainChart.png");

                    TxtToPDF.convert("Results_txt/DomainReport.txt", "Results_pdf/DomainReport.pdf",
                            "Charts/DomainChart.png");
                }catch (Exception e){
                    System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                }
                break;
            case "4":
                Properties properties = new Properties();
                try (FileInputStream fis = new FileInputStream("config.properties")) {
                    properties.load(fis);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                String ipAddress = properties.getProperty("ipAddress");
                ScanIp ipScanner = new ScanIp(apiKey, ipAddress);
                String responseScanIp = ipScanner.getResponse();
                String newFilePath4 = "Json_Report/IpReport.json";
                try(FileWriter wr4 = new FileWriter(newFilePath4)) {
                    wr4.write(responseScanIp);
                    wr4.close();

                    JsonToTxt.convert(newFilePath4, "Results_txt/IpReport.txt");

                    JsonToCsv.convert(newFilePath4, "Results_csv/IpReport.csv");

                    GenGraph.generate("Results_csv/IpReport.csv", "Charts/IpChart.png");

                    TxtToPDF.convert("Results_txt/IpReport.txt", "Results_pdf/IpReport.pdf",
                            "Charts/IpChart.png");
                }catch (Exception e){
                    System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}
