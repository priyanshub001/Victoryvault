package com.example.victoryvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {



    public static int postdelay=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              Intent intent = new Intent(MainActivity.this, startactivity.class);
                startActivity(intent);
                finish();

            }
        },postdelay);





    }
}