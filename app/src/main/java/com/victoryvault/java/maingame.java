package com.victoryvault.java;

import static androidx.core.app.PendingIntentCompat.getActivity;
import static androidx.core.content.ContentProviderCompat.requireContext;
import static com.example.victoryvault.R.id.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.victoryvault.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class maingame extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnv;
    DrawerLayout dl;
    NavigationView navigationView;

    LinearLayout enteramount;

    ProgressBar progressBar;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    replaceFragment(new com.victoryvault.java.home_fragment());

                }
            }, 1000); // 2 seconds
        }
    }


    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingame);

        bnv = findViewById(bottomnavigation);
        dl = findViewById(R.id.drawer_layout);
        enteramount = findViewById(R.id.enteramount);
        progressBar = findViewById(R.id.progressBar);


       navigationView = findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);

        // Show progress bar initially









        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.open_nav, R.string.close_nav);
        dl.addDrawerListener(toggle);
        toggle.syncState();

//        progressBar.setVisibility(View.VISIBLE);

//        // Delay hiding the progress bar after 2 seconds (2000 milliseconds)
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Hide the progress bar after the delay
//                progressBar.setVisibility(View.GONE);
//
//                // Continue with your activity flow
//            }
//        }, 2000); //






//        // Setting up NestedScrollView listener to detect scroll position
//        NestedScrollView nestedScrollView = findViewById(R.id.nes);
//        nestedScrollView.setOnScrollChangeListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            // Enable swipe-to-refresh only if the user is at the top
//            if (scrollY == 0) {
//                swipe.setEnabled(true);  // Enable swipe when at the top
//            } else {
//                swipe.setEnabled(false);  // Disable swipe when scrolled down
//            }
//        });

//


        bnv.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                replaceFragment(new com.victoryvault.java.home_fragment());
            } else if (id == R.id.challenge) {
                replaceFragment(new challenge());
            } else if (id == R.id.notification) {
                replaceFragment(new notification_fragment());
            } else if (id == R.id.profile) {
                replaceFragment(new com.victoryvault.java.profile_fragment());
            } else if (id == R.id.create) {
                showBottomDialog();
            }
            return true;
        });



    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }


    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout freefire = dialog.findViewById(R.id.layoutff);
        LinearLayout bgmi = dialog.findViewById(R.id.layoutbgmi);
        LinearLayout ludo = dialog.findViewById(R.id.layoutludo);

        freefire.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(com.victoryvault.java.maingame.this, "Free fire selected", Toast.LENGTH_SHORT).show();
        });

        bgmi.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(com.victoryvault.java.maingame.this, "BGMI selected", Toast.LENGTH_SHORT).show();
        });

        ludo.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(com.victoryvault.java.maingame.this, "Ludo selected", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.home_nav){
            bnv.setSelectedItemId(R.id.home);
            replaceFragment(new home_fragment());

        } else if (item.getItemId()==R.id.profile_nav) {
            bnv.setSelectedItemId(R.id.profile);
            replaceFragment(new profile_fragment());
        } else if (item.getItemId()==R.id.share){
            Toast.makeText(this, "Shared", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.about) {
            Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
            
        } else if (item.getItemId()==R.id.logout) {
            showlogoutDialog();
            
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showlogoutDialog() {
        new AlertDialog.Builder(this) // Use `this` for the activity context
                .setTitle("Logout")
                .setMessage("Do you want to continue to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Reset SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.putBoolean("isRegestered", false);// Set login status to false
                    editor.apply();






                    // Sign out from Firebase
                    FirebaseAuth.getInstance().signOut();

                    // Start the login activity
                    Intent intent = new Intent(this, login_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                    startActivity(intent);

                    // Show a Toast message
                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

                    // Close the dialog
                    dialog.dismiss();

                    // Finish the current activity
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Close the dialog if the user cancels
                .show(); // Show the alert dialog
    }





    @Override
    public void onBackPressed() {
        // Close the navigation drawer if it's open
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            // Otherwise, handle the default back button behavior
            super.onBackPressed();
        }
 }

}
