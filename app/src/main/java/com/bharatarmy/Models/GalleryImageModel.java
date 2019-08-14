package com.bharatarmy.Models;

public class GalleryImageModel {

    String imageUri;
    String imageSize;
    String uploadcompelet;
    String uploadfail;

    String commentuserName;
    String commenttime;
    String commentText;
    String commentLikes;
    String commentLikesTotal;


    public GalleryImageModel(String imageUri, String imageSize,String uploadcompelet,String uploadfail) {
        this.imageUri = imageUri;
        this.imageSize = imageSize;
        this.uploadcompelet=uploadcompelet;
        this.uploadfail=uploadfail;
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



    public GalleryImageModel( String commentuserName, String commenttime,String commentText,
                              String commentLikes, String commentLikesTotal){
        this.commentuserName=commentuserName;
        this.commenttime=commenttime;
        this.commentText=commentText;
        this.commentLikes=commentLikes;
        this.commentLikesTotal=commentLikesTotal;
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
