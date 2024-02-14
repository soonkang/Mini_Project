package com.sp.mini_assignment.Settings;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.sp.mini_assignment.R;


public class FeedbackFragment extends Fragment {

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
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("soonkangt@gmail.com") + "?subject=" + Uri.encode("Feedback") + "$body=" + Uri.encode("");
                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "send emai;"));
            }
        });

        return view;
    }

    private void sendDataToFirebase() {
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String message = comment.getText().toString();
        float rate = rating.getRating();

        boolean isValid = true;

        Firebase child_name = firebase.child("Name");
        if (name.isEmpty()) {
            Name.setError("This is a required field");
            isValid = false;
        } else {
            Name.setError(null);
        }

        Firebase child_email = firebase.child("Email");
        if (email.isEmpty()) {
            Email.setError("This is required field");
            isValid = false;
        } else {
            Email.setError(null);
        }

        Firebase child_message = firebase.child("Comments");
        if (message.isEmpty()) {
            comment.setError("This is required field");
            isValid = false;
        } else {
            comment.setError(null);
        }

        Firebase child_rate = firebase.child("Rate");
        if (rate == 0) {
            ratingError.setText("This is a required field");
            isValid = false;
        } else {
            ratingError.setText(null);
        }

        if (isValid) {
            child_name.setValue(name);
            child_email.setValue(email);
            child_message.setValue(message);
            child_rate.setValue(rate);

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
    }



    private void showDetailsDialog(String name, String email, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Sent details")
                .setMessage("Name - " + name + "\n\nEmail - " + email + "\n\nComments - " + message)
                .show();
    }
}
