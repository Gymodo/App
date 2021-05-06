package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.ReservationAdapter;
import com.github.gymodo.adapters.UserReservationsAdapter;
import com.github.gymodo.reservation.Reservation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserReservationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseAuth firebaseAuth;
    UserReservationsAdapter reservationAdapter;

    public UserReservationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserReservationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserReservationsFragment newInstance(String param1, String param2) {
        UserReservationsFragment fragment = new UserReservationsFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_reservations, container, false);
        List<Reservation> userReservations = new ArrayList<>();

        //hook
        RecyclerView userReservationsRecyclerView = view.findViewById(R.id.userReservationsRecyclerView);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);

        firebaseAuth = FirebaseAuth.getInstance();

        //TODO, query to get only user reservations directly

        Reservation.listAll().addOnSuccessListener(reservations -> {
            for (Reservation r : reservations) {

                if (r.getUserIds().contains(firebaseAuth.getCurrentUser().getUid())) {
                    userReservations.add(r);
                }
            }

            Collections.sort(userReservations, new Comparator<Reservation>() {
                public int compare(Reservation o1, Reservation o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            userReservationsRecyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
            reservationAdapter = new UserReservationsAdapter(getContext(), userReservations);
            userReservationsRecyclerView.setAdapter(reservationAdapter);

        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(view);

                navController.navigate(R.id.user_reservations_to_addReservation);
            }
        });

        return view;
    }
}