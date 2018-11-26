package com.gng.bcgsz.timetask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private static final int CHRONO_BLACK = Color.parseColor("#1F1F1F");
    private TextView emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //changes the app bar menu to black
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CHRONO_BLACK));
        getSupportActionBar().setTitle("Settings");
        //Load settings
        SharedPreferences sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        emailField = findViewById(R.id.emailField);
        Switch reportEnabledSwitch = findViewById(R.id.reportSwitch);
        String savedEmail = sharedPreferences.getString("UserEmail", null);
        if(savedEmail != null){
            emailField.setText(savedEmail);
        }
        reportEnabledSwitch.setChecked(sharedPreferences.getBoolean("SendReport",false));
    }

    public void switched(View view){
        Switch reportEnabledSwitch = (Switch) view;
        SharedPreferences sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean("SendReport",reportEnabledSwitch.isChecked());
        sharedPreferencesEditor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveEmail();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveEmail();
    }

    private void saveEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("UserEmail",emailField.getText().toString().trim().toLowerCase());
        sharedPreferencesEditor.apply();
    }

    public void editTask(View view){
        Intent startEditTaskActivity = new Intent(this, EditTaskActivity.class);
        startActivity(startEditTaskActivity);
    }

}
