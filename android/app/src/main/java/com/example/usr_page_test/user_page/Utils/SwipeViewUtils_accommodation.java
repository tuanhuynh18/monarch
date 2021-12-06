package com.example.usr_page_test.user_page.Utils;

import com.example.usr_page_test.user_page.Entity.ActivityItem;

import java.util.ArrayList;

public class SwipeViewUtils_accommodation extends SwipeViewUtils {
    @Override
    public void get_list() {
        activityItemList = new ArrayList<>();
        ActivityItem a1 = new ActivityItem("peach Inn","30","Mary");
        ActivityItem a2 = new ActivityItem("Hilton","40","Mary");
        ActivityItem a3 = new ActivityItem("Beijing Hotel","10","Peter");
        activityItemList.add(a1);
        activityItemList.add(a2);
        activityItemList.add(a3);
    }

    @Override
    public void remove_item(int position) {
        activityItemList.remove(position);
        userEnterAdapter.notifyDataSetChanged();
    }

    @Override
    public void onInputComplete(String item, String cost) {
        user_enter = new String[]{item, cost};
        activityItemList.get(user_change_pos).setActivityName(user_enter[0]);
        activityItemList.get(user_change_pos).setCost(user_enter[1]);
        //list.set(user_change_pos,user_enter[0]);
        userEnterAdapter.notifyDataSetChanged();
        //TODO: send this to backend
    }
}
