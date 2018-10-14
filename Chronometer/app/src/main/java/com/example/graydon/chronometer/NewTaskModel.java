package com.example.graydon.chronometer;

import java.util.ArrayList;

public class NewTaskModel {

    private StoredTaskManager storedTaskManager = new StoredTaskManager();
    private ArrayList<Task> allTasksList;
    private String name;
    private Duration startDuration, endDuration;

    public NewTaskModel(Duration startDuration, Duration endDuration, String name){
        this.startDuration = startDuration;
        this.endDuration = endDuration;
        this.name = name;
    }


}
