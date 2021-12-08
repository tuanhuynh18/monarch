package com.example.monarch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UserPageActivity extends AppCompatActivity {
    private static final Calendar myCalendar = Calendar.getInstance();

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