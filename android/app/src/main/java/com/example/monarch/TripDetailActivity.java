package com.example.monarch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.MyPlace;
import com.example.monarch.data.Trip;
import com.example.monarch.data.User;
import com.example.monarch.util.ViewWeightAnimationWrapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

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
    private FusedLocationProviderClient fusedLocationClient;

    // vars
    private int trip_position;
    private boolean mLocationPermissionGranted = false;
    private int mMapLayoutState = 0; // 0 = contract, 1 = expand
    private GoogleMap mGoogleMap;
    RecyclerView mRecyclerView;
    PlaceAdapder mAdapter;
//    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isServicesAvailable()) {
            Log.d(TAG, "onCreate: map created.");
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            setContentView(R.layout.activity_trip_detail);

            mTripItemRecyclerView = findViewById(R.id.trip_item_list_recycler_view);
            mMapContainer = findViewById(R.id.map_container);

            mFullScreenImageView = findViewById(R.id.full_screen_image_view);
            mFullScreenImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMapLayoutState == MAP_LAYOUT_STATE_CONTRACTED) {
                        mMapLayoutState = MAP_LAYOUT_STATE_EXPANDED;
                        expandMapAnimation();
                    } else if (mMapLayoutState == MAP_LAYOUT_STATE_EXPANDED) {
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
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS, Place.Field.PRICE_LEVEL, Place.Field.RATING));

            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                    Log.i(TAG, "Place: " + place.toString());
                    Log.i(TAG, "address components: " + place.getAddressComponents());
                    // add place to backend and frontend
                    addPlace(place);
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

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            // recyclerview
            ArrayList<MyPlace> places = User.getUserInstance().getChosenTrip().getPlaces();
            if (places == null) {
                places = new ArrayList<MyPlace>();
                User.getUserInstance().getChosenTrip().setPlaces(places);
            }

            mRecyclerView = findViewById(R.id.trip_item_list_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new PlaceAdapder(this, places);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void addPlace(Place place) {
        MyPlace new_place = new MyPlace(place);

        Gson gson = new Gson();
        JSONObject body = null;
        try {
            JSONObject data = new JSONObject(gson.toJson(new_place));
            Log.d(TAG, data.toString());
            body = new JSONObject().put("place", data);
            Log.d(TAG, body.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getResources().getString(R.string.back_end_base) + getResources().getString(R.string.get_all_places_endpoint);

        JsonObjectRequest addPlaceRequest = new JsonObjectRequest
                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Add place successfully");
                        mAdapter = new PlaceAdapder(getApplicationContext(), User.getUserInstance().getChosenTrip().getPlaces());
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        User.getUserInstance().getChosenTrip().getPlaces().add(new_place);
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(addPlaceRequest);

        body = null;
        try {
            JSONObject data = new JSONObject();
            data.put("google_id", place.getId());
            body = new JSONObject().put("place", data);
            Log.d(TAG, "add place to a trip" + body.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = getResources().getString(R.string.back_end_base) + "/trips/" + User.getUserInstance().getChosenTrip().getId() + getResources().getString(R.string.get_all_places_endpoint);

        JsonObjectRequest addPlaceToTrip = new JsonObjectRequest
                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Add place to a trip successfully");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(addPlaceToTrip);
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(TripDetailActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_MAP_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("My Location")) {
            addPinLocation(latLng, title, BitmapDescriptorFactory.HUE_BLUE);
        } else {
            addPinLocation(latLng, title, BitmapDescriptorFactory.HUE_RED);
        }
    }

    private void addPinLocation(LatLng latLng, String title, float color) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title).icon(BitmapDescriptorFactory.defaultMarker(color));
        mGoogleMap.addMarker(options);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isServicesAvailable()) {
            if (isGPSEnabled()) {
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
    public boolean isServicesAvailable() {
        Log.d(TAG, "isServicesAvailable: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(TripDetailActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            // everything is good and user can have google map
            Log.d(TAG, "isServicesAvailable: Google Play services is available");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // services not available but we can resolve it
            Log.d(TAG, "isServicesAvailable: services not available but we can resolve it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(TripDetailActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
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
    private void expandMapAnimation() {
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

    private void contractMapAnimation() {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        LatLng currentLocation = new LatLng(33.77576938714813, -84.39629573138684);
                        if (location != null) {
                            // Logic to handle location object
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            moveCamera(currentLocation, DEFAULT_MAP_ZOOM, "My location");
                        }
                    }
                });
        LatLng currentLocation = new LatLng(33.77576938714813, -84.39629573138684);
        fusedLocationClient.getLastLocation();
        moveCamera(currentLocation, DEFAULT_MAP_ZOOM, "My location");
        // Move the camera instantly to Sydney with a zoom of 15.
        for (MyPlace p: User.getUserInstance().getChosenTrip().getPlaces()) {
            Log.d(TAG, p.getTitle() + " - " + p.getLatitude() + " - " +p.getLongitude() + "\n");
            addPinLocation(new LatLng(p.getLatitude(), p.getLongitude()), p.getTitle(), BitmapDescriptorFactory.HUE_BLUE);
        }
    }

    // recyclerview
    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyPlace mPlace;
        private int mPosition;
        private TextView mPlaceName;
        private TextView mPlaceAddress;
        private TextView mEstimatedCost;

        public PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place, parent, false));
            itemView.setOnClickListener(this);

            mPlaceName = (TextView) itemView.findViewById(R.id.place_name);
            mPlaceAddress = (TextView) itemView.findViewById(R.id.place_address);
            mEstimatedCost = (TextView) itemView.findViewById(R.id.place_estimated_cost);
        }

        public void bind(MyPlace place, int position) {
            mPlace = place;
            mPlaceName.setText(mPlace.getTitle());
            mPlaceAddress.setText(mPlace.getAddress().toString());
            mEstimatedCost.setText("$" + mPlace.getCost());
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            User.getUserInstance().getChosenTrip().setChosen_place_position(mPosition);
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
            holder.bind(place, position);
        }

        @Override
        public int getItemCount() {
            return mPlaces.size();
        }
    }
}

