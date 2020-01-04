package com.bharatarmy.Models;

import java.util.Objects;

public class GalleryImageModel {

    private String imageUri;
    private String imageSize;
    private String uploadcompelet;
    private int id;
    private String videolength;
    private String fileType;
    private String videoTitle;
    private String videoDesc;
    private String videoHeight;
    private String videoWidth;
    private String privacySetting;
//    private String thumbnail;


    String imageStr;

    String headertxt;
    String subtxt;
    int headerImage;
    String selectedposition;


    public GalleryImageModel() {
    }

    public GalleryImageModel(String headertxt, String subtxt, int headerImage, String selectedposition) {
        this.headertxt = headertxt;
        this.subtxt = subtxt;
        this.headerImage = headerImage;
        this.selectedposition = selectedposition;
    }

    public String getHeadertxt() {
        return headertxt;
    }

    public void setHeadertxt(String headertxt) {
        this.headertxt = headertxt;
    }

    public String getSubtxt() {
        return subtxt;
    }

    public void setSubtxt(String subtxt) {
        this.subtxt = subtxt;
    }

    public int getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(int headerImage) {
        this.headerImage = headerImage;
    }

    public String getSelectedposition() {
        return selectedposition;
    }

    public void setSelectedposition(String selectedposition) {
        this.selectedposition = selectedposition;
    }

    public GalleryImageModel(String imageStr) {
        this.imageStr = imageStr;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public GalleryImageModel(String imageUri, String imageSize, String uploadcompelet, String videolength, String fileType,
                             String videoTitle, String videoDesc, String videoHeight, String videoWidth, String privacysetting/*,
                             String thumbnail*/) {
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet = uploadcompelet;
        this.videolength = videolength;
        this.fileType = fileType;
        this.videoTitle = videoTitle;
        this.videoDesc = videoDesc;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.privacySetting = privacysetting;
//        this.thumbnail = thumbnail;
    }


    public GalleryImageModel(int id, String imageUri, String imageSize, String uploadcompelet, String videolength, String fileType,
                             String videoTitle, String videoDesc, String videoHeight, String videoWidth, String privacySetting) {
        //                             String thumbnail
        this.id = id;
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet = uploadcompelet;
        this.videolength = videolength;
        this.fileType = fileType;
        this.videoTitle = videoTitle;
        this.videoDesc = videoDesc;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.privacySetting = privacySetting;
//        this.thumbnail = thumbnail;
    }

    public String getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(String videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(String videoWidth) {
        this.videoWidth = videoWidth;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploadcompelet() {
        return uploadcompelet;
    }

    public void setUploadcompelet(String uploadcompelet) {
        this.uploadcompelet = uploadcompelet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getVideolength() {
        return videolength;
    }

    public void setVideolength(String videolength) {
        this.videolength = videolength;
    }

    public String getPrivacySetting() {
        return privacySetting;
    }

    public void setPrivacySetting(String privacySetting) {
        this.privacySetting = privacySetting;
    }

//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GalleryImageModel that = (GalleryImageModel) o;
        return id == that.id &&
                Objects.equals(imageUri, that.imageUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUri);
    }
}
