package com.robinsonir.fittrack.decrypt;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class EnvFileDecrypter {
    public Map<String, String> variables;

    // Helper method to convert hexadecimal string to byte array
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static Map<String, String> parseEnvFile(String data) {
        Map<String, String> variables = new HashMap<>();
        String[] lines = data.split("\\r?\\n");
        for (String line : lines) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                variables.put(parts[0], parts[1]);
            }
        }
        return variables;
    }

    public void decryptFile() throws Exception {
        // Path to the encrypted file
        String encryptedFilePath = "fit-track/.env.enc";
        // Path to save the decrypted file
        // Key and IV used for encryption (in hexadecimal format)
        String keyHex = "9c041637890f2cf3a5698c2f6d08ec1bf68ba5bcdb41a35b1ba71ea6fed37787"; // 32 bytes (256 bits)
        String ivHex = "e5be904b37dd5056b825ca54f347e8a8";   // 16 bytes (128 bits)

        // Convert key and IV from hexadecimal strings to byte arrays
        byte[] keyBytes = hexStringToByteArray(keyHex);
        byte[] ivBytes = hexStringToByteArray(ivHex);

        // Create SecretKey and IvParameterSpec objects
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        // Initialize Cipher for decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        // Read encrypted file
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(encryptedFilePath));

        // Decrypt the file
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedData = new String(decryptedBytes);

        variables = parseEnvFile(decryptedData);


    }
}


