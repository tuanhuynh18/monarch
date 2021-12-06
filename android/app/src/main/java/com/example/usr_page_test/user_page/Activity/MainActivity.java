package com.example.usr_page_test.user_page.Activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.usr_page_test.R;
import com.example.usr_page_test.user_page.Utils.NavigationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View.OnClickListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String LIFECYCLE = "MainActivity:Lifecycle";
    Button button;
    BottomNavigationBar bottomNavigationBar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.mainpage);
        button =  findViewById(R.id.mainButton);
        Log.e(LIFECYCLE, "onCreate() is Running__before super.onCreate called");
        super.onCreate(savedInstanceState);
        Log.e(LIFECYCLE, "onCreate() is Running__after super.onCreate called");;
        button.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, UserSearchActivity.class);
                Intent intent = new Intent(MainActivity.this, UserSearchActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        NavigationUtils navigationUtils = new NavigationUtils();
        navigationUtils.init_bar(bottomNavigationBar, this);



    }

    @Override
    protected void onRestart() {
        Log.e(LIFECYCLE, "onRestart() is Running__before super's called");
        super.onRestart();
        Log.e(LIFECYCLE, "onRestart() is Running__after super's called");
        Date currentTime = Calendar.getInstance().getTime();
        Log.e("Lifecycle",currentTime.toString());
    }

    @Override
    protected void onStart() {
        Log.e(LIFECYCLE, "onStart() is Running__before super.onStart called");
        super.onStart();
        Log.e(LIFECYCLE, "onStart() is Running__after super.onStart called");
        Date currentTime = Calendar.getInstance().getTime();
        Log.e("Lifecycle",currentTime.toString());
        //FireStoreUtil.modifyFireStore("MainActivity",Calendar.getInstance().getTime().toString());
    }

    @Override
    protected void onResume() {
        Log.e(LIFECYCLE, "onResume() is Running__before super.onResume called");
        super.onResume();
        Log.e(LIFECYCLE, "onResume() is Running__after super.onResume called");
        Date currentTime = Calendar.getInstance().getTime();
        Log.e("Lifecycle",currentTime.toString());
    }

    @Override
    protected void onPause() {
        Log.e(LIFECYCLE, "onPause() is Running__before super's called");
        super.onPause();
        Log.e(LIFECYCLE, "onPause() is Running__after super's called");
        Date currentTime = Calendar.getInstance().getTime();
        Log.e("Lifecycle",currentTime.toString());
    }

    @Override
    protected void onStop() {
        Log.e(LIFECYCLE, "onStop() is Running__before super's called");
        super.onStop();
        Log.e(LIFECYCLE, "onStop() is Running__after super's called");
    }

    @Override
    protected void onDestroy() {
        Log.e(LIFECYCLE, "onDestroy() is Running__before super's called");
        super.onDestroy();
        Log.e(LIFECYCLE, "onDestroy() is Running__after super's called");
    }

//    public void toTask(View view) {
//        startActivity(new Intent(this, TaskActivity.class));
//        //finish();
//    }
}


