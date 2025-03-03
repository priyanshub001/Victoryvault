package com.example.victoryvault;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class home_fragment extends Fragment {

    private boolean isBackPressedOnce = false;
//    SwipeRefreshLayout swp;




    public home_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_fragment, container, false);
//        swp = rootView.findViewById(R.id.swipe);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isBackPressedOnce){
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireActivity(), "Please click Back again to exist", Toast.LENGTH_SHORT).show();
                    isBackPressedOnce=true;

                    new android.os.Handler()
                            .postDelayed(() -> isBackPressedOnce = false,2000);
                }

            }
        });

//        // Configure SwipeRefreshLayout
//        swp.setOnRefreshListener(() -> {
//            // Perform your refresh logic here
//            Toast.makeText(requireActivity(), "Refreshing data...", Toast.LENGTH_SHORT).show();
//
//            // Simulate a network call or data refresh
//            new Handler().postDelayed(() -> {
//                // Stop the refreshing animation after completing the refresh
//                swp.setRefreshing(false);
//                Toast.makeText(requireActivity(), "Refresh complete", Toast.LENGTH_SHORT).show();
//
//            }, 2000); // 2-second delay for simulation
//        });

        // Initialize SliderView here before returning the view

        // Set up your SliderView here, like setting an adapter, etc.
        // Example: sliderView.setSliderAdapter(adapter);

        return rootView;
    }
}
