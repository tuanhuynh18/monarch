package com.example.monarch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.MyPlace;
import com.example.monarch.data.Trip;
import com.example.monarch.data.User;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UserPageActivity extends AppCompatActivity {
    private static final Calendar myCalendar = Calendar.getInstance();
    private static final String TAG = "UserPage";
    // widgets
    private EditText mStartDateEditText;
    private EditText mEndDateEditText;
    private Button mCreateTripButton;
    private EditText mTripNameEditText;
    private EditText mBudgetEditText;
    private TripAdapder mAdapter;
    RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        MaterialDatePicker datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

        // wire widgets
        mStartDateEditText = findViewById(R.id.input_start_date_edit_text);
        mEndDateEditText = findViewById(R.id.input_end_date_edit_text);
        mCreateTripButton = findViewById(R.id.create_trip_button);
        mTripNameEditText = findViewById(R.id.trip_name_edit_text);
        mBudgetEditText = findViewById(R.id.budget_edit_text);

        // create date picker
        DatePickerDialog startDatePickerDialog = createDatePickerWithEditText(mStartDateEditText);
        DatePickerDialog endDatePickerDialog = createDatePickerWithEditText(mEndDateEditText);

        mStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startDatePickerDialog.show();
            }
        });
        mEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                endDatePickerDialog.show();
            }
        });

        // create trip button listener
        mCreateTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trip_name = mTripNameEditText.getText().toString();
                String start_date = mStartDateEditText.getText().toString();
                String end_date = mEndDateEditText.getText().toString();
                int budget = Integer.parseInt(mBudgetEditText.getText().toString());
                Trip new_trip = new Trip(trip_name, start_date, end_date, budget);

                Gson gson = new Gson();
                JSONObject body = null;
                try {
                    body = new JSONObject(gson.toJson(new_trip));
                    Log.d(TAG, body.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = getResources().getString(R.string.back_end_base) + getResources().getString(R.string.get_all_trip_endpoint);

                JsonObjectRequest loginRequest = new JsonObjectRequest
                        (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "Add trip successfully");
                                User.getUserInstance().getTrips().add(new_trip);
                                User.getUserInstance().setChosen_trip_position(User.getUserInstance().getTrips().size()-1);
                                mAdapter = new TripAdapder(getApplicationContext(), User.getUserInstance().getTrips());
                                mRecyclerView.setAdapter(mAdapter);
                                Intent intent = new Intent(getApplicationContext(), TripDetailActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);
            }
        });

        // fetch itinerary
        String url = getResources().getString(R.string.back_end_base) + getResources().getString(R.string.get_all_trip_endpoint);
        JsonArrayRequest getTripsRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Fetch trip data successfully" + response.toString());
                        ArrayList<Trip> trips= new ArrayList<>();
                        Gson gson = new Gson();
                        trips = gson.fromJson(response.toString(), new TypeToken<ArrayList<Trip>>(){}.getType());
                        User.getUserInstance().setTrips(trips);
                        mAdapter = new TripAdapder(getApplicationContext(), User.getUserInstance().getTrips());
                        mRecyclerView.setAdapter(mAdapter);
                        Log.d(TAG, "Fetch trip data successfully" + trips.size());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        try {
            Log.d(TAG, "Request header" + getTripsRequest.getHeaders().toString() + getTripsRequest.toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(getTripsRequest);

        // recyclerview
        mRecyclerView = findViewById(R.id.itinerary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TripAdapder(this, User.getUserInstance().getTrips());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateLabel(EditText editText) {
        String myFormat = "yyyy-mm-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private DatePickerDialog createDatePickerWithEditText(EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }
        });
        return datePickerDialog;
    }

    // recyclerview
    private class TripHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Trip mTrip;
        private int mPosition;
        private TextView mCity;
        private TextView mDate;
        private TextView mBudget;

        public TripHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trip, parent, false));
            itemView.setOnClickListener(this);

            mCity = (TextView) itemView.findViewById(R.id.trip_city);
            mDate = (TextView) itemView.findViewById(R.id.trip_date);
            mBudget = (TextView) itemView.findViewById(R.id.trip_budget);
        }

        public void bind(Trip trip, int position) {
            mTrip = trip;
            mCity.setText(mTrip.getName());
            mDate.setText(mTrip.getStartEndDate());
            mBudget.setText("$" + mTrip.getBudget());
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            String url = getResources().getString(R.string.back_end_base) + "/trips/" + mTrip.getId() + "/places.json";
            JsonArrayRequest getTripsRequest = new JsonArrayRequest
                    (url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, "Fetch places data successfully" + response.toString());
                            ArrayList<MyPlace> places = new ArrayList<>();
                            Gson gson = new Gson();
                            places = gson.fromJson(response.toString(), new TypeToken<ArrayList<MyPlace>>(){}.getType());
                            mTrip.setPlaces(places);
                            // go to trip detail activity
                            User.getUserInstance().setChosen_trip_position(mPosition);
                            Intent intent = new Intent(getApplicationContext(), TripDetailActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            try {
                Log.d(TAG, "Request header" + getTripsRequest.getHeaders().toString() + getTripsRequest.toString());
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }
            RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(getTripsRequest);
        }
    }

    private class TripAdapder extends RecyclerView.Adapter<TripHolder> {
        private ArrayList<Trip> mTrips;
        private Context mContext;
        public TripAdapder(Context context, ArrayList<Trip> trips) {
            mTrips = trips;
            mContext = context;
        }

        @Override
        public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            return new TripHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TripHolder holder, int position) {
            Trip trip = mTrips.get(position);
            holder.bind(trip, position);
        }

        @Override
        public int getItemCount() {
            return mTrips.size();
        }
    }
}