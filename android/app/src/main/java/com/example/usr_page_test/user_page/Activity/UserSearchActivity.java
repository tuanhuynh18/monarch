package com.example.usr_page_test.user_page.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.usr_page_test.R;
import com.example.usr_page_test.user_page.Entity.Itinerary;
import com.example.usr_page_test.user_page.Utils.MyLocation;
import com.example.usr_page_test.user_page.Utils.NavigationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserSearchActivity extends AppCompatActivity {
    private EditText location;
    private EditText budget;
    private EditText startDate;
    private EditText endDate;
    private RecyclerView itinerary;
    private FloatingActionButton newSearchBotton;
    private Button logOut;
    private Button locate;
    MyRecyclerViewAdapter adapter;
    List<Itinerary> itineraries;
    int haveServey = 0;
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        haveServey = 0;
        //do survey only once
        if(haveServey == 0)
            showList();


        itineraries = new ArrayList<>();
        setContentView(R.layout.user_dashboard);
        location = findViewById(R.id.location);
        budget = findViewById(R.id.budget);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        itinerary = findViewById(R.id.itineraryRecyclerView);
        newSearchBotton = findViewById(R.id.addNSearch);

        adapter = new MyRecyclerViewAdapter( itineraries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itinerary.setLayoutManager(layoutManager);
        //adapter.setClickListener(this);
        itinerary.setAdapter(adapter);
        itinerary.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        newSearchBotton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSearch();
            }
        });
        logOut = findViewById(R.id.logOutButton);
        locate = findViewById(R.id.locate);
        Context context = this;

        locate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLocation myLocation = new MyLocation();
                myLocation.basePermissionX(UserSearchActivity.this);
                MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                    @Override
                    public void gotLocation(Location location1){
                        //Got the location!
                        Geocoder geocoder;
                        Address addresses = new Address(null);
                        geocoder = new Geocoder(UserSearchActivity.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1).get(0); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String address = addresses.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.getLocality();
                        String state = addresses.getAdminArea();
                        String country = addresses.getCountryName();
                        String postalCode = addresses.getPostalCode();
                        String knownName = addresses.getFeatureName(); // Only if available else return NULL
                        location.setText(address.toString());
                    }
                };
                //MyLocation myLocation = new MyLocation();
                myLocation.getLocation(context, locationResult);

            }
        });

        logOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                //Intent intent = new Intent(this, LoginActivity.class);
                //startActivity(intent);
            }
        });

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        NavigationUtils navigationUtils = new NavigationUtils();
        navigationUtils.init_bar(bottomNavigationBar, this);






    }

    @Override
    protected void onStart() {

        super.onStart();
        // GET personal itineraries
        try {
            getItinerary();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * User Survey dialog
     */
    int checkedItem = 0;
    private void showList() {
        //默认选中的item
        final String[] items = {"High School Student", "College Student", "I'm working"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("User Survey")
                .setCancelable(false)
                .setSingleChoiceItems(items,checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkedItem=i;
                        //Toast.makeText(UserSearchActivity.this, "你点击的内容为： " + items[i], Toast.LENGTH_LONG).show();
                    }
                });
        builder.setPositiveButton("Go!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showThanks();
                haveServey++;
                Log.e("type", items[checkedItem].toString());
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showThanks();
                    }
                });
        builder.create().show();
    }
    private void showThanks(){
        final String s ="Thank you! \n Enjoy your trip!";
        Toast.makeText(this, s, Toast.LENGTH_LONG);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.ic_launcher)
//                .setTitle("dialog choice")
//                .setCancelable(false)
//                .setMessage(s);
//        builder.setPositiveButton("go", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        builder.create().show();
    }



    private void getItinerary() throws JSONException {

        Bundle b = getIntent().getExtras();
        //String userId=b.getString("userId");
        String userId = "yingyingying";

        String url = "https://yata-monarch.herokuapp.com/trips.json";
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);//need to tell backend this
        //searchResult[] response_for_next = {new searchReasult()};
        List<JSONObject> searchResults = new ArrayList<>();


