package com.sp.mini_assignment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CustomerSupportFragment extends Fragment {
    TextView chatMessage;
    ImageView chatBubble;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_support, container, false);
        chatMessage = rootView.findViewById(R.id.chat_message);
        chatBubble = rootView.findViewById(R.id.chat_bubble);

        // Check if the delay flag is set
        boolean delayVisibility = getArguments() != null && getArguments().getBoolean("delayVisibility", false);

        if (delayVisibility) {
            // If delay is required, set visibility to INVISIBLE initially
            chatBubble.setVisibility(View.INVISIBLE);
            chatMessage.setVisibility(View.INVISIBLE);

            // Delay the visibility of chatBubble and chatMessage by 5 seconds
            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Show chatBubble and chatMessage after 5 seconds
                    chatBubble.setVisibility(View.VISIBLE);
                    chatMessage.setVisibility(View.VISIBLE);
                }
            }, 3000); // 5000 milliseconds = 5 seconds
        } else {
            // If no delay, show chatBubble and chatMessage immediately
            chatBubble.setVisibility(View.VISIBLE);
            chatMessage.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

}
