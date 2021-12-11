package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.MyPlace;
import com.example.monarch.data.Trip;
import com.example.monarch.data.User;
import com.example.monarch.util.FirestoreUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private PlaceAdapder mAdapter;
    private TextView mPaidAmountTextView;

    private double mAmountPaid;
    private String TAG = "ReceiptPage";
    private List<MyPlace> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        places = new ArrayList<>();
        mPaidAmountTextView = findViewById(R.id.paid_amount);
        // mPaidAmountTextView.setText(calculateTotalTrueCost((ArrayList<MyPlace>) places));
        mRecyclerview = findViewById(R.id.receipt_item_recycler_view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlaceAdapder(this, (ArrayList<MyPlace>) places);
        mRecyclerview.setAdapter(mAdapter);


        FirestoreUtils firestoreUtils = new FirestoreUtils();
        firestoreUtils.modifyFireStore("ReceiptActivity", Calendar.getInstance().getTime().toString());
    }

    @Override
    protected void onStart() {

        super.onStart();
        get_data();
    }

    private void get_data() {
        String url = getResources().getString(R.string.back_end_base) + "/trips/" + User.getUserInstance().getChosenTrip().getId() + getResources().getString(R.string.get_all_places_endpoint);
        Gson gson = new GsonBuilder().create();
        JsonArrayRequest TripDetail = new JsonArrayRequest
                (Request.Method.GET, url,new JSONArray(), new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Add place to a trip successfully");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                places.add(gson.fromJson(String.valueOf(response.getJSONObject(i)),MyPlace.class));

                            }
                            mAdapter.notifyDataSetChanged();
                            mPaidAmountTextView.setText(calculateTotalTrueCost((ArrayList<MyPlace>) places));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(TripDetail);
    }


    public String calculateTotalTrueCost(ArrayList<MyPlace> places) {
        double total = 0;
        for (MyPlace p: places) {
            total += p.getTrue_cost();
        }
        return Double.toString(total);
    }

    // recyclerview
    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyPlace mPlace;
        private int mPosition;
        private TextView mPlaceName;
        private TextView mPlaceAddress;
        private TextView mTrueCost;

        public PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place_receipt, parent, false));
            itemView.setOnClickListener(this);

            mPlaceName = (TextView) itemView.findViewById(R.id.place_name_receipt);
            mPlaceAddress = (TextView) itemView.findViewById(R.id.place_address_receipt);
            mTrueCost = (TextView) itemView.findViewById(R.id.true_cost_receipt);
        }

        public void bind(MyPlace place, int position) {
            mPlace = place;
            mPlaceName.setText(mPlace.getTitle());
            mPlaceAddress.setText(mPlace.getAddress().toString());
            mTrueCost.setText("$" + mPlace.getTrue_cost());
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
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