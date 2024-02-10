package com.sp.mini_assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

public class Fragment_payment extends Fragment {

    private RelativeLayout cardLayout;
    private TextView tvCardNumber, tvExpirationDate, tvCvv;

    public Fragment_payment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cardLayout = view.findViewById(R.id.relativeLayout);
        tvCardNumber = view.findViewById(R.id.tvCardNumber);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        tvCvv = view.findViewById(R.id.tvCvv);
        Bundle args = getArguments();
        if (args != null && args.containsKey("cardDetails")) {
            String cardDetails = args.getString("cardDetails");
            // Use the cardDetails as needed
        }
        // Retrieve card details from the shared ViewModel
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observe changes in the card details LiveData
        sharedViewModel.getCardDetails().observe(getViewLifecycleOwner(), new Observer<CardDetails>() {
            @Override
            public void onChanged(CardDetails cardDetails) {
                // Display card details on the bank card layout
                if (cardDetails != null) {
                    tvCardNumber.setText(cardDetails.getCardNumber());
                    tvExpirationDate.setText(cardDetails.getExpirationDate());
                    tvCvv.setText(cardDetails.getCvv());

                    // Make the bank card layout visible
                    cardLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}

