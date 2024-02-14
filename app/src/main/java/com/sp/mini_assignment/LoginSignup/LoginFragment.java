package com.sp.mini_assignment.LoginSignup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sp.mini_assignment.Home.Main;
import com.sp.mini_assignment.Interfaces.OnLoginSuccessListener;
import com.sp.mini_assignment.R;

import java.util.Objects;

public class LoginFragment extends Fragment {

    EditText loginUsername;
    EditText loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        loginUsername = rootView.findViewById(R.id.login_username);
        loginPassword = rootView.findViewById(R.id.login_password);
        loginButton = rootView.findViewById(R.id.login_button);
        signupRedirectText = rootView.findViewById(R.id.signuptext);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUpFragment();
            }
        });
        return rootView;
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if(val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if(val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://carparks-ddecb-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference =database.getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if(Objects.equals(passwordFromDB, userPassword)) {
                        loginUsername.setError(null);
                        Intent intent = new Intent(requireContext(), Main.class);
                        startActivity(intent);
                    } else {
                        loginPassword.setError("Invalid Username or Password");
                        loginPassword.requestFocus();
                    }
                } else {
                        loginUsername.setError("User does not exist");
                        loginUsername.requestFocus();
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ((OnLoginSuccessListener) requireActivity()).onLoginSuccess(userUsername);

    }

    private void navigateToSignUpFragment() {
        // Create an instance of the LoginFragment
        SignUpFragment signUpFragment = new SignUpFragment();

        // Navigate to the LoginFragment
        getParentFragmentManager().beginTransaction()
                .replace(android.R.id.content, signUpFragment)
                .addToBackStack(null) // Optional: Add to back stack to allow back navigation
                .commit();
    }




}