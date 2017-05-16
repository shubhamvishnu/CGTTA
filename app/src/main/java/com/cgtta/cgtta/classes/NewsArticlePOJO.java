package com.cgtta.cgtta.classes;

/**
 * Created by shubh on 5/16/2017.
 */

public class NewsArticlePOJO {
    String title;
    String content;
    String imageUrl;
    String type;

    public NewsArticlePOJO(String title, String content, String imageUrl, String type) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

