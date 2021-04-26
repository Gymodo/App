package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


import com.github.gymodo.user.User;

import java.util.Calendar;
import java.util.Date;


public class AfterSignUp extends AppCompatActivity {

    //Date picker
    private DatePickerDialog datePicker;
    private EditText textDate;

    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sign_up);

        //Hooks
        textDate = findViewById(R.id.aftersignupBirthdate);
        numberPicker = findViewById(R.id.aftersignupNumberPicker);

        //User received
        User userTmp = (User) getIntent().getSerializableExtra("userTmp");


        numberPicker.setMinValue(18);
        numberPicker.setMaxValue(250);

        //Date picker
        textDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONDAY);
            final int year = calendar.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(AfterSignUp.this,
                    (view, year1, month1, dayOfMonth) -> textDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day);
            datePicker.show();
        });

        //Number picker
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> Toast.makeText(AfterSignUp.this, newVal + "", Toast.LENGTH_SHORT).show());

    }
}