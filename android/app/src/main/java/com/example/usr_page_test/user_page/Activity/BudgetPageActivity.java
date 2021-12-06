package com.example.usr_page_test.user_page.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.usr_page_test.R;
import com.example.usr_page_test.user_page.Entity.ActivityItem;
import com.example.usr_page_test.user_page.Adapter.BaseSwipListAdapter;
import com.example.usr_page_test.user_page.Fragment.EnterDialogFragment;
import com.example.usr_page_test.user_page.Utils.NavigationUtils;
import com.example.usr_page_test.user_page.Utils.SwipeViewUtils;
import com.example.usr_page_test.user_page.Utils.SwipeViewUtils_accommodation;
import com.example.usr_page_test.user_page.VolleyUtils;

import java.util.ArrayList;
import java.util.List;

public class BudgetPageActivity  extends AppCompatActivity{
    /**
     * Created by lyd10892 on 2016/8/23.
     */
    private List<String> list;
    private List<ActivityItem> activityItemList;
    private List<ActivityItem> accomodationItemList;
    private List<ActivityItem> accomodationItemList1;
    private List<ActivityItem> placeItemList;
    private List<ActivityItem> activity0ItemList;
    private RecyclerView mRecyclerView;
    private SwipeMenuListView userEnterList;
    private VolleyUtils volleyUtils;
    private String[] res;
    private FragmentManager supportFragmentManager;
    private String[] user_enter;
    private int user_change_pos;
    // private userEnterAdapter uea;
    private RecyclerView accomodationRecyclerView;
    private Switch aSwitch1;
    //private BudgetListFragment budgetListFragment1;
    private Switch aSwitch2;
    public SwipeMenuListView other_activity_list;
    public SwipeMenuListView accommodation_list;
    private SwipeViewUtils swipeViewUtils1;
    private SwipeViewUtils swipeViewUtils_accommodation;
    BottomNavigationBar bottomNavigationBar;
    //List<activityItem> activityUtils = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        volleyUtils = new VolleyUtils();
        aSwitch1 = findViewById(R.id.switch1);
        aSwitch2 = findViewById(R.id.switch_accommodation);
//        FragmentManager fm = getSupportFragmentManager();
//        // for user enter list
//
//        BudgetListFragment bf = new BudgetListFragment();
//        Bundle bundle_other = new Bundle();
//        bundle_other.putInt("fragmentID", R.layout.my_fragment_layout);
//        bundle_other.putInt("listID", R.id.user_enter_list);
//        bf.setArguments(bundle_other);
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(bf,null);
//        ft.hide(bf);
//        //accommodation
//        BudgetListFragment_Accommodation bfa = new BudgetListFragment_Accommodation();
//        ft.add(bfa, "acco");
//        ft.commit();

        other_activity_list = findViewById(R.id.user_enter_list);
        accommodation_list = findViewById(R.id.accommodation_list);
        other_activity_list.setVisibility(View.GONE);
        accommodation_list.setVisibility(View.GONE);
        swipeViewUtils1 = new SwipeViewUtils();
        swipeViewUtils1.create_swipe_list(this,this,other_activity_list);
        swipeViewUtils_accommodation = new SwipeViewUtils_accommodation();
        swipeViewUtils_accommodation.create_swipe_list(this,this,accommodation_list);
        other_activity_list.setAdapter(swipeViewUtils1.userEnterAdapter);
        accommodation_list.setAdapter(swipeViewUtils_accommodation.userEnterAdapter);
        //View view = findViewById(R.id.);

//        View view1 = findViewById(R.id.my_fragment_accommodation);
//        view1.setVisibility(View.GONE);
        //ft.add(bf, "onelist");
        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //BudgetListFragment bf1 = (BudgetListFragment) fm.findFragmentByTag("onelist");
                if (isChecked) {
                    other_activity_list.setVisibility(View.VISIBLE);
                }
                else {
                    other_activity_list.setVisibility(View.GONE);
                }
            }
        });
        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //BudgetListFragment bf1 = (BudgetListFragment) fm.findFragmentByTag("onelist");
                if (isChecked) {
                    accommodation_list.setVisibility(View.VISIBLE);
                }
                else {
                    accommodation_list.setVisibility(View.GONE);
                }
            }
        });

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        NavigationUtils navigationUtils = new NavigationUtils();
        navigationUtils.init_bar(bottomNavigationBar, this);
//        FragmentTransaction ft = fm.beginTransaction();
//        budgetListFragment1 = new BudgetListFragment();
//        //budgetListFragment1.setComponents(R.id.fragment_other,R.id.switch1,R.id.addButton1);
//        ft.show(budgetListFragment1);
//        ft.commit();

//        budgetListFragment1.setOnButtonClick(new BudgetListFragment.OnOpenButtonClick() {
//                                                 @Override
//                                                 public void onOpenClick(View view) {
//                                                    fragmentTransaction.show(budgetListFragment1);
//                                                 }
//                                             },
//                new BudgetListFragment.OnCloseButtonClick() {
//                    @Override
//                    public void onCloseClick(View view) {
//                        fragmentTransaction.hide(budgetListFragment1);
//                    }
//                });
//        supportFragmentManager = getSupportFragmentManager();
//        //initView();
//        accomodationRecyclerView = findViewById(R.id.accomodation_list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        accomodationRecyclerView.setLayoutManager(layoutManager);
//        init_accomodation_list();
        //init_user_enter_list();
//        list = new ArrayList<String>(Arrays.asList(new String[]{"abc", "def", "kill"}));
//        initView_user_enter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        swipeViewUtils1.get_list();
        swipeViewUtils_accommodation.get_list();
        //FireStoreUtil.modifyFireStore("BudgetPageActivity", Calendar.getInstance().getTime().toString());
    }
//    public void init_accomodation_list() {
//        accomodationItemList = new ArrayList<>();
//        ActivityItem a1 = new ActivityItem("movie~0","30","Mary");
//        ActivityItem a2 = new ActivityItem("Sov0","40","Mary");
//        ActivityItem a3 = new ActivityItem("Bike0","10","Peter");
//        accomodationItemList.add(a1);
//        accomodationItemList.add(a2);
//        accomodationItemList.add(a3);
//        ItemRecyclerViewAdapter itemRecyclerViewAdapter1 = new ItemRecyclerViewAdapter(accomodationItemList, R.layout.activity_desc_item);
//        accomodationRecyclerView.setAdapter(itemRecyclerViewAdapter1);
//        aSwitch1 = findViewById(R.id.switch1);
//        accomodationItemList1 = new ArrayList<>();
//        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    accomodationItemList1 = new ArrayList<>(accomodationItemList);
//                    accomodationItemList.clear();
//                    itemRecyclerViewAdapter1.notifyDataSetChanged();
//                }
//                else {
//                    if (accomodationItemList1.size() > 0)
//                        accomodationItemList.addAll(accomodationItemList1);
//                    itemRecyclerViewAdapter1.notifyDataSetChanged();
//                }
//            }
//        });
//
//    }





//    private class userEnterAdapter extends BaseSwipListAdapter {
//
//        @Override
//        public int getCount() {
//            return activityItemList.size();
//        }
//
//        @Override
//        public ActivityItem getItem(int position) {
//            return activityItemList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = View.inflate(getApplicationContext(),
//                        R.layout.activity_desc_item, null);
//                new ViewHolder(convertView);
//            }
//            ViewHolder holder = (ViewHolder) convertView.getTag();
//            ActivityItem item = getItem(position);
//            //holder.iv_icon.setImageResource(R.drawable.ic_launcher);
//            holder.tv_name.setText(item.getActivityName());
//            holder.itemCost.setText(item.getCost());
//            holder.itemPayer.setText(item.getPayer());
////            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(MyActivity.this, "点击了图标", Toast.LENGTH_SHORT).show();
////                }
////            });
//            holder.tv_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Toast.makeText(MyActivity.this, "点击了文字", Toast.LENGTH_SHORT).show();
//                }
//            });
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView itemCost;
//            TextView tv_name;
//            TextView itemPayer;
//
//            public ViewHolder(View view) {
//                //iv_icon = (TextView) view.findViewById(R.id.activi);
//                tv_name = (TextView) view.findViewById(R.id.item_id);
//                itemCost = view.findViewById(R.id.item_cost);
//                itemPayer = view.findViewById(R.id.item_payer);
//
//                view.setTag(this);
//            }
//        }
//
//        @Override
//        public boolean getSwipEnableByPosition(int position) {        //设置条目是否可以滑动
////            if (position % 2 == 0) {
////                return false;
////            }
//            return true;
//        }
//
//    }
//
//    private int dp2px(int dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                getResources().getDisplayMetrics());
//    }


//    private void initView_user_enter() {
//        //userEnterList = findViewById(R.id.user_enter_list1);
//        uea = new userEnterAdapter();
//        userEnterList.setAdapter(uea);
//
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                openItem.setWidth(dp2px(90));
//                openItem.setIcon(R.drawable.baseline_edit_note_red_100_24dp);
//
//                // set item width
////                openItem.setWidth(dp2px(90));
////                // set item title
////                openItem.setTitle("Edit");
////                // set item title fontsize
////                openItem.setTitleSize(15);
////                // set item title font color
////                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);
//
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(dp2px(90));
//                // set a icon
//                deleteItem.setIcon(R.drawable.baseline_delete_red_100_18dp);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };
//
//// set creator
//        userEnterList.setMenuCreator(creator);
//
//        userEnterList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        // open a fragment for collecting data
//                        // TODO : build the diaglog for editing
//                        Context context = BudgetPageActivity.this;
//                        EnterDialogFragment dialog = new EnterDialogFragment();
//                        dialog.show(supportFragmentManager, "enter item");
//                        BudgetPageActivity.this.user_change_pos = position;
//                        break;
//                    case 1:
//                        // delete
//                        //volleyUtils.post(list.get(position), String.class, res);
//                        activityItemList.remove(position);
//                        uea.notifyDataSetChanged();
//                        break;
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });
//
//
//    }

    //public class SmartTvControllerFragment extends Fragment implements View.OnClickListener





}
