package com.example.graydon.chronometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class NewTaskActivity extends AppCompatActivity {

    //Activity - where the UI gets updated!! No logic in here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        spinnerDetails();
    }

    public void spinnerDetails(){
        Spinner spinner = (Spinner) findViewById(R.id.typeOfTask_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    public void addButtonClicked(View view){
        //create new task
    }




}
