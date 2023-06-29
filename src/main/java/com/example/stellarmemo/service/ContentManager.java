package com.example.stellarmemo.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentManager {
    //提取表达式中的图片的路径
    public static String[] extractImageUrls(String htmlString) {
        Pattern pattern = Pattern.compile("<img\\s+src=['\"]([^'\"]+)['\"][^>]*>");
        Matcher matcher = pattern.matcher(htmlString);
        Matcher matcher2 = pattern.matcher(htmlString);

        int count = 0;
        while (matcher.find()) count++;
        String[] imageUrls = new String[count];

        int i = 0;
        while (matcher2.find()) {
            imageUrls[i] = matcher2.group(1);
            i++;
        }
        return imageUrls;
    }

    public static int nameCount = countImage();
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

    //将html中的url替换成新的
    private static String replaceImageUrls(String htmlString, String[] imageUrls) {
        for (int i = 0; i < imageUrls.length; i++) {
            String imageUrl = imageUrls[i];
            String imageName = "image" + (nameCount + 1) + ".jpg";

            nameCount++;

            htmlString = htmlString.replace(imageUrl, System.getProperty("user.dir") + "/src/main/resources/image/" + imageName);
            htmlString = htmlString.replaceAll("\\\\", "/");
        }
        return htmlString;
    }

    //数image文件夹中已存放的图片数
    private static int countImage() {
        File folder = new File(System.getProperty("user.dir") + "/src/main/resources/image");
        File[] list = folder.listFiles();
        int count = 0;
        for(File file : list) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    public static String modifiedContent(String htmlString) {

        String[] imageUrls = extractImageUrls(htmlString);

        copyAndRenameImages(imageUrls);

        String modifiedHtmlString = replaceImageUrls(htmlString, imageUrls);

        System.out.println(modifiedHtmlString);

        return modifiedHtmlString;
    }
}
