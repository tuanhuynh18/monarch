package com.example.monarch.user_page;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.monarch.R
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSearchActivity extends Activity {
    private EditText location;
    private EditText budget;
    private EditText startDate;
    private EditText endDate;
    private RecyclerView itinerary;
    private FloatingActionButton newSearchBotton;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dashboard);
        location = findViewById(R.id.location);
        budget = findViewById(R.id.budget);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        itinerary = findViewById(R.id.itineraryRecyclerView);


        try {
            getItinerary();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        newSearchBotton = findViewById(R.id.addNSearch);
        newSearchBotton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSearch();
            }
        });




    }

    private void getItinerary() throws JSONException {

        Bundle b = getIntent().getExtras();
        //String userId=b.getString("userId");
        String userId = "yingyingying";
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//
//        Context context = UserSearchActivity.this;

        //requestQueue.start();

        String url = "http://baidu.com";
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);//need to tell backend this
        //searchResult[] response_for_next = {new searchReasult()};
        List<JSONObject> searchResults = new ArrayList<>();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray ja = response.getJSONArray("result");//need change
                    Log.e("Detail",ja.toString());

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jsonObject =  ja.getJSONObject(i);
                        searchResults.add(jsonObject);
                    }
                    Log.e("suncess",searchResults.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.getMessage());

                    }
                }
        );
//        StringRequest jor = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        Log.e("success","congra");
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Volley", "Error");
//            }
//        });

        Log.e("message","I'm here");
        RequestQueue requestQueue = Volley.newRequestQueue(UserSearchActivity.this);
        //requestQueue.add(jor);
        //itinerary = findViewById(R.id.itineraryRecyclerView);
        itinerary.setLayoutManager(new LinearLayoutManager(this));
        Gson gson = new GsonBuilder().create();
        List<Itinerary> itineraries = new ArrayList<>();
//        String s1 = "{\"location\":\"boston\",\"startDate\":\"03/22/1999\",\"endDate\":\"04/10/2022\"}";
//        String s2 = "{\"location\":\"new york\",\"startDate\":\"02/11/2000\",\"endDate\":\"12/12/1999\"}";
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
        for (JSONObject item : searchResults) {
            itineraries.add(gson.fromJson(item.toString(), Itinerary.class));
        }
        Log.e("succ",itineraries.get(0).getLocation());
        adapter = new MyRecyclerViewAdapter( itineraries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itinerary.setLayoutManager(layoutManager);
        //adapter.setClickListener(this);
        itinerary.setAdapter(adapter);
        itinerary.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

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
            Itinerary itinerary = mData.get(position);
            Log.e("holder location", itinerary.getLocation());
            holder.location.setText(itinerary.getLocation());
            holder.duration.setText(itinerary.getStartDate() + "-" + itinerary.getEndDate());
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


            ViewHolder(View itemView) {
                super(itemView);
                location = itemView.findViewById(R.id.itemLocation);
                duration = itemView.findViewById(R.id.itemDuration);
                search = itemView.findViewById(R.id.unitSearch);
                search.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.location.getText(), Toast.LENGTH_SHORT).show();
            }
        }

        // convenience method for getting data at click position
        Itinerary getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught

    }



    private void attemptSearch() {
        String locationVal = location.getText().toString();
        String bugetVal = budget.getText().toString();
        String startDateVal = startDate.getText().toString();
        String endDateVal = endDate.getText().toString();

        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();


        String url = "http://suggest.taobao.com/sug?code=utf-8&q=商品关键字&callback=cb";//send to the database
        Map<String, Object> param = new HashMap<>();
        param.put("location", locationVal);//need to tell backend this
        param.put("budget", bugetVal);
        param.put("startDate", startDateVal);
        param.put("endDate", endDateVal);
        //searchResult[] response_for_next = {new searchResult()};
        List<JSONObject> searchResults = new ArrayList<>();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray ja = response.getJSONArray("posts");//need change
                    Log.e("Detail", ja.toString());

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jsonObject = ja.getJSONObject(i);
                        searchResults.add(jsonObject);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");

                    }
                }
        );


        requestQueue.add(jor);

        //public PostObjectRequest(String url, Map<String,String> params,Type type, ResponseListener lis


//        Intent intent = new Intent(this, SecondActivity.class);
//        intent.putExtra("search result", searchResults.toString());
//        startActivity(intent);
    }

}

//    /**
//     * Created by gyzhong on 15/3/1.
//     * 简化回调接口
//     */
//    public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {
//    }
//    /**
//     * Created by gyzhong on 15/3/1.
//     */
//    public class PostObjectRequest<T> extends Request<T> {
//
//        /**
//         * 正确数据的时候回掉用
//         */
//        private ResponseListener mListener ;
//        /*用来解析 json 用的*/
//        private Gson mGson ;
//        /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
//        private Type mClazz ;
//        /*请求 数据通过参数的形式传入*/
//        private Map<String,String> mParams;
//        //需要传入参数，并且请求方式不能再为 get，改为 post
//        public PostObjectRequest(String url, Map<String,String> params,Type type, ResponseListener listener) {
//            super(Method.POST, url, listener);
//            this.mListener = listener ;
//            mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() ;
//            mClazz = type ;
//            setShouldCache(false);
//            mParams = params ;
//        }
//
//        /**
//         * 这里开始解析数据
//         * @param response Response from the network
//         * @return
//         */
//        @Override
//        protected Response<T> parseNetworkResponse(NetworkResponse response) {
//            try {
//                T result ;
//                String jsonString =
//                        new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//                Log.v("zgy", "====jsonString===" + jsonString);
//                result = mGson.fromJson(jsonString,mClazz) ;
//                return Response.success(result,
//                        HttpHeaderParser.parseCacheHeaders(response));
//            } catch (UnsupportedEncodingException e) {
//                return Response.error(new ParseError(e));
//            }
//        }
//
//        /**
//         * 回调正确的数据
//         * @param response The parsed response returned by
//         */
//        @Override
//        protected void deliverResponse(T response) {
//            mListener.onResponse(response);
//        }
//
//        //关键代码就在这里，在 Volley 的网络操作中，如果判断请求方式为 Post 则会通过此方法来获取 param，所以在这里返回我们需要的参数，
//
//        protected Map<String, String> getParams() throws AuthFailureError {
//            return mParams;
//        }
//    }
//}
