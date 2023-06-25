package scanner;

import scanner.Scanner;

import java.util.Base64;

public class ScanUrl extends Scanner {
    private String urlToScan;

    public ScanUrl(String apiKey, String urlToScan){
        super(apiKey, "https://www.virustotal.com/api/v3/urls/" + getUrlId(urlToScan));
        this.urlToScan = urlToScan;
    }

    private static String getUrlId(String urlToScan){
        String url = urlToScan;
        byte[] urlBytes = url.getBytes();

        String encodedUrl = Base64.getUrlEncoder().withoutPadding().encodeToString(urlBytes);
        encodedUrl = encodedUrl.replaceAll("=", "");

        return encodedUrl;
    }
}
