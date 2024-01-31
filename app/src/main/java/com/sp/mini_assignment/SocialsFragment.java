package com.sp.mini_assignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SocialsFragment extends Fragment {

    ImageView twitter, whatsapp, facebook, google;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_socials, container, false);

        twitter = rootView.findViewById(R.id.twitter_icon);
        whatsapp = rootView.findViewById(R.id.whatsapp_icon);
        facebook = rootView.findViewById(R.id.facebook_icon);
        google = rootView.findViewById(R.id.google_icon);
        allClickListener();

        return rootView; // Don't forget to return the rootView
    }

    private void allClickListener() {
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://twitter.com/ParkSwiftApp");
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://chat.whatsapp.com/JxMVdlv3VbWKERvllEofCI");
            }
        });


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://facebook.com/ParkSwiftApp");
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.google.com");
            }
        });
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
