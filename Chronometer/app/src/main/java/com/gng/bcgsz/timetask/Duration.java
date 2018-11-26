package com.gng.bcgsz.timetask;

import android.os.Parcel;
import android.os.Parcelable;

public class Duration implements Parcelable{

    private long seconds;
    private int minute;
    private int hourOfDay;


    /**
     * Constructor for the Duration.
     * @param hourOfDay
     * @param minute
     */
    public Duration(int hourOfDay, int minute){
        if(hourOfDay < 0 || hourOfDay > 23 || minute > 59 || minute < 0){
            throw new IllegalArgumentException("Hours must be in 0-23 range, Minutes must be in 0-59 range");
        }
        this.minute = minute;
        this.hourOfDay = hourOfDay;
    }

    /**
     * Reading parcelable values inputted.
     * @param in
     */
    protected Duration(Parcel in) {
        seconds = in.readLong();
        minute = in.readInt();
        hourOfDay = in.readInt();
    }


    /**
     * Parcelable related creation.
     */
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


    /**
     * Returns the number of seconds of Duration.
     * @return
     */
    public long getSeconds(){
        return seconds;
    }


    /**
     * Returns Duration minute value.
     * @return int
     */
    public int getMinute(){
        return minute;
    }


    /**
     * Returns the Duration hour value.
     * @return
     */
    public int getHour(){
        return hourOfDay;
    }


    /**
     * Sets the seconds in millisecond value
     * @param seconds
     */
    public void setSeconds(int seconds){
        if(seconds <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.seconds = seconds * 1000;
    }


    /**
     * Sets the minutes in millisecond value.
     * @param minutes
     */
    public void setMinute(int minutes){
        if(minutes <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.minute = minute * 60000;
    }


    /**
     * Sets the hours in milliseconds format.
     * @param hours
     */
    public void setHour(int hours){
        if(hours <= 0){
            throw new IllegalArgumentException("Cannot enter a value less than or equal to 0");
        }
        this.hourOfDay = hours * 3600000;
    }


    /**
     * Returns the total time of Duration.
     * @return long
     */
    public long getTotalTime(){
        return (this.seconds + this.minute + this.hourOfDay);
    }


    /**
     * Calls describe content method
     * @return int
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Used so information can be passed between activities.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(seconds);
        dest.writeInt(minute);
        dest.writeInt(hourOfDay);
    }
}

