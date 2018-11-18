package com.example.graydon.chronometer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class EventInfoProgressActivity extends AppCompatActivity implements EndOfEventListener {
    protected TextView timeLeftTextView;
    protected TextView taskNameTextView;
    protected ProgressBar circularProgressBar;
    private EventInProgressModel model;
    private CountDownTimer timer;
    private static final String EVENT = "Event";
    private static final String TASK = "Current Task: ";
    private static final int CHRONO_BLACK = Color.parseColor("#1F1F1F");
    private static final int CHRONO_GREEN = Color.parseColor("#3ba039");
    private AlarmManager alarmManager;
    private PendingIntent alarmEndPendingIntent;
    private PendingIntent alarmReminderPendingIntent;
    private static final String TAG = "SGAGB074";
    private boolean eventIsOver = false;
    private  EventOverDialog eventOverDialog;
    private boolean alarmSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Event event;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info_progress);
        timeLeftTextView = findViewById(R.id.timeLeftTextView);
        taskNameTextView = findViewById(R.id.taskNameTextView);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        String dayOfWeek = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String monthOfYear = Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
        String dayOfMonth = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        getSupportActionBar().setTitle(dayOfWeek + " " + monthOfYear + " " + dayOfMonth);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CHRONO_BLACK));
        eventOverDialog = new EventOverDialog();
        if(StoredTaskManager.eventIsInProgress(getApplicationContext())){
            event = StoredTaskManager.getCurrentEvent(getApplicationContext());
            alarmSet = true;
//            Intent infoToInProgressIntent = getIntent();
//            event = infoToInProgressIntent.getParcelableExtra(EVENT);
        }
        else{
            Intent infoToInProgressIntent = getIntent();
            event = infoToInProgressIntent.getParcelableExtra(EVENT);
        }

        if(event == null)
            moveToEventInfoActivity();
        model = new EventInProgressModel(getApplicationContext(),event);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        updateUI();
        createNextTaskTimer();
    }

    /**
     * Changes the circular progress bar's secondary progress colour
     * @param color the colour to use
     */

    private void setSecondaryProgressBarColour(int color){
        if(circularProgressBar.getProgressDrawable() instanceof LayerDrawable){
            LayerDrawable progressDrawable = (LayerDrawable) circularProgressBar.getProgressDrawable();
            if(progressDrawable.getDrawable(1) instanceof  GradientDrawable){
                ((GradientDrawable) progressDrawable.getDrawable(1)).setColor(color);
            }
        }
    }
    /**
     * Method called when the cancel button is clicked
     * @param view the cancel button clicked
     */
    public void onCancelButtonClick(View view){
        EndDayDialog endDayDialog = new EndDayDialog();
        endDayDialog.show(getSupportFragmentManager(), "End Day Dialog");
        eventIsOver = true;
    }

    /**
     * Method called wehn the completed is called, indicating that task has been finished
     * @param view the completed button
     */
    public void onCompletedButtonClick(View view){
        Button completedButton = findViewById(R.id.completedButton);
        model.setCurrentTaskIsComplete(true);
        StoredTaskManager.saveCurrentEvent(getApplicationContext(),model.getEvent());
        completedButton.setBackground(getResources().getDrawable(R.drawable.green_rounded_edge_button));
        Toast.makeText(this, "Marked as completed.", Toast.LENGTH_LONG).show();
    }


    /**
     * Creates a new task time using the current task being completed (in model)
     */
    public void createNextTaskTimer(){
        if (timer != null){
            timer.cancel();
        }
        circularProgressBar.setMax((int) model.getTotalTaskLength());
        circularProgressBar.setProgress(circularProgressBar.getMax());
        if(!alarmSet)
            setUpEndAndReminderAlarm();
        timer = new CountDownTimer(model.getCurrentTimeLeft(),100) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Converting milliseconds to hours.minutes,seconds
                String hours   = Integer.toString(((int) ((millisUntilFinished / (1000*60*60)) % 24)));
                String minutes = Integer.toString(((int) ((millisUntilFinished / (1000*60)) % 60)));
                String seconds = Integer.toString((int) (millisUntilFinished / 1000) % 60 );
                circularProgressBar.setSecondaryProgress( (int) millisUntilFinished);
                float percentComplete = ( (float) millisUntilFinished/circularProgressBar.getMax())*100;
                if (percentComplete <= 20 ){
                    setSecondaryProgressBarColour(Color.RED);

                }
                else if (percentComplete <= 50){
                    setSecondaryProgressBarColour(Color.YELLOW);
                }
                else{
                    setSecondaryProgressBarColour(CHRONO_GREEN);
                }

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
                circularProgressBar.setSecondaryProgress(0);
                if (!(model.getEvent().hasNext())){
                    model.sendReport();
                    timeLeftTextView.setText("00");
                    if(!getSupportFragmentManager().isStateSaved() && !eventOverDialog.isAdded())
                        eventOverDialog.show(getSupportFragmentManager(), "Event Over Dialog");
                    eventIsOver = true;
                }
                else{
                    model.nextTask(false);
                    updateUI();
                    createNextTaskTimer();
                }
            }
        }.start();
    }

    /**
     * Changes activity to the event info activity
     */
    public void moveToEventInfoActivity(){
        if (timer != null)
            timer.cancel();
        Intent toEventInfoActivity = new Intent(EventInfoProgressActivity.this, EventInfoActivity.class);
        startActivity(toEventInfoActivity);
    }

    /**
     * Changes activity to new activity
     */
    public void moveToNewActivity(){
        if (timer != null)
            timer.cancel();
        Intent toEventInfoActivity = new Intent(EventInfoProgressActivity.this, NewTaskActivity.class);
        startActivity(toEventInfoActivity);
    }

    /**
     * Updates the UI with the current task info
     */
    public void updateUI(){
        Button completedButton = findViewById(R.id.completedButton);
        Drawable completedButtonBackground = completedButton.getBackground();
        if(model.getCurrentTask().getIsComplete()){
            completedButton.setBackground(getResources().getDrawable(R.drawable.green_rounded_edge_button));
        }
        else{
            completedButton.setBackground(getResources().getDrawable(R.drawable.clear_rounded_edge_button));
        }
        taskNameTextView.setText(TASK + model.getCurrentTask().getName());
    }

    /**
     * Creates alarm for remidner and end time of task
     */
    public void setUpEndAndReminderAlarm(){
        Intent alarmIntent = new Intent(EventInfoProgressActivity.this, AlarmReceiver.class);
        alarmIntent.putExtra("reminderTime", Integer.toString(model.getCurrentTask().getReminderTimeMinutes()));
        alarmIntent.putExtra("isEndAlarm", true);
        alarmEndPendingIntent = PendingIntent.getBroadcast(EventInfoProgressActivity.this,0,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.putExtra("isEndAlarm", false);
        alarmReminderPendingIntent = PendingIntent.getBroadcast(EventInfoProgressActivity.this,1,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,model.getCurrentTaskEndTime().getTimeInMillis(),alarmEndPendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,model.getCurrentTaskEndTime().getTimeInMillis() - model.getCurrentTaskReminderTimeInMili(),alarmReminderPendingIntent);
    }

    /**
     *  Simualtes the event that will be passed to this activity from event info activity
     * @return event made
     */

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

    @Override
    protected void onResume() {
        super.onResume();
        if(eventIsOver && !eventOverDialog.isAdded())
            eventOverDialog.show(getSupportFragmentManager(), "Event Over Dialog");

    }

    @Override
    public void onEndOfEvent() {
        if(timer != null)
            timer.cancel();
        if (alarmManager != null){
            if (alarmEndPendingIntent != null)
                alarmManager.cancel(alarmEndPendingIntent);

            if (alarmReminderPendingIntent != null)
                alarmManager.cancel(alarmReminderPendingIntent);

        }


        StoredTaskManager.setEventInProgress(getApplicationContext(),false);
        moveToEventInfoActivity();
    }

    @Override
    public void onBackPressed() {
        //Do nothing if the back button is pressed.

    }
}
