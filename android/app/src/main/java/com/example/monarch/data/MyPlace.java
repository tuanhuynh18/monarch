package com.example.monarch.data;

import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

public class MyPlace {
    private int id;
    private String title;
    private Address address;
    private int cost;
    private String description;
    private String note;
    private String google_id;
    private double latitude;
    private double longitude;

    private int estimated_cost;

    public MyPlace(Place place) {
        List<AddressComponent> listAddressComponents = place.getAddressComponents().asList();
        address = new Address(listAddressComponents);
        google_id = place.getId();
        int priceLevel = place.getPriceLevel() != null ? place.getPriceLevel() : 1;
        estimated_cost = priceLevel * CostOfLiving.AVERAGE_COST_OF_LIVING.get(address.getCity());
        latitude = place.getLatLng().latitude;
        longitude = place.getLatLng().longitude;
        note = "";
        description = "";
        title = place.getName();
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getEstimated_cost() {
        return estimated_cost;
    }

    public void setEstimated_cost(int estimated_cost) {
        this.estimated_cost = estimated_cost;
    }

    public int getEstimatedCost() {
        return estimated_cost;
    }

    public void setEstimatedCost(int mEstimatedCost) {
        this.estimated_cost = mEstimatedCost;
    }

    public void setName(String mName) {
    }

    public Address getAddress() {
        return address;
    }
}