//        JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                try {
//
//                    JSONArray ja = response;//need change
//
//
//                    for (int i = 0; i < ja.length(); i++) {
//
//                        JSONObject jsonObject =  ja.getJSONObject(i);
//                        Log.e("Detail"+i,jsonObject.toString());
//                        searchResults.add(jsonObject);
//                    }
//                    Log.e("suncess",searchResults.toString());
//
//
//
//                    Gson gson = new GsonBuilder().create();
//
//                    JSONObject a1 = new JSONObject();
//                    a1.put("location","boston");
//                    a1.put("startDate","03/22/1999");
//                    a1.put("endDate","04/10/2020");
//                    JSONObject a2 = new JSONObject();
//                    a2.put("location","new york");
//                    a2.put("startDate","03/22/1939");
//                    a2.put("endDate","02/10/2010");
//                    searchResults.add(a1);
//                    searchResults.add(a2);
//                    for (JSONObject item : searchResults) {
//                        itineraries.add(gson.fromJson(String.valueOf(item),Itinerary.class));
//                    }
//                    for (Itinerary it : itineraries) {
//                        String tmp;
//                        tmp = it.getStartDate();
//                        if (tmp != null) {
//                            it.setStartDate(tmp.substring(0,10));
//                        }
//                        else continue;
//                        tmp = it.getEndDate();
//                        if (tmp != null) {
//                            it.setEndDate(tmp.substring(0,10));
//                        }
//                    }
//
//                    adapter.notifyDataSetChanged();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley", "error");
//
//                    }
//                }
//        );
        JSONObject a1 = new JSONObject();
        a1.put("location","boston");
        a1.put("startDate","03/22/1999");
        a1.put("endDate","04/10/2020");
        JSONObject a2 = new JSONObject();
        a2.put("location","new york");
        a2.put("startDate","03/22/1939");
        a2.put("endDate","02/10/2010");
        searchResults.add(a1);
        searchResults.add(a2);
        Gson gson = new Gson();
        for (JSONObject item : searchResults) {
            itineraries.add(gson.fromJson(String.valueOf(item),Itinerary.class));
        }

        adapter.notifyDataSetChanged();
        Log.e("data",itineraries.toString());
        Log.e("message","I'm here");
//        RequestQueue requestQueue = Volley.newRequestQueue(UserSearchActivity.this);
//        requestQueue.add(jor);
    }



    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<Itinerary> mData;
        private LayoutInflater mInflater;
        //private ItemClickListener mClickListener;


        // data is passed into the constructor
        MyRecyclerViewAdapter(List<Itinerary> data) {
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_item, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        //here I need to know what backend send to me
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Itinerary itinerary = itineraries.get(position);
            //Log.e("holder location", itinerary.getLocation());
            holder.location.setText(itinerary.getLocation());
            holder.duration.setText(itinerary.getStartDate() + "-" + itinerary.getEndDate());
            holder.item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserSearchActivity.this, BudgetPageActivity.class);
                    startActivity(intent);
                }
            });
        }


        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
            TextView location;
            TextView duration;
            Button search;
            LinearLayout item;


            ViewHolder(View itemView) {
                super(itemView);
                location = itemView.findViewById(R.id.itemLocation);
                duration = itemView.findViewById(R.id.itemDuration);
                item = itemView.findViewById(R.id.itineraryItem);
                //search = itemView.findViewById(R.id.unitSearch);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.location.getText(), Toast.LENGTH_SHORT).show();
            }
        }

        // convenience method for getting data at click position
        Itinerary getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught

    }


    //main button triggered
    private void attemptSearch() {
        String locationVal = location.getText().toString();
        String bugetVal = budget.getText().toString();
        String startDateVal = startDate.getText().toString();
        String endDateVal = endDate.getText().toString();

        //public PostObjectRequest(String url, Map<String,String> params,Type type, ResponseListener lis


//        Intent intent = new Intent(this, SecondActivity.class);
//        intent.putExtra("search result", searchResults.toString());
//        startActivity(intent);
    }

}


