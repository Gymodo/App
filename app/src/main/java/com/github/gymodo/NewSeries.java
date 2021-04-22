package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

public class NewSeries extends AppCompatActivity {

    private Spinner seriesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_series);

        //hook
        seriesSpinner = findViewById(R.id.newSeriesSpinner);
    }
}