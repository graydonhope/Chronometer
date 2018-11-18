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
    private TextView taskNameDisplay;
    private String taskName;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerStart;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerEnd;
    private int startTimeHour = -1, endTimeHour = -1, startTimeMinute = -1, endTimeMinute = -1, reminderTime = -1;
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
        setContentView(R.layout.activity_newtask);
        intent = getIntent();
        event = intent.getParcelableExtra("event");
        taskModel = new NewTaskModel(this);
        spinnerItems.add("New");
        loadSpinneritems();
        spinner = findViewById(R.id.typeOfTask_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        taskNameDisplay = findViewById(R.id.taskNameEditText);
        startDateDisplay = findViewById(R.id.startDate_textView);
        endDateDisplay = findViewById(R.id.endDate_textView);
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


    public Event getEvent(){
        return event;
    }


    public void displayEndDate(){
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


    public boolean isValidTimeframe(){
        boolean validTimeframe = false;

        if(startTimeHour != -1 && startTimeMinute != -1 && endTimeHour != -1 && endTimeMinute != -1){

            if((startTimeHour < endTimeHour) || ((startTimeHour <= endTimeHour) && startTimeMinute < endTimeMinute)){
                validTimeframe = true;
            }
            else{
                validTimeframe = false;
            }
        }

        return validTimeframe;
    }


    public boolean isValidNameEntered(){
        boolean validNameEntered;
        EditText taskNameEditText = findViewById(R.id.taskNameEditText);
        taskName = taskNameEditText.getText().toString();

        if(!taskName.equals("")){
            validNameEntered = taskModel.checkName(event, taskName);
        }
        else{
            validNameEntered = false;
        }

        return validNameEntered;
    }


    public boolean isValidReminder(){
        boolean validReminderTime;
        TextView reminder_number = findViewById(R.id.reminderTime_textView);
        Integer reminder_amount = Integer.parseInt(reminder_number.getText().toString());
        reminderTime = reminder_amount;

        if(!(reminderTime == -1)){
            validReminderTime = true;
        }
        else{
            validReminderTime = false;
        }

        return validReminderTime;
    }


    public void addButtonClicked(View view){
        //create new task - need name, hours, minutes, reminder
        if(isValidNameEntered() && isValidTimeframe() && isValidReminder()){
            Duration startTime = new Duration(this.startTimeHour, this.startTimeMinute);
            Duration endTime = new Duration(this.endTimeHour, this.endTimeMinute);

            if(taskModel.checkTimeFrame(event, startTime, endTime)){
                Task newTask = new Task(taskName, startTime, endTime, reminderTime);
                taskModel.saveTask(this, newTask);
                event.addTask(newTask);

                if(taskModel.checkSavedTasks(newTask)){
                    storedTaskManager.addTask(this.context, newTask);
                    spinnerItems.add(newTask.getName());
                    Log.d("ghope04999", "addButtonClicked: Task was saved");
                }
                else{
                    Log.d("ghope04999", "addButtonClicked: Task was not saved");
                }
                Intent intent = new Intent();
                intent.putExtra("Task", newTask);
                setResult(RESULT_OK,intent);
                finish ();
            }
            else{
                Toast.makeText(this, "Unable to Add Task" , Toast.LENGTH_LONG).show();
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


    public void getSelectedTask(View view){
        String taskName = (String) spinner.getSelectedItem();
        findSelectedTaskData(taskName);
    }


    public void findSelectedTaskData(String taskName){

        if(!taskName.equals("New")){

            ArrayList<Task> tasks = storedTaskManager.getAllTasks(context);
            int numberOfTasks = tasks.size();

            if(numberOfTasks <= 0){
                Log.d("ghope04999", "displaySelectedTaskData: NOT GOOOOOOOOD SOMETHING WRONG");
            }

            Task loadedTask = null;
            boolean foundTask = false;
            for(int i = 0; i < numberOfTasks; i++){
                if(tasks.get(i).getName().equals(taskName)){
                    loadedTask = tasks.get(i);
                    foundTask = true;
                    break;
                }
                else{
                    Log.d("ghope04999", "displaySelectedTaskData: Task was never found");
                }
            }

            if(foundTask){
                displaySelectedTask(loadedTask);
            }
            else{
                Log.d("ghope04999", "displaySelectedTaskData: Unable to find task");
                Toast.makeText(this, "Unable to find task", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void displaySelectedTask(Task task){

        Duration startDuration = task.getStartDuration();
        Duration endDuration = task.getEndDuration();
        int reminderTime = task.getReminderTimeMinutes();
        int startHour = startDuration.getHour();
        int startMinute = startDuration.getMinute();
        int endHour = endDuration.getHour();
        int endMinute = endDuration.getMinute();

        String startTimeToDisplay;
        String startMinuteToDisplay;
        String endTimeToDisplay;
        String endMinuteToDisplay;

        if(startMinute < 10){
            startMinuteToDisplay = "0" + startMinute;
        }
        else{
            startMinuteToDisplay = "" + startMinute;
        }

        if(startHour < 10){
            startTimeToDisplay = "0" + startHour;
        }
        else{
            startTimeToDisplay = "" + startHour;
        }

        String time = startTimeToDisplay + ":" + startMinuteToDisplay;
        startDateDisplay.setText(time);

        if(endMinute < 10){
            endMinuteToDisplay = "0" + endMinute;
        }
        else{
            endMinuteToDisplay = "" + endMinute;
        }

        if(endHour < 10){
            endTimeToDisplay = "0" + endHour;
        }
        else{
            endTimeToDisplay = "" + endHour;
        }

        String endTime = endTimeToDisplay + ":" + endMinuteToDisplay;
        endDateDisplay.setText(endTime);

        String taskName = task.getName();
        taskNameDisplay.setText(taskName);
    }


    public void loadSpinneritems(){

        ArrayList<Task> tasks = storedTaskManager.getAllTasks(context);
        int numberOfTasks = tasks.size();

        if(numberOfTasks > 0){
            for(int i = 0; i < numberOfTasks; i++){
                spinnerItems.add(tasks.get(i).getName());
            }
        }
    }
}
