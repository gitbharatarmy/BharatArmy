package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BAPollDatum {

    @SerializedName("BAPollListId")
    @Expose
    private Integer bAPollListId;
    @SerializedName("BAPollQuestion")
    @Expose
    private String bAPollQuestion;
    @SerializedName("BAPollQuestionAnswerData")
    @Expose
    private List<BAPollDatum> bAPollQuestionAnswerData = null;

    public Integer getBAPollListId() {
        return bAPollListId;
    }

    public void setBAPollListId(Integer bAPollListId) {
        this.bAPollListId = bAPollListId;
    }

    public String getBAPollQuestion() {
        return bAPollQuestion;
    }

    public void setBAPollQuestion(String bAPollQuestion) {
        this.bAPollQuestion = bAPollQuestion;
    }

    public List<BAPollDatum> getBAPollQuestionAnswerData() {
        return bAPollQuestionAnswerData;
    }

    public void setBAPollQuestionAnswerData(List<BAPollDatum> bAPollQuestionAnswerData) {
        this.bAPollQuestionAnswerData = bAPollQuestionAnswerData;
    }


    @SerializedName("BAPollQuestionAnswer")
    @Expose
    private String bAPollQuestionAnswer;

    public String getBAPollQuestionAnswer() {
        return bAPollQuestionAnswer;
    }

    public void setBAPollQuestionAnswer(String bAPollQuestionAnswer) {
        this.bAPollQuestionAnswer = bAPollQuestionAnswer;
    }

    @SerializedName("BAPollQuestionAnswerVote")
    @Expose
    private String bAPollQuestionAnswerVote;
    @SerializedName("BAPollVoteCount")
    @Expose
    private Double bAPollVoteCount;
    public Double getBAPollVoteCount() {
        return bAPollVoteCount;
    }

    public void setBAPollVoteCount(Double bAPollVoteCount) {
        this.bAPollVoteCount = bAPollVoteCount;
    }
    public String getBAPollQuestionAnswerVote() {
        return bAPollQuestionAnswerVote;
    }

    public void setBAPollQuestionAnswerVote(String bAPollQuestionAnswerVote) {
        this.bAPollQuestionAnswerVote = bAPollQuestionAnswerVote;
    }


    @SerializedName("BAPollQuestionAnswerSelected")
    @Expose
    private String bAPollQuestionAnswerSelected;

    public String getbAPollQuestionAnswerSelected() {
        return bAPollQuestionAnswerSelected;
    }

    public void setbAPollQuestionAnswerSelected(String bAPollQuestionAnswerSelected) {
        this.bAPollQuestionAnswerSelected = bAPollQuestionAnswerSelected;
    }

    @SerializedName("BAQuizListId")
    @Expose
    private Integer bAQuizListId;
    @SerializedName("BAQuizQuestion1")
    @Expose
    private String bAQuizQuestion1;
    @SerializedName("BAQuizQuestionAnswerData")
    @Expose
    private List<BAPollDatum> bAQuizQuestionAnswerData = null;

    @SerializedName("BAQuizQuestionAnswerSelected")
    @Expose
    private String bAquizQuestionAnswerSelected;

    public String getbAquizQuestionAnswerSelected() {
        return bAquizQuestionAnswerSelected;
    }

    public void setbAquizQuestionAnswerSelected(String bAquizQuestionAnswerSelected) {
        this.bAquizQuestionAnswerSelected = bAquizQuestionAnswerSelected;
    }

    public Integer getBAQuizListId() {
        return bAQuizListId;
    }

    public void setBAQuizListId(Integer bAQuizListId) {
        this.bAQuizListId = bAQuizListId;
    }

    public String getBAQuizQuestion1() {
        return bAQuizQuestion1;
    }

    public void setBAQuizQuestion1(String bAQuizQuestion1) {
        this.bAQuizQuestion1 = bAQuizQuestion1;
    }

    public List<BAPollDatum> getBAQuizQuestionAnswerData() {
        return bAQuizQuestionAnswerData;
    }

    public void setBAQuizQuestionAnswerData(List<BAPollDatum> bAQuizQuestionAnswerData) {
        this.bAQuizQuestionAnswerData = bAQuizQuestionAnswerData;
    }


    @SerializedName("BAQuizQuestionAnswer")
    @Expose
    private String bAQuizQuestionAnswer;

    public String getBAQuizQuestionAnswer() {
        return bAQuizQuestionAnswer;
    }

    public void setBAQuizQuestionAnswer(String bAQuizQuestionAnswer) {
        this.bAQuizQuestionAnswer = bAQuizQuestionAnswer;
    }
}
