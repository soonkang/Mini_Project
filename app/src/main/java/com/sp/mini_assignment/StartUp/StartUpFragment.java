package com.sp.mini_assignment.StartUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.sp.mini_assignment.LoginSignup.LoginFragment;
import com.sp.mini_assignment.R;

public class StartUpFragment extends Fragment {

    Button lets_go;


    public StartUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_start_up, container, false);

        lets_go = rootView.findViewById(R.id.redeem_300);

        lets_go.setOnClickListener(v ->
                navigateToLoginFragment());

        return  rootView;
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