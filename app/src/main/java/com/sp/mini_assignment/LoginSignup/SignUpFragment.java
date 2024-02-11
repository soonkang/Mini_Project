package com.sp.mini_assignment.LoginSignup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sp.mini_assignment.Adapters.LoginHelper;
import com.sp.mini_assignment.R;

public class SignUpFragment extends Fragment {

    EditText signupName, signupEmail, signupUsername, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signupName = rootView.findViewById(R.id.signup_name);
        signupEmail = rootView.findViewById(R.id.signup_email);
        signupUsername = rootView.findViewById(R.id.signup_username);
        signupPassword = rootView.findViewById(R.id.signup_password);
        signupButton = rootView.findViewById(R.id.signup_button);
        loginRedirectText = rootView.findViewById(R.id.logintext);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance("https://mini-assignment-signup-login-default-rtdb.firebaseio.com/");
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                LoginHelper loginhelper = new LoginHelper(name, email, username, password);
                reference.child(name).setValue(loginhelper);
                reference.child(password).setValue(loginhelper)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(requireContext(), "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                                navigateToLoginFragment();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "Failed to sign up. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


       loginRedirectText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               navigateToLoginFragment();
           }
       });
        return rootView;
    }

    private void navigateToLoginFragment() {
        // Create an instance of the LoginFragment
        LoginFragment loginFragment = new LoginFragment();

        // Navigate to the LoginFragment
        getParentFragmentManager().beginTransaction()
                .replace(android.R.id.content, loginFragment)
                .addToBackStack(null) // Optional: Add to back stack to allow back navigation
                .commit();
    }

}