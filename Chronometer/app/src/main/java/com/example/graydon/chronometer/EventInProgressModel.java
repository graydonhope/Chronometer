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
//        currentTaskStartTime.set(Calendar.HOUR_OF_DAY,currentTask.getStartTime().getHour());
//        currentTaskStartTime.set(Calendar.MINUTE,currentTask.getEndTime());
        return currentTaskStartTime;
    }

    public long currentTimeLeft(){
//        long startTimeInMili = getCurrentTaskStartTime();
        return 0;
    }
}
