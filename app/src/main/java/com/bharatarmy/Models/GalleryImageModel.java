package com.bharatarmy.Models;

public class GalleryImageModel {

    String imageUri;
    String imageSize;

    public GalleryImageModel(String imageUri, String imageSize) {
        this.imageUri = imageUri;
        this.imageSize = imageSize;

    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }
}
