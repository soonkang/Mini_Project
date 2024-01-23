package com.sp.mini_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.sp.mini_assignment.Main;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Always show the splash screen
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(this, Main.class);
            startActivity(mainIntent);

            finish();
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
