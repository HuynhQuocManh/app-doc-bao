package com.example.mice.rss_reader.modal;

public class VideoYoutube {
    public String title;
    public String id;
    public String image;

    public String description;

    public VideoYoutube(String title, String id, String image, String description) {
        this.title = title;
        this.id = id;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
