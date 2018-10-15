package com.example.graydon.chronometer;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable{
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

    protected Task(Parcel in) {
        name = in.readString();
        isComplete = in.readByte() != 0;
        startDuration = in.readParcelable(Duration.class.getClassLoader());
        endDuration = in.readParcelable(Duration.class.getClassLoader());
        reminderTime = in.readInt();
    }

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
}

