package com.example.graydon.chronometer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewTaskModel extends AppCompatActivity{

    private StoredTaskManager storedTaskManager = new StoredTaskManager();
    private ArrayList<Task> allTasksList;
    private List<String> spinnerItems;
    private String name;
    private Context context;
    private Event event;
    private Duration startDuration, endDuration;
    private int taskListSize, currentStartTimeHour, currentEndTimeHour, currentStartTimeMinute, currentEndTimeMinute;
    private NewTaskActivity taskActivity;

    //NEED to save the spinner items list so that after you click "end task" and it comes back to the
    // new Task page, it keeps the new task in the spinner dropdown menu. problem now is that every time you get to
    // the new task activity page, it setscontent view and creates a new task model which erases any tasks that were added

    public NewTaskModel(Context context){
        spinnerItems = new ArrayList<String>();
        spinnerItems.add("New");
        this.allTasksList  = storedTaskManager.getAllTasks(context);
        this.context = context;
        this.taskActivity = new NewTaskActivity();
    }


    public boolean checkNameUnique(Context context){
        taskListSize = allTasksList.size();
        boolean nameFound = false;
        if(taskListSize > 0){
            for(int i = 0; i < taskListSize; i++){
                Task taskAtIteration = storedTaskManager.getTask(context, i);
                if(name.equals(taskAtIteration.getName())){
                    nameFound = true;
                    break;
                }
            }
        }
        return nameFound;
    }

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
                    Log.d("ghope04999", "checkName: Name is equal to anothjer name!!");
                    break;
                }
                else{
                    validName = true;
                }
            }
            return validName;
        }
    }



    public boolean checkSavedTasks(Task task){

        boolean canSaveTask = false;
        ArrayList<Task> tasks = storedTaskManager.getAllTasks(context);
        String taskName = task.getName();

        for(int i = 0; i < tasks.size(); i++){
            if(!tasks.get(i).getName().equals(taskName)){
                canSaveTask = false;
                break;
            }
            else{
                canSaveTask = true;
            }
        }
        return canSaveTask;
    }

    public boolean checkTimeFrame(Event eventPassed, Duration startDuration, Duration endDuration){

        event = eventPassed;
        Log.d("ghope0494449", "checkTimeFrame: Is event null?" + " " + (event == null));
        boolean newEvent = (event == null);
        if(!newEvent){
            Log.d("##########", "checkTimeFrame: First IF STATEMENT");
            if(!event.isEmpty()){
                Log.d("#########", "checkTimeFrame: Second if checking if event has any tasks");
                ArrayList<Task> tasks = event.getTasks();
                Log.d("PPPPPPPPP", "checkTimeFrame: number of tasks: " + tasks.size());
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
                    //Need to change from checking all saved tasks to just "current" ones
                    for(int i = 0; i < taskListSize; i++){
                        Task taskAtIteration = tasks.get(i);

                        Log.d("ghope04999", "checkTimeFrame: task time start: " + taskAtIteration.getStartHour());

                        //check if time has been added
                        int startHour   = taskAtIteration.getStartHour();
                        // int startMinute = taskAtIteration.getStartMinute();
                        int endHour     = taskAtIteration.getEndHour();
                        //int endMinute   = taskAtIteration.getEndMinute();
                        if(currentStartTimeHour == -1 && currentStartTimeMinute == -1 && currentEndTimeHour == -1 && currentEndTimeMinute == -1){
                            validTime = false;
                        }

                        if((currentStartTimeHour < startHour) && (currentEndTimeHour > endHour)){
                            validTime = false;
                        }

                        if(((currentStartTimeHour < startHour) && ((currentEndTimeHour > startHour) && (currentEndTimeHour <= endHour))) ||  (startHour < currentStartTimeHour) && ((endHour > currentStartTimeHour) && (endHour <= currentEndTimeHour))){
                            validTime = false;
                            Log.d("ghope04999", "checkTimeFrame: this is null? " + (this == null));
                        }

                        if(currentStartTimeHour == startHour){
                            validTime = false;
                        }

                        if((currentStartTimeHour > currentEndTimeHour) || ((currentStartTimeHour == currentEndTimeHour) && (currentStartTimeMinute >= currentEndTimeMinute)) ){
                            validTime = false;
                        }

                        if(((currentStartTimeHour > startHour) && (currentStartTimeHour < endHour)) || ((currentEndTimeHour > startHour) && (currentEndTimeHour < endHour))){
                            validTime = false;
                        }
                    }
                }
                Log.d("ghope04999", "checkTimeFrame: Deeper valid time: " + validTime);
                return validTime;
            }//end of tasks isEmpty
            else{
                return true;
            }
        }//end of event being null check
        else{
            return true;
        }
    }

    public void saveTask(Context context, Task task){
        /***
         * I COMMENTED THIS OUT -Santos
         */
        storedTaskManager.addTask(context, task);
    }
    public void loadPreviousTasks(Context context){
        ArrayList<Task> previousTasks  = storedTaskManager.getAllTasks(context);
        int numberOfPreviousTasks = previousTasks.size();
        if(numberOfPreviousTasks > 0){
            for(int i = 0; i < numberOfPreviousTasks; i++){
                this.allTasksList.add(previousTasks.get(i));
            }
        }
    }

    public void setTaskName(String name){
        if(name == null){
            throw new IllegalArgumentException();
        }
        else{
            this.name = name;
        }
    }

    public String getTaskName(){
        return this.name;
    }

    public void setEndTime(Duration duration){
        this.endDuration = duration;
    }

    public void setStartTime(Duration duration){
        this.startDuration = duration;
    }

    public List<String> retrieveSpinnerItems(){
        return spinnerItems;
    }

    public void addTaskToSpinnerList(Task task){
        if(task != null){
            allTasksList.add(task);
        }
    }

    public void addTaskToSpinner(Task task){
        if (task != null) {
            allTasksList.add(task);
            spinnerItems.add(task.getName());
        }
        else{
            throw new IllegalArgumentException("Task cannot be null");
        }
    }

    public void addSpinnerName(String name){
        spinnerItems.add(name);
    }

    public void setStart(Duration startDuration){
        if(startDuration != null){
            this.startDuration = startDuration;
        }
        else{
            throw new IllegalArgumentException("Duration cannot be null");
        }
    }

    public void setEnd(Duration endDuration){
        if(endDuration != null){
            this.endDuration = endDuration;
        }
        else{
            throw new IllegalArgumentException("Duration cannot be null");
        }
    }
}
