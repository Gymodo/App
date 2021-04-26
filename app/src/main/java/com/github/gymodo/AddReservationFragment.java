package com.github.gymodo;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReservationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NavController navController;

    public AddReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddReservationFragment newInstance(String param1, String param2) {
        AddReservationFragment fragment = new AddReservationFragment();
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


        // This callback will only be called when MyFragment is at least Started.
/*
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default *//*) {
            @Override
            public void handleOnBackPressed() {


                int num = getParentFragmentManager().getBackStackEntryCount();
                Toast.makeText(getActivity(), "entra: " + num, Toast.LENGTH_SHORT).show();

                if (num > 0){

                    MainActivity mainActivity = (MainActivity)getActivity();
                    if (mainActivity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mainActivity.drawerLayout.closeDrawer(GravityCompat.START);
                    } else {

                    }
                }


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);*/
        // The callback can be enabled or disabled here or in handleOnBackPressed()

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_reservation, container, false);

        //NavController navController = Navigation.findNavController(view);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {


                int num = getParentFragmentManager().getBackStackEntryCount();
                Toast.makeText(getActivity(), "entra: " + num, Toast.LENGTH_SHORT).show();

                if (num > 0){

                    MainActivity mainActivity = (MainActivity)getActivity();
                    if (mainActivity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mainActivity.drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        getParentFragmentManager().popBackStack();
                    }
                }


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()

        //navController =  Navigation.findNavController(view);


        return view;
    }




}