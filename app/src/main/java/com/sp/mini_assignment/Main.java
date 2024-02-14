package com.sp.mini_assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Directly switch to Fragment_feedback when the activity starts
        switchToFragmentFeedback(null); // Passing null as there are no card details initially
    }

    // Method to switch to Fragment_feedback with card details
    public void switchToFragmentFeedback(String cardDetails) {
        Log.d("Main", "Switching to Fragment_feedback");

        // Create an instance of Fragment_feedback
        Fragment_fedback fragmentFeedback = new Fragment_feedback();

        // Pass card details to Fragment_feedback using Bundle
        Bundle bundle = new Bundle();
        bundle.putString("cardDetails", cardDetails);
        fragmentFeedback.setArguments(bundle);

        // Replace the current fragment with Fragment_feedback
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentFeedback)
                .addToBackStack(null)
                .commit();
    }
}
