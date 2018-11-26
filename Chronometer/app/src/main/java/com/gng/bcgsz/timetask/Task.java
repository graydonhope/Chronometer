package com.gng.bcgsz.timetask;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable{

    private String name;
    private boolean isComplete;
    private Duration startDuration, endDuration;
    private int reminderTime;


    /**
     * Constructor for Task. Requires start and end Durations, a task name, and reminder time amount in minutes.
     * @param name
     * @param startTime
     * @param endTime
     * @param reminderTimeMinutes
     */
    public Task(String name, Duration startTime, Duration endTime, int reminderTimeMinutes) {
        this.reminderTime = reminderTimeMinutes;
        this.name = name;
        isComplete = false;
        this.startDuration = startTime;
        this.endDuration = endTime;
    }


    /**
     * Returns the name of the Task.
     * @return String
     */
    public String toString (){

        return name + "isCompleted:" + isComplete;
    }


    /**
     * Parcelable requirements.
     * @param in
     */
    protected Task(Parcel in) {
        name = in.readString();
        isComplete = in.readByte() != 0;
        startDuration = in.readParcelable(Duration.class.getClassLoader());
        endDuration = in.readParcelable(Duration.class.getClassLoader());
        reminderTime = in.readInt();
    }


    /**
     * Parcelable related creations.
     */
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    /**
     *Returns if the task has been completed
     * @return boolean
     */
    public boolean getIsComplete() {
        return this.isComplete;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isComplete ? 1 : 0));
        dest.writeParcelable(startDuration, flags);
        dest.writeParcelable(endDuration, flags);
        dest.writeInt(reminderTime);
    }


    /**
     * Setters and Getters for instance variables.
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

    public int getReminderTimeMinutes(){
        return this.reminderTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}

