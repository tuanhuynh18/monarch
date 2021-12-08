package com.example.monarch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.User;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UserPageActivity extends AppCompatActivity {
    private static final Calendar myCalendar = Calendar.getInstance();
    private static final String TAG = "UserPage";

    private EditText mStartDateEditText;
    private EditText mEndDateEditText;
    private Button mCreateTripButton;
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
                Intent intent = new Intent(getApplicationContext(), TripDetailActivity.class);
                startActivity(intent);
            }
        });

        // fetch itinerary
        String url = getResources().getString(R.string.back_end_base) + getResources().getString(R.string.get_all_places_endpoint);
        JsonObjectRequest getTripsRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Fetch trip data successfully" + response.toString());
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

    private void updateLabel(EditText editText) {
        String myFormat = "MM/dd/yy"; //In which you need put here
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
}