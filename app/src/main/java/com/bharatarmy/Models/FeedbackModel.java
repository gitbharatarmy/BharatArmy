package com.bharatarmy.Models;

public class FeedbackModel {

    int feedbackIconImage;
    String feedbackOptionselectedStr;
    String feedbackIconSize;

    String imageThumbUrl;
    String imageWatchList;
    String imageOrvideo;
    String imageFullUrl;
    String videoFullUrl;


    /*question type
     * 1= Multichoice
     * 2= Singlechoice
     * 3= Singlechoicerating
     * 4= Singlechoiceimage
     * 5= Edittext*/

    String feedbackQuestionStr;
    String feedbackQuestionAnswerStr;
    String feedbackQuestionTypeStr;
    String feedbackQuestionAnswerSelectedStr;
    String feedbackUserGivenAnswerStr;
    String feedbackQuestionCategoryStr;


    public FeedbackModel(String feedbackQuestionStr, String feedbackQuestionCategoryStr, String feedbackQuestionTypeStr) {
        this.feedbackQuestionStr = feedbackQuestionStr;
        this.feedbackQuestionCategoryStr = feedbackQuestionCategoryStr;
        this.feedbackQuestionTypeStr = feedbackQuestionTypeStr;
    }

    public String getFeedbackQuestionStr() {
        return feedbackQuestionStr;
    }

    public void setFeedbackQuestionStr(String feedbackQuestionStr) {
        this.feedbackQuestionStr = feedbackQuestionStr;
    }

    public String getFeedbackQuestionCategoryStr() {
        return feedbackQuestionCategoryStr;
    }

    public void setFeedbackQuestionCategoryStr(String feedbackQuestionCategoryStr) {
        this.feedbackQuestionCategoryStr = feedbackQuestionCategoryStr;
    }

    public String getFeedbackQuestionTypeStr() {
        return feedbackQuestionTypeStr;
    }

    public void setFeedbackQuestionTypeStr(String feedbackQuestionTypeStr) {
        this.feedbackQuestionTypeStr = feedbackQuestionTypeStr;
    }


    //    public FeedbackModel( String feedbackQuestionAnswerStr, String feedbackQuestionAnswerSelectedStr,
//                         String feedbackUserGivenAnswerStr) {
//        this.feedbackQuestionStr = feedbackQuestionStr;
//        this.feedbackQuestionAnswerStr = feedbackQuestionAnswerStr;
//        this.feedbackQuestionTypeStr = feedbackQuestionTypeStr;
//        this.feedbackQuestionAnswerSelectedStr = feedbackQuestionAnswerSelectedStr;
//        this.feedbackUserGivenAnswerStr = feedbackUserGivenAnswerStr;
//        this.feedbackQuestionCategoryStr = feedbackQuestionCategoryStr;
//    }


    public FeedbackModel(int feedbackIconImage, String feedbackOptionselectedStr, String feedbackIconSize) {
        this.feedbackIconImage = feedbackIconImage;
        this.feedbackOptionselectedStr = feedbackOptionselectedStr;
        this.feedbackIconSize = feedbackIconSize;
    }

    public int getFeedbackIconImage() {
        return feedbackIconImage;
    }

    public void setFeedbackIconImage(int feedbackIconImage) {
        this.feedbackIconImage = feedbackIconImage;
    }

    public String getFeedbackOptionselectedStr() {
        return feedbackOptionselectedStr;
    }

    public void setFeedbackOptionselectedStr(String feedbackOptionselectedStr) {
        this.feedbackOptionselectedStr = feedbackOptionselectedStr;
    }

    public String getFeedbackIconSize() {
        return feedbackIconSize;
    }

    public void setFeedbackIconSize(String feedbackIconSize) {
        this.feedbackIconSize = feedbackIconSize;
    }


}
