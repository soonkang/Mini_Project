package com.sp.mini_assignment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class Fragment_fedback extends Fragment {

    EditText Name, Email, comment;
    TextView ratingError;
    RatingBar rating;
    Button send, details;
    Firebase firebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fedback, container, false);

        Name = view.findViewById(R.id.Name);
        Email = view.findViewById(R.id.Email);
        comment = view.findViewById(R.id.comment);
        rating = view.findViewById(R.id.rating);
        ratingError = view.findViewById(R.id.ratingError);
        send = view.findViewById(R.id.Submit);
        details = view.findViewById(R.id.view);

        Firebase.setAndroidContext(requireContext());

        String uniqueID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (uniqueID != null) {
            firebase = new Firebase("https://fedback-form-643f8-default-rtdb.asia-southeast1.firebasedatabase.app/" + uniqueID);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToFirebase();
            }
        });

        return view;
    }

    private void sendDataToFirebase() {
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String message = comment.getText().toString();
        float rate = rating.getRating();

        Firebase child_name = firebase.child("Name");
        child_name.setValue(name);
        if (name.isEmpty()) {
            Name.setError("This is skrequired field");
            send.setEnabled(false);
        } else {
            Name.setError(null);
            send.setEnabled(true);
        }

        Firebase child_email = firebase.child("Email");
        child_email.setValue(email);
        if (email.isEmpty()) {
            Email.setError("This is required field");
            send.setEnabled(false);
        } else {
            Email.setError(null);
            send.setEnabled(true);
        }

        Firebase child_message = firebase.child("Comments");
        child_message.setValue(message);
        if (message.isEmpty()) {
            comment.setError("This is required field");
            send.setEnabled(false);
        } else {
            comment.setError(null);
            send.setEnabled(true);
        }

        Firebase child_rate = firebase.child("Rate");
        child_rate.setValue(rate);
        if (rate == 0) {
            ratingError.setText("This is a required field");
            send.setEnabled(false);
        } else {
            ratingError.setText(null);
            send.setEnabled(true);
        }

        Toast.makeText(requireContext(), "Your data was sent to the server", Toast.LENGTH_SHORT).show();

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailsDialog(name, email, message);
            }
        });
        Fragment_fedback_submitted fragmentSubmitted = new Fragment_fedback_submitted();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentSubmitted);
        transaction.addToBackStack(null);  // Add the transaction to the back stack
        transaction.commit();
    }

    private void showDetailsDialog(String name, String email, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Sent details")
                .setMessage("Name - " + name + "\n\nEmail - " + email + "\n\nComments - " + message)
                .show();
    }
}
