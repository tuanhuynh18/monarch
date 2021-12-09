package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.monarch.data.MyPlace;
import com.example.monarch.data.User;

public class PlaceDetailActivity extends AppCompatActivity {

    TextView mPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        MyPlace place = User.getUserInstance().getChosenTrip().getChosenPlace();
        mPlaceName = findViewById(R.id.place_name);
        mPlaceName.setText(place.getTitle());
    }
}