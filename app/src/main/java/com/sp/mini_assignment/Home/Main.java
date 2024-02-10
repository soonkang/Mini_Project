package com.sp.mini_assignment.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sp.mini_assignment.About.AboutFragment;
import com.sp.mini_assignment.Adapters.CarparkAdapter;
import com.sp.mini_assignment.Adapters.Carpark;
import com.sp.mini_assignment.FavouriteCarpark.FavouriteCarpark;
import com.sp.mini_assignment.History.HistoryFragment;
import com.sp.mini_assignment.GoogleMap.Map_GPS_Fragment;
import com.sp.mini_assignment.Interfaces.OnItemClickListener;
import com.sp.mini_assignment.QRScanner.QRScannerFragment;
import com.sp.mini_assignment.R;
import com.sp.mini_assignment.Adapters.SearchResultsAdapter;
import com.sp.mini_assignment.Settings.SettingsFragment;
import com.sp.mini_assignment.SlotSelect.SlotSelectFragment;

import java.util.ArrayList;
import java.util.List;


import com.sp.mini_assignment.Adapters.favouriteCarparkHelper;


public class Main extends AppCompatActivity implements OnItemClickListener{


    private static final int MENU_ITEM_SETTING = R.id.nav_setting;
    private static final int MENU_ITEM_ABOUT = R.id.nav_about;

    private static final int MENU_ITEM_PROFILE = R.id.nav_profile;

    private static final int MENU_ITEM_HELP_CENTRE = R.id.nav_help_centre;

    private static final int MENU_ITEM_LOGOUT = R.id.nav_logout;

    private static final int MENU_ITEM_EXIT = R.id.nav_exit;



    DrawerLayout drawerLayout;
    NavigationView navigationView;

    BottomNavigationView btmNavigationView;
    ActionBarDrawerToggle drawerToggle;

    ImageView nav_drawer, dots_menu;

    EditText home_searchBar;

    RecyclerView nearestCarparks, home_search_menu;

    CarparkAdapter carparkAdapter;

    SearchResultsAdapter searchResultsAdapter;



    List<Carpark> carparkList, searchResultsList;

    favouriteCarparkHelper favouriteCarparkHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        // List of lists so thus call ArrayList class
        carparkList = new ArrayList<>();
        searchResultsList = new ArrayList<>();

        home_search_menu = findViewById(R.id.home_search_menu);

        nearestCarparks = findViewById(R.id.nearestCarparks);
        home_searchBar = findViewById(R.id.home_searchBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        nav_drawer = findViewById(R.id.nav_drawer);
        dots_menu = findViewById(R.id.dots_menu);
        btmNavigationView = findViewById(R.id.btmNavigationView);

        btmNavigationView.setOnItemSelectedListener(this::onBottomNavigationItemSelected);

        nearestCarparks.setLayoutManager(new LinearLayoutManager(this));
        home_search_menu.setLayoutManager(new LinearLayoutManager(this));

        carparkAdapter = new CarparkAdapter(carparkList, this, favouriteCarparkHelper);
        nearestCarparks.setAdapter(carparkAdapter);

        carparkAdapter.setOnItemClickListener(this);


        searchResultsAdapter = new SearchResultsAdapter(searchResultsList, carparkList, this);

        home_search_menu.setAdapter(searchResultsAdapter);

        favouriteCarparkHelper = new favouriteCarparkHelper(this);









        // Get a reference to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://carparks-ddecb-default-rtdb.asia-southeast1.firebasedatabase.app");


        // Retrieve data from nodes in Firebase Realtime Database
        DatabaseReference pasirRisRef = database.getReference().child("Carpark_PasirRis");
        DatabaseReference jurongEastRef = database.getReference().child("Carpark_JurongEast");

        // Manually inserting 'fake' data to database as retrieving live data is hard

        pasirRisRef.child("carparkName").setValue("Pasir Ris Drive 3, Carpark B 45, Singapore 292192");
        pasirRisRef.child("carparkDistance").setValue("3.9km");
        pasirRisRef.child("carparkImage").setValue("gs://carparks-ddecb.appspot.com/02445A1C-2F0C-4E32-AF5D-87B8186CC01D.jpeg");
        pasirRisRef.child("carparkLevel").setValue("Slot 2");
        pasirRisRef.child("carparkDate").setValue("Monday");
        pasirRisRef.child("carparkPrice").setValue("\n" +
                "Rate: 7am-7pm | $1.23/1st 30mins\n" +
                "                      | $0.05/sub min\n" +
                "       7pm-11:30pm| $0.03/min");

        jurongEastRef.child("carparkName").setValue("Jurong Street 21, Carpark A 45, Singapore 381929");
        jurongEastRef.child("carparkDistance").setValue("5.2km");
        jurongEastRef.child("carparkImage").setValue("gs://carparks-ddecb.appspot.com/F104115A-27F1-4D1C-A4C4-18F6012E1175.webp");
        jurongEastRef.child("carparkLevel").setValue("Slot 6");
        jurongEastRef.child("carparkDate").setValue("Monday");
        jurongEastRef.child("carparkPrice").setValue("\n" +
                "Rate: 7am-7pm | $1.23/1st 30mins\n" +
                "                      | $0.05/sub min\n" +
                "       7pm-11:30pm| $0.03/min");

        // Retrieve data from Carpark_PasirRis node
        pasirRisRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String carparkName = dataSnapshot.child("carparkName").getValue(String.class);
                String carparkDistance = dataSnapshot.child("carparkDistance").getValue(String.class);
                String carparkImage = dataSnapshot.child("carparkImage").getValue(String.class);
                String carparkLevel = dataSnapshot.child("carparkLevel").getValue(String.class);
                String carparkDate = dataSnapshot.child("carparkDate").getValue(String.class);
                String carparkPrice = dataSnapshot.child("carparkPrice").getValue(String.class);

                // Create a Carpark object using the retrieved data
                Carpark carpark = new Carpark(carparkImage, carparkName, carparkDistance, carparkLevel, carparkDate, carparkPrice, "0",1);
                // Add the carpark object to the list
                carparkList.add(carpark);
                Log.d("FirebaseData", "carparkName: " + carparkName + ", carparkDistance: " + carparkDistance + ", carparkImage: " + carparkImage);


                // Get a reference to the image in Firebase Storage
                if (carparkImage != null && !carparkImage.isEmpty()) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference imageRef = storage.getReferenceFromUrl(carparkImage);

                    // Get the download URL for the image
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Add the obtained URL to the carpark object
                        carpark.setCarparkImage(uri.toString());
                        // Notify the adapter that the data set has changed
                        carparkAdapter.notifyDataSetChanged();
                    }).addOnFailureListener(exception -> {
                        // Handle any errors that may occur while getting the download URL for an image
                        Log.e("FirebaseData", "Error getting download URL", exception);
                    });
                }

                // Notify the adapter that the data set has changed
                carparkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e("FirebaseData", "Database error: " + databaseError.getMessage());
            }
        });

// Retrieve data from Carpark_JurongEast node
        jurongEastRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String carparkName = dataSnapshot.child("carparkName").getValue(String.class);
                String carparkDistance = dataSnapshot.child("carparkDistance").getValue(String.class);
                String carparkImage = dataSnapshot.child("carparkImage").getValue(String.class);
                String carparkLevel = dataSnapshot.child("carparkLevel").getValue(String.class);
                String carparkDate = dataSnapshot.child("carparkDate").getValue(String.class);
                String carparkPrice = dataSnapshot.child("carparkPrice").getValue(String.class);

                // Create a Carpark object using the retrieved data
                Carpark carpark = new Carpark(carparkImage, carparkName, carparkDistance, carparkLevel, carparkDate, carparkPrice, "0", 2);
                // Checking if Image is  passed as URL
                String carparkUrl  = carpark.getCarparkImage();
                Log.d("carparkURL" , carparkUrl);
                // Add the carpark object to the list
                carparkList.add(carpark);

                // Get a reference to the image in Firebase Storage
                if (carparkImage != null && !carparkImage.isEmpty()) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference imageRef = storage.getReferenceFromUrl(carparkImage);

                    // Get the download URL for the image
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Add the obtained URL to the carpark object
                        carpark.setCarparkImage(uri.toString());
                        // Notify the adapter that the data set has changed
                        carparkAdapter.notifyDataSetChanged();
                    }).addOnFailureListener(exception -> {
                        // Handle any errors that may occur while getting the download URL for an image
                        Log.e("FirebaseData", "Error getting download URL", exception);
                    });
                }


                // Notify the adapter that the data set has changed
                carparkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e("FirebaseData", "Database error: " + databaseError.getMessage());
            }
        });




        home_searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // EditText has gained focus
                        Log.d("EditTextFocus", "EditText has gained focus");
                        home_search_menu.setVisibility(View.VISIBLE);
                        searchResultsAdapter.updateSearchResults(getCurrentQuery());

                        // Show or hide the search menu based on your logic
                    } else {
                        // EditText has lost focus
                        Log.d("EditTextFocus", "EditText has lost focus");
                        home_search_menu.setVisibility(View.GONE);
                        // Hide the search menu if needed
                    }
                }
            });



        home_searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Called when the text in the EditText changes

                searchResultsAdapter.updateSearchResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used, but required to implement TextWatcher
            }
        });



        nav_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialise drawer state when activity first boot up
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        dots_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Main.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.details, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item clicks here
                        int itemId = item.getItemId();

                        if(itemId == R.id.dots_menu_item_about)
                        {
                            replaceFragment(new AboutFragment());
                                return true;
                        }

                        if(itemId == R.id.dots_menu_item_map_view)
                        {
                            Intent intent = new Intent(Main.this, Map_GPS_Fragment.class);
                            startActivity(intent);
                            return true;
                        }

                        if(itemId == R.id.dots_menu_item_favourites)
                        {
                            FavouriteCarpark fragment = new FavouriteCarpark(favouriteCarparkHelper );
                            replaceFragment(fragment);
                            return true;
                        }
                        return false;
                    }


                });
                popupMenu.show();
            }
        });


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        // Set ActionBarDrawToggle as the listener for DrawerLayout
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                btmNavigationView.getMenu().findItem(btmNavigationView.getSelectedItemId()).setChecked(false);





                if (itemId == MENU_ITEM_SETTING) {
                    replaceFragment(new SettingsFragment());
                    drawerLayout.closeDrawer(navigationView);
                    btmNavigationView.getMenu().findItem(btmNavigationView.getSelectedItemId()).setChecked(false);



                    return true;

                } else if (itemId == MENU_ITEM_ABOUT) {
                    replaceFragment(new AboutFragment());
                    drawerLayout.closeDrawer(navigationView);
                    return true;


               /* } else if (itemId == MENU_ITEM_PROFILE) {
                    replaceFragment(new ProfileFragment());
                    drawerLayout.closeDrawer(navigationView);
                    return true;

                } else if (itemId == MENU_ITEM_HELP_CENTRE) {
                    replaceFragment(new HelpCentreFragment());
                    drawerLayout.closeDrawer(navigationView);
                    return true;

                } else if (itemId == MENU_ITEM_LOGOUT) {
                    replaceFragment(new LogoutFragment());
                    drawerLayout.closeDrawer(navigationView);
                    return true;

                } else if (itemId == MENU_ITEM_EXIT) {
                    replaceFragment(new ExitFragment());
                    drawerLayout.closeDrawer(navigationView);
                    return true;
                } */

                }
                return false;
            }
        });



    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private boolean onBottomNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); // Get the ID of the selected item

        if (itemId == R.id.qr_page) {
            replaceFragment(new QRScannerFragment());
            return true;
        } else if(itemId == R.id.home_page) {
            replaceFragment(new HomeFragment());
            return true;
        } else if (itemId == R.id.history_page) {
        replaceFragment(new HistoryFragment());
        return true; }
        /* else if (itemId == R.id.notifications_page) {
            replaceFragment(new NotificationsFragment());
            return true;
        } */




        return false;
    }


    public String getCurrentQuery() {
        return home_searchBar.getText().toString();
    }



    @Override
    public void onItemClick(int position) {
        // Handle the item click event
        Carpark clickedCarpark = carparkList.get(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable("carpark", clickedCarpark);

        SlotSelectFragment fragment = new SlotSelectFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }




}
