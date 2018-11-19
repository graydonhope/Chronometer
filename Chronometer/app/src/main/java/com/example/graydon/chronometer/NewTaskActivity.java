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
    private TextView editReminderTime;
    private String taskName;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerStart;
    private TimePickerDialog.OnTimeSetListener timePickerDialogListenerEnd;
    private int startTimeHour = -1, endTimeHour = -1, startTimeMinute = -1, endTimeMinute = -1, reminderTime = -1;
    private static final int CHRONO_BLACK = Color.parseColor("#1F1F1F");
    private NewTaskModel taskModel;
    private StoredTaskManager storedTaskManager;
    private Spinner spinner;
    private Context context;
    private List<String> spinnerItems = new ArrayList<>();
    private Intent intent;
    private Event event;


    /**
     * In the onCreate method, the event is received from EventInfoActivity. The event is required in this activity so that tasks can be validated before
     * they are entered into the task. The spinner object is the dropdown menu. Initially it needs a "New" option in case the user
     * wants to create a new task. The rest of the items in the spinner dropdown are loaded from the loadSpinnerItems method. This
     * will populate all of the previously saved tasks on the device. The time picker dialog is the time selection tool which
     * allows the user to choose a start and end time for the task they are creating.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = getApplicationContext();
        super.onCreate(savedInstanceState);
        this.storedTaskManager = new StoredTaskManager();
        setContentView(R.layout.activity_newtask);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CHRONO_BLACK));
        intent = getIntent();
        event = intent.getParcelableExtra("event");
        taskModel = new NewTaskModel(this);
        spinnerItems.add("New");
        loadSpinnerItems();
        spinner = findViewById(R.id.typeOfTask_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        taskNameDisplay = findViewById(R.id.taskNameEditText);
        editReminderTime = findViewById(R.id.editReminderTime);
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


    /**
     * Changes the time selector date format into a 24 format for user clarity. If
     * the hour or minute is less than 10, it will concatenate a zero to enhance GUI.
     */
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


    /**
     * Changes the time selector date format into a 24 format for user clarity. If
     * the hour or minute is less than 10, it will concatenate a zero to enhance GUI.
     */
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


    /**
     * Returns a boolean value which checks to see if the user has entered dates into the start and end TextViews.
     * @return boolean
     */
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


    /**
     * Returns a boolean value which ensures the user has entered a task name.
     * @return boolean
     */
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


    /**
     * Returns a boolean value which ensures the user has entered a valid reminder time value.
     * @return boolean
     */
    public boolean isValidReminder(){
        boolean validReminderTime = false;

        try{
            String reminderTimeEntry = editReminderTime.getText().toString();
            reminderTime = Integer.parseInt(reminderTimeEntry);

            if(reminderTime > 0){
                validReminderTime = true;
            }
            else{
                validReminderTime = false;
            }

        }
        catch(NumberFormatException e){
            Toast.makeText(this, "Invalid Reminder Time", Toast.LENGTH_LONG).show();
        }

     return validReminderTime;
    }


    /**
     * When the addtask button is clicked, this method is called. This method validates what values the user has entered
     * into the new task activity. The method calls a helper function to ensure the time frame is valid.
     * Once the values have been verified, the method will add the task to the event. If the task name has not been
     * taken before, it will save the task on the device using the stored task manager. Then the newly added task is sent
     * back to the Event activity so it can be added to the list view of tasks.
     * @param view
     */
    public void addButtonClicked(View view){

        if(isValidNameEntered() && isValidTimeframe() && isValidReminder()){
            Duration startTime = new Duration(this.startTimeHour, this.startTimeMinute);
            Duration endTime = new Duration(this.endTimeHour, this.endTimeMinute);

            if(taskModel.checkTimeFrame(event, startTime, endTime)){
                Task newTask = new Task(taskName, startTime, endTime, reminderTime);
                event.addTask(newTask);

                if(taskModel.checkSavedTasks(newTask)){
                    taskModel.saveTask(this, newTask);
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
                Toast.makeText(this, "Unable to Add Task. Invalid time frame" , Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Please enter a valid task name, reminder time, and time frame", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Returns the current event
     * @return Event
     */
    public Event getEvent(){
        return event;
    }


    /**
     * Cancels the operation of adding a new task
     * @param view
     */
    public void cancelButton (View view){
        Intent intent = new Intent(this, EventInfoActivity.class);
        setResult (RESULT_CANCELED,intent);
        finish();
    }


    /**
     * Sends the user selection from the spinner dropdown menu to the findSelectedTaskData method.
     * @param view
     */
    public void getSelectedTask(View view){
        String taskName = (String) spinner.getSelectedItem();
        findSelectedTaskData(taskName);
    }


    /**
     * Iterates through the saved tasks and finds which task has been selected. It then retrieves that specific tasks information
     * and sends to the displaySelectedTask method.
     * @param taskName
     */
    public void findSelectedTaskData(String taskName){

        if(!taskName.equals("New")){

            ArrayList<Task> tasks = storedTaskManager.getAllTasks(context);
            int numberOfTasks = tasks.size();

            if(numberOfTasks <= 0){
                Log.d("ghope04999", "displaySelectedTaskData: Incorrect Amount of Tasks");
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
        else{
            taskNameDisplay.setText("");
            startDateDisplay.setText("1:00 PM");
            endDateDisplay.setText("2:00 PM");
        }
    }


    /**
     * Organizes the Tasks duration so it can be displayed on the UI.
     * @param task
     */
    public void displaySelectedTask(Task task){
        Duration startDuration = task.getStartDuration();
        Duration endDuration = task.getEndDuration();
        reminderTime = task.getReminderTimeMinutes();
        Log.d("ghope04999", "displaySelectedTask: Reminder time from loaded task: " + reminderTime);
        startTimeHour = startDuration.getHour();
        startTimeMinute = startDuration.getMinute();
        endTimeHour = endDuration.getHour();
        endTimeMinute = endDuration.getMinute();

        String startTimeToDisplay;
        String startMinuteToDisplay;
        String endTimeToDisplay;
        String endMinuteToDisplay;

        if(startTimeMinute < 10){
            startMinuteToDisplay = "0" + startTimeMinute;
        }
        else{
            startMinuteToDisplay = "" + startTimeMinute;
        }

        if(startTimeHour < 10){
            startTimeToDisplay = "0" + startTimeHour;
        }
        else{
            startTimeToDisplay = "" + startTimeHour;
        }

        String time = startTimeToDisplay + ":" + startMinuteToDisplay;
        startDateDisplay.setText(time);

        if(endTimeMinute < 10){
            endMinuteToDisplay = "0" + endTimeMinute;
        }
        else{
            endMinuteToDisplay = "" + endTimeMinute;
        }

        if(endTimeHour < 10){
            endTimeToDisplay = "0" + endTimeHour;
        }
        else{
            endTimeToDisplay = "" + endTimeHour;
        }

        String endTime = endTimeToDisplay + ":" + endMinuteToDisplay;
        endDateDisplay.setText(endTime);

        String taskName = task.getName();
        taskNameDisplay.setText(taskName);

        String stringReminderTime = Integer.toString(reminderTime);
        editReminderTime.setText(stringReminderTime);
    }


    /**
     * Iterates through the saved task list and adds the tasks to the dropdown spinner. This is so the user has
     * previous tasks available for selection.
     */
    public void loadSpinnerItems(){
        ArrayList<Task> tasks = storedTaskManager.getAllTasks(context);
        int numberOfTasks = tasks.size();

        if(numberOfTasks > 0){
            for(int i = 0; i < numberOfTasks; i++){
                spinnerItems.add(tasks.get(i).getName());
            }
        }
    }
}
