package com.example.graydon.chronometer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class EventInfoProgressActivity extends AppCompatActivity {
    protected TextView timeLeftTextView;
    protected TextView taskNameTextView;
    protected Event event;
    private EventInProgressModel model;
    private CountDownTimer timer;
    private static final String EVENT = "Event";
    private static final String TASK = "Current Task: ";
    private static final int CHRONO_PURPLE = Color.parseColor("#843EAF");
    private static final int CHRONO_GREEN = Color.parseColor("#3ba039");
    private AlarmManager alarmManager;
    private PendingIntent alarmEndPendingIntent;
    private PendingIntent alarmReminderPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info_progress);
        timeLeftTextView = findViewById(R.id.timeLeftTextView);
        taskNameTextView = findViewById(R.id.taskNameTextView);
        String dayOfWeek = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String monthOfYear = Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
        String dayOfMonth = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        getSupportActionBar().setTitle(dayOfWeek + " " + monthOfYear + " " + dayOfMonth);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CHRONO_PURPLE));
        Intent intent = new Intent(EventInfoProgressActivity.this,NewTaskActivity.class);
        Intent infoToInProgressIntent = getIntent();
        event = infoToInProgressIntent.getParcelableExtra(EVENT);
        if(event == null)
            moveToNewActivity();

//        event = createEvent(); //infoToInProgressIntent.getParcelableExtra(EXTRA);
        model = new EventInProgressModel(event);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        updateUI();
        createNextTaskTimer();
    }
    public void onCancelButtonClick(View view){
        EndDayDialog endDayDialog = new EndDayDialog();
        endDayDialog.show(getSupportFragmentManager(), "End Day Dialog");
        alarmManager.cancel(alarmEndPendingIntent);
        alarmManager.cancel(alarmReminderPendingIntent);
    }
    public void onCompletedButtonClick(View view){
        Button completedButton = findViewById(R.id.completedButton);
        completedButton.setBackgroundColor(CHRONO_GREEN);
    }

    public void createNextTaskTimer(){
        if (timer != null){
            timer.cancel();
        }
        setUpEndAndReminderAlarm();
        timer = new CountDownTimer(model.getCurrentTimeLeft(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String hours   = Integer.toString(((int) ((millisUntilFinished / (1000*60*60)) % 24)));
                String minutes = Integer.toString(((int) ((millisUntilFinished / (1000*60)) % 60)));
                String seconds = Integer.toString((int) (millisUntilFinished / 1000) % 60 );
                if (Integer.parseInt(hours) < 10)
                    hours = "0" + hours;
                if (Integer.parseInt(minutes) < 10)
                    minutes = "0" + minutes;
                if (Integer.parseInt(seconds) < 10)
                    seconds = "0" + seconds;
                if (Integer.parseInt(minutes) == 0){
                    timeLeftTextView.setText(seconds);
                }
                else if(Integer.parseInt(hours) == 0) {
                    timeLeftTextView.setText(minutes + ":" + seconds);
                }
                else{
                    timeLeftTextView.setText(hours + ":" + minutes + ":" + seconds);
                }
            }
            @Override
            public void onFinish() {
                //Move onto next task
                if (!(model.getEvent().hasNext())){
                    moveToNewActivity();
                }
                else{
                    model.nextTask(false);
                    updateUI();
                    createNextTaskTimer();
                }
            }
        }.start();
    }

    public void moveToEventInfoActivity(){
        if (timer != null)
            timer.cancel();
        Intent toEventInfoActivity = new Intent(EventInfoProgressActivity.this, EventInfoActivity.class);
        startActivity(toEventInfoActivity);
    }
    public void moveToNewActivity(){
        if (timer != null)
            timer.cancel();
        Intent toEventInfoActivity = new Intent(EventInfoProgressActivity.this, NewTaskActivity.class);
        startActivity(toEventInfoActivity);
    }
    public void updateUI(){
        Button completedButton = findViewById(R.id.completedButton);
        completedButton.setBackgroundColor(CHRONO_PURPLE);
        taskNameTextView.setText(TASK + model.getCurrentTask().getName());
    }
    public void setUpEndAndReminderAlarm(){
        Intent alarmIntent = new Intent(EventInfoProgressActivity.this, AlarmReceiver.class);
        alarmEndPendingIntent = PendingIntent.getBroadcast(EventInfoProgressActivity.this,0,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmReminderPendingIntent = PendingIntent.getBroadcast(EventInfoProgressActivity.this,1,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,model.getCurrentTaskEndTime().getTimeInMillis(),alarmEndPendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP,model.getCurrentTaskEndTime().getTimeInMillis() - model.getCurrentTaskReminderTimeInMili(),alarmReminderPendingIntent);
    }

    //Simulates event
    public Event createEvent(){

        Duration dur1 = new Duration(0,0);
        Duration dur2 = new Duration(4,0);
        Task task1 = new Task("Stock produce", dur1,dur2, 1);

        Duration dur3 = new Duration(4,0);
        Duration dur4 = new Duration(8,0);
        Task task2 = new Task("Unload Delivery", dur3,dur4, 15);

        Duration dur5 = new Duration(8,0);
        Duration dur6 = new Duration(12,0);
        Task task3 = new Task("Stock Toys", dur5,dur6, 15);

        Duration dur7 = new Duration(12,0);
        Duration dur8 = new Duration(16,0);
        Task task4 = new Task("Stock Bed & Bath", dur7,dur8, 15);

        Duration dur9 = new Duration(16,0);
        Duration dur10 = new Duration(20,0);
        Task task5 = new Task("Stock Seasonal", dur7,dur8, 15);

        Duration dur11 = new Duration(20,0);
        Duration dur12 = new Duration(23,59);
        Task task6 = new Task("Stock Bed & Bath", dur11,dur12, 15);

        Event event = new Event();
        event.addTask(task1);
        event.addTask(task2);
        event.addTask(task3);
        event.addTask(task4);
        event.addTask(task5);
        event.addTask(task6);
        return event;
    }

}
