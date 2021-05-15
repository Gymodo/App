package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.ReservationAdapter;
import com.github.gymodo.reservation.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddReservationFragment extends Fragment {
    private List<Reservation> reservationList;
    ReservationAdapter reservationAdapter;

    CalendarView calendarView;
    RecyclerView recyclerView;

    public AddReservationFragment() {
        // Required empty public constructor
    }

    public static AddReservationFragment newInstance(String param1, String param2) {
        AddReservationFragment fragment = new AddReservationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void showHours(Date date, int year, int month, int day){

        Log.d("hours", "entra");

        Reservation.listPastDate(date).addOnSuccessListener(new OnSuccessListener<List<Reservation>>() {
            @Override
            public void onSuccess(List<Reservation> reservations) {
                reservationList = reservations;
                if (reservationList.size() > 0){
                    Toast.makeText(getContext(), "SIZE: " + reservationList.get(0).getDate().toString(), Toast.LENGTH_SHORT).show();
                } else {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, day);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);

                    for (int i = 8; i <= 23 ;i++){
                        Reservation r = new Reservation();

                        cal.set(Calendar.HOUR_OF_DAY,i);
                        Date d = cal.getTime();

                        r.setDate(d);


                        r.setDuration(60);
                        Log.d("dates", date.toString());

                        reservationList.add(r);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
                reservationAdapter = new ReservationAdapter(getContext(), reservationList);
                recyclerView.setAdapter(reservationAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "No reservations found", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_reservation, container, false);


        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.reservationsRecyclerView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Calendar cal = Calendar.getInstance();
        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int date  = cal.get(Calendar.DATE);
        cal.clear();
        cal.set(year, month, date);
        long todayMillis2 = cal.getTimeInMillis();

        calendarView.setMinDate(todayMillis2);//Set min date in order to prevent the user to make a reservation for yesterday


        calendarView.setOnDateChangeListener((view1, year1, month1, dayOfMonth) -> {

            Date selectedDate = new GregorianCalendar(year1, (month1 + 1), dayOfMonth).getTime();

            showHours(selectedDate, year1, month1, dayOfMonth);

        });


        return view;
    }




}