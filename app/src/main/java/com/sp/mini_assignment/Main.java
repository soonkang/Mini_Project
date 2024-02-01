package com.sp.mini_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of Fragment_Add_Card
        Fragment_Paypal fragmentPaypal = new Fragment_Paypal();

        // Add Fragment_Add_Card to the activity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentPaypal)
                .addToBackStack(null)
                .commit();
    }

    // Method to switch to Fragment_Payment with card details
    public void switchToFragmentPayment(String cardDetails) {
        Log.d("Main", "Switching to Fragment_Payment");

        Fragment_payment fragmentPayment = new Fragment_payment();

        Bundle bundle = new Bundle();
        bundle.putString("cardDetails", cardDetails);
        fragmentPayment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentPayment)
                .addToBackStack(null)
                .commit();
    }


}