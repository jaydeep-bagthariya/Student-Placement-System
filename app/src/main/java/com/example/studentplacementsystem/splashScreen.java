package com.example.studentplacementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splashScreen.this, Modules.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                overridePendingTransition(R.anim.view,R.anim.view);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}
