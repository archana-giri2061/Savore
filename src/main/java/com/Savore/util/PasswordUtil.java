package com.Savore.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and verification using SHA-256 with a salt.
 */
public class PasswordUtil {
    private static final int SALT_LENGTH = 16; // 16 bytes for salt

    /**
     * Hashes a password using SHA-256 with a random salt.
     *
     * @param password the plain-text password to hash
     * @return a string in the format "hashedPassword:salt" where both are Base64-encoded
     * @throws Exception if hashing fails
     */
    public static String hashPassword(String password) throws Exception {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Generate a random salt
        byte[] salt = generateSalt();
        String hashedPassword = hashWithSalt(password.trim(), salt);

        // Encode hash and salt as Base64 for storage
        String encodedHash = Base64.getEncoder().encodeToString(hexToBytes(hashedPassword));
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        System.out.println("Generated hash for password: " + encodedHash);
        System.out.println("Generated salt: " + encodedSalt);
        return encodedHash + ":" + encodedSalt;
    }

    /**
     * Verifies a plain-text password against a stored hash:salt pair.
     *
     * @param plainPassword the plain-text password to verify
     * @param storedPassword the stored "hashedPassword:salt" string
     * @return true if the password matches, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null || storedPassword.isEmpty()) {
            System.err.println("Invalid input: plainPassword=" + (plainPassword == null ? "null" : "[HIDDEN]") +
                              ", storedPassword=" + storedPassword);
            return false;
        }

        try {
            // Split stored password into hash and salt
            String[] parts = storedPassword.split(":");
            if (parts.length != 2) {
                System.err.println("Invalid stored password format: " + storedPassword);
                return false;
            }

            // Decode hash and salt
            byte[] storedHash = Base64.getDecoder().decode(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            System.out.println("Stored hash: " + parts[0]);
            System.out.println("Stored salt: " + parts[1]);

            // Hash the provided password with the same salt
            String computedHash = hashWithSalt(plainPassword.trim(), salt);
            byte[] computedHashBytes = hexToBytes(computedHash);
            boolean isMatch = MessageDigest.isEqual(computedHashBytes, storedHash);
            System.out.println("Computed hash: " + Base64.getEncoder().encodeToString(computedHashBytes));
            System.out.println("Password match: " + isMatch);
            return isMatch;
        } catch (Exception e) {
            System.err.println("Password verification error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Hashes a password with a given salt using SHA-256.
     *
     * @param password the password to hash
     * @param salt the salt to use
     * @return the hex-encoded hashed password
     * @throws Exception if hashing fails
     */
    private static String hashWithSalt(String password, byte[] salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        digest.update(password.getBytes("UTF-8"));
        byte[] hash = digest.digest();
        return bytesToHex(hash);
    }
    
    
    /**
     * Generates a random salt.
     *
     * @return a byte array containing the salt
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Converts a byte array to a hex string.
     *
     * @param bytes the byte array
     * @return the hex string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Converts a hex string to a byte array.
     *
     * @param hex the hex string
     * @return the byte array
     */
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}