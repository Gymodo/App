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
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position) {

        //TODO arreglar para que se vea bien la fecha y hora
        holder.reserv_row_start_time.setText(mReservations.get(position).getDuration());
        holder.reserv_row_layout.setOnClickListener(v -> {

            Toast.makeText(mContext, "Reservation for: " + mReservations.get(position).getDate().toString(), Toast.LENGTH_SHORT).show();
            //Save reservation on BBDD
            /*
            mReservations.get(position).addUserId(firebaseAuth.getCurrentUser().getUid());
            mReservations.get(position).save();*/

        });
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
