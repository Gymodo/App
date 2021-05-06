package com.github.gymodo.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.MainActivity;
import com.github.gymodo.R;
import com.github.gymodo.reservation.Reservation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Reservation> mReservations;
    FirebaseAuth firebaseAuth;

    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYYY-hh:mm");
    SimpleDateFormat sdfDate=new SimpleDateFormat("dd/MM/YYYY");
    SimpleDateFormat sdfTime=new SimpleDateFormat("hh:mm");

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

        int startHour = calendar.get(Calendar.HOUR_OF_DAY);

        holder.reserv_row_start_time.setText( startHour + ":" + displayMinutes(calendar.get(Calendar.MINUTE)));

        long timeInSecs = calendar.getTimeInMillis();
        Date endTimeDate = new Date(timeInSecs + (mReservations.get(position).getDuration() * 60 * 1000));
        calendar.setTime(endTimeDate);

        holder.reserv_row_end_time.setText( calendar.get(Calendar.HOUR_OF_DAY) + ":" + displayMinutes(calendar.get(Calendar.MINUTE)));

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        Log.d("time", "MY " + hour  + " fire_ " + calendar.get(Calendar.HOUR_OF_DAY));

        holder.reserv_row_layout.setClickable(false);

        if (hour < startHour){

            holder.reserv_row_start_time.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
            holder.reserv_row_end_time.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
            holder.min_hour_separator.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));

            holder.reserv_row_layout.setOnClickListener(v -> {

                Reservation.listAll().addOnSuccessListener(reservations -> {

                    String myDateStr = sdf.format( mReservations.get(position).getDate());
                    final boolean[] added = {false};
                    boolean reservationDateExists = false;
                    for (Reservation r : reservations){

                        Log.d("date" , "RESERVA: " + r);

                        String fireDateStr = sdf.format(r.getDate());
                        if(myDateStr.equals(fireDateStr)){ //If the date already exists

                            reservationDateExists = true;

                            Log.d("date" , "esta USER id: "+ Arrays.asList(r.getUserIds().contains(firebaseAuth.getCurrentUser().getUid())));

                            if (!Arrays.asList(r.getUserIds().contains(firebaseAuth.getCurrentUser().getUid())).get(0)){ //Returns true if UserId is in array


                                new MaterialAlertDialogBuilder(mContext)
                                        .setTitle("Make reservation")
                                        .setMessage("Want to make reservation for " + sdfDate.format(date) + " at " + sdfTime.format(date) + "?")
                                        .setPositiveButton( "RESERVE", (dialog, which) -> {

                                            r.addUserId(firebaseAuth.getCurrentUser().getUid());
                                            r.update();

                                            added[0] = true;

                                            MainActivity mainActivity = (MainActivity) mContext;

                                            mainActivity.onBackPressed();

                                        })

                                        .setNegativeButton("CANCEL", (dialog, which) -> Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show())

                                        .show();


                            } else {
                                holder.reserv_row_start_time.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryLightColor));
                                holder.reserv_row_end_time.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryLightColor));
                                holder.min_hour_separator.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryLightColor));
                            }

                            break;
                        }
                    }


                    if (!added[0] && !reservationDateExists){

                        new MaterialAlertDialogBuilder(mContext)
                                .setTitle("Make reservation")
                                .setMessage("Want to make reservation for " + sdfDate.format(date) + " at " + sdfTime.format(date) + "?")
                                .setPositiveButton( "RESERVE", (dialog, which) -> {

                                    mReservations.get(position).addUserId(firebaseAuth.getCurrentUser().getUid());
                                    mReservations.get(position).save().addOnSuccessListener(aVoid -> Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show());

                                    added[0] = true;

                                    MainActivity mainActivity = (MainActivity) mContext;

                                    mainActivity.onBackPressed();
                                })

                                .setNegativeButton("CANCEL", (dialog, which) -> Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show())

                                .show();
                    }
                });


            });
        } else {
            holder.reserv_row_start_time.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryLightColor));
            holder.reserv_row_end_time.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryLightColor));
            holder.min_hour_separator.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryLightColor));


        }

    }

    void createReservation(boolean reservationExists){

    }

    @Override
    public int getItemCount() {
        return mReservations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reserv_row_start_time;
        TextView reserv_row_end_time;
        TextView min_hour_separator;

        ConstraintLayout reserv_row_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reserv_row_start_time = itemView.findViewById(R.id.textStartHour);
            reserv_row_end_time = itemView.findViewById(R.id.textEndHour);
            min_hour_separator = itemView.findViewById(R.id.min_hour_separator);

            reserv_row_layout = itemView.findViewById(R.id.row_reservationConstraint);
        }
    }
}
