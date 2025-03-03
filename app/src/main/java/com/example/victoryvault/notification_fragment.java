package com.example.victoryvault;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class notification_fragment extends Fragment {
    private boolean isbackpress=false;


    public notification_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification_fragment, container, false);

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
        return view;
    }
}