package com.example.monarch.data;

public class Address {
    private int id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;

    public String toString() {
        line1 = line1 == null ? "" : line1 + "\n";
        line2 = line2 == null ? "" : line2 + "\n";
        city = city == null ? "" : city;
        state = state == null ? "" : state;
        zip = zip == null ? "" : zip;
        return line1 + line2 + city + ", " + state + ", " + zip;
    }
}
