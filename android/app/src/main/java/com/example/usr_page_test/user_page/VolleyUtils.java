package com.example.usr_page_test.user_page;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class VolleyUtils {
    private static RequestQueue mQueue;
    private static String prepareParam(Map<String, String> paramMap) {
        StringBuilder sb = new StringBuilder();
        if (paramMap.isEmpty()) {
            return "";
        } else {
            for (String key : paramMap.keySet()) {
                String value =  paramMap.get(key);
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }
    public <T> void post(String url, final Class<T> clazz, String[] res) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley", "response-->" + response);
                Gson gson = new Gson();
                res[0] =  response;
                //listener.onSuccess(gson.fromJson(response, clazz));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "response-->" + error.getMessage());
                res[0] = "error";
                //listener.onError(error.getMessage());
            }
        }) {
            /**
             * Post请求和Get请求的使用步骤上的区别在于请求条件的指定
             * 必须在StringRequest对象的后面添加{}，并且
             * 在{}内重写getParams方法，该方法的返回值就是所有的请求条件
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //将请求条件封装到map对象中
                Map<String, String> map = new HashMap<>();
                //listener.OnMap(map);
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
