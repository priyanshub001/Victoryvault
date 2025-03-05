package com.victoryvault.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.victoryvault.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class _enter_otp extends AppCompatActivity {

    private EditText otpInput;
    private Button verifyOtpButton;
    private FirebaseAuth mAuth;
    private String verificationId; // Will store verificationId from previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        otpInput = findViewById(R.id.otpInput);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);
        mAuth = FirebaseAuth.getInstance();

        // Get the verificationId passed from forgett.java
        verificationId = getIntent().getStringExtra("verificationId");

        if (verificationId == null) {
            Toast.makeText(this, "Verification ID missing", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity if verificationId is missing
        }

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpInput.getText().toString().trim();

                if (otp.isEmpty()) {
                    Toast.makeText(com.victoryvault.java._enter_otp.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verify the OTP
                verifyOtp(otp);
            }
        });
    }

    private void verifyOtp(String otp) {
        // Create a PhoneAuthCredential with the entered OTP and verification ID
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

        // Sign in with the credential
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // OTP verification succeeded
                        Toast.makeText(com.victoryvault.java._enter_otp.this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
                        // Redirect to the next activity, like reset password
                        Intent intent = new Intent(com.victoryvault.java._enter_otp.this, _new_password.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // OTP verification failed
                        Toast.makeText(com.victoryvault.java._enter_otp.this, "Invalid OTP, please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
