package com.example.graydon.chronometer;

public class Task {
    private String name;
    private boolean isComplete;
    private long minutes_inMilli;
    private long hours_inMilli;

    public Task(String name, int hours, int minutes){
        if(hours < 0 || hours > 23 || minutes > 59 || minutes < 0){
            throw new IllegalArgumentException("Hours must be in 0-23 range, Minutes must be in 0-59 range");
        }
        this.name = name;
        isComplete = false;
        this.minutes_inMilli = minutes * 60000;
        this.hours_inMilli = hours * 3600000;
    }
    public boolean getIsComplete(){
        return this.isComplete;
    }

    public String getName(){
        return this.name;
    }


    public long getTaskTime_inMilli(){
        return (this.hours_inMilli + this.minutes_inMilli);
    }


    public void setName(String name){
        this.name = name;
    }


    public void setTaskTime(int hours, int minutes){
        setMinutes(minutes);
        setHours(hours);
    }

    public String toString(){
        return "Name of task: " + name;
    }

    public void setIsComplete(boolean isComplete){
        this.isComplete = isComplete;
    }

    public long getMinutes_inMilli(){
        return this.minutes_inMilli;
    }

    public long getHours_inMilli(){
        return this.hours_inMilli;
    }

    public void setHours(int hours){
        this.hours_inMilli = hours * 3600000;
    }

    public void setMinutes(int minutes){
        this.minutes_inMilli = minutes * 60000;
    }
}
