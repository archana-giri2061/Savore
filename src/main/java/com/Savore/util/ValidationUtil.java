package com.Savore.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import jakarta.servlet.http.Part;

/**
 * Utility class for validating user input fields.
 * Covers common checks such as null, email format, password strength, etc.
 * 
 * author: Archana Giri (23048573)
 */
public class ValidationUtil {

    /**
     * Checks if a string is null or empty after trimming.
     *
     * @param value the input string
     * @return true if null or empty, false otherwise
     */
    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates that a string contains only alphabetic characters (a-z, A-Z).
     *
     * @param value the input string
     * @return true if valid, false otherwise
     */
    public boolean isAlphabetic(String value) {
        return !isNullOrEmpty(value) && value.matches("^[a-zA-Z]+$");
    }

    /**
     * Validates that a string starts with a letter and only contains alphanumeric characters.
     *
     * @param value the input string
     * @return true if valid, false otherwise
     */
    public boolean isAlphanumericStartingWithLetter(String value) {
        return !isNullOrEmpty(value) && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    /**
     * Validates that a string is either "male" or "female" (case-insensitive).
     *
     * @param value the gender string
     * @return true if valid, false otherwise
     */
    public boolean isValidGender(String value) {
        return !isNullOrEmpty(value) && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    /**
     * Validates the format of an email address.
     *
     * @param email the email string
     * @return true if valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return !isNullOrEmpty(email) && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates that a phone number is exactly 10 digits and starts with '98'.
     *
     * @param number the phone number string
     * @return true if valid, false otherwise
     */
    public boolean isValidPhoneNumber(String number) {
        return !isNullOrEmpty(number) && number.matches("^98\\d{8}$");
    }

    /**
     * Validates password strength — must contain at least one uppercase letter,
     * one digit, one special character, and be at least 8 characters long.
     *
     * @param password the password string
     * @return true if valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return !isNullOrEmpty(password) && password.matches(passwordRegex);
    }

    /**
     * Validates whether the file part contains a valid image file extension.
     *
     * @param imagePart the uploaded file part
     * @return true if extension is valid (jpg, jpeg, png, gif), false otherwise
     */
    public boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
               fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /**
     * Checks whether password and re-typed password match.
     *
     * @param password        the original password
     * @param retypePassword  the confirmation password
     * @return true if both match, false otherwise
     */
    public boolean doPasswordsMatch(String password, String retypePassword) {
        return !isNullOrEmpty(password) && !isNullOrEmpty(retypePassword) && password.equals(retypePassword);
    }

    /**
     * Validates if a date of birth corresponds to an age of at least 16 years.
     *
     * @param dob the date of birth
     * @return true if the user is 16 or older, false otherwise
     */
    public boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) return false;
        return Period.between(dob, LocalDate.now()).getYears() >= 16;
    }
}
