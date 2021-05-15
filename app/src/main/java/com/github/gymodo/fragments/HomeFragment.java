package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.MainActivity;
import com.github.gymodo.R;
import com.github.gymodo.adapters.PostsAdapter;
import com.github.gymodo.social.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardViewAddReservation = view.findViewById(R.id.home_reservation_cardview);
        CardView cardViewDiets = view.findViewById(R.id.home_diet_cardview);
        CardView cardViewWorkouts = view.findViewById(R.id.home_workout_cardview);

        MainActivity mainActivity = (MainActivity) getActivity();

        firebaseAuth = FirebaseAuth.getInstance();

        cardViewAddReservation.setOnClickListener(v -> {
            //Select another host frgament
            MainActivity mainActivity1 = (MainActivity) getActivity();
            mainActivity1.setHostFragment(3);

            Fragment fragment = mainActivity1.adapter.getItem(mainActivity1.viewPager.getCurrentItem());

            NavController myNavController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());
            myNavController.navigate(R.id.user_reservations_to_addReservation);
        });

        cardViewDiets.setOnClickListener(v -> {
            //Select another host frgament
            mainActivity.setHostFragment(4);

            Fragment fragment = mainActivity.adapter.getItem(mainActivity.viewPager.getCurrentItem());
            NavController myNavController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());
            myNavController.navigate(R.id.myDietsFragment);
        });

        cardViewWorkouts.setOnClickListener(v -> {
            mainActivity.setHostFragment(1);

            Fragment fragment = mainActivity.adapter.getItem(mainActivity.viewPager.getCurrentItem());

            NavController myNavController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());
            myNavController.navigate(R.id.workoutListFragment);
        });

        return view;
    }

}