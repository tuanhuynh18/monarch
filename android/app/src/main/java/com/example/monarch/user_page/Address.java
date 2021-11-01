package com.example.monarch.user_page;

public class Address {
    String address;// = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city; // = addresses.get(0).getLocality();
    String state; //= addresses.get(0).getAdminArea();
    String country; // = addresses.get(0).getCountryName();
    String postalCode; //  = addresses.get(0).getPostalCode();
    String knownName;   //= addresses.get(0).getFeatureName(); // Only if availab

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public void setKnownName(String knownName) {
        this.knownName = knownName;
    }
}
