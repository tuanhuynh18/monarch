package com.example.monarch.data;

public class MyPlace {
    private int mEstimatedCost;
    private String mAddress;
    private String mName;
    public MyPlace(String name, String address, int cost) {
        mEstimatedCost = cost;
        mAddress = address;
        mName = name;
    }

    public int getEstimatedCost() {
        return mEstimatedCost;
    }

    public void setEstimatedCost(int mEstimatedCost) {
        this.mEstimatedCost = mEstimatedCost;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
