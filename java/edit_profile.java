package com.example.victoryvault;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class edit_profile extends AppCompatActivity {

    private EditText editName, editEmail, editUsername, editMobile, editCrush;
    private String nameUser, emailUser, usernameUser, userMobile;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference databaseReference;
    private Button btnSave;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        editName = findViewById(R.id.editname);
        editEmail = findViewById(R.id.editemail);
        editUsername = findViewById(R.id.editusername);
        editMobile = findViewById(R.id.editmobile);
        editCrush = findViewById(R.id.editcrush);
        btnSave = findViewById(R.id.savebutton);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Load user data from SharedPreferences
        loadUserData();

        // Set up save button click listener
        btnSave.setOnClickListener(v -> {
            if (isNameChanged() || isEmailChanged() || isMobileChanged()) {
                saveUserData();
                Toast.makeText(edit_profile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), maingame.class); // Replace HomeActivity with your actual activity class
                startActivity(intent);

            } else {
                Toast.makeText(edit_profile.this, "No changes detected.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up swipe refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            ProgressDialog progressDialog = new ProgressDialog(edit_profile.this);
            progressDialog.setMessage("Refreshing...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Handler().postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                loadUserData();
                recreate();
            }, 1000);
        });

        // Back button logic to confirm exit
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isBackPressed) {
                    finish();
                } else {
                    Toast.makeText(edit_profile.this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
                    isBackPressed = true;
                    new Handler().postDelayed(() -> isBackPressed = false, 2000);
                }
            }
        });
    }

    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString().trim())) {
            databaseReference.child(usernameUser).child("name")
                    .setValue(editName.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(this, "Failed to update name.", Toast.LENGTH_SHORT).show();
                        }
                    });
            nameUser = editName.getText().toString().trim();
            return true;
        }
        return false;
    }

    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString().trim())) {
            databaseReference.child(usernameUser).child("email")
                    .setValue(editEmail.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(this, "Failed to update email.", Toast.LENGTH_SHORT).show();
                        }
                    });
            emailUser = editEmail.getText().toString().trim();
            return true;
        }
        return false;
    }

    private boolean isMobileChanged() {
        if (!userMobile.equals(editMobile.getText().toString().trim())) {
            databaseReference.child(usernameUser).child("mobile")
                    .setValue(editMobile.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(this, "Failed to update mobile.", Toast.LENGTH_SHORT).show();
                        }
                    });
            userMobile = editMobile.getText().toString().trim();
            return true;
        }
        return false;
    }

    private void loadUserData() {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        nameUser = preferences.getString("name", "No Name");
        emailUser = preferences.getString("email", "No Email");
        usernameUser = preferences.getString("username", "No Username");
        userMobile = preferences.getString("mobile", "No Mobile");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editMobile.setText(userMobile);
    }

    private void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", nameUser);
        editor.putString("email", emailUser);
        editor.putString("mobile", userMobile);
        editor.apply();
    }
}
