package com.example.monarch.data;

import java.util.ArrayList;

public class User {
    private ArrayList<Trip> mTrips;
    private static User mUser;

    private User () {
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
}
