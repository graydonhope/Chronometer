package com.example.graydon.chronometer;

public class Task {
    String name;
    Duration durationOfTask;
    boolean isComplete;

    public Task(String name, Duration durationOfTask){
        this.name = name;
        this.durationOfTask = durationOfTask;
        isComplete = false;
    }
    public boolean getIsComplete(){
        return this.isComplete;
    }

    public String getName(){
        return this.name;
    }

    public Duration getDuration(){
        return this.durationOfTask;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDuration(Duration duration){
        this.durationOfTask = duration;
    }

    public String toString(){
        return "Name of task: " + name;
    }

    public void setIsComplete(boolean isComplete){
        this.isComplete = isComplete;
    }
}
