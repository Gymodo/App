package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


import java.util.Calendar;


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
        textDate = findViewById(R.id.aftersignup_birthdate);
        numberPicker = findViewById(R.id.aftersignup_numberpicker);

        numberPicker.setMinValue(18);
        numberPicker.setMaxValue(250);

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONDAY);
                final int year = calendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(AfterSignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                textDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(AfterSignUp.this, newVal+"", Toast.LENGTH_SHORT).show();
            }
        });

    }
}