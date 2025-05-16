package com.Savore.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.Part;

public class ImageUtil {

    /**
     * Extracts original file name from the uploaded part.
     */
    public String getImageNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        String imageName = null;

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }

        return (imageName == null || imageName.isEmpty()) ? "default.jpg" : imageName;
    }

    /**
     * Uploads the image to the specified folder in your system.
     * This version uses a hardcoded base path and appends the given folder name.
     */
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(saveFolder);
        File fileSaveDir = new File(savePath);

        if (!fileSaveDir.exists() && !fileSaveDir.mkdirs()) {
            System.out.println("Failed to create directory: " + savePath);
            return false;
        }

        try {
            String originalName = getImageNameFromPart(part);
            String extension = originalName.substring(originalName.lastIndexOf('.'));
            String uniqueFileName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + extension;

            String filePath = savePath + File.separator + uniqueFileName;
            part.write(filePath);

            System.out.println("✅ Image saved to: " + filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Constructs the full absolute system path to save images into.
     * This must match your project folder structure.
     */
    public String getSavePath(String saveFolder) {
        return "C:\\Users\\ARCHANA\\eclipse-workspace\\Savore\\src\\main\\webapp\\Resources\\Images\\System/UserProfile" 
                + File.separator + saveFolder;
    }

    /**
     * Static method that returns the unique filename after saving.
     * Useful when you want to save the actual filename into DB.
     */
    public static String saveImage(Part part, String uploadDir) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFileName = part.getSubmittedFileName();
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String uniqueName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + extension;

            part.write(uploadDir + File.separator + uniqueName);
            return uniqueName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
