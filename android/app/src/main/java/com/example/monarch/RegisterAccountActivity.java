package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.monarch.API.RequestQueueSingleton;
import com.example.monarch.data.User;
import com.example.monarch.util.FirestoreUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegisterAccountActivity extends AppCompatActivity {
    private static final String TAG = "Register";

    private Button mRegisterBtn;
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        // wire widget
        mUsername = findViewById(R.id.register_email_edit_text);
        mPassword = findViewById(R.id.register_password_edit_text);
        mRegisterBtn = findViewById(R.id.register_button);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreUtils firestoreUtils = new FirestoreUtils();
                firestoreUtils.clickLog("Register" + " Click", java.util.Calendar.getInstance().getTime().toString());

                String url = getResources().getString(R.string.back_end_base) + getResources().getString(R.string.register_endpoint);
                JSONObject registerData = new JSONObject();
                JSONObject body = new JSONObject();
                try {
                    registerData.put("email", mUsername.getText());
                    registerData.put("password", mPassword.getText());
                    body.put("user", registerData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest loginRequest = new JsonObjectRequest
                        (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "Register successfully");
                                mPassword.setText("");
                                mUsername.setText("");
                                mPassword.clearFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(mRegisterBtn.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                Toast toast = Toast.makeText(getApplicationContext(), "Register successfully", Toast.LENGTH_LONG);
                                toast.show();
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

        FirestoreUtils firestoreUtils = new FirestoreUtils();
        firestoreUtils.modifyFireStore("RegisterActivity", Calendar.getInstance().getTime().toString());
    }
}