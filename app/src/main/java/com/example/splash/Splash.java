package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Splash screen lasts for 4000 milliseconds, then moves to FingerPrint Activity
        new Handler().postDelayed(new Runnable() {
                @Override
            public void run() {
                Intent i = new Intent(Splash.this, FingerPrint.class);
                startActivity(i);
                finish();
            }
        }, 4000);
    }
}
