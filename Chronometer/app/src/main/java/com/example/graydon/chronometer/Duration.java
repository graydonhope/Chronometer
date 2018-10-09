package com.example.graydon.chronometer;

public class Duration {
    private long seconds;
    private long minutes;
    private long hours;

    public Duration(long hours, long minutes, long seconds){

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

    public void setSeconds(long seconds){
        this.seconds = seconds;
    }

    public void setMinutes(long minutes){
        this.minutes = minutes;
    }

    public void setHours(long hours){
        this.hours = hours;
    }
}
