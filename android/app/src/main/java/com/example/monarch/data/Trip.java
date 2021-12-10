package com.example.monarch.data;

import java.util.ArrayList;

public class Trip {
    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private int budget;
    private String starts_at;
    private String ends_at;
    private int chosen_place_position;

    private ArrayList<MyPlace> places;
    private String mCity;

    public Trip(String trip_name, String start_date, String end_date, int trip_budget) {
        name = trip_name;
        budget = trip_budget;
        starts_at = start_date;
        ends_at = end_date;
    }

    public int getChosen_place_position() {
        return chosen_place_position;
    }

    public void setChosen_place_position(int chosen_place_position) {
        this.chosen_place_position = chosen_place_position;
    }

    public MyPlace getChosenPlace() {
        return places.get(chosen_place_position);
    }

    public ArrayList<MyPlace> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<MyPlace> places) {
        this.places = places;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public String getCity() {
        return mCity;
    }

    public String getStartEndDate() {
        if (starts_at == null || ends_at == null){
            return "? to ?";
        }
        return starts_at.substring(0, 10) + " to " + ends_at.substring(0,10);
    }
}
