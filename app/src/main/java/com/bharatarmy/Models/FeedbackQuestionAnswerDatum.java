package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackQuestionAnswerDatum {
    /*First question variable*/
    @SerializedName("BAFeedbackQuestionAnswerId")
    @Expose
    private Integer bAFeedbackQuestionAnswerId;
    @SerializedName("QuestionHeader")
    @Expose
    private String questionHeader;
    @SerializedName("QuestionType")
    @Expose
    private String questionType;
    @SerializedName("QuestionTitle")
    @Expose
    private String questionTitle;
    @SerializedName("QuestionAnswerText")
    @Expose
    private String questionAnswerText;
    @SerializedName("AnswerList")
    @Expose
    private List<FeedbackAnswerList> answerList = null;

    public Integer getBAFeedbackQuestionAnswerId() {
        return bAFeedbackQuestionAnswerId;
    }

    public void setBAFeedbackQuestionAnswerId(Integer bAFeedbackQuestionAnswerId) {
        this.bAFeedbackQuestionAnswerId = bAFeedbackQuestionAnswerId;
    }

    public String getQuestionHeader() {
        return questionHeader;
    }

    public void setQuestionHeader(String questionHeader) {
        this.questionHeader = questionHeader;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionAnswerText() {
        return questionAnswerText;
    }

    public void setQuestionAnswerText(String questionAnswerText) {
        this.questionAnswerText = questionAnswerText;
    }

    public List<FeedbackAnswerList> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<FeedbackAnswerList> answerList) {
        this.answerList = answerList;
    }


    /*Second question variable*/
    @SerializedName("QuestionAnswer1Text")
    @Expose
    private String questionAnswer1Text;
    @SerializedName("QuestionAnswer2Text")
    @Expose
    private String questionAnswer2Text;
    @SerializedName("QuestionAnswer3Text")
    @Expose
    private String questionAnswer3Text;
    @SerializedName("QuestionAnswer4Text")
    @Expose
    private String questionAnswer4Text;
    @SerializedName("QuestionAnswer5Text")
    @Expose
    private String questionAnswer5Text;
    @SerializedName("QuestionAnswer6Text")
    @Expose
    private String questionAnswer6Text;
    @SerializedName("QuestionAnswer1Image")
    @Expose
    private String questionAnswer1Image;
    @SerializedName("QuestionAnswer2Image")
    @Expose
    private String questionAnswer2Image;
    @SerializedName("QuestionAnswer3Image")
    @Expose
    private String questionAnswer3Image;
    @SerializedName("QuestionAnswer4Image")
    @Expose
    private String questionAnswer4Image;
    @SerializedName("QuestionAnswer5Image")
    @Expose
    private String questionAnswer5Image;
    @SerializedName("QuestionAnswer6Image")
    @Expose
    private String questionAnswer6Image;

    public String getQuestionAnswer1Text() {
        return questionAnswer1Text;
    }

    public void setQuestionAnswer1Text(String questionAnswer1Text) {
        this.questionAnswer1Text = questionAnswer1Text;
    }

    public String getQuestionAnswer2Text() {
        return questionAnswer2Text;
    }

    public void setQuestionAnswer2Text(String questionAnswer2Text) {
        this.questionAnswer2Text = questionAnswer2Text;
    }

    public String getQuestionAnswer3Text() {
        return questionAnswer3Text;
    }

    public void setQuestionAnswer3Text(String questionAnswer3Text) {
        this.questionAnswer3Text = questionAnswer3Text;
    }

    public String getQuestionAnswer4Text() {
        return questionAnswer4Text;
    }

    public void setQuestionAnswer4Text(String questionAnswer4Text) {
        this.questionAnswer4Text = questionAnswer4Text;
    }

    public String getQuestionAnswer5Text() {
        return questionAnswer5Text;
    }

    public void setQuestionAnswer5Text(String questionAnswer5Text) {
        this.questionAnswer5Text = questionAnswer5Text;
    }

    public String getQuestionAnswer6Text() {
        return questionAnswer6Text;
    }

    public void setQuestionAnswer6Text(String questionAnswer6Text) {
        this.questionAnswer6Text = questionAnswer6Text;
    }

    public String getQuestionAnswer1Image() {
        return questionAnswer1Image;
    }

    public void setQuestionAnswer1Image(String questionAnswer1Image) {
        this.questionAnswer1Image = questionAnswer1Image;
    }

    public String getQuestionAnswer2Image() {
        return questionAnswer2Image;
    }

    public void setQuestionAnswer2Image(String questionAnswer2Image) {
        this.questionAnswer2Image = questionAnswer2Image;
    }

    public String getQuestionAnswer3Image() {
        return questionAnswer3Image;
    }

    public void setQuestionAnswer3Image(String questionAnswer3Image) {
        this.questionAnswer3Image = questionAnswer3Image;
    }

    public String getQuestionAnswer4Image() {
        return questionAnswer4Image;
    }

    public void setQuestionAnswer4Image(String questionAnswer4Image) {
        this.questionAnswer4Image = questionAnswer4Image;
    }

    public String getQuestionAnswer5Image() {
        return questionAnswer5Image;
    }

    public void setQuestionAnswer5Image(String questionAnswer5Image) {
        this.questionAnswer5Image = questionAnswer5Image;
    }

    public String getQuestionAnswer6Image() {
        return questionAnswer6Image;
    }

    public void setQuestionAnswer6Image(String questionAnswer6Image) {
        this.questionAnswer6Image = questionAnswer6Image;
    }

    /*Third Question variable same as First Question variable*/

    /*Fourth Question variable same as First Question variable*/

    /*FiFth Question variable same as First Question variable*/

    /*Six Question variable same as First Question variable*/

    /*Seven Question variable same as First Question variable*/

    /*Eight Question variable same as First Question variable*/

    /*Nine Question variable*/
    @SerializedName("QuestionAnswer1Rating")
    @Expose
    private String questionAnswer1Rating;

    public String getQuestionAnswer1Rating() {
        return questionAnswer1Rating;
    }
    public void setQuestionAnswer1Rating(String questionAnswer1Rating) {
        this.questionAnswer1Rating = questionAnswer1Rating;
    }

    /*Ten Question variable same as Second Question variable*/

    /*Eleven Question variable same as Second Question variable*/

    /*Twelve Question variable same as Second Question variable*/

    /*Thirteen Question variable same as Second Question variable*/
}
