package com.sp.mini_assignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username= signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                LoginHelper loginhelper = new LoginHelper(name, email, username, password);
                reference.child(name).setValue(loginhelper);

               Toast.makeText(requireContext(), "You have signed up successfully!", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getActivity(), LoginFragment.class);
               startActivity(intent);
           }
       });

       loginRedirectText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), LoginFragment.class);
               startActivity(intent);
           }
       });
        return rootView;
    }

}