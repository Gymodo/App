package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gymodo.sharedPreferences.MySharedPreferences;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private DatePickerDialog settingsDatePicker;
    private EditText settingsTextDate;
    private TextInputEditText settingsUserName;
    private TextInputEditText settingsUserPassword;
    private Button settingsSaveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //Hook
        settingsTextDate = findViewById(R.id.settingsInputBirthDate);
        settingsUserName = findViewById(R.id.settingsInputTextName);
        settingsUserPassword = findViewById(R.id.settingsInputTextPassword);
        settingsSaveBtn = findViewById(R.id.settingsSaveButton);


        //Date picker
        settingsTextDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONDAY);
            final int year = calendar.get(Calendar.YEAR);

            settingsDatePicker = new DatePickerDialog(
                    SettingsActivity.this,
                    (view, year1, month1, dayOfMonth) -> settingsTextDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day);
            settingsDatePicker.show();

        });


        //Get user data from database
        User.getByID(FirebaseAuth.getInstance().getUid()).addOnSuccessListener(user -> {
            settingsTextDate.setText(user.getBirthDateString());
            settingsUserName.setText(user.getName());
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error while loading user data!", Toast.LENGTH_SHORT).show();
            Log.d("loadSettingsUserData", e.toString());
        });


        settingsSaveBtn.setOnClickListener(v -> User.getByID(FirebaseAuth.getInstance().getUid()).addOnSuccessListener(user -> {

            //Update user data
            user.setName(settingsUserName.getText().toString());
            user.setBirthDate(settingsTextDate.getText().toString());
            user.update();

            //Update user password
            if (!settingsUserPassword.getText().toString().isEmpty()) {
                FirebaseAuth settingsFirebaseAuth = FirebaseAuth.getInstance();

                //Update user credential
                settingsFirebaseAuth.getCurrentUser().updatePassword(settingsUserPassword.getText().toString()).addOnSuccessListener(aVoid -> {
                    MySharedPreferences.saveSharePref(settingsFirebaseAuth.getCurrentUser().getEmail(), settingsUserPassword.getText().toString(), this);
                });
            }

            onBackPressed();

        }));


    }


}