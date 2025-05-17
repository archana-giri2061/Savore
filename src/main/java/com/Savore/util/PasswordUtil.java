package com.Savore.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for securely hashing and verifying passwords using SHA-256 and random salts.
 * Ensures safe storage of passwords by combining hash and salt in Base64 format.
 * 
 * author: 23048573_ArchanaGiri
 */
public class PasswordUtil {

    private static final int SALT_LENGTH = 16; // 16 bytes salt length

    /**
     * Hashes a plain-text password using SHA-256 with a randomly generated salt.
     *
     * @param password the plain-text password
     * @return a string in the format "Base64(hashedPassword):Base64(salt)"
     * @throws Exception if hashing fails
     */
    public static String hashPassword(String password) throws Exception {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        byte[] salt = generateSalt();
        String hashedPasswordHex = hashWithSalt(password.trim(), salt);

        String encodedHash = Base64.getEncoder().encodeToString(hexToBytes(hashedPasswordHex));
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        System.out.println("🔐 Hashed password: " + encodedHash);
        System.out.println("🧂 Generated salt: " + encodedSalt);

        return encodedHash + ":" + encodedSalt;
    }

    /**
     * Verifies a plain password against a stored "hash:salt" string.
     *
     * @param plainPassword the plain-text password entered by user
     * @param storedPassword the stored password in "hash:salt" format
     * @return true if the password is correct, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null || storedPassword.isEmpty()) {
            System.err.println("⚠️ Invalid input for password check.");
            return false;
        }

        try {
            String[] parts = storedPassword.split(":");
            if (parts.length != 2) {
                System.err.println("❌ Invalid stored password format.");
                return false;
            }

            byte[] storedHash = Base64.getDecoder().decode(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);

            System.out.println("🔒 Verifying password with stored salt...");

            String computedHashHex = hashWithSalt(plainPassword.trim(), salt);
            byte[] computedHashBytes = hexToBytes(computedHashHex);

            boolean isMatch = MessageDigest.isEqual(computedHashBytes, storedHash);

            System.out.println("✅ Password match: " + isMatch);
            return isMatch;

        } catch (Exception e) {
            System.err.println("❌ Error during password verification: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Applies SHA-256 hashing algorithm with salt.
     *
     * @param password the password to hash
     * @param salt     the salt to apply
     * @return hex string of hashed result
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
     * Generates a secure random salt.
     *
     * @return a byte array containing random salt
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Converts byte array to hex string.
     *
     * @param bytes the byte array
     * @return hex string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Converts hex string to byte array.
     *
     * @param hex the hex string
     * @return byte array
     */
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) (
                    (Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16)
            );
        }

        return data;
    }
}
