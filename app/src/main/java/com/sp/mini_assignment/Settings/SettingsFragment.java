package com.sp.mini_assignment.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.mini_assignment.BookSlot.BookedSlotFragment;
import com.sp.mini_assignment.LoginSignup.LoginFragment;
import com.sp.mini_assignment.R;


public class SettingsFragment extends Fragment {

    TextView profile, bookedslot, community, notifications, points, subscriptions, feedback, deactivate;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        profile = rootView.findViewById(R.id.settings_profile);
        bookedslot = rootView.findViewById(R.id.settings_bookedslot);
        community = rootView.findViewById(R.id.settings_community);
        notifications = rootView.findViewById(R.id.settings_notifications);
        points = rootView.findViewById(R.id.settings_points);
        subscriptions = rootView.findViewById(R.id.settings_subscriptions);
        feedback = rootView.findViewById(R.id.settings_feedback);
        deactivate = rootView.findViewById(R.id.settings_deactivate);

        bookedslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BookedSlotFragment());
                v.performClick();
            }

        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CommunityForumFragment());
            }

        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new NotificationFragment());
            }

        });


        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PointsSystemFragment());
            }

        });


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FeedbackFragment());
            }

        });

        deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new LoginFragment());
            }
        });


        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_settings, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}