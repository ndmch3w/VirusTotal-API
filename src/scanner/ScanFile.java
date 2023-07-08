package scanner;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ScanFile extends Scanner {
    private String filePath;

    // Change the endpoint URL to get file report
    public ScanFile(String apiKey, String filePath) throws NoSuchAlgorithmException {
        super(apiKey, "https://www.virustotal.com/api/v3/files/" +hash(filePath));
        this.filePath = filePath;
    }

    // Using SHA-256 to hash file to complete the getting file report endpoint URL
    private static String hash(String filePath) throws NoSuchAlgorithmException{
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            byte[] hash = digest.digest();
            BigInteger hashValue = new BigInteger(1, hash);
            return hashValue.toString(16);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
