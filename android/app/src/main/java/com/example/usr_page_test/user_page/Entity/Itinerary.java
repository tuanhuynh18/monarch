package com.example.usr_page_test.user_page.Entity;

import com.google.gson.annotations.SerializedName;

public class Itinerary {
    //@SerializedName("name")
    private String location;
    //@SerializedName("starts_at")
    private String startDate;
    //@SerializedName("ends_at")
    private String endDate;

    public Itinerary(String location, String startDate, String endDate) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Itinerary() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
