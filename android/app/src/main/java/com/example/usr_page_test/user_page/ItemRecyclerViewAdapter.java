package com.example.usr_page_test.user_page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.usr_page_test.R;
import com.example.usr_page_test.user_page.Entity.ActivityItem;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private List<ActivityItem> mData;
    private int layout;
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;


    // data is passed into the constructor
    public ItemRecyclerViewAdapter(List<ActivityItem> data, int layoutID) {
        mData = data;
        layout = layoutID;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    //here I need to know what backend send to me
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Itinerary itinerary = mData.get(position);
        ActivityItem activityItem = mData.get(position);
        //Log.e("holder location", itinerary.getLocation());
        holder.activityName.setText(activityItem.getActivityName());
        holder.activityCost.setText(activityItem.getCost());
        holder.activityPayer.setText(activityItem.getPayer());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityName;
        TextView activityCost;
        TextView activityPayer;


        ViewHolder(View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.item_id);
            activityCost = itemView.findViewById(R.id.item_cost);
            activityPayer = itemView.findViewById(R.id.item_payer);
        }

    }

    // convenience method for getting data at click position
    ActivityItem getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught

}


