package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.MyPlace;
import com.example.monarch.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaceDetailActivity extends AppCompatActivity {
    private static final String TAG = "Place detail";

    private TextView mPlaceName;
    private EditText mTrueCostEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        MyPlace place = User.getUserInstance().getChosenTrip().getChosenPlace();
        mPlaceName = findViewById(R.id.place_name);
        mTrueCostEditText = findViewById(R.id.true_cost_edit_text);

        mPlaceName.setText(place.getTitle());

        mTrueCostEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    JSONObject body = null;
                    try {
                        JSONObject data = new JSONObject();
                        data.put("google_id", place.getGoogle_id());
                        String doubleStr = ((EditText) v).getText().toString();
                        data.put("cost", Double.parseDouble(doubleStr));
                        body = new JSONObject().put("true_cost", data);
                        Log.d(TAG, "add true cost to place" + body.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url = getResources().getString(R.string.back_end_base) + "/trips/" + User.getUserInstance().getChosenTrip().getId() + "/true_costs.json";

                    JsonObjectRequest updateTrueCost = new JsonObjectRequest
                            (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d(TAG, "Update true cost of a place successfully");
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(updateTrueCost);
                }
            }
        });
    }
}