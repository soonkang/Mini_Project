package com.sp.mini_assignment.GoogleMap;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.Manifest;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sp.mini_assignment.Adapters.Carpark;
import com.sp.mini_assignment.R;
import com.sp.mini_assignment.databinding.FragmentMapGpsBinding;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;


import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import com.google.maps.android.PolyUtil;




public class carparklocation_map_fragment extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 100;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    private GoogleMap mMap;
    private FragmentMapGpsBinding binding;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location currentLocation;

    private SearchView searchView;

    private ImageView speechTotxt;

    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;

    private boolean isListening = false;

    private LatLng carparkLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentMapGpsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = findViewById(R.id.searchView);

        speechTotxt = findViewById(R.id.speechtoTxt);


        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                carparkLocation = (LatLng) bundle.get("carpark_location");
                if (carparkLocation != null) {
                    // Save the carparkLocation in SharedPreferences
                    saveCarparkLocation(carparkLocation);
                }
            }
        }






        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with map initialization and location request
            initMap();

        } else {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        Places.initialize(getApplicationContext(), "AIzaSyCEhqRluTkjj7OBuaNWJH4ZtkvlUMpcOK4");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform the search query
                performPlaceSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: Implement live search suggestions as the user types
                return false;
            }
        });

        // Check for Mic Permissions
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);

        }


        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            Log.e(TAG, "Speech recognition not supported");
        } else {

            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    // Called when the speech recognition service is ready for the user to start speaking
                }

                @Override
                public void onBeginningOfSpeech() {
                    // Called when the user starts speaking
                    searchView.setQueryHint("Listening...");
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    // Called when the RMS value of the input audio has changed
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    // Called when the audio buffer is received
                }

                @Override
                public void onEndOfSpeech() {
                    // Called when the user stops speaking
                }

                @Override
                public void onError(int error) {
                    // Called when there is an error in speech recognition
                    Log.e(TAG, "Speech recognition error: " + error);
                }

                @Override
                public void onResults(Bundle results) {
                    // Called when the recognition is successful
                    speechTotxt.setImageResource(R.drawable.mic_off_logo);
                    ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (matches != null && !matches.isEmpty()) {
                        String text = matches.get(0); // Get the first result
                        searchView.setQuery(text, false);

                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    // Called when partial recognition results are available
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    // Called when a speech recognition event occurs
                }
            });
        }

        // Start listening for speech input when the ImageView is clicked
        speechTotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                speechTotxt.setImageResource(R.drawable.mic_logo); // mic on
                if (speechRecognizer != null) {
                    if (isListening) {
                        // Stop listening if already listening
                        speechRecognizer.stopListening();
                        speechTotxt.setImageResource(R.drawable.mic_off_logo);
                        isListening = false;
                    } else {
                        // Start listening if not listening
                        speechRecognizer.startListening(speechRecognizerIntent);
                        isListening = true;
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void performPlaceSearch(String query) {
        // Create a new instance of the AutocompleteSessionToken
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Set up the autocomplete request
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(token)
                .setQuery(query)
                .build();

        // Perform the autocomplete request
        PlacesClient placesClient = Places.createClient(this);
        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                // Process each prediction (e.g., display in a list or dropdown)
                Log.i(TAG, prediction.getFullText(null).toString());

                // Assuming the user selects the first prediction
                if (!response.getAutocompletePredictions().isEmpty()) {
                    AutocompletePrediction selectedPrediction = response.getAutocompletePredictions().get(0);
                    String placeId = selectedPrediction.getPlaceId();

                    // Retrieve the place details using the place ID
                    placesClient.fetchPlace(FetchPlaceRequest.newInstance(placeId, Arrays.asList(Place.Field.LAT_LNG)))
                            .addOnSuccessListener((placeResponse) -> {
                                Place place = placeResponse.getPlace();
                                LatLng destinationLatLng= place.getLatLng();
                                // Use the LatLng for further processing (e.g., update map with marker)
                                updateMapWithMarker(destinationLatLng, selectedPrediction.getFullText(null).toString());
                                calculateAndDrawDirections(destinationLatLng);

                            })
                            .addOnFailureListener((exception) -> {
                                // Handle any errors
                                Log.e(TAG, "Place details request failed: " + exception.getMessage());
                            });
                }
            }
        }).addOnFailureListener((exception) -> {
            // Handle any errors
            Log.e(TAG, "Autocomplete prediction request failed: " + exception.getMessage());
        });
    }

    private void calculateAndDrawDirections(LatLng destination) {
        // Check if currentLocation is available
        if (currentLocation != null) {
            new AsyncTask<Void, Void, DirectionsResult>() {
                @Override
                protected DirectionsResult doInBackground(Void... voids) {
                    try {
                        // Create a Directions API request
                        DirectionsApiRequest request = DirectionsApi.newRequest(getGeoContext())
                                .origin(new com.google.maps.model.LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                                .mode(TravelMode.DRIVING); // Specify the travel mode (e.g., driving, walking, etc.)

                        // Execute the request synchronously
                        return request.await();
                    } catch (Exception e) {
                        Log.e(TAG, "Directions API request failed: " + e.getMessage());
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(DirectionsResult result) {
                    // Handle the directions result
                    if (result != null && result.routes != null && result.routes.length > 0) {
                        DirectionsRoute route = result.routes[0];
                        List<LatLng> decodedPath = PolyUtil.decode(route.overviewPolyline.getEncodedPath());
                        mMap.addPolyline(new PolylineOptions().addAll(decodedPath).color(Color.BLUE));
                    }
                }
            }.execute();
        } else {
            // Handle the case when currentLocation is null
            Log.e(TAG, "Current location is null");
        }
    }

    private GeoApiContext getGeoContext() {
        // Create a GeoApiContext with your Google Maps API key
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCEhqRluTkjj7OBuaNWJH4ZtkvlUMpcOK4") // Replace with your Google Maps API key
                .build();
        return geoApiContext;
    }
    private void updateMapWithMarker(LatLng latLng, String title) {
        // Clear any existing markers on the map
        mMap.clear();

        // Add a marker to the map
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title));

        // Move the camera to the marker's position
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);



        carparkLocation = getCarparkLocation();

        if (carparkLocation != null) {
            Log.d("carparkLocation: ", carparkLocation.toString());
            mMap.addMarker(new MarkerOptions()
                    .position(carparkLocation)
                    .title("Carpark Location"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carparkLocation, 15f));
        }

        FloatingActionButton recenterButton = findViewById(R.id.recenterButton);
        recenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recenter the map to the user's current location
                if (currentLocation != null) {
                    LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Location (LIVE)"));
                }
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();

    }

    private void saveCarparkLocation(LatLng carparkLocation) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("carpark_location_lat", String.valueOf(carparkLocation.latitude));
        editor.putString("carpark_location_lng", String.valueOf(carparkLocation.longitude));
        editor.apply();
    }


    private LatLng getCarparkLocation() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String carparkLat = sharedPreferences.getString("carpark_location_lat", null);
        String carparkLng = sharedPreferences.getString("carpark_location_lng", null);

        if (carparkLat != null && carparkLng != null) {
            Log.d("CarparkLatLng", "Retrieving LatLng");
            double latitude = Double.parseDouble(carparkLat);
            double longitude = Double.parseDouble(carparkLng);
            return new LatLng(latitude, longitude);
        } else {
            return null;
        }
    }

}
