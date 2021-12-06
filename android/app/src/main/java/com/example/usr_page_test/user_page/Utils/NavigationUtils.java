package com.example.usr_page_test.user_page.Utils;

import android.content.Context;
import android.content.Intent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.usr_page_test.R;
import com.example.usr_page_test.user_page.Activity.BudgetPageActivity;
import com.example.usr_page_test.user_page.Activity.MainActivity;

public class NavigationUtils {
    public void init_bar(BottomNavigationBar bottomNavigationBar, Context context) {
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 0) {
                    Intent intent= new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
                if (position == 1) {
                    Intent intent= new Intent(context, BudgetPageActivity.class);
                    context.startActivity(intent);
                }

            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.black);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, "Home").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.baseline_edit_note_red_100_24dp, "Recipt").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.baseline_edit_note_red_100_24dp, "whatever").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.baseline_edit_note_red_100_24dp, "whatever").setActiveColorResource(R.color.white))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}
