package com.example.graydon.chronometer;

import android.content.Context;

import java.util.Calendar;

public class EventInProgressModel {
    private Event event;
    private Task currentTask;
    public EventInProgressModel(Context appContext, Event event){
        if (event == null)
            throw new IllegalArgumentException("Event cannot be null");
        this.event = event;
        if(event.getCurrentTaskIndex() == -1){
            this.currentTask = event.nextTask(false);
        }
        else{
            this.currentTask = event.getTask(event.getCurrentTaskIndex());
        }
        StoredTaskManager.saveCurrentEvent(appContext,this.event);
        StoredTaskManager.setEventInProgress(appContext,true);
    }
    public Task nextTask(boolean isComplete){
        return currentTask = event.nextTask(isComplete);
    }

    public Calendar getCurrentTaskStartTime(){
        Calendar currentTaskStartTime = Calendar.getInstance();
        currentTaskStartTime.set(Calendar.HOUR_OF_DAY,currentTask.getStartHour());
        currentTaskStartTime.set(Calendar.MINUTE,currentTask.getStartMinute());
        currentTaskStartTime.set(Calendar.SECOND,0);
        currentTaskStartTime.set(Calendar.MILLISECOND,0);
        return currentTaskStartTime;
    }


    public Calendar getCurrentTaskEndTime(){
        Calendar currentTaskEndTime = Calendar.getInstance();
        currentTaskEndTime.set(Calendar.HOUR_OF_DAY,currentTask.getEndHour());
        currentTaskEndTime.set(Calendar.MINUTE,currentTask.getEndMinute());
        currentTaskEndTime.set(Calendar.SECOND,0);
        currentTaskEndTime.set(Calendar.MILLISECOND,0);
        return currentTaskEndTime;
    }

    public long getCurrentTimeLeft(){
        return getCurrentTaskEndTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }
    public Task getCurrentTask(){
        return currentTask;
    }

    public Event getEvent(){
        return  event;
    }
    public long getCurrentTaskReminderTimeInMili(){
        return currentTask.getReminderTimeMinutes() * 60000;
    }
    public void setCurrentTaskIsComplete(boolean isComplete){
        getCurrentTask().setIsComplete(isComplete);
    }

    public void saveCurrentEvent(){


    }





}
