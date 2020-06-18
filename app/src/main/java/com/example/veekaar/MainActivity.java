package com.example.veekaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    public static final int SPLASH_SCREEN = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, PhotoCapturing.class);
                startActivity(intent);
                finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        },SPLASH_SCREEN);

    }

    //----------------------------------To access this activity tap on text within 3 seconds!!!-----------------------------

    public void aboutDeveloper(View view){
        Intent intent1 = new Intent(MainActivity.this , DeveloperDetails.class);
        startActivity(intent1);
    }

}