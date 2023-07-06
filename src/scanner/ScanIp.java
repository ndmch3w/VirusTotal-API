package scanner;


public class ScanIp extends Scanner {
    private String ipAdress;

    public ScanIp(String apiKey, String ipAdress){
        super(apiKey, "https://www.virustotal.com/api/v3/ip_addresses/" + ipAdress);
        this.ipAdress = ipAdress;
    }
}
