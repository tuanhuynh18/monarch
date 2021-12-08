package com.example.monarch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
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

        // login button click
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserPageActivity.class);
                startActivity(intent);
            }
        });

        // register click
        mRegisterTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}