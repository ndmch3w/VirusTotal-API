package scanner.file;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHash {
    public static String hash(String filePath) throws NoSuchAlgorithmException{
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            byte[] hash = digest.digest();
            BigInteger hashValue = new BigInteger(1, hash);
            return hashValue.toString(16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

