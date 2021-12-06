package com.example.usr_page_test.user_page.Entity;

public class ActivityItem {
    private String activityName;
    private String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    private String payer;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public ActivityItem(String activityName, String cost, String payer) {
        this.activityName = activityName;
        this.cost = cost;
        this.payer = payer;
    }
}
