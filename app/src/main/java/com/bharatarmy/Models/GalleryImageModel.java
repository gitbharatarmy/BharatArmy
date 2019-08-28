package com.bharatarmy.Models;

public class GalleryImageModel {

    private String imageUri;
    private String imageSize;
    private int uploadcompelet;
private int id;

    String commentuserName;
    String commenttime;
    String commentText;
    String commentLikes;
    String commentLikesTotal;


    public GalleryImageModel() {
    }


    public GalleryImageModel(String imageUri, String imageSize, int uploadcompelet) {
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet = uploadcompelet;
    }
    public GalleryImageModel(int id,String imageUri, String imageSize, int uploadcompelet) {
        this.id=id;
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet = uploadcompelet;
    }

    public int getUploadcompelet() {
        return uploadcompelet;
    }

    public void setUploadcompelet(int uploadcompelet) {
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
