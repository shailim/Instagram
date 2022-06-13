package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);  // no need because showing splash screen from manifest
        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}