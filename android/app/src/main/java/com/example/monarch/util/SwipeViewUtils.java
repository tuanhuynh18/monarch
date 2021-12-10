package com.example.monarch.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.R;
//import com.example.monarch.Activity.BudgetPageActivity;
//import com.example.monarch.Adapter.BaseSwipListAdapter;
//import com.example.monarch.Entity.ActivityItem;
//import com.example.monarch.Fragment.EnterDialogFragment;
//import com.google.android.gms.tasks.OnCompleteListener;
import com.example.monarch.TripDetailActivity;
import com.example.monarch.data.MyPlace;
import com.example.monarch.data.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SwipeViewUtils{
    private static final String TAG = "TripDetail";
    private Switch btn;
    private TextView te;
    private Button addBtn;
    //    private  OnOpenButtonClick onOpenButtonClick;
//    private OnCloseButtonClick onCloseButtonClick;
    private int fragmentId;
    private View listId;
    private int addButtonId;
    public SwipeMenuListView swipeMenuView;
    public List<MyPlace> activityItemList;
    public UserEnterAdapter userEnterAdapter;
    public int user_change_pos;
    public String[] user_enter;
    public Context pass_to_listener;
    public Set<String> got;
//    private FirebaseFirestore db;
    private TripDetailActivity ba;
    private List<JSONObject> searchResults;
    List<Integer> ids;

//    public void setComponents(int fragmentId, int listId) {
//        this.fragmentId = fragmentId;
//        this.listId = listId;
//        //this.addButtonId = addButtonId;
//    }

//    @Override
//    public void onInputComplete(String item, String cost) {
//        user_enter = new String[]{item, cost};
//        String tmpId = activityItemList.get(user_change_pos).getActivityName();
//        edit_item(tmpId, user_enter[0], user_enter[1]);
//        activityItemList.get(user_change_pos).setActivityName(user_enter[0]);
//        activityItemList.get(user_change_pos).setCost(user_enter[1]);
//        //list.set(user_change_pos,user_enter[0]);
//        userEnterAdapter.notifyDataSetChanged();
//        //TODO: send this to backend
//    }


    public void create_swipe_list(Context context, TripDetailActivity ba, SwipeMenuListView view) {
        swipeMenuView =  view;
        create_bean(context, ba);
    }

    public void create_bean(Context context, TripDetailActivity ba){
        pass_to_listener = context;
        activityItemList = new ArrayList<>();
        init_list();
        userEnterAdapter = new UserEnterAdapter();
        swipeMenuView.setAdapter(userEnterAdapter);
        this.ba = ba;
        got = new HashSet<>();
        ids = new ArrayList<>();
        searchResults = new ArrayList<>();
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                pass_to_listener.getResources().getDisplayMetrics());
    }
    public void get_list() {
        activityItemList = new ArrayList<>();
        String url = ba.getResources().getString(R.string.back_end_base) + "/trips/" + User.getUserInstance().getChosenTrip().getId() + ba.getResources().getString(R.string.get_all_places_endpoint);
        Gson gson = new GsonBuilder().create();
        Log.e(TAG, "heeeeere");
        JsonArrayRequest TripDetail = new JsonArrayRequest
                (Request.Method.GET, url,new JSONArray(), new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Add place to a trip successfully");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                activityItemList.add(gson.fromJson(String.valueOf(response.getJSONObject(i)),MyPlace.class));
                                ids.add((Integer) response.getJSONObject(i).get("id"));

                            }
                            Log.e(TAG, response.toString());
                            userEnterAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueueSingleton.getInstance(ba).addToRequestQueue(TripDetail);
    }



//    public void get_list() {
//        //activityItemList = new ArrayList<>();
//        db = FirebaseFirestore.getInstance();
//        // TODO: multi thread
//        db.collection("OtherActivity")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (!got.contains(document.get("ID"))) {
//                                    ActivityItem ai = new ActivityItem((String)document.get("ID"), (String)document.get("cost"), (String)document.get("payer"));
//                                    activityItemList.add(ai);
//                                    got.add((String) document.get("ID"));
//                                }
//                                userEnterAdapter.notifyDataSetChanged();
//                                Log.d("fs", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d("fs", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//    }

    private void init_list() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        pass_to_listener);
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(dp2px(90));
                openItem.setIcon(R.drawable.common_google_signin_btn_icon_dark);

                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Edit");
//                // set item title fontsize
//                openItem.setTitleSize(15);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        pass_to_listener);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.common_google_signin_btn_icon_dark);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        swipeMenuView.setMenuCreator(creator);
        swipeMenuView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
//                        // open a fragment for collecting data
//                        // TODO : build the diaglog for editing
//                        Context context = pass_to_listener;
//                        EnterDialogFragment dialog = new EnterDialogFragment();
//                        dialog.setContext((EnterDialogFragment.InputListener) SwipeViewUtils.this);
//                        //what if the budgetfragment has not been sent before dialog onclick created
//                        dialog.show(ba.getSupportFragmentManager(), "enter item");
//                        user_change_pos = position;
//                        break;
                    case 1:
                        // delete
                        //volleyUtils.post(list.get(position), String.class, res);
                        remove_item(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
//    public void edit_item(String id, String newId, String newCost) {
//        Query query = db.collection("OtherActivity").whereEqualTo("ID", id);
//        query.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("fs delete", document.getId() + " => " + document.getData());
//                                String idEdited = document.getId();
//                                db.collection("OtherActivity").document(idEdited)
//                                        .update("ID", newId,"cost", newCost)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d("fs edit", "DocumentSnapshot successfully edited!");
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.w("fs edit", "Error editing document", e);
//                                            }
//                                        });
//                            }
//                        } else {
//                            Log.d("fs edit", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
    public void remove_item(int position) {
        String url = ba.getResources().getString(R.string.back_end_base) + "/trips/" + User.getUserInstance().getChosenTrip().getId() + "/places"+"/"+ids.get(position);
        Log.e(TAG, url);
        Gson gson = new GsonBuilder().create();
        Log.e(TAG, "heeeeere");
        activityItemList.remove(position);
        userEnterAdapter.notifyDataSetChanged();
        JsonObjectRequest TripDetail = new JsonObjectRequest
                (Request.Method.DELETE, url,new JSONObject(),new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "delete place success");
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueueSingleton.getInstance(ba).addToRequestQueue(TripDetail);
    }
//    public void remove_item(int position) {
//        Query query = db.collection("OtherActivity").whereEqualTo("ID", activityItemList.get(position).getActivityName());
//        query.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("fs delete", document.getId() + " => " + document.getData());
//                                String idDelete = document.getId();
//                                db.collection("OtherActivity").document(idDelete)
//                                        .delete()
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d("fs delete", "DocumentSnapshot successfully deleted!");
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.w("fs delete", "Error deleting document", e);
//                                            }
//                                        });
//                            }
//                        } else {
//                            Log.d("fs delete", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        activityItemList.remove(position);
//        userEnterAdapter.notifyDataSetChanged();
//    }
    public class UserEnterAdapter extends BaseSwipListAdapter {

        @Override
        public int getCount() {
            return activityItemList.size();
        }

        @Override
        public MyPlace getItem(int position) {
            return activityItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(pass_to_listener,
                        R.layout.list_item_place, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            MyPlace place = getItem(position);
            //holder.iv_icon.setImageResource(R.drawable.ic_launcher);
            holder.mPlace = place;
            holder.mPlaceName.setText(place.getTitle());
            holder.mPlaceAddress.setText(place.getAddress().toString());
            holder.mEstimatedCost.setText("$" + place.getCost());
            holder.mPosition = position;
//            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(MyActivity.this, "点击了图标", Toast.LENGTH_SHORT).show();
//                }
//            });

            return convertView;
        }

        class ViewHolder {
            private MyPlace mPlace;
            private int mPosition;
            private TextView mPlaceName;
            private TextView mPlaceAddress;
            private TextView mEstimatedCost;

            public ViewHolder(View view) {
                //iv_icon = (TextView) view.findViewById(R.id.activi);
                mPlaceName = (TextView) view.findViewById(R.id.place_name);
                mPlaceAddress = (TextView) view.findViewById(R.id.place_address);
                mEstimatedCost = (TextView) view.findViewById(R.id.place_estimated_cost);

                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {        //设置条目是否可以滑动
//            if (position % 2 == 0) {
//                return false;
//            }
            return true;
        }

    }


}
