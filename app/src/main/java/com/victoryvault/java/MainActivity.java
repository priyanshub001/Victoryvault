package com.victoryvault.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.victoryvault.R;

public class MainActivity extends AppCompatActivity {



    public static int postdelay=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              Intent intent = new Intent(com.victoryvault.java.MainActivity.this, startactivity.class);
                startActivity(intent);
                finish();

            }
        },postdelay);





    }
}