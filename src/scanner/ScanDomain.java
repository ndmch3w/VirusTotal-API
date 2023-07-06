package scanner;


public class ScanDomain extends Scanner {
    private String domain;

    public ScanDomain(String apiKey, String domain) {
        super(apiKey, "https://www.virustotal.com/api/v3/domains/" + domain);
        this.domain = domain;
    }
}