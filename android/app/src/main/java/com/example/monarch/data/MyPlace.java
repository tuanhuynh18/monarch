package com.example.monarch.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

public class MyPlace implements Parcelable {
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

    public MyPlace(Place place)  {
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

    protected MyPlace(Parcel in) {
        title = in.readString();
        estimated_cost = in.readDouble();
        cost = in.readDouble();
        description = in.readString();
        note = in.readString();
        google_id = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        rating = in.readDouble();
        true_cost = in.readDouble();
    }

    public static final Creator<MyPlace> CREATOR = new Creator<MyPlace>() {
        @Override
        public MyPlace createFromParcel(Parcel in) {
            return new MyPlace(in);
        }

        @Override
        public MyPlace[] newArray(int size) {
            return new MyPlace[size];
        }
    };

    public double getTrue_cost() {
        return true_cost;
    }

    public void setTrue_cost(double true_cost) {
        this.true_cost = true_cost;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeDouble(estimated_cost);
        parcel.writeDouble(cost);
        parcel.writeString(description);
        parcel.writeString(note);
        parcel.writeString(google_id);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(rating);
        parcel.writeDouble(true_cost);
    }
}
