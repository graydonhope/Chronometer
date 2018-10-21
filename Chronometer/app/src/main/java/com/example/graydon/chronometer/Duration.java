package com.example.graydon.chronometer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.TimerTask;

public class Duration implements Parcelable{
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

    protected Duration(Parcel in) {
        seconds = in.readLong();
        minute = in.readInt();
        hourOfDay = in.readInt();
    }

    public static final Creator<Duration> CREATOR = new Creator<Duration>() {
        @Override
        public Duration createFromParcel(Parcel in) {
            return new Duration(in);
        }

        @Override
        public Duration[] newArray(int size) {
            return new Duration[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(seconds);
        dest.writeInt(minute);
        dest.writeInt(hourOfDay);
    }
}

