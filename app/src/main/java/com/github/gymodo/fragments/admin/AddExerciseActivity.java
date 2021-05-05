package com.github.gymodo.fragments.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.MuscleAdapter;
import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.exercise.Muscle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.stream.Collectors;

public class AddExerciseActivity extends AppCompatActivity {
    TextInputEditText exerciseName;
    TextInputEditText exerciseDesc;
    RecyclerView recyclerView;
    MuscleAdapter adapter;
    FloatingActionButton addExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        addExercise = findViewById(R.id.AddExerciseButton);
        exerciseName = findViewById(R.id.AddExerciseInputName);
        exerciseDesc = findViewById(R.id.AddExerciseInputDescription);
        recyclerView = findViewById(R.id.AddExerciseMuscleList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new MuscleAdapter();
        recyclerView.setAdapter(adapter);

        Muscle.listAll().addOnSuccessListener(muscles -> {
            adapter.submitList(muscles);
        });

        addExercise.setOnClickListener(v -> {
            Exercise exercise = new Exercise();
            exercise.setName(exerciseName.getText().toString());
            exercise.setDescription(exerciseDesc.getText().toString());
            exercise.setMuscleIds(adapter.getSelectedItems().parallelStream().map(f -> f.getId()).collect(Collectors.toList()));
            exercise.save().addOnCompleteListener(id -> {
                // TODO: FIX THIS ASAP.
                Toast.makeText(this, "Exercise Added: " + id, Toast.LENGTH_SHORT).show();
                onBackPressed();
            });
        });
    }
}