package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.gymodo.fragments.base_fragments.DietBaseFragment;
import com.github.gymodo.fragments.base_fragments.HomeBaseFragment;
import com.github.gymodo.fragments.base_fragments.ReservationBaseFragment;
import com.github.gymodo.fragments.base_fragments.WorkoutBaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    //Menu burger and drawerlayout
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //Bottom navigation
    BottomNavigationView bottomNav;
    ViewPagerAdapter adapter;
    private ViewPager viewPager;
    MenuItem prevMenuItem;
    int previousHostFragment;

    private Stack<Integer> backstack = new Stack<>();


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
        viewPager = findViewById(R.id.viewPager);

        //Initialize backstack
        if (backstack.empty()){
            backstack.push(0);
        }

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
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                previousHostFragment = viewPager.getCurrentItem();

                switch (item.getItemId()){

                    case R.id.nav_home:
                        //viewPager.setCurrentItem(0, false);
                        setHostFragment(0);
                        return true;

                    case R.id.nav_fitness:
                        //viewPager.setCurrentItem(1, false);
                        setHostFragment(1);

                        return true;

                    case R.id.nav_reservations:
                        //viewPager.setCurrentItem(2, false);
                        setHostFragment(2);
                        return true;

                    case R.id.nav_diets:
                        setHostFragment(3);
                        return true;

                    default:
                        return false;

                }

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNav.getMenu().getItem(0).setChecked(false);

                bottomNav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNav.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){

        HomeBaseFragment homeBaseFragment = new HomeBaseFragment();
        WorkoutBaseFragment workoutBaseFragment = new WorkoutBaseFragment();
        ReservationBaseFragment reservationBaseFragment = new ReservationBaseFragment();
        DietBaseFragment dietBaseFragment = new DietBaseFragment();

        adapter.addFragment(homeBaseFragment);
        adapter.addFragment(workoutBaseFragment);
        adapter.addFragment(reservationBaseFragment);
        adapter.addFragment(dietBaseFragment);
        viewPager.setAdapter(adapter);

        //initialize previous item variable
        previousHostFragment = 0;
    }

    private void setHostFragment(int position){

        viewPager.setCurrentItem(position, false);
        backstack.push(position);

        Log.d("stack", "Size: " + backstack.size());

    }


    @Override
    public void onBackPressed() {

        Fragment fragment = adapter.getItem(viewPager.getCurrentItem());

        NavController navController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());

        //Toast.makeText(this, "item: " + navController.getCurrentDestination(), Toast.LENGTH_SHORT).show();

        // Handles the open/close actions of the drawer.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            //Toast.makeText(this, "Num stack " + navController.getBackStack().size(), Toast.LENGTH_SHORT).show();
            if (!navController.navigateUp()){//Si el fragment no puede ir atras pues se va

                Toast.makeText(this, "BACKSTACK: " + backstack.size(), Toast.LENGTH_SHORT).show();
                if (backstack.size() > 1){ //Si no es la home
                    //viewPager.setCurrentItem(previousHostFragment, false);
                    backstack.pop();
                    viewPager.setCurrentItem(backstack.peek(), false);
                } else  {
                    super.onBackPressed();
                }
            }
            /*
            if (navController.navigateUp()){

                Toast.makeText(this, "can navigate up", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CAN'T navigate up", Toast.LENGTH_SHORT).show();
            }*/
        }

        //super.onBackPressed();
    }



}