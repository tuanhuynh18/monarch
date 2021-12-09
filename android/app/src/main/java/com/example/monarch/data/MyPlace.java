package com.example.monarch.data;

public class MyPlace {
    private int id;
    private String title;
    private Address address;
    private int cost;
    private String description;
    private String note;
    private String google_id;
    private int latitude;
    private int longitude;

    private int mEstimatedCost;
    private String mName;
    public MyPlace(String name, String address, int cost) {
        mEstimatedCost = cost;
        mName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getmEstimatedCost() {
        return mEstimatedCost;
    }

    public void setmEstimatedCost(int mEstimatedCost) {
        this.mEstimatedCost = mEstimatedCost;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getEstimatedCost() {
        return mEstimatedCost;
    }

    public void setEstimatedCost(int mEstimatedCost) {
        this.mEstimatedCost = mEstimatedCost;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Address getAddress() {
        return address;
    }
}
