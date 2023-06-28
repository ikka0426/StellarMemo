package com.example.stellarmemo.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentManager {
    public static String[] extractImageUrls(String htmlString) {
        Pattern pattern = Pattern.compile("<img\\s+src=['\"]([^'\"]+)['\"][^>]*>");
        Matcher matcher = pattern.matcher(htmlString);
        
        int count = matcher.groupCount();
        String[] imageUrls = new String[count + 1];

        int i = 0;
        while (matcher.find()) {
            imageUrls[i] = matcher.group(1);
            i++;
        }
        return imageUrls;
    }

    public static int nameCount;
    private static void copyAndRenameImages(String[] imageUrls) {
        for (int i = 0; i < imageUrls.length; i++) {
            String imageUrl = imageUrls[i];
            String imageName = "image" + (nameCount + i + 1) + ".jpg";
            try {
                InputStream inputStream = new FileInputStream(imageUrl);
                OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/image/" + imageName);
                byte[] buffer = new byte[1024];
                int len;
                while((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0 ,len);
                }

                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String replaceImageUrls(String htmlString, String[] imageUrls) {
        for (int i = 0; i < imageUrls.length; i++) {
            String imageUrl = imageUrls[i];
            String imageName = "image" + (nameCount + i + 1) + ".jpg";

            nameCount++;

            htmlString = htmlString.replace(imageUrl, System.getProperty("user.dir") + "/src/main/resources/image/" + imageName);
        }
        return htmlString;
    }

    public static void main(String[] args) {
        String htmlString = "<html><body><img src= \"D:/image1.jpg\"></body></html>";
    }
}
