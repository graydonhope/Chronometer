package com.example.graydon.chronometer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class NewTaskActivity extends AppCompatActivity {

    //Activity - where the UI gets updated!! No logic in here
    private TextView startDateDisplay;
    private TextView endDateDisplay;
    private String taskName;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerStart;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerEnd;
    private int startTimeHour = -1, endTimeHour = -1, startTimeMinute = -1, endTimeMinute = -1, reminderTime = -1;
    private boolean validNameEntered, validTimeframe, validRemindertime;
    private NewTaskModel taskModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        startDateDisplay = (TextView) findViewById(R.id.startDate_textView);
        endDateDisplay = (TextView) findViewById(R.id.endDate_textView);
        spinnerDetails();
    }

    public void onStartTimeClick(View view){
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean twentyfourHourTime = false;
        TimePickerDialog dialog = new TimePickerDialog(
                NewTaskActivity.this, android.R.style.Theme_Holo_Light_DarkActionBar, timePickerDialogListenerStart, hourOfDay, minute, twentyfourHourTime);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        displayStartDate();
    }

    public void displayStartDate(){
        timePickerDialogListenerStart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeHour = hourOfDay;
                startTimeMinute = minute;
                String AM_PM;
                String minuteToDisplay;
                if(minute < 10){
                    minuteToDisplay = "0" + minute;
                }
                else{
                    minuteToDisplay = "" + minute;
                }
                if(hourOfDay < 12){
                    AM_PM = "AM";
                }
                else {
                    AM_PM = "PM";
                }
                String time = hourOfDay + ":" + minuteToDisplay + " " + AM_PM;
                startDateDisplay.setText(time);
            }
        };
    }

    /**
     *
     * @param view
     */
    public void onEndTimeClick(View view){
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean twentyfourHourTime = false;
        TimePickerDialog dialog = new TimePickerDialog(
                NewTaskActivity.this, android.R.style.Theme_Holo_Light_DarkActionBar, timePickerDialogListenerEnd, hourOfDay, minute, twentyfourHourTime);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        displayEndDate();
    }

    public void displayEndDate(){
        timePickerDialogListenerEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeHour = hourOfDay;
                endTimeMinute = minute;
                String AM_PM;
                String minuteToDisplay;
                if(minute < 10){
                    minuteToDisplay = "0" + minute;
                }
                else{
                    minuteToDisplay = "" + minute;
                }
                if(hourOfDay < 12){
                    AM_PM = "AM";
                }
                else {
                    AM_PM = "PM";
                }
                String time = hourOfDay + ":" + minuteToDisplay + " " + AM_PM;
                endDateDisplay.setText(time);
            }
        };
    }


    public void spinnerDetails(){
        Spinner spinner = (Spinner) findViewById(R.id.typeOfTask_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }


    public void isValidTimeframe(){
        if(startTimeHour != -1 && startTimeMinute != -1 && endTimeHour != -1 && endTimeMinute != -1){
            Log.d("!!!!!!!!!", "isValidTimeframe: startTime Minutes" + startTimeMinute + " endTime Minutes " + endTimeMinute);
            if((startTimeHour < endTimeHour) || ((startTimeHour <= endTimeHour) && startTimeMinute < endTimeMinute)){
                validTimeframe = true;
            }
            else{
                validTimeframe = false;
            }
        }

    }

    public void isValidNameEntered(){
        EditText taskNameEditText= (EditText) findViewById(R.id.taskNameEditText);
        taskName = taskNameEditText.getText().toString();
        if(!taskName.equals("")){
            validNameEntered = true;
        }
        else{
            validNameEntered = false;
        }
    }

    public void isValidReminder(){
        TextView reminder_number = (TextView) findViewById(R.id.reminderTime_textView);
        Integer reminder_amount = Integer.parseInt(reminder_number.getText().toString());
        reminderTime = (int) reminder_amount;
        if(!(reminderTime == -1)){
            validRemindertime = true;
        }
        else{
            validRemindertime = false;
        }
    }

    public void addButtonClicked(View view){
        //create new task - need name, hours, minutes, reminder
        isValidNameEntered(); isValidReminder(); isValidTimeframe();
        Log.d("!!!!After methods ", "addButtonClicked: ValidName:" + validNameEntered + " ValidReminder: " + validRemindertime + " valid TimeFrame: " + validTimeframe);
        if(validNameEntered && validTimeframe && validRemindertime){
            Duration startTime = new Duration(this.startTimeHour, this.startTimeMinute);
            Duration endTime = new Duration(this.endTimeHour, this.endTimeMinute);
            taskModel = new NewTaskModel(this, startTime, endTime, taskName);

            if(!(taskModel.checkNameUnique()) && taskModel.checkTimeFrame()){
                Log.d("!!!!!XXX", "addButtonClicked: Inside seoncd if");
                Task newTask = new Task(taskName, startTime, endTime, reminderTime);
                taskModel.saveTask(this, newTask);
                Log.d("!!!!!!!!!!!", "addButtonClicked: NEW Task ADDED and Saved!!");
                Event event = new Event();
                event.addTask(newTask);
                Intent intent = new Intent(this, EventInfoProgressActivity.class);
                intent.putExtra("Event", event);
            }
        }
        else{
            Toast.makeText(this, "Unable to add Task ", Toast.LENGTH_LONG).show();
        }
    }
}
