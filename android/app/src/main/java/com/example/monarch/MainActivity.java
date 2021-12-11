package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.monarch.util.FirestoreUtils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}