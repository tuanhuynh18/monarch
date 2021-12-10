package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monarch.data.MyPlace;
import com.example.monarch.data.User;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private PlaceAdapder mAdapter;
    private TextView mPaidAmountTextView;

    private double mAmountPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        mPaidAmountTextView = findViewById(R.id.paid_amount);
        mPaidAmountTextView.setText(calculateTotalTrueCost(User.getUserInstance().getChosenTrip().getPlaces()));
        mRecyclerview = findViewById(R.id.receipt_item_recycler_view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlaceAdapder(this, User.getUserInstance().getChosenTrip().getPlaces());
        mRecyclerview.setAdapter(mAdapter);
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