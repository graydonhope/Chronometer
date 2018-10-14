package com.example.graydon.chronometer;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventInfoProgressActivity extends AppCompatActivity {
    TextView timeLeftTextView;
    Event event;
    private EventInProgressModel model;
    private static final String EXTRA = "Extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info_progress);
        Intent infoToInProgressIntent = getIntent();
        event = infoToInProgressIntent.getParcelableExtra(EXTRA);
        model = new EventInProgressModel(event);
        CountDownTimer timer = new CountDownTimer(model.getCurrentTimeLeft(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftTextView.setText(Long.toString(millisUntilFinished));
            }
            @Override
            public void onFinish() {
                //Move onto next task //CREATE NEW TIMER METHOD
                Task nextTask = model.nextTask();
                timeLeftTextView.setText("done");
            }
        }.start();
//        Log.d("!!!!!!!!!","This is the event: " + event);
    }

    public void onCancelButtonClick(View view){
        EndDayDialog endDayDialog = new EndDayDialog();
        endDayDialog.show(getSupportFragmentManager(), "End Day Dialog");
    }
    public void onCompletedButtonClick(View view){
        Button completedButton = findViewById(R.id.completedButton);
        completedButton.setBackgroundColor(Color.parseColor("#3ba039"));
    }



}
