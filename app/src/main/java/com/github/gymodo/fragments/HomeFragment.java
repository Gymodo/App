package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.MainActivity;
import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AddReservationFragment addReservationFragment = new AddReservationFragment();

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        cardViewAddReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Select another host frgament
                mainActivity.setHostFragment(2);

                Fragment fragment = mainActivity.adapter.getItem(mainActivity.viewPager.getCurrentItem());

                NavController myNavController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());
                myNavController.navigate(R.id.user_reservations_to_addReservation);


            }
        });

        cardViewDiets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select another host frgament
                mainActivity.setHostFragment(3);

                Fragment fragment = mainActivity.adapter.getItem(mainActivity.viewPager.getCurrentItem());

                NavController myNavController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());
                myNavController.navigate(R.id.myDietsFragment);


            }
        });

        cardViewWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setHostFragment(1);

                Fragment fragment = mainActivity.adapter.getItem(mainActivity.viewPager.getCurrentItem());

                NavController myNavController =  NavHostFragment.findNavController(fragment.getChildFragmentManager().getPrimaryNavigationFragment());
                myNavController.navigate(R.id.workoutListFragment);
            }
        });

        return view;
    }
}