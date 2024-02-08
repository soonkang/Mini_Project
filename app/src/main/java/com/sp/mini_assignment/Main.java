package com.sp.mini_assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Directly switch to Fragment_Point_System when the activity starts
        switchToFragmentProfile();
    }

    // Method to switch to Fragment_Point_System
    public void switchToFragmentProfile() {
        // Create an instance of Fragment_Point_System
        fragment_profile fragmentProfile = new fragment_profile();

        // Replace the current fragment with Fragment_Point_System
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentProfile)
                .commit();
    }
}