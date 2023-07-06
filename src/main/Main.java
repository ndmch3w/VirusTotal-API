package main;

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
    private static void showMenu(){
        System.out.println("--------------------------------");
        System.out.println("Choose your option:");
        System.out.println("1. Upload and Scan file (<650mb)");
        System.out.println("2. Scan URL");
        System.out.println("3. Scan domain name");
        System.out.println("4. Scan IP address");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input your VirusTotal API key for more options:");
        String apiKey = sc.next();

        showMenu();

        System.out.println("Type in your selection (e.g. '1'):");
        int choice = sc.nextInt();

        while (choice != 0){
            if (choice < 0 || choice > 4){
                System.out.println("Wrong choice input, please input an integer from 0 to 4:");
                choice = sc.nextInt();
            }else{
                switch (choice) {
                    case 1:
                        System.out.println("Type in the filepath you want to scan below:");
                        // C:/Users/DELL/Downloads/ideaIC-2023.1.1.exe
                        // E:/AfterJenkins/Project1Test1/Malware test files/wildfire-test-apk-file.apk
                        sc.nextLine();
                        String filePath = sc.nextLine().trim();

                        ScanFile fileScanner = new ScanFile(apiKey, filePath);
                        String responseScanFile = fileScanner.getResponse();
                        String newFilePath1 = "json_report/FileReport.json";

                        try (FileWriter wr1 = new FileWriter(newFilePath1)) {
                            wr1.write(responseScanFile);
                            wr1.close();

                            JsonToTxt.convert(newFilePath1, "results_txt/file_report/overall_report.txt");

                            JsonToTxt.getFileInfo(newFilePath1, "results_txt/file_report/info_report.txt");

                            JsonToCsv.convert(newFilePath1, "results_csv/FileReport.csv");

                            GenGraph.generate("results_csv/FileReport.csv", "charts/FileChart.png");


                            TxtToPDF.convert("results_txt/file_report/overall_report.txt",
                                    "results_txt/file_report/info_report.txt",
                                    "results_pdf/FileReport.pdf",
                                    "charts/FileChart.png");

                        } catch (Exception e) {
                            System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                        }finally {
                            showMenu();
                            System.out.println("Type in your selection (e.g. '1'):");
                            choice = sc.nextInt();
                        }
                        break;
                    case 2:
                        System.out.println("Type in the URL you want to scan below:");
                        // https://www.powerfinanceworld.com (raise exception since website isn't active)
                        // https://credltagricole-contact.46-30-203-97.plesk.page/13209
                        String url = sc.next();

                        ScanUrl urlScanner = new ScanUrl(apiKey, url);
                        String responseScanUrl = urlScanner.getResponse();
                        String newFilePath2 = "json_report/UrlReport.json";

                        try(FileWriter wr2 = new FileWriter(newFilePath2)) {
                            wr2.write(responseScanUrl);
                            wr2.close();

                            JsonToTxt.convert(newFilePath2, "results_txt/url_report/overall_report.txt");

                            JsonToTxt.getUrlInfo(newFilePath2, "results_txt/url_report/info_report.txt");

                            JsonToCsv.convert(newFilePath2, "results_csv/UrlReport.csv");

                            GenGraph.generate("results_csv/UrlReport.csv", "charts/UrlChart.png");

                            TxtToPDF.convert("results_txt/url_report/overall_report.txt",
                                    "results_txt/url_report/info_report.txt",
                                    "results_pdf/UrlReport.pdf",
                                    "charts/UrlChart.png");
                        } catch (Exception e){
                            System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                        }finally {
                            showMenu();
                            System.out.println("Type in your selection (e.g. '1'):");
                            choice = sc.nextInt();
                        }
                        break;
                    case 3:
                        System.out.println("Type in the domain name you want to scan below:");
                        // clicnews.com
                        String domain = sc.next();

                        ScanDomain domainScanner = new ScanDomain(apiKey, domain);
                        String responseScanDomain = domainScanner.getResponse();
                        String newFilePath3 = "json_report/DomainReport.json";
                        try (FileWriter wr3 = new FileWriter(newFilePath3)) {
                            wr3.write(responseScanDomain);
                            wr3.close();

                            JsonToTxt.convert(newFilePath3, "results_txt/domain_report/overall_report.txt");

                            JsonToTxt.getDomainInfo(newFilePath3, "results_txt/domain_report/info_report.txt");

                            JsonToCsv.convert(newFilePath3, "results_csv/DomainReport.csv");

                            GenGraph.generate("results_csv/DomainReport.csv", "charts/DomainChart.png");

                            TxtToPDF.convert("results_txt/domain_report/overall_report.txt",
                                    "results_txt/domain_report/info_report.txt",
                                    "results_pdf/DomainReport.pdf",
                                    "charts/DomainChart.png");
                        }catch (Exception e){
                            System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                        }finally {
                            showMenu();
                            System.out.println("Type in your selection (e.g. '1'):");
                            choice = sc.nextInt();
                        }
                        break;
                    case 4:
                        System.out.println("Type in the IP address you want to scan below:");
                        // 139.198.38.106
                        String ipAddress = sc.next();

                        ScanIp ipScanner = new ScanIp(apiKey, ipAddress);
                        String responseScanIp = ipScanner.getResponse();
                        String newFilePath4 = "json_report/IpReport.json";
                        try(FileWriter wr4 = new FileWriter(newFilePath4)) {
                            wr4.write(responseScanIp);
                            wr4.close();

                            JsonToTxt.convert(newFilePath4, "results_txt/ip_report/overall_report.txt");

                            JsonToTxt.getIpInfo(newFilePath4, "results_txt/ip_report/info_report.txt");

                            JsonToCsv.convert(newFilePath4, "results_csv/IpReport.csv");

                            GenGraph.generate("results_csv/IpReport.csv", "charts/IpChart.png");

                            TxtToPDF.convert("results_txt/ip_report/overall_report.txt",
                                    "results_txt/ip_report/info_report.txt",
                                    "results_pdf/IpReport.pdf",
                                    "charts/IpChart.png");
                        }catch (Exception e){
                            System.out.println("Error occurred while creating/writing file: " + e.getMessage());
                        }finally {
                            showMenu();
                            System.out.println("Type in your selection (e.g. '1'):");
                            choice = sc.nextInt();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}