package com.example.monarch.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.libraries.places.api.model.AddressComponent;

import java.util.List;

public class Address implements Parcelable {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private int zip;
    private int addressable_id;

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getAddressable_id() {
        return addressable_id;
    }

    public void setAddressable_id(int addressable_id) {
        this.addressable_id = addressable_id;
    }


    protected Address(Parcel in) {
        line1 = in.readString();
        line2 = in.readString();
        city = in.readString();
        state = in.readString();
        zip = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

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
        line1 = "";
        line2 = "";
        city = "";
        state = "";
        for (int i = 0; i < listAddressComponents.size(); i++) {
            AddressComponent component = listAddressComponents.get(i);
            if (component.getTypes().contains("street_name"))
                line1 += component.getName();
            if (component.getTypes().contains("route"))
                line1 += component.getName();
            if (component.getTypes().contains("locality"))
                city = component.getName();
            if (component.getTypes().contains("administrative_area_level_1"))
                state += component.getShortName();
            if (component.getTypes().contains("postal_code"))
                zip = Integer.parseInt(component.getName());

        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(line1);
        parcel.writeString(line2);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeInt(zip);
    }
}
