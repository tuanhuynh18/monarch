//package com.example.usr_page_test.user_page;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.usr_page_test.R;
//
//import java.util.ArrayList;
//
//public class BudgetListFragment_Accommodation extends BudgetListFragment{
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_accommdation, container, false);
//        this.swipeMenuView = view.findViewById(R.id.accommodation_list);
//        create_bean();
//        return  view;
//    }
//
//    @Override
//    public void get_list() {
//        activityItemList = new ArrayList<>();
//        ActivityItem a1 = new ActivityItem("peach Inn","30","Mary");
//        ActivityItem a2 = new ActivityItem("Hilton","40","Mary");
//        ActivityItem a3 = new ActivityItem("Beijing Hotel","10","Peter");
//        activityItemList.add(a1);
//        activityItemList.add(a2);
//        activityItemList.add(a3);
//    }
//
//
//    @Override
//    public void remove_item(int position) {
//        activityItemList.remove(position);
//        userEnterAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onInputComplete(String item, String cost) {
//        user_enter = new String[]{item, cost};
//        activityItemList.get(user_change_pos).setActivityName(user_enter[0]);
//        activityItemList.get(user_change_pos).setCost(user_enter[1]);
//        //list.set(user_change_pos,user_enter[0]);
//        userEnterAdapter.notifyDataSetChanged();
//        //TODO: send this to backend
//    }
//}
