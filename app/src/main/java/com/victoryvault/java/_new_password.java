package com.victoryvault.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victoryvault.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class _new_password extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://victoryvault-80df5-default-rtdb.firebaseio.com/");
    EditText newPasswordInput, confirmPasswordInput;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPasswordInput = findViewById(R.id.newPassword);
        confirmPasswordInput = findViewById(R.id.confirmPassword);
        saveButton = findViewById(R.id.savePasswordButton);

        // Get username from previous activity
        String username = getIntent().getStringExtra("username");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordInput.getText().toString().trim();
                String confirmPassword = confirmPasswordInput.getText().toString().trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(com.victoryvault.java._new_password.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(com.victoryvault.java._new_password.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update password in Firebase Realtime Database
                databaseReference.child("users").child(username).child("password").setValue(newPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(com.victoryvault.java._new_password.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(com.victoryvault.java._new_password.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
