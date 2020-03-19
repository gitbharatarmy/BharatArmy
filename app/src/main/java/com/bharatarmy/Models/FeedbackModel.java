package com.bharatarmy.Models;

public class FeedbackModel {

      String recommendStr;

      public void FeedbackModel(String recommendStr){
          this.recommendStr=recommendStr;
      }

    public String getRecommendStr() {
        return recommendStr;
    }

    public void setRecommendStr(String recommendStr) {
        this.recommendStr = recommendStr;
    }
}
