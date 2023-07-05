package com.example.stellarmemo.service;

public class ContentManager {

    //去掉HTML标签，并将图片部分替换为“[图片]”
    public static String stripHtmlTags(String content) {
        String text = content.replaceAll("<img[^>]*>", "[图片]");
        text = text.replaceAll("<[^>]*>", "");
        return text;
    }
}
