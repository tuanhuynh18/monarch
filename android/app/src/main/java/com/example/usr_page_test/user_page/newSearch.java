package com.example.usr_page_test.user_page;

import com.google.gson.annotations.SerializedName;

public class newSearch {
    @SerializedName("name")
    private String location;

    @SerializedName("budget")
    private int budget;

    @SerializedName("starts_at")
    private String startDate;

    @SerializedName("ends_at")
    private String endDate;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
