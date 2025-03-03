package com.example.victoryvault;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class profile_fragment extends Fragment {

    private RelativeLayout rl,editprofile;
    TextView t1,t2;

    private boolean isbackpress=false;

    public profile_fragment() {
        // Required empty public constructor
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        // Initialize the RelativeLayout by finding it in the inflated layout
        rl = view.findViewById(R.id.logoutlayout);// Assuming "rel" is the ID of your RelativeLayout
        t1 =view.findViewById(R.id.nameprofiletext);
        t2 =view.findViewById(R.id.emailprofile);
        editprofile=view.findViewById(R.id.rel);






        SharedPreferences preferences = requireContext().getSharedPreferences("UserPrefs", requireContext().MODE_PRIVATE);
        String savedFullname = preferences.getString("name", "No Name");
        String savedEmail = preferences.getString("email", "No Email");


        t1.setText(savedFullname);
        t2.setText(savedEmail);


        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isbackpress){
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireActivity(), "Please click Back again to exist", Toast.LENGTH_SHORT).show();
                    isbackpress=true;

                    new android.os.Handler().postDelayed(() -> isbackpress=false,2000);
                }

            }
        });

        // You can now use rl for any further operations, like setting click listeners, etc.
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity()) // Use getActivity() to get context for AlertDialog
                        .setTitle("Logout")
                        .setMessage("Do you want to continue to log out?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            // Reset SharedPreferences
                            SharedPreferences preferences = requireContext().getSharedPreferences("UserPrefs", requireContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isLoggedIn", false); // Set login status to false
                            editor.putBoolean("isRegestered", false);
                            editor.apply();

                            // Sign out from Firebase
                            FirebaseAuth.getInstance().signOut();


                            // Start the login activity
                            Intent intent = new Intent(getActivity(), login_activity.class);


                            // Show a Toast message
                            Toast.makeText(getActivity(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            // Close the dialog
                            dialog.dismiss();

                            // Finish the current activity to log out (optional)
                            getActivity().finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss(); // Close the dialog without any action
                        })
                        .show(); // Show the alert dialog
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), edit_profile.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


        return view;
    }
}
