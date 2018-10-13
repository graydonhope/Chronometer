package com.example.graydon.chronometer;

import java.util.TimerTask;

public class Duration {
    private long seconds;
    private long minutes;
    private long hours;

    public Duration(int hours, int minutes, int seconds){
        this.seconds = seconds * 1000;
        this.minutes = minutes * 60000;
        this.hours = hours * 3600000;
    }

    public long getSeconds(){
        return seconds;
    }

    public long getMinutes(){
        return minutes;
    }

    public long getHours(){
        return hours;
    }

    public void setSeconds(int seconds){
        if(seconds <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.seconds = seconds * 1000;
    }

    public void setMinutes(int minutes){
        if(minutes <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.minutes = minutes * 60000;
    }

    public void setHours(int hours){
        if(hours <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.hours = hours * 3600000;
    }

    public long getTotalTime(){
        return (this.seconds + this.minutes + this.hours);
    }

}

