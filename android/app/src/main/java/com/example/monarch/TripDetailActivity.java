package com.example.monarch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monarch.data.MyPlace;
import com.example.monarch.util.ViewWeightAnimationWrapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "TripDetail";

    public static final int ERROR_DIALOG_REQUEST = 1;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 3;

    private static final int MAP_LAYOUT_STATE_CONTRACTED = 0;
    private static final int MAP_LAYOUT_STATE_EXPANDED = 1;

    private static final int DEFAULT_MAP_ZOOM = 15;

    // widgets
    private ImageView mFullScreenImageView;
    private RecyclerView mTripItemRecyclerView;
    private RelativeLayout mMapContainer;
    private AutoCompleteTextView mSearchText;

    // vars
    private boolean mLocationPermissionGranted = false;
    private int mMapLayoutState = 0; // 0 = contract, 1 = expand
    private GoogleMap mGoogleMap;
//    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;

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

            // Initialize the SDK
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_API_key));

            // Create a new PlacesClient instance
            PlacesClient placesClient = Places.createClient(this);

            // Initialize the AutocompleteSupportFragment.
            AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

            // Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));

            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                    Log.i(TAG, "Place: " + place);
                    LatLng latLong = place.getLatLng();
                    String name = place.getName();
                    moveCamera(latLong, DEFAULT_MAP_ZOOM, name);
                }


                @Override
                public void onError(@NonNull Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });



//            mSearchText = findViewById(R.id.input_search);
//            mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                    if(actionId == EditorInfo.IME_ACTION_SEARCH
//                            || actionId == EditorInfo.IME_ACTION_DONE
//                            || keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                            || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
//
//                        //execute our method for searching
//                        geoLocate();
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
//                        return true;
//                    }
//
//                    return false;
//                }
//            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            // recyclerview
            ArrayList<MyPlace> places = new ArrayList<>();
            places.add(new MyPlace("Hongkong supermarket", "123 Atl street", 100));
            places.add(new MyPlace("Time square", "456 New York blv", 200));
            places.add(new MyPlace("Chocolate factory", "248 Garden Grove CA", 300));

            RecyclerView recyclerView = findViewById(R.id.trip_item_list_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            PlaceAdapder adapter = new PlaceAdapder(this, places);
            recyclerView.setAdapter(adapter);
        }
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(TripDetailActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_MAP_ZOOM,
                    address.getAddressLine(0));
        }

    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mGoogleMap.addMarker(options);
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

        // Move the camera instantly to Sydney with a zoom of 15.
        moveCamera(currentLocation, DEFAULT_MAP_ZOOM, "My location");
    }

    // recyclerview
    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyPlace mPlace;

        private TextView mPlaceName;
        private TextView mPlaceAddress;
        private TextView mEstimatedCost;

        public PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place, parent, false));
            itemView.setOnClickListener(this);

            mPlaceName = (TextView) itemView.findViewById(R.id.place_name);
            mPlaceAddress = (TextView) itemView.findViewById(R.id.place_address);
            mEstimatedCost = (TextView) itemView.findViewById(R.id.place_true_cost);
        }

        public void bind(MyPlace place) {
            mPlace = place;
            mPlaceName.setText(mPlace.getName());
            mPlaceAddress.setText(mPlace.getAddress());
            mEstimatedCost.setText("$" + mPlace.getEstimatedCost());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), PlaceDetailActivity.class);
            startActivity(intent);
        }
    }

    private class PlaceAdapder extends RecyclerView.Adapter<PlaceHolder> {
        private ArrayList<MyPlace> mPlaces;
        private Context mContext;
        public PlaceAdapder(Context context, ArrayList<MyPlace> places) {
            mPlaces = places;
            mContext = context;
        }

        @Override
        public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            return new PlaceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PlaceHolder holder, int position) {
            MyPlace place = mPlaces.get(position);
            holder.bind(place);
        }

        @Override
        public int getItemCount() {
            return mPlaces.size();
        }
    }
}

