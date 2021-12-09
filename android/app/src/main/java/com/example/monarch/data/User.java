package com.example.monarch.data;

import java.util.ArrayList;

public class User {
    private ArrayList<Trip> mTrips;
    private static User mUser;
    private int chosen_trip_position;

    public int getChosen_trip_position() {
        return chosen_trip_position;
    }

    public void setChosen_trip_position(int chosen_trip_position) {
        this.chosen_trip_position = chosen_trip_position;
    }

    private User () {
        mTrips = new ArrayList<>();
        chosen_trip_position = -1;
    }

    public static User getUserInstance() {
        if (mUser == null) {
            mUser = new User();
        }
        return mUser;
    }

    public ArrayList<Trip> getTrips() {
        return mTrips;
    }

    public void setTrips(ArrayList<Trip> mTrips) {
        this.mTrips = mTrips;
    }

    public Trip getChosenTrip() {
        if (chosen_trip_position == -1) return null;
        return mTrips.get(chosen_trip_position);
    }

}
