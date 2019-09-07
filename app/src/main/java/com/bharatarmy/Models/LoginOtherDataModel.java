package com.bharatarmy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginOtherDataModel {
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("MessageHeaderText")
    @Expose
    private String messageHeaderText;
    @SerializedName("MessageDescription")
    @Expose
    private String messageDescription;
    @SerializedName("MessageText")
    @Expose
    private String messageText;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageHeaderText() {
        return messageHeaderText;
    }

    public void setMessageHeaderText(String messageHeaderText) {
        this.messageHeaderText = messageHeaderText;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

}
