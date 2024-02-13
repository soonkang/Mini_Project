package com.sp.mini_assignment.SplashScreen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.sp.mini_assignment.Home.Main;
import com.sp.mini_assignment.R;
import com.sp.mini_assignment.StartUp.StartUpFragment;

public class SplashActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Find the TextView by its ID
        ImageView animTextView = findViewById(R.id.anim);
        TextView text = findViewById(R.id.animText);

        // Load the Netflix-like splash animation
        Animation logoSplashAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.text);

        // Set the animation listener to start the Main activity when the animation ends
        logoSplashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Start playing the background music when the animation starts
                playBackgroundMusic();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // After the animation ends, wait for an additional 3 seconds before starting the Main activity
                new Handler().postDelayed(() -> {
                    // Stop playing the background music
                    stopBackgroundMusic();

                    getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new StartUpFragment())
                            .commit();
                }, 3000); // 3000 milliseconds = 3 seconds
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Start the animation on the TextView
        animTextView.startAnimation(logoSplashAnimation);
        text.startAnimation(textAnimation);

    }

    private void playBackgroundMusic() {
        // Initialize MediaPlayer with the background music
        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        // Set loop to true for continuous playback
        mediaPlayer.setLooping(true);

        // Start playing the background music
        mediaPlayer.start();
    }

    private void stopBackgroundMusic() {
        // Stop and release MediaPlayer when not needed
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop and release MediaPlayer when the activity is stopped
        stopBackgroundMusic();
    }
}
