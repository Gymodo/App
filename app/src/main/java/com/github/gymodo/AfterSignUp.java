package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.gymodo.user.User;
import com.github.gymodo.user.UserGoal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AfterSignUp extends AppCompatActivity {

    //Date picker
    private DatePickerDialog datePicker;
    private EditText textDate;
    private NumberPicker numberPicker;
    private Button afterSignUpfinishBtn;

    private RadioGroup afterSignupRadioGroup;
    private RadioButton aftersignupBuildMuscleBtn;
    private RadioButton aftersignupLoseFatBtn;
    private RadioButton aftersignupGetHardyBtn;

    private FirebaseFirestore afterSignupFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sign_up);

        //Hooks
        afterSignupFireBase = FirebaseFirestore.getInstance();

        textDate = findViewById(R.id.aftersignupBirthdate);
        numberPicker = findViewById(R.id.aftersignupNumberPicker);
        afterSignUpfinishBtn = findViewById(R.id.aftersignupFinishButton);

        afterSignupRadioGroup = findViewById(R.id.afterSignupRadioGroup);
        aftersignupBuildMuscleBtn = findViewById(R.id.aftersignupBuildMuscle);
        aftersignupLoseFatBtn = findViewById(R.id.aftersignupLoseFat);
        aftersignupGetHardyBtn = findViewById(R.id.aftersignupGetHardy);

        //Create empty user
        User userTmp = new User();


        //Set min and max value for numberpicker
        numberPicker.setMinValue(18);
        numberPicker.setMaxValue(250);
        numberPicker.setValue(70);

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


        //Every time user select a date, the user birthdate variable will be update automatically.
        textDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userTmp.setBirthDate(stringToDate(textDate.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //Number picker
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            userTmp.setWeight(numberPicker.getValue());
        });


        //Get user name from getIntent
        userTmp.setName(getIntent().getStringExtra("registerUserName"));
        userTmp.setEmail(getIntent().getStringExtra("registerEmail"));

        afterSignupRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int radioSelected = afterSignupRadioGroup.getCheckedRadioButtonId();
            switch (radioSelected) {
                case R.id.aftersignupBuildMuscle:
                    setRadioBtnBackgroundShadow(radioSelected);
                    userTmp.setGoal(UserGoal.BUILD_MUSCLE);
                    break;
                case R.id.aftersignupLoseFat:
                    setRadioBtnBackgroundShadow(radioSelected);
                    userTmp.setGoal(UserGoal.LOSE_FAT);
                    break;
                case R.id.aftersignupGetHardy:
                    setRadioBtnBackgroundShadow(radioSelected);
                    userTmp.setGoal(UserGoal.GET_HARDY);
                    break;
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();

        afterSignUpfinishBtn.setOnClickListener(v -> {

            userTmp.save(uid).addOnFailureListener(fail -> {
                Log.e("user-save", fail.getMessage());
                Toast.makeText(AfterSignUp.this, "Error adding user", Toast.LENGTH_SHORT).show();
            });

            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void setRadioBtnBackgroundShadow(int selectedRadio) {
        switch (selectedRadio) {
            case R.id.aftersignupBuildMuscle:
                aftersignupBuildMuscleBtn.setBackgroundResource(R.drawable.radio_button_shadow);
                aftersignupLoseFatBtn.setBackgroundResource(R.color.transparent);
                aftersignupGetHardyBtn.setBackgroundResource(R.color.transparent);
                break;
            case R.id.aftersignupLoseFat:
                aftersignupLoseFatBtn.setBackgroundResource(R.drawable.radio_button_shadow);
                aftersignupBuildMuscleBtn.setBackgroundResource(R.color.transparent);
                aftersignupGetHardyBtn.setBackgroundResource(R.color.transparent);
                break;
            case R.id.aftersignupGetHardy:
                aftersignupGetHardyBtn.setBackgroundResource(R.drawable.radio_button_shadow);
                aftersignupLoseFatBtn.setBackgroundResource(R.color.transparent);
                aftersignupBuildMuscleBtn.setBackgroundResource(R.color.transparent);
                break;
        }
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