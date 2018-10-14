package com.example.graydon.chronometer;

import java.util.TimerTask;

public class Duration {
    private long seconds;
    private int minute;
    private int hourOfDay;

    public Duration(int hourOfDay, int minute){
        if(hourOfDay < 0 || hourOfDay > 23 || minute > 59 || minute < 0){
            throw new IllegalArgumentException("Hours must be in 0-23 range, Minutes must be in 0-59 range");
        }
        this.minute = minute;
        this.hourOfDay = hourOfDay;
    }

    public long getSeconds(){
        return seconds;
    }

    public int getMinute(){
        return minute;
    }

    public int getHour(){
        return hourOfDay;
    }

    public void setSeconds(int seconds){
        if(seconds <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.seconds = seconds * 1000;
    }

    public void setMinute(int minutes){
        if(minutes <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.minute = minute * 60000;
    }

    public void setHour(int hours){
        if(hours <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.hourOfDay = hours * 3600000;
    }

    public long getTotalTime(){
        return (this.seconds + this.minute + this.hourOfDay);
    }

}

