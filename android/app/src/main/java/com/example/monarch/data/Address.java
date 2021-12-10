package com.example.monarch.data;

import com.google.android.libraries.places.api.model.AddressComponent;

import java.util.List;

public class Address {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private int zip;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
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

    public String getZip() {
        return Integer.toString(zip);
    }

    public void setZip(String zip) {
        this.zip = Integer.parseInt(zip);
    }

    public String toString() {
        line1 = line1 == null ? "" : line1 + "\n";
        if (line2 == null) {
            line2 = "";
        } else {
            if (!line2.equals("")) line2 = line2 + "\n";
        }
        city = city == null ? "" : city;
        state = state == null ? "" : state;
        return line1 + line2 + city + ", " + state + ", " + zip;
    }

    public Address(List<AddressComponent> listAddressComponents) {
        /*
        AddressComponents{asList=[AddressComponent{name=225, shortName=225, types=[street_number]}, AddressComponent{name=Baker Street Northwest, shortName=Baker St NW, types=[route]}, AddressComponent{name=Centennial Park District, shortName=Centennial Park District, types=[neighborhood, political]}, AddressComponent{name=Atlanta, shortName=Atlanta, types=[locality, political]}, AddressComponent{name=Fulton County, shortName=Fulton County, types=[administrative_area_level_2, political]}, AddressComponent{name=Georgia, shortName=GA, types=[administrative_area_level_1, political]}, AddressComponent{name=United States, shortName=US, types=[country, political]}, AddressComponent{name=30313, shortName=30313, types=[postal_code]}]}
        225-[street_number]
        Baker Street Northwest-[route]
        Centennial Park District-[neighborhood, political]
        Atlanta-[locality, political]
        Fulton County-[administrative_area_level_2, political]
        Georgia-[administrative_area_level_1, political]
        United States-[country, political]
        */
        line1 = listAddressComponents.get(0).getName() + ' ' + listAddressComponents.get(1).getName();
        line2 = "";
        city = listAddressComponents.get(3).getName();
        state = listAddressComponents.get(5).getShortName();
        this.setZip(listAddressComponents.get(7).getName());
    }
}
