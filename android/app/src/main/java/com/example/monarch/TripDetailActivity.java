package com.example.monarch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.monarch.util.ViewWeightAnimationWrapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "TripDetail";

    public static final int ERROR_DIALOG_REQUEST = 1;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 3;

    private static final int MAP_LAYOUT_STATE_CONTRACTED = 0;
    private static final int MAP_LAYOUT_STATE_EXPANDED = 1;

    // widgets
    private ImageView mFullScreenImageView;
    private RecyclerView mTripItemRecyclerView;
    private RelativeLayout mMapContainer;

    // vars
    private boolean mLocationPermissionGranted = false;
    private int mMapLayoutState = 0; // 0 = contract, 1 = expand
    private GoogleMap mGoogleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isServicesAvailable()) {
            Log.d(TAG, "onCreate: map created.");
            setContentView(R.layout.activity_trip_detail);

            mTripItemRecyclerView = findViewById(R.id.trip_item_list_recycler_view);
            mMapContainer = findViewById(R.id.map_container);

            mFullScreenImageView = findViewById(R.id.full_screen_image_view);
            mFullScreenImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMapLayoutState == MAP_LAYOUT_STATE_CONTRACTED){
                        mMapLayoutState = MAP_LAYOUT_STATE_EXPANDED;
                        expandMapAnimation();
                    }
                    else if(mMapLayoutState == MAP_LAYOUT_STATE_EXPANDED){
                        mMapLayoutState = MAP_LAYOUT_STATE_CONTRACTED;
                        contractMapAnimation();
                    }
                }
            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isServicesAvailable()) {
            if(isGPSEnabled()) {
                if (mLocationPermissionGranted) {
                    // display map
                } else {
                    getLocationPermission();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    //
                } else {
                    getLocationPermission();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            // get data
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void init() {
    }

    // checking if the device have google play services
    public boolean isServicesAvailable(){
        Log.d(TAG, "isServicesAvailable: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(TripDetailActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            // everything is good and user can have google map
            Log.d(TAG, "isServicesAvailable: Google Play services is available");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // services not available but we can resolve it
            Log.d(TAG, "isServicesAvailable: services not available but we can resolve it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(TripDetailActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this, "Unable to run map", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    // checking if the application has GPS enable
    public boolean isGPSEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // alert no gps
            return false;
        }
        return true;
    }

    // -------------------- helper functions
    private void expandMapAnimation(){
        ViewWeightAnimationWrapper mapAnimationWrapper = new ViewWeightAnimationWrapper(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
                "weight",
                50,
                100);
        mapAnimation.setDuration(800);

        ViewWeightAnimationWrapper recyclerAnimationWrapper = new ViewWeightAnimationWrapper(mTripItemRecyclerView);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
                "weight",
                50,
                0);
        recyclerAnimation.setDuration(800);

        recyclerAnimation.start();
        mapAnimation.start();
    }

    private void contractMapAnimation(){
        ViewWeightAnimationWrapper mapAnimationWrapper = new ViewWeightAnimationWrapper(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
                "weight",
                100,
                50);
        mapAnimation.setDuration(800);

        ViewWeightAnimationWrapper recyclerAnimationWrapper = new ViewWeightAnimationWrapper(mTripItemRecyclerView);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
                "weight",
                0,
                50);
        recyclerAnimation.setDuration(800);

        recyclerAnimation.start();
        mapAnimation.start();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng currentLocation = new LatLng(33.77576938714813, -84.39629573138684);
        double bottomBoundary =  currentLocation.latitude - .1;
        double leftBoundary = currentLocation.longitude - .1;
        double topBoundary = currentLocation.latitude + .1;
        double rightBoundary = currentLocation.longitude + .1;
        LatLngBounds boundary = new LatLngBounds(
                new LatLng(-44, 113), // SW bounds
                new LatLng(-10, 154)  // NE bounds
        );

        // Move the camera instantly to Sydney with a zoom of 15.
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title("Marker in Sydney"));

    }
}
