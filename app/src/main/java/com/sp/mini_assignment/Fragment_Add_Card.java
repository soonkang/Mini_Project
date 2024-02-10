package com.sp.mini_assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

public class Fragment_Add_Card extends Fragment {

    private EditText editCardNumber, editExpirationDate, editCvv;
    private Button btnSave;
    private RelativeLayout cardLayout;

    public Fragment_Add_Card() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);

        editCardNumber = view.findViewById(R.id.editCardNumber);
        editExpirationDate = view.findViewById(R.id.editExpirationDate);
        editCvv = view.findViewById(R.id.editCvv);
        btnSave = view.findViewById(R.id.btnSave);
        cardLayout = view.findViewById(R.id.relativeLayout);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fragment_Add_Card", "Save button clicked");

                // Get the entered card details
                String cardNumber = editCardNumber.getText().toString();
                String expirationDate = editExpirationDate.getText().toString();
                String cvv = editCvv.getText().toString();

                // Create a CardDetails object
                CardDetails cardDetails = new CardDetails(cardNumber, expirationDate, cvv);

                // Save the card details to SharedPreferences and ViewModel
                saveCardDetails(cardDetails);

                // Navigate to Fragment_payment or perform other actions
//                ((Main) requireActivity()).switchToFragmentPayment(cardDetails.toString()); // Assuming cardDetails has a useful toString() method
            }
        });

        return view;
    }

    private void saveCardDetails(CardDetails cardDetails) {
        Log.d("Fragment_Add_Card", "Saving card details to SharedPreferences");

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("CardDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cardDetails);

        editor.putString("cardDetails", json);
        editor.apply();

        Log.d("Fragment_Add_Card", "Card details saved to SharedPreferences");

        // Set the card details in the shared ViewModel
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setCardDetails(cardDetails);

        Log.d("Fragment_Add_Card", "Card details set in ViewModel");
    }
}