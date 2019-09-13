package com.bharatarmy.Models;

public class GalleryImageModel {

    private String imageUri;
    private String imageSize;
    private String uploadcompelet;
    private int id;
    private String videolength;
    private String fileType;
    private String videoTitle;
    private String videoDesc;

    String commentuserName;
    String commenttime;
    String commentText;
    String commentLikes;
    String commentLikesTotal;




    public GalleryImageModel() {
    }


    public GalleryImageModel(String imageUri, String imageSize, String uploadcompelet,String videolength,String fileType,
                             String videoTitle,String videoDesc) {
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet = uploadcompelet;
        this.videolength=videolength;
        this.fileType=fileType;
        this.videoTitle=videoTitle;
        this.videoDesc=videoDesc;
    }

    public GalleryImageModel(int id, String imageUri, String imageSize, String uploadcompelet,String videolength,String fileType,
                             String videoTitle,String videoDesc) {
        this.id = id;
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet = uploadcompelet;
        this.videolength=videolength;
        this.fileType=fileType;
        this.videoTitle=videoTitle;
        this.videoDesc=videoDesc;
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

    public GalleryImageModel(String commentuserName, String commenttime, String commentText,
                             String commentLikes, String commentLikesTotal) {
        this.commentuserName = commentuserName;
        this.commenttime = commenttime;
        this.commentText = commentText;
        this.commentLikes = commentLikes;
        this.commentLikesTotal = commentLikesTotal;
    }

    public String getCommentuserName() {
        return commentuserName;
    }

    public void setCommentuserName(String commentuserName) {
        this.commentuserName = commentuserName;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentLikes() {
        return commentLikes;
    }

    public void setCommentLikes(String commentLikes) {
        this.commentLikes = commentLikes;
    }

    public String getCommentLikesTotal() {
        return commentLikesTotal;
    }

    public void setCommentLikesTotal(String commentLikesTotal) {
        this.commentLikesTotal = commentLikesTotal;
    }
}
