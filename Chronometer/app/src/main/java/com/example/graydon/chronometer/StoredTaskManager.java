package com.example.graydon.chronometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.graydon.chronometer.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*****
 *
 * CLASS NAME WILL BE CHANGED TO SaveManger
 *
 *
 */

/**
 * This is a class used to manage the user's saved tasks.
 */
public class StoredTaskManager {
    private final static Gson gson = new Gson();
    private final static String TASKS = "tasks";
    private final static String CURRENT_EVENT = "current_event";
    private final static String EVENT_IN_PROGRESS = "event_in_progress";
    private final static String USER_TASKS = "user_tasks";
    private final static String USER_EVENTS = "user_events";

    /**************************************************************************
     *
     * PUBLIC STATIC METHODS FOR DEALING WITH TASKS
     *
     **************************************************************************/
    /**
     * Retrieves tasks saved on the user's phone from SharedPreferences and returns them
     * @param appContext the application context (used for sharedPreferences)
     * * @return the list of tasks saved on user's phone.
    **/
    public static ArrayList<Task> getAllTasks(Context appContext) {
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_TASKS, Context.MODE_PRIVATE);
        String jsonOfTasks = sharedPreferences.getString(TASKS,null);
        if (jsonOfTasks == null)
            return new ArrayList<>();
        Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();
        return gson.fromJson(jsonOfTasks,taskListType);
    }

    /**
     * Retrieves a saved task at specified location (Returns null if location not in range)
     * @param appContext the application context (used for sharedPreferences)
     * @param location the index of the task
     * @return the task found at the given index
     */
    public static Task getTask(Context appContext, int location){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        ArrayList<Task> tasks = getAllTasks(appContext);
        if (location < 0 || location >= tasks.size())
            throw new IndexOutOfBoundsException(location + " is out of range. The tasks list has a size of " + tasks.size());
        return tasks.get(location);
    }

    /**
     * Adds a task to the end of the stored tasks list
     * @param appContext the application context (used for sharedPreferences)
     * @param taskToAdd the task to be added to the saved tasks
     */
    public static void addTask(Context appContext,Task taskToAdd){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        if (taskToAdd == null)
            throw new IllegalArgumentException("Passed in a null task");
        ArrayList<Task> tasks = getAllTasks(appContext);
        tasks.add(taskToAdd);
        saveTaskList(appContext,tasks);
    }

    /**
     * Removes the task at the specified location from the tasks list
     * @param appContext the application context (used for sharedPreferences)
     * @param location the location of the task to remove
     */
    public static void removeTask(Context appContext,int location){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        ArrayList<Task> tasks = getAllTasks(appContext);
        if (location < 0 || location >= tasks.size())
            throw new IndexOutOfBoundsException(location + " is out of range. The tasks list has a size of " + tasks.size());
        tasks.remove(location);
        saveTaskList(appContext,tasks);
    }

    /**
     * Removes all of the saved tasks
     * @param appContext
     */
    public static void removeAllTasks(Context appContext){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        saveTaskList(appContext, new ArrayList<Task>());
    }

    /**************************************************************************
     *
     * PRIVATE STATIC METHODS FOR DEALING WITH TASKS
     *
     **************************************************************************/
    /**
     * Saves a task list using shared preferences
     * @param appContext the application context (used for sharedPreferences)
     * @param taskListToSave the list of tasks to be saved
     */
    private static void saveTaskList(Context appContext, ArrayList<Task> taskListToSave){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        if (taskListToSave == null)
            throw new IllegalArgumentException("Task list cannot be null");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_TASKS, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        String jsonOfTasks = gson.toJson(taskListToSave);
        sharedPreferencesEditor.putString(TASKS,jsonOfTasks);
        sharedPreferencesEditor.apply();
    }
    /**************************************************************************
     *
     * PUBLIC STATIC METHODS FOR DEALING WITH CURRENT EVENT
     *
     **************************************************************************/

    /**
     * Method used to save an event under the key "current_even" please note this method should
     * only be used to save the current even in progress.
     * @param appContext the application context (used for sharedPreferences)
     * @param eventToSave the event to be saved
     */
    public static void saveCurrentEvent(Context appContext,Event eventToSave){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        if (eventToSave == null)
            throw new IllegalArgumentException("Cannot save null event");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_EVENTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        String jsonOfEvent = gson.toJson(eventToSave);
        sharedPreferencesEditor.putString(CURRENT_EVENT,jsonOfEvent);
        sharedPreferencesEditor.apply();
    }
    /**
     * Returns the saved current event
     * @param appContext the application context (used for sharedPreferences)
     */
    public static Event getCurrentEvent(Context appContext){
        if (appContext == null)
            throw new IllegalArgumentException("App context cannot be null");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_EVENTS, Context.MODE_PRIVATE);
        String jsonEvent = sharedPreferences.getString(CURRENT_EVENT,null);
        Log.d("SGAGB074", jsonEvent);
        if (jsonEvent == null)
            return null;

        Type eventType = new TypeToken<Event>() {}.getType();
        return gson.fromJson(jsonEvent,eventType);
    }

    /**
     * Set the boolean indicating whether or not an event is currently in progress.
     * @param appContext the application context (used for sharedPreferences)
     * @param eventInProgress
     */
    public static void setEventInProgress(Context appContext,boolean eventInProgress){
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_EVENTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(EVENT_IN_PROGRESS, eventInProgress);
        sharedPreferencesEditor.apply();
        Log.d("SGAGB074 - SET", Boolean.toString(eventInProgress));

    }

    /**
     * Returns true if there an event currently in progress
     * @param appContext the application context (used for sharedPreferences)
     * @return true if there is an event current in progress, false otherwise
     */

    public static boolean eventIsInProgress(Context appContext){
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_EVENTS, Context.MODE_PRIVATE);
        boolean eventIsInProgress = sharedPreferences.getBoolean(EVENT_IN_PROGRESS,false);
        Log.d("SGAGB074 - GET", Boolean.toString(eventIsInProgress));
        return eventIsInProgress;
    }
}
