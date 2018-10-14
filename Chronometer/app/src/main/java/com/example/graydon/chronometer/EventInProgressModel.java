package com.example.graydon.chronometer;

import java.util.Calendar;

public class EventInProgressModel {
    private Event event;
    private Task currentTask;
    public EventInProgressModel(Event event){
        if (event == null)
            throw new IllegalArgumentException("Event cannot be null");
        this.event = event;
    }
    public Task nextTask(boolean isComplete){
        return currentTask = event.nextTask(isComplete);
    }

    public Calendar getCurrentTaskStartTime(){
        Calendar currentTaskStartTime = Calendar.getInstance();
        currentTaskStartTime.set(Calendar.HOUR_OF_DAY,currentTask.getStartTimeHour());
        currentTaskStartTime.set(Calendar.MINUTE,currentTask.getStartTimeMinute());
        currentTaskStartTime.set(Calendar.SECOND,0);
        currentTaskStartTime.set(Calendar.MILLISECOND,0);
        return currentTaskStartTime;
    }
    public Calendar getCurrentTaskEndTime(){
        Calendar currentTaskEndTime = Calendar.getInstance();
        currentTaskEndTime.set(Calendar.HOUR_OF_DAY,currentTask.getStartTimeHour());
        currentTaskEndTime.set(Calendar.MINUTE,currentTask.getStartTimeMinute());
        currentTaskEndTime.set(Calendar.SECOND,0);
        currentTaskEndTime.set(Calendar.MILLISECOND,0);
        return currentTaskEndTime;
    }

    public long getCurrentTimeLeft(){
        return getCurrentTaskEndTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }


}
