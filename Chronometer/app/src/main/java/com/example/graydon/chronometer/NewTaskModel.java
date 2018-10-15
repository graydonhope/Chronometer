package com.example.graydon.chronometer;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class NewTaskModel {

    private StoredTaskManager storedTaskManager = new StoredTaskManager();
    private ArrayList<Task> allTasksList;
    private String name;
    private Context context;
    private Duration startDuration, endDuration;
    private static NewTaskModel instance;
    private int taskListSize, currentStartTimeHour, currentEndTimeHour, currentStartTimeMinute, currentEndTimeMinute;


    public NewTaskModel(Context context, Duration startDuration, Duration endDuration, String name){
        this.startDuration = startDuration;
        this.endDuration   = endDuration;
        this.name          = name;
        this.instance      = this;
        this.context       = context;
        this.allTasksList  = storedTaskManager.getAllTasks(context);
        this.currentStartTimeHour   = this.startDuration.getHour();
        this.currentEndTimeHour     = this.endDuration.getHour();
        this.currentStartTimeMinute = this.startDuration.getMinute();
        this.currentEndTimeMinute   = this.endDuration.getMinute();
    }


    public boolean checkNameUnique(){
       taskListSize = allTasksList.size();
        boolean nameFound = false;
        if(taskListSize > 0){
            for(int i = 0; i < taskListSize; i++){
                Task taskAtIteration = storedTaskManager.getTask(this.context, i);
                if(name.equals(taskAtIteration.getName())){
                    nameFound = true;
                    Log.d("!!!!!", "checkNameUnique: Duplicate Name found");
                    break;
                }
            }
        }
        return nameFound;
    }

    public boolean checkTimeFrame(){
        boolean validTime = true;
        Log.d("AAAAAAAAAAAAA", "checkTimeFrame: IsTaslkist empty??: " + allTasksList.isEmpty());
        taskListSize = allTasksList.size();
        for(int i = 0; i < taskListSize; i++){
            Task taskAtIteration = storedTaskManager.getTask(this.context, i);
            //check if time has been added
            int startHour   = taskAtIteration.getStartHour();
            int startMinute = taskAtIteration.getStartMinute();
            int endHour     = taskAtIteration.getEndHour();
            int endMinute   = taskAtIteration.getEndMinute();
            if(currentStartTimeHour == -1 && currentStartTimeMinute == -1 && currentEndTimeHour == -1 && currentEndTimeMinute == -1){
                validTime = false;
                Log.d("!!!!!!", "checkTimeFrame: SETtofalse!");
            }

            if((currentStartTimeHour < startHour) && (currentEndTimeHour > endHour)){
                validTime = false;
                Log.d("!!!!!!", "checkTimeFrame: SETtofalse  2");
            }

            if(((currentStartTimeHour < startHour) && ((currentEndTimeHour > startHour) && (currentEndTimeHour <= endHour))) ||  (startHour < currentStartTimeHour) && ((endHour > currentStartTimeHour) && (endHour <= currentEndTimeHour))){
                validTime = false;
                Log.d("!!!!!!", "checkTimeFrame: SETtofalse    3");
            }

            if(currentStartTimeHour == startHour){
                validTime = false;
                Log.d("THIS ONE!!!!!XXX", "checkTimeFrame: Blocked because you already have a task that starts at that time");
            }



            if((currentStartTimeHour > currentEndTimeHour) || ((currentStartTimeHour == currentEndTimeHour) && (currentStartTimeMinute >= currentEndTimeMinute)) ){
                Log.d("!!!!XXXXX!!!", "checkTimeFrame: currentstarTimeMinute: " + currentEndTimeMinute + " Current End Time Min: " + currentEndTimeMinute);
                validTime = false;
                Log.d("!!!!!!", "checkTimeFrame: SETtofalse    4");
            }

            if(((currentStartTimeHour > startHour) && (currentStartTimeHour < endHour)) || ((currentEndTimeHour > startHour) && (currentEndTimeHour < endHour))){
                validTime = false;
                Log.d("!!!!!!", "checkTimeFrame: SETtofalse       5");
            }
        }
        return validTime;
    }

    public void saveTask(Context context, Task task){
        storedTaskManager.addTask(context, task);
    }
}
