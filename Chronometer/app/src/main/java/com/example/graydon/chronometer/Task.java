package com.example.graydon.chronometer;

public class Task {
    private String name;
    private boolean isComplete;
    private Duration startDuration, endDuration;
    private int reminderTime;

    public Task(String name, Duration startTime, Duration endTime, int reminderTimeMinutes) {
        this.reminderTime = reminderTimeMinutes;
        this.name = name;
        isComplete = false;
        this.startDuration = startTime;
        this.endDuration = endTime;
    }

    /**
     *
     * @return
     */
    public boolean getIsComplete() {
        return this.isComplete;
    }

    /**
     *
     * @param isComplete
     */
    public void setIsComplete(boolean isComplete){
        this.isComplete = isComplete;
    }

    public Duration getStartDuration(){
        return this.startDuration;
    }

    public Duration getEndDuration(){
        return this.endDuration;
    }

    public int getStartHour(){
        return this.startDuration.getHour();
    }

    public int getEndHour(){
        return this.endDuration.getHour();
    }

    public int getStartMinute(){
        return this.startDuration.getMinute();
    }

    public int getEndMinute(){
        return this.endDuration.getMinute();
    }

    public void setStartHour(int startHour){
        this.startDuration.setHour(startHour);
    }

    public void setStartTimeMinute(int startTimeMinute){
        this.startDuration.setMinute(startTimeMinute);
    }

    public void setEndTimeMinute(int endTimeMinute){
        this.endDuration.setMinute(endTimeMinute);
    }

    public void setEndHour(int endHour){
        this.endDuration.setHour(endHour);
    }

    public int getReminderTimeMinutes(){
        return this.reminderTime;
    }

    public void setReminderMinutes(int reminderTime){
        this.reminderTime = reminderTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}

