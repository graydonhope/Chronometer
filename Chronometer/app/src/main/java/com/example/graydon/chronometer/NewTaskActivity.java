package com.example.graydon.chronometer;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NewTaskActivity extends AppCompatActivity {

    private TextView startDateDisplay;
    private TextView endDateDisplay;
    private String taskName;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerStart;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerEnd;
    private int startTimeHour = -1, endTimeHour = -1, startTimeMinute = -1, endTimeMinute = -1, reminderTime = -1;
    private boolean validNameEntered, validTimeframe, validRemindertime;
    private NewTaskModel taskModel;
    private StoredTaskManager storedTaskManager;
    private TimePickerDialog startTimePickerDialog, endTimePickerDialog;
    private Spinner spinner;
    private Context context;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private List<String> spinnerItems = new ArrayList<String>();
    private Intent intent;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = getApplicationContext();
        super.onCreate(savedInstanceState);
        this.storedTaskManager = new StoredTaskManager();
        storedTaskManager.removeAllTasks(this.context);
        setContentView(R.layout.activity_newtask);
        event = new Event();


        /*
        //Speak to Billal about what he will name event
        intent = getIntent();
        event = intent.getParcelableExtra("Event");

        */


        spinner = findViewById(R.id.typeOfTask_spinner);
        taskModel = new NewTaskModel(NewTaskActivity.this);
        spinnerItems = taskModel.retrieveSpinnerItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        startDateDisplay = (TextView) findViewById(R.id.startDate_textView);
        endDateDisplay = (TextView) findViewById(R.id.endDate_textView);
        startDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        endDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        timePickerDialogListenerStart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeHour = hourOfDay;
                startTimeMinute = minute;
                String startTimeToDisplay;
                String minuteToDisplay;
                if(minute < 10){
                    minuteToDisplay = "0" + minute;
                }
                else{
                    minuteToDisplay = "" + minute;
                }
                if(hourOfDay < 10){
                    startTimeToDisplay = "0" + hourOfDay;
                }
                else{
                    startTimeToDisplay = "" + hourOfDay;
                }
                String time = startTimeToDisplay + ":" + minuteToDisplay;
                startDateDisplay.setText(time);
            }
        };

        timePickerDialogListenerEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeHour = hourOfDay;
                endTimeMinute = minute;
                String endTimeToDisplay;
                String minuteToDisplay;
                if(minute < 10){
                    minuteToDisplay = "0" + minute;
                }
                else{
                    minuteToDisplay = "" + minute;
                }
                if(hourOfDay < 10){
                    endTimeToDisplay = "0" + hourOfDay;
                }
                else{
                    endTimeToDisplay = "" + hourOfDay;
                }
                String time = endTimeToDisplay + ":" + minuteToDisplay;
                endDateDisplay.setText(time);
            }
        };
    }

    public void displayStartDate(){
        timePickerDialogListenerStart =  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeHour = hourOfDay;
                startTimeMinute = minute;
                String startTimeToDisplay;
                String minuteToDisplay;
                if(minute < 10){
                    minuteToDisplay = "0" + minute;
                }
                else{
                    minuteToDisplay = "" + minute;
                }
                if(hourOfDay < 10){
                    startTimeToDisplay = "0" + hourOfDay;
                }
                else{
                    startTimeToDisplay = "" + hourOfDay;
                }
                String time = startTimeToDisplay + ":" + minuteToDisplay;
                startDateDisplay.setText(time);
            }
        };
    }


    /*
    public void updateTimeDisplay(int startHour, int startMinute, int endHour, int endMinute){
        startTimePickerDialog.updateTime(startHour, startMinute);
        endTimePickerDialog.updateTime(endHour, endMinute);
    }

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

        event = new Event();
        event.addTask(task1);
        event.addTask(task2);
        event.addTask(task3);
        event.addTask(task4);
        event.addTask(task5);
        event.addTask(task6);
        return event;
    }

    public Event getEvent(){
        return createEvent();
        //in future put return event once billal has sent me an event that i can add tasks to
    }

    public void displayEndDate(){
        timePickerDialogListenerEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeHour = hourOfDay;
                startTimeMinute = minute;
                String endTimeToDisplay;
                String minuteToDisplay;
                if(minute < 10){
                    minuteToDisplay = "0" + minute;
                }
                else{
                    minuteToDisplay = "" + minute;
                }
                if(hourOfDay < 10){
                    endTimeToDisplay = "0" + hourOfDay;
                }
                else{
                    endTimeToDisplay = "" + hourOfDay;
                }
                String time = endTimeToDisplay + ":" + minuteToDisplay;
                endDateDisplay.setText(time);
            }
        };
    }


    public void isValidTimeframe(){
        if(startTimeHour != -1 && startTimeMinute != -1 && endTimeHour != -1 && endTimeMinute != -1){
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
        if(validNameEntered && validTimeframe && validRemindertime){
            Duration startTime = new Duration(this.startTimeHour, this.startTimeMinute);
            Duration endTime = new Duration(this.endTimeHour, this.endTimeMinute);
            taskModel.setStartTime(startTime);
            taskModel.setEndTime(endTime);
            taskModel.setTaskName(taskName);
            taskModel.setStart(startTime); taskModel.setEnd(endTime);
            Context contextToSend = getApplicationContext();
            if(!taskModel.checkTimeFrame()){
                Task newTask = new Task(taskName, startTime, endTime, reminderTime);
                taskModel.saveTask(this, newTask);
                event.addTask(newTask);
                Log.d("!!!!!!!@@@@@@@", "addButtonClicked: ADDED TASK");
                taskModel.addTaskToSpinner(newTask);
                if(taskModel.checkSavedTasks(newTask)){
                    storedTaskManager.addTask(this.context, newTask);
                    Log.d("!!!!XXXXXXXXXX", "addButtonClicked: Task was saved! Hooray!! :D");
                }
                else{
                    Log.d("@@@@@@@@@@@!!!!!!!", "addButtonClicked: Task was not saved");
                }

//                Intent intent = new Intent(this, EventInfoProgressActivity.class);
//                intent.putExtra("Event", event);
//                startActivity(intent);
                Intent intent = new Intent();
                intent.putExtra("Task", newTask);
                setResult(RESULT_OK,intent);
                finish ();
            }
        }
        else{
            Toast.makeText(this, "Unable to add Task ", Toast.LENGTH_LONG).show();
        }
    }
    public void cancelButton (View view){
        Intent intent = new Intent(this, EventInfoActivity.class);
        setResult (RESULT_CANCELED,intent);

        finish();
    }
}
