package com.example.graydon.chronometer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.base.AbstractInterval;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class NewTaskModel extends AppCompatActivity{

    private StoredTaskManager storedTaskManager = new StoredTaskManager();
    private String name;
    private Context context;
    private Event event;
    private Duration startDuration, endDuration;
    private int taskListSize, currentStartTimeHour, currentEndTimeHour, currentStartTimeMinute, currentEndTimeMinute;
    private List<String> spinnerItems;


    /**
     * Constructor for the Task Model. Makes a taskActivity object to access UI related components and send requested information back.
     * @param context
     */
    public NewTaskModel(Context context){
        spinnerItems = new ArrayList<>();
        spinnerItems.add("New");
        this.context = context;
    }


    /**
     * Returns a boolean value which checks all tasks in a given Event for uniqueness of user entered name.
     * @param event
     * @param taskName
     * @return boolean
     */
    public boolean checkName(Event event, String taskName){
        if(event == null){
            throw new IllegalArgumentException("event cannot be null");
        }
        else if(event.isEmpty()){
            return true;
        }
        else{
            ArrayList<Task> tasks = event.getTasks();
            int numberOfTasks = tasks.size();
            boolean validName = false;
            for(int i = 0; i < numberOfTasks; i++){
                if(event.getTask(i).getName().equals(taskName)){
                    validName = false;
                    break;
                }
                else{
                    validName = true;
                }
            }
            return validName;
        }
    }



    /**
     * Returns a boolean value to ensure a unique task name.
     * @param task
     * @return boolean
     */
    public boolean checkSavedTasks(Task task){
        boolean canSaveTask = false;
        ArrayList<Task> tasks = storedTaskManager.getAllTasks(context);
        String taskName = task.getName();
        int numberOfTasks = tasks.size();
        Log.d("ghope04999", "checkSavedTasks: Numebr of tasks: " + numberOfTasks);

        if(numberOfTasks < 1){
            canSaveTask = true;
        }
        else{
            for(int i = 0; i < numberOfTasks; i++){

                if(tasks.get(i).getName().equals(taskName)){
                    canSaveTask = false;
                    Log.d("ghope04999", "checkSavedTasks: Current task name: " + task.getName());
                    Log.d("ghope04999", "checkSavedTasks: Found it here: " + tasks.get(i).getName());
                    break;
                }
                else{
                    Log.d("ghope04999", "checkSavedTasks: inside else????");
                    canSaveTask = true;
                }
            }
        }
        Log.d("ghope04999", "checkSavedTasks: boolean: " + canSaveTask);
        return canSaveTask;
    }


    /**
     * Returns if the user entered time frame is valid. The user cannot enter conflicting or overlapping times.
     * @param eventPassed
     * @param startDuration
     * @param endDuration
     * @return boolean
     */
    public boolean checkTimeFrame(Event eventPassed, Duration startDuration, Duration endDuration){
        event = eventPassed;
        boolean newEvent = (event == null);

        if(!newEvent){

            if(!event.isEmpty()){
                ArrayList<Task> tasks = event.getTasks();
                boolean validTime = true;

                if(tasks.size() == 0){
                    validTime = true;
                }
                else{
                    taskListSize = tasks.size();

                    this.currentStartTimeHour   = startDuration.getHour();
                    this.currentEndTimeHour     = endDuration.getHour();
                    this.currentStartTimeMinute = startDuration.getMinute();
                    this.currentEndTimeMinute   = endDuration.getMinute();
                    LocalDateTime localDateTime = new LocalDateTime();
                    Calendar startTime = Calendar.getInstance();
                    Calendar endTime = Calendar.getInstance();
                    startTime.set(2018, 1,1, this.currentStartTimeHour, this.currentStartTimeMinute);
                    endTime.set(2018,1,1, this.currentEndTimeHour, this.currentEndTimeMinute);


                    for(int i = 0; i < taskListSize; i++){
                        Task taskAtIteration = tasks.get(i);
                        int startHour   = taskAtIteration.getStartHour();
                        int endHour     = taskAtIteration.getEndHour();
                        int startMinute = taskAtIteration.getStartMinute();
                        int endMinute   = taskAtIteration.getEndMinute();

                        Calendar startCheck = Calendar.getInstance();
                        Calendar endCheck = Calendar.getInstance();
                        startCheck.set(2018, 1, 1, startHour, startMinute);
                        endCheck.set(2018,1,1, endHour, endMinute);

                        if((startTime.before(endCheck)) && (startCheck.before(endTime))){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Time overlap");
                        }



                        /*

                        if(currentStartTimeHour == -1 && currentStartTimeMinute == -1 && currentEndTimeHour == -1 && currentEndTimeMinute == -1){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Case1" + validTime);
                        }

                        if((currentStartTimeHour < startHour) && (currentEndTimeHour > endHour)){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Case2" + validTime);

                        }

                        if(((currentStartTimeHour < startHour) && ((currentEndTimeHour > startHour) && (currentEndTimeHour <= endHour))) ||  (startHour < currentStartTimeHour) && ((endHour > currentStartTimeHour) && (endHour <= currentEndTimeHour))){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Case3" + validTime);

                        }

                        if((currentStartTimeHour == startHour) && ((currentStartTimeMinute <= startMinute) || (currentStartTimeMinute <=endMinute))){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Case4" + validTime);

                        }

                        if((currentStartTimeHour > currentEndTimeHour) || ((currentStartTimeHour == currentEndTimeHour) && (currentStartTimeMinute >= currentEndTimeMinute)) ){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Case5" + validTime);

                        }

                        if(((currentStartTimeHour > startHour) && (currentStartTimeHour < endHour)) || ((currentEndTimeHour > startHour) && (currentEndTimeHour < endHour))){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: Case6" + validTime);

                        }

                        */


                    }
                }
                return validTime;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }


    /**
     * Accessing the stored task manager to save the specific task.
     * @param context
     * @param task
     */
    public void saveTask(Context context, Task task){
        storedTaskManager.addTask(context, task);
    }


    /**
     * Setting the task name.
     * @param name
     */
    public void setTaskName(String name){

        if(name == null){
            throw new IllegalArgumentException();
        }
        else{
            this.name = name;
        }
    }


    /**
     * Accessing the duration object to set the start time value.
     * @param startDuration
     */
    public void setStart(Duration startDuration){

        if(startDuration != null){
            this.startDuration = startDuration;
        }
        else{
            throw new IllegalArgumentException("Duration cannot be null");
        }
    }


    /**
     * Accessing the duration object to set the end time value.
     * @param endDuration
     */
    public void setEnd(Duration endDuration){

        if(endDuration != null){
            this.endDuration = endDuration;
        }
        else{
            throw new IllegalArgumentException("Duration cannot be null");
        }
    }
}
