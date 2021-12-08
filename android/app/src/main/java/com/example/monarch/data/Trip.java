package com.example.monarch.data;

import java.util.ArrayList;

public class Trip {
    private ArrayList<Place> mPlaces;
    private String mCity;
    private String mStartDate;
    private String mEndDate;
    private int mBudget;

    public int getBudget() {
        return mBudget;
    }

    public Trip (String city, String startDate, String endDate, int budget) {
        mCity = city;
        mStartDate = startDate;
        mEndDate = endDate;
        mBudget = budget;
    }

    public String getCity() {
        return mCity;
    }

    public String getStartEndDate() {
        return mStartDate + " - " + mEndDate;
    }
}
