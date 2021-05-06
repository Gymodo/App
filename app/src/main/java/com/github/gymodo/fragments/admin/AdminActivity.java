package com.github.gymodo.fragments.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.github.gymodo.R;

public class AdminActivity extends AppCompatActivity {
    Button addExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addExercise = findViewById(R.id.AdminAddExercise);

        addExercise.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddExerciseActivity.class);
            startActivity(intent);
        });
    }
}