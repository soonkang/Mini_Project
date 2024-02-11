package com.sp.mini_assignment.Settings;


import android.widget.Toast;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sp.mini_assignment.R;

public class CommunityForumFragment extends Fragment {

    private RatingBar j8Rating;
    private ImageView favouriteImageView;
    private ImageView commentImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_community_forum, container, false);

        j8Rating = rootView.findViewById(R.id.j8ratingBar);
        favouriteImageView = rootView.findViewById(R.id.favourite);
        commentImageView = rootView.findViewById(R.id.comment_icon);

        // Set OnClickListener for the RatingBar
        j8Rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                showToast("Changed rating to: " + rating);
            }
        });

        // Set OnClickListener for the Favourite ImageView
        favouriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle the favorite state
                toggleFavoriteState();
            }
        });

        // Set OnClickListener for the Comment ImageView
        commentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the ForumFragment when the Comment ImageView is clicked
                openForumFragment();
            }
        });

        return rootView;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void toggleFavoriteState() {
        // Toggle the source of the ImageView between notfavourite and favourite
        if (favouriteImageView.getTag() == null || favouriteImageView.getTag().equals("notfavourite")) {
            favouriteImageView.setImageResource(R.drawable.favourite);
            favouriteImageView.setTag("favourite");
            showToast("Favourited!");
        } else {
            favouriteImageView.setImageResource(R.drawable.notfavourite);
            favouriteImageView.setTag("notfavourite");
            showToast("Unfavourited!");
        }
    }


    private void openForumFragment() {
        // Create a new instance of ForumFragment
        ForumFragment forumFragment = new ForumFragment();

        // Replace the current fragment with the ForumFragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, forumFragment);
        fragmentTransaction.addToBackStack(null);  // Optional: Add to back stack
        fragmentTransaction.commit();
    }
}
