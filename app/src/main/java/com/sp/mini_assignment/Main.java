package com.sp.mini_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Main extends AppCompatActivity {

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

    LinearLayout home_searchBar;

    RecyclerView nearestCarparks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        nearestCarparks = findViewById(R.id.nearestCarparks);
        home_searchBar = findViewById(R.id.home_searchBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        nav_drawer = findViewById(R.id.nav_drawer);
        dots_menu = findViewById(R.id.dots_menu);
        btmNavigationView = findViewById(R.id.btmNavigationView);

        btmNavigationView.setOnItemSelectedListener(this::onBottomNavigationItemSelected);

        nearestCarparks.setLayoutManager(new LinearLayoutManager(this));

        home_searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (itemId == MENU_ITEM_SETTING) {
                    replaceFragment(new SettingsFragment());
                    drawerLayout.closeDrawer(navigationView);
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
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null); // Add to stack to handle back clicks
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private boolean onBottomNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); // Get the ID of the selected item

        if (itemId == R.id.qr_page) {
            replaceFragment(new QRScannerFragment());
            return true;
        } else if (itemId == R.id.home_page) {
            replaceFragment(new HomeFragment());
            return true; }
        /* else if (itemId == R.id.notifications_page) {
            replaceFragment(new NotificationsFragment());
            return true;
        } */




        return false;
    }



}
