package com.victoryvault.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victoryvault.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://victoryvault-80df5-default-rtdb.firebaseio.com/");


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggin = preferences.getBoolean("isLoggedIn", false);
        boolean isreges = preferences.getBoolean("isRegestered", false);

        if (isLoggin || isreges) {
            Intent intent = new Intent(getApplicationContext(), com.victoryvault.java.maingame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn1;
        btn1 = findViewById(R.id.lsignup);
        EditText username = findViewById(R.id.lusername);
        EditText password = findViewById(R.id.lpassword);
        Button login = findViewById(R.id.llogin);
        TextView forgetpas = findViewById(R.id.forget);
        FirebaseAuth auth = FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernametxt= username.getText().toString();
                String passwordtxt= password.getText().toString();


                if (usernametxt.isEmpty() || passwordtxt.isEmpty()){

                    Toast.makeText(com.victoryvault.java.login_activity.this, "Please Enter the all field", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(usernametxt)){

                                String getpassword = snapshot.child(usernametxt).child("password").getValue(String.class);

                                if (getpassword == null) {
                                    Toast.makeText(com.victoryvault.java.login_activity.this, "Password not found in the database.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (getpassword.equals(passwordtxt)){
                                    String name = snapshot.child(usernametxt).child("name").getValue(String.class);
                                    String email = snapshot.child(usernametxt).child("email").getValue(String.class);
                                    String username = snapshot.child(usernametxt).child("username").getValue(String.class);
                                    String mobile= snapshot.child(usernametxt).child("mobile").getValue(String.class);


                                    SharedPreferences sharedpreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedpreferences.edit();
                                    editor.putBoolean("isLoggedIn",true);
                                    editor.putString("name",name);
                                    editor.putString("email",email);
                                    editor.putString("username",username);
                                    editor.putString("mobile",mobile);

                                    editor.apply();


                                    startActivity(new Intent(getApplicationContext(), maingame.class));
                                    finish();
                                } else {

                                    Toast.makeText(com.victoryvault.java.login_activity.this, "please enter the correct password", Toast.LENGTH_SHORT).show();
                                    password.setError("Password is incorrect");
                                    password.requestFocus();
                                }
                            } else {
                                Toast.makeText(com.victoryvault.java.login_activity.this, "username not found", Toast.LENGTH_SHORT).show();
                                username.setError("Username is incorrect");
                                username.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

      forgetpas.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent = new Intent(getApplicationContext(), forgett.class);
              startActivity(intent);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

          }
      });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(getApplicationContext(), regester.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });

    }

}