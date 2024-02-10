package com.sp.mini_assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_Point_System extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button redeemButton50;
    private Button redeemButton300;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__point__system, container, false);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        redeemButton50 = view.findViewById(R.id.redeem_50);
        redeemButton300 = view.findViewById(R.id.redeem_300);

        // Add click listeners to redeem buttons
        redeemButton50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redeemPoints(50);
            }
        });

        redeemButton300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redeemPoints(300);
            }
        });

        return view;
    }

    // Method to deduct points from the user's balance
    private void redeemPoints(final int pointsToDeduct) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            final String userId = user.getUid();
            mDatabase.child("users").child(userId).child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int currentCoins = dataSnapshot.getValue(Integer.class);
                        if (currentCoins >= pointsToDeduct) {
                            int newCoins = currentCoins - pointsToDeduct;
                            mDatabase.child("users").child(userId).child("coins").setValue(newCoins);
                            // Perform action after deducting coins
                            // For example, show a success message
                            Toast.makeText(getContext(), "Points redeemed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle insufficient balance error
                            Toast.makeText(getContext(), "Insufficient points", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    Toast.makeText(getContext(), "Database error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
