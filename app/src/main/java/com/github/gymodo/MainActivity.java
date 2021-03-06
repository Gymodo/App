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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.github.gymodo.fragments.admin.AdminActivity;
import com.github.gymodo.fragments.base_fragments.PostBaseFragment;
import com.github.gymodo.reservation.Reservation;
import com.github.gymodo.sharedPreferences.MySharedPreferences;
import com.github.gymodo.fragments.base_fragments.DietBaseFragment;
import com.github.gymodo.fragments.base_fragments.HomeBaseFragment;
import com.github.gymodo.fragments.base_fragments.ReservationBaseFragment;
import com.github.gymodo.fragments.base_fragments.WorkoutBaseFragment;
import com.github.gymodo.adapters.ViewPagerAdapter;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mainActivityFirebaseAuth;
    private FirebaseUser firebaseUser;

    //Menu burger and drawerlayout
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //Bottom navigation
    BottomNavigationView bottomNav;
    public ViewPagerAdapter adapter;
    public ViewPager viewPager;
    MenuItem prevMenuItem;
    int previousHostFragment;

    private final Stack<Integer> backstack = new Stack<>();

    //CardViews
    private CardView cardViewDiet;
    private CardView cardViewWorkout;
    private CardView cardViewReservation;

    //TextView
    private TextView headerUsername;


    @Override
    protected void onResume() {
        super.onResume();

        backstack.pop();
        bottomNav.setSelectedItemId(bottomNav.getSelectedItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hooks
        mainActivityFirebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);

        bottomNav = findViewById(R.id.bottomNav);
        viewPager = findViewById(R.id.viewPager);

        headerUsername = navigationView.getHeaderView(0).findViewById(R.id.textHeaderUsername);

        //Set username


        User.getByID(FirebaseAuth.getInstance().getUid()).addOnSuccessListener(user -> {
            headerUsername.setText(user.getName());
            //Toast.makeText(this, ""+user.getName(), Toast.LENGTH_SHORT).show();
        });


        //Initialize backstack
        if (backstack.empty()) {
            backstack.push(0);
        }

        // Makes this activity use our toolbar.

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Log.d("ADMIN", "calling");


        User.getByID(mainActivityFirebaseAuth.getCurrentUser().getUid()).addOnSuccessListener(user -> {
            Log.d("ADMIN", "got user");
            if (user.isAdmin()) {
                Log.d("ADMIN", "user is admin");
                MenuItem menuAdmin = navigationView.getMenu().findItem(R.id.menu_admin);
                menuAdmin.setVisible(true);
            }
        });

        navigationView.setNavigationItemSelectedListener((item -> {
            int itemId = item.getItemId();

            // Menu handlers.
            if (itemId == R.id.menu_home) {
                setHostFragment(0);
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (itemId == R.id.menu_news) {
                startActivity(new Intent(this, NewsActivity.class));
            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (itemId == R.id.menu_logout) {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                signOut();
                MySharedPreferences.deleteSharedPref(this);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(this, AdminActivity.class));
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

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        //viewPager.setCurrentItem(0, false);
                        setHostFragment(0);
                        return true;

                    case R.id.nav_fitness:
                        //viewPager.setCurrentItem(1, false);
                        setHostFragment(1);

                        return true;

                    case R.id.nav_posts:
                        //viewPager.setCurrentItem(2, false);
                        setHostFragment(2);
                        return true;

                    case R.id.nav_reservations:
                        //viewPager.setCurrentItem(2, false);
                        setHostFragment(3);
                        return true;

                    case R.id.nav_diets:
                        setHostFragment(4);
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

    private void setupViewPager(ViewPager viewPager) {

        HomeBaseFragment homeBaseFragment = new HomeBaseFragment();
        WorkoutBaseFragment workoutBaseFragment = new WorkoutBaseFragment();
        PostBaseFragment postBaseFragment = new PostBaseFragment();
        ReservationBaseFragment reservationBaseFragment = new ReservationBaseFragment();
        DietBaseFragment dietBaseFragment = new DietBaseFragment();

        adapter.addFragment(homeBaseFragment);
        adapter.addFragment(workoutBaseFragment);
        adapter.addFragment(postBaseFragment);
        adapter.addFragment(reservationBaseFragment);
        adapter.addFragment(dietBaseFragment);
        viewPager.setAdapter(adapter);

        //initialize previous item variable
        previousHostFragment = 0;
    }

    public void setHostFragment(int position) {

        if (position == 0) { //If is the home fragment, display the toolbar
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }

        viewPager.setCurrentItem(position, false);
        backstack.push(position);

        Log.d("stack", "Size: " + backstack.size());

    }


    @Override
    public void onBackPressed() {

        Fragment fragment = adapter.getItem(viewPager.getCurrentItem());

        NavController navController = NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());

        //Toast.makeText(this, "item: " + navController.getCurrentDestination(), Toast.LENGTH_SHORT).show();

        // Handles the open/close actions of the drawer.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            if (!navController.navigateUp()) {//Si el fragment no puede ir atras pues se va

                //Toast.makeText(this, "BACKSTACK: " + backstack.size(), Toast.LENGTH_SHORT).show();
                if (backstack.size() > 1) { //Si no es la home
                    //viewPager.setCurrentItem(previousHostFragment, false);
                    backstack.pop();
                    if (backstack.peek() == 0) {
                        toolbar.setVisibility(View.VISIBLE);
                    }
                    viewPager.setCurrentItem(backstack.peek(), false);
                } else {
                    super.onBackPressed();
                }
            }
        }

        //super.onBackPressed();
    }

    public void signOut() {
        mainActivityFirebaseAuth.signOut();
        onBackPressed();
    }


}