package com.github.gymodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.reservation.Reservation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Reservation> mReservations;
    FirebaseAuth firebaseAuth;

    public ReservationAdapter(Context mContext, List<Reservation> mReservations) {
        this.mContext = mContext;
        this.mReservations = mReservations;
    }

    @NonNull
    @Override
    public ReservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        firebaseAuth = FirebaseAuth.getInstance();
        View view = layoutInflater.inflate(R.layout.reservation_hours_row, parent, false);

        return new MyViewHolder(view);
    }

    String displayMinutes(int m){
        String min = m + "";

        if (m < 10){
            min = "0" + m;
        }

        return min;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position) {

        Date date = mReservations.get(position).getDate();
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date

        holder.reserv_row_start_time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + displayMinutes(calendar.get(Calendar.MINUTE)));

        long timeInSecs = calendar.getTimeInMillis();
        Date endTimeDate = new Date(timeInSecs + (mReservations.get(position).getDuration() * 60 * 1000));
        calendar.setTime(endTimeDate);

        holder.reserv_row_end_time.setText( calendar.get(Calendar.HOUR_OF_DAY) + ":" + displayMinutes(calendar.get(Calendar.MINUTE)));

        holder.reserv_row_layout.setOnClickListener(v -> {

            //Toast.makeText(mContext, "Reservation for: " + mReservations.get(position).getDate().toString(), Toast.LENGTH_SHORT).show();
            //Save reservation on BBDD

            //mReservations.get(position).addUserId(firebaseAuth.getCurrentUser().getUid());
            //mReservations.get(position).save().addOnSuccessListener(aVoid -> Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show());

        });
    }

    @Override
    public int getItemCount() {
        return mReservations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reserv_row_start_time;
        TextView reserv_row_end_time;

        ConstraintLayout reserv_row_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reserv_row_start_time = itemView.findViewById(R.id.textStartHour);
            reserv_row_end_time = itemView.findViewById(R.id.textEndHour);

            reserv_row_layout = itemView.findViewById(R.id.row_reservationConstraint);
        }
    }
}
