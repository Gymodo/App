package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


import com.github.gymodo.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        User userTmp = new User();
        userTmp.setName(getIntent().getStringExtra("name"));


        numberPicker.setMinValue(18);
        numberPicker.setMaxValue(250);

        //Date picker
        textDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONDAY);
            final int year = calendar.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(
                    AfterSignUp.this,
                    (view, year1, month1, dayOfMonth) -> textDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day);

            datePicker.show();

        });

        //Every time you select a date in the date picker
        textDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(AfterSignUp.this, ""+textDate.getText(), Toast.LENGTH_SHORT).show();
                userTmp.setBirthDate(stringToDate(textDate.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //Number picker
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            userTmp.setWeight(numberPicker.getValue());
            //Toast.makeText(AfterSignUp.this, userTmp.getWeight() + "", Toast.LENGTH_SHORT).show();
            Log.d("userWeight", userTmp.getWeight() + "");
        });

    }

    /**
     * Convert string to date
     *
     * @param dateString
     * @return Date
     */
    public Date stringToDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}