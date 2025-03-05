package com.victoryvault.java;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.victoryvault.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.FirebaseException;

public class forgett extends AppCompatActivity {


    private Button sendOTPButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgett);


        sendOTPButton = findViewById(R.id.reset); // Send OTP button

        sendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(com.victoryvault.java.forgett.this, "Work in Progress", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
