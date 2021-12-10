package com.example.monarch.data;

import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

public class MyPlace {
    private String title;
    private Address address;
    private double estimated_cost;
    private double cost;
    private String description;
    private String note;
    private String google_id;
    private double latitude;
    private double longitude;
    private double rating;
    private Address address_attributes;
    private double true_cost;

    public MyPlace(Place place) {
        List<AddressComponent> listAddressComponents = place.getAddressComponents().asList();
        address = new Address(listAddressComponents);
        address_attributes = address;
        google_id = place.getId();
        int priceLevel = place.getPriceLevel() != null ? place.getPriceLevel() : 1;
        estimated_cost = priceLevel * CostOfLiving.AVERAGE_COST_OF_LIVING.get(address.getState());
        latitude = place.getLatLng().latitude;
        longitude = place.getLatLng().longitude;
        note = "";
        description = "";
        title = place.getName();
        rating = place.getRating();
        cost = estimated_cost;
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

    public double getCost() {
        return estimated_cost;
    }

    public void setCost(int cost) {
        this.estimated_cost = cost;
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

    public void setEstimatedCost(int mEstimatedCost) {
        cost = mEstimatedCost;
    }

    public void setName(String mName) {
    }

    public Address getAddress() {
        return address;
    }
}
