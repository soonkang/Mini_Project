package com.sp.mini_assignment.Payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sp.mini_assignment.Adapters.CardDetails;
import com.sp.mini_assignment.R;
import com.sp.mini_assignment.Adapters.SharedViewModel;

public class PaymentFragment extends Fragment {

    private RelativeLayout cardLayout;
    private TextView tvCardNumber, tvExpirationDate, tvCvv;

    AppCompatButton addCard;

    Button pay;
    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);



        cardLayout = view.findViewById(R.id.relativeLayout);
        tvCardNumber = view.findViewById(R.id.tvCardNumber);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        tvCvv = view.findViewById(R.id.tvCvv);
        pay = view.findViewById(R.id.pay);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the addCard button and set the onClickListener
        addCard = view.findViewById(R.id.add);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with the AddCardFragment
                replaceFragment(new AddCardFragment(), true);

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PaypalFragment(), true);

            }
        });

        // Restore the Pay button's visibility based on the back stack entry count
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            pay.setVisibility(View.VISIBLE);
        }
    }
    private void replaceFragment(Fragment fragment, boolean hidePay) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_payment, fragment);
        if (hidePay) {
            pay.setVisibility(View.GONE);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

