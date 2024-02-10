package com.sp.mini_assignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.io.File;


public class fragment_profile extends Fragment {

    private ImageView placeImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String imagePath;
    EditText Name,Email,Password,DOB,Phone;
    Button send;
    Firebase firebase;

    public fragment_profile() {
        // Required empty public constructor
    }

    public static fragment_profile newInstance(String param1, String param2) {
        return new fragment_profile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        placeImage = view.findViewById(R.id.place_image);
        Button selectImageButton = view.findViewById(R.id.Image);
        Name = view.findViewById(R.id.name2);
        Email = view.findViewById(R.id.email2);
        Password = view.findViewById(R.id.password);
        DOB = view.findViewById(R.id.birth);
        Phone = view.findViewById(R.id.phone);
        send = view.findViewById((R.id.credit));

        Firebase.setAndroidContext(requireContext());

        String uniqueID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (uniqueID != null) {
            firebase = new Firebase("https://profile-4c7c0-default-rtdb.asia-southeast1.firebasedatabase.app/" + uniqueID);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToFirebase();
            }

            private void sendDataToFirebase() {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String dob = DOB.getText().toString();
                String phone = Phone.getText().toString();

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

                Firebase child_password = firebase.child("Password");
                child_password.setValue(password);
                if (password.isEmpty()) {
                    Password.setError("This is required field");
                    send.setEnabled(false);
                } else {
                    Password.setError(null);
                    send.setEnabled(true);
                }

                Firebase child_dob = firebase.child("Date Of Birth");
                child_dob.setValue(dob);
                if (dob.isEmpty()) {
                    DOB.setError("This is required field");
                    send.setEnabled(false);
                } else {
                    DOB.setError(null);
                    send.setEnabled(true);
                }

                Firebase child_phone = firebase.child("Phone");
                child_phone.setValue(phone);
                if (phone.isEmpty()) {
                    Phone.setError("This is required field");
                    send.setEnabled(false);
                } else {
                    Phone.setError(null);
                    send.setEnabled(true);
                }

                Toast.makeText(requireContext(), "Your data was sent to the server", Toast.LENGTH_SHORT).show();
            }
        });



        // Set a click listener for the "Select Image" button
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image gallery when the button is clicked
                openImagePicker();
            }
        });

        return view;
    }

    // Open the image picker to select an image
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of the image picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            placeImage.setImageURI(imageUri);

            // Save the image URI or path to your database
            imagePath = getImagePathFromUri(imageUri);

            // Now, get the URI using FileProvider directly from imageUri
            Uri fileProviderUri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getApplicationContext().getPackageName() + ".provider",
                    new File(imagePath)
            );

            // Do something with fileProviderUri if needed
        }
    }

    private String getImagePathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }

        return null;
    }
}
