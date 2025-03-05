package com.victoryvault.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victoryvault.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class regester extends AppCompatActivity {

    // create object of database refrence class to access firebase's Realtime databse
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://victoryvault-80df5-default-rtdb.firebaseio.com/");



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        Button btnlogin;


        btnlogin = findViewById(R.id.rlogin);
        EditText name = findViewById(R.id.rname);
        EditText email = findViewById(R.id.remail);
        EditText username = findViewById(R.id.rusername);
        EditText password = findViewById(R.id.rpassword);
        EditText mobile = findViewById(R.id.rmobilenum);
        Button regester = findViewById(R.id.rsignup);




        regester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nametxt= name.getText().toString();
                String emailtxt= email.getText().toString();
                String usernametxt= username.getText().toString();
                String passwordtxt= password.getText().toString();
                String mobiletxt= mobile.getText().toString();

                if (nametxt.isEmpty() || emailtxt.isEmpty() || usernametxt.isEmpty() || passwordtxt.isEmpty() || mobiletxt.isEmpty()){
                    Toast.makeText(com.victoryvault.java.regester.this, "Please enter the all field", Toast.LENGTH_SHORT).show();
                } else {


                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernametxt)){

                                Toast.makeText(com.victoryvault.java.regester.this, "Username is already taken", Toast.LENGTH_SHORT).show();

                            } else {
                                // sending data to realtime firebase database
                                // we are using username as unqiue identify of every user
                                databaseReference.child("users").child(usernametxt).child("name").setValue(nametxt);
                                databaseReference.child("users").child(usernametxt).child("email").setValue(emailtxt);
                                databaseReference.child("users").child(usernametxt).child("username").setValue(usernametxt);
                                databaseReference.child("users").child(usernametxt).child("password").setValue(passwordtxt);
                                databaseReference.child("users").child(usernametxt).child("mobile").setValue(mobiletxt);


                                SharedPreferences sharedPreferences =getSharedPreferences("UserPrefs",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("isRegestered",true);
                                editor.putString("name",nametxt);
                                editor.putString("email",emailtxt);
                                editor.putString("username",usernametxt);
                                editor.putString("mobile",mobiletxt);

                                editor.apply();


                                Toast.makeText(com.victoryvault.java.regester.this, "Regester successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), maingame.class);
                                startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();



                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }

            }
        });



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });

    }
}