package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.User;
import com.example.monarch.util.FirestoreUtils;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login";

    private MaterialButton mLoginBtn;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mRegisterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.username_edit_text);
        mPassword = findViewById(R.id.password_edit_text);
        mLoginBtn = findViewById(R.id.login_button);
        mRegisterTextView = findViewById(R.id.register_text_view);

        // create volley request queue
        RequestQueueSingleton.getInstance(getApplicationContext());
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);

        // login button click
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FirestoreUtils firestoreUtils = new FirestoreUtils();
                firestoreUtils.clickLog("Login" + " Click", java.util.Calendar.getInstance().getTime().toString());

                String url = getResources().getString(R.string.back_end_base) + getResources().getString(R.string.login_endpoint);
                JSONObject loginData = new JSONObject();
                JSONObject body = new JSONObject();
                try {
                    loginData.put("email", mUsername.getText());
                     loginData.put("password", mPassword.getText());
                    body.put("user", loginData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest loginRequest = new JsonObjectRequest
                        (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "Login successfully");
                                Intent intent = new Intent(getApplicationContext(), UserPageActivity.class);
                                startActivity(intent);
                                User.getUserInstance();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);
                Log.d(TAG, "Made login request" + body.toString());
            }
        });

        // register click
        mRegisterTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FirestoreUtils firestoreUtils = new FirestoreUtils();
                firestoreUtils.clickLog("LoginToRegister" + " Click", java.util.Calendar.getInstance().getTime().toString());

                Intent intent = new Intent(getApplicationContext(), RegisterAccountActivity.class);
                startActivity(intent);
            }
        });
        FirestoreUtils firestoreUtils = new FirestoreUtils();
        firestoreUtils.modifyFireStore("LoginActivity", Calendar.getInstance().getTime().toString());
    }
}