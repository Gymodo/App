package com.github.gymodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.gymodo.user.User;
import com.github.gymodo.user.UserGoal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private NumberPicker profileNumberPicker;
    private Button profileSaveBtn;

    private RadioGroup profileRadioGroup;
    private RadioButton profileRadioBtnBuildMuscle;
    private RadioButton profileRadioBtnLoseFat;
    private RadioButton profileRadioBtnGetHardy;

    private int userWeightTmp;
    private UserGoal userGoalTmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Hook
        profileNumberPicker = findViewById(R.id.profileNumberPicker);
        profileSaveBtn = findViewById(R.id.profileSaveButton);
        profileRadioGroup = findViewById(R.id.profileRadioGroup);
        profileRadioBtnBuildMuscle = findViewById(R.id.profileBuildMuscle);
        profileRadioBtnLoseFat = findViewById(R.id.profileLoseFat);
        profileRadioBtnGetHardy = findViewById(R.id.profileGetHardy);

        //Set min and max value for numberpicker
        profileNumberPicker.setMinValue(18);
        profileNumberPicker.setMaxValue(250);

        getUserDataFromDb();


        profileNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            userWeightTmp = newVal;
        });


        profileRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int radioSelected = profileRadioGroup.getCheckedRadioButtonId();
            switch (radioSelected) {
                case R.id.profileBuildMuscle:
                    setRadioBtnBackgroundShadow(radioSelected);
                    userGoalTmp = UserGoal.BUILD_MUSCLE;
                    break;
                case R.id.profileLoseFat:
                    setRadioBtnBackgroundShadow(radioSelected);
                    userGoalTmp = UserGoal.LOSE_FAT;
                    break;
                case R.id.profileGetHardy:
                    setRadioBtnBackgroundShadow(radioSelected);
                    userGoalTmp = UserGoal.GET_HARDY;
                    break;
            }
        });


        profileSaveBtn.setOnClickListener(v -> User.getByID(FirebaseAuth.getInstance().getUid()).addOnSuccessListener(user -> {
            user.setWeight(userWeightTmp);
            user.setGoal(userGoalTmp);
            user.update();
            super.onBackPressed();
        }));

    }


    //Get user data from database
    private void getUserDataFromDb() {
        User.getByID(FirebaseAuth.getInstance().getUid()).addOnSuccessListener(user -> {

            //Save user data in local variables
            userWeightTmp = (int) user.getWeight();
            userGoalTmp = user.getGoal();

            profileNumberPicker.setValue(userWeightTmp);
            switch (userGoalTmp) {
                case BUILD_MUSCLE:
                    profileRadioBtnBuildMuscle.setBackgroundResource(R.drawable.radio_button_shadow);
                    break;
                case LOSE_FAT:
                    profileRadioBtnLoseFat.setBackgroundResource(R.drawable.radio_button_shadow);
                    break;
                case GET_HARDY:
                    profileRadioBtnGetHardy.setBackgroundResource(R.drawable.radio_button_shadow);
                    break;
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(ProfileActivity.this, "Error while loading user data!", Toast.LENGTH_SHORT).show();
            Log.d("profileLoadUserDataError", "" + e);
        });
    }


    private void setRadioBtnBackgroundShadow(int selectedRadio) {
        switch (selectedRadio) {
            case R.id.profileBuildMuscle:
                profileRadioBtnBuildMuscle.setBackgroundResource(R.drawable.radio_button_shadow);
                profileRadioBtnLoseFat.setBackgroundResource(R.color.transparent);
                profileRadioBtnGetHardy.setBackgroundResource(R.color.transparent);
                break;
            case R.id.profileLoseFat:
                profileRadioBtnLoseFat.setBackgroundResource(R.drawable.radio_button_shadow);
                profileRadioBtnBuildMuscle.setBackgroundResource(R.color.transparent);
                profileRadioBtnGetHardy.setBackgroundResource(R.color.transparent);
                break;
            case R.id.profileGetHardy:
                profileRadioBtnGetHardy.setBackgroundResource(R.drawable.radio_button_shadow);
                profileRadioBtnLoseFat.setBackgroundResource(R.color.transparent);
                profileRadioBtnBuildMuscle.setBackgroundResource(R.color.transparent);
                break;
        }
    }
}