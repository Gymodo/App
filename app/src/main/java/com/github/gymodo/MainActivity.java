package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //Menu burger and drawerlayout
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //Bottom navigation
    BottomNavigationView bottomNav;
    FrameLayout main_frame;
    // Fragments
    private HomeFragment homeFragment;
    private UserReservationsFragment userReservationsFragment;
    Fragment activeFragment;
    private WorkoutListFragment workoutListFragment;


    public static FragmentManager fragmentManager;


    //CardViews
    private CardView cardViewDiet;
    private CardView cardViewWorkout;
    private CardView cardViewReservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hooks
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);

        cardViewDiet = findViewById(R.id.home_diet_cardview);
        cardViewWorkout = findViewById(R.id.home_workout_cardview);
        cardViewReservation = findViewById(R.id.home_reservation_cardview);

        bottomNav = findViewById(R.id.bottomNav);
        main_frame = findViewById(R.id.main_frame);

        homeFragment = new HomeFragment();
        userReservationsFragment = new UserReservationsFragment();
        workoutListFragment = new WorkoutListFragment();

        fragmentManager = getSupportFragmentManager();

        // Makes this activity use our toolbar.
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener((item -> {
            int itemId = item.getItemId();

            // Menu handlers.
            if (itemId == R.id.menu_home) {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.menu_profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.menu_settings) {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.menu_logout) {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }));




        //Bottom navigation

        //Assign the main fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,homeFragment);
        fragmentTransaction.commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_fitness:
                        setFragment(workoutListFragment);
                        return true;

                    case R.id.nav_reservations:
                        setFragment(userReservationsFragment);
                        return true;

                    case R.id.nav_diets:

                        return true;

                    default:
                        return false;

                }

            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Handles the open/close actions of the drawer.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }



}