package com.github.gymodo.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.reservation.Reservation;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class UserReservationsAdapter extends RecyclerView.Adapter<UserReservationsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Reservation> mReservations;

    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYYY");


    public UserReservationsAdapter(Context mContext, List<Reservation> mReservations) {
        this.mContext = mContext;
        this.mReservations = mReservations;
    }

    @NonNull
    @Override
    public UserReservationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.reservations_row, parent, false);

        return new UserReservationsAdapter.MyViewHolder(view);
    }

    String displayMinutes(int m){
        String min = m + "";

        if (m < 10){
            min = "0" + m;
        }

        return min;
    }

    @Override
    public void onBindViewHolder(@NonNull UserReservationsAdapter.MyViewHolder holder, int position) {

        Date date = mReservations.get(position).getDate();
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date

        holder.reserv_row_start_time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + displayMinutes(calendar.get(Calendar.MINUTE)));

        long timeInSecs = calendar.getTimeInMillis();
        Date endTimeDate = new Date(timeInSecs + (mReservations.get(position).getDuration() * 60 * 1000));
        calendar.setTime(endTimeDate);

        holder.reserv_row_end_time.setText( calendar.get(Calendar.HOUR_OF_DAY) + ":" + displayMinutes(calendar.get(Calendar.MINUTE)));

        holder.reserv_row_date.setText(sdf.format(date));

        ObjectAnimator objectAnimator;

        if(position%2 == 0){

            objectAnimator = ObjectAnimator.ofFloat(holder.reserv_row_layout, "x", 1000f, 0f);

        } else {
            objectAnimator = ObjectAnimator.ofFloat(holder.reserv_row_layout, "x", -1000f, 0f);
        }
        objectAnimator.setDuration(500);
        objectAnimator.start();



    }

    @Override
    public int getItemCount() {
        return mReservations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reserv_row_start_time;
        TextView reserv_row_end_time;
        TextView reserv_row_date;

        ConstraintLayout reserv_row_layout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reserv_row_start_time = itemView.findViewById(R.id.reserv_row_start_time);
            reserv_row_end_time = itemView.findViewById(R.id.reserv_row_end_time);
            reserv_row_date = itemView.findViewById(R.id.reserv_row_date);
            reserv_row_layout = itemView.findViewById(R.id.reserv_row_layout);



        }
    }
}
