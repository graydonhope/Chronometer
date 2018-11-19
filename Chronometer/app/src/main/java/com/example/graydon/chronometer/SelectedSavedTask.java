package com.example.graydon.chronometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedSavedTask extends AppCompatActivity {

    private Task task;
    TextView taskName;
    TextView taskStartDur;
    TextView taskEndDur;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_saved_task);

        Intent intent = getIntent();
        task=intent.getParcelableExtra("selectedSavedTask");
    }
}
