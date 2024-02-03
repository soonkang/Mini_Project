package com.sp.mini_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Assuming you have an activity_main.xml layout

        // Begin the transaction
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Replace the content frame with the HelpPageFragment initially
        fragmentTransaction.replace(R.id.fragment_container, new HelpPageFragment());

        // Commit the transaction
        fragmentTransaction.commit();
    }
}
