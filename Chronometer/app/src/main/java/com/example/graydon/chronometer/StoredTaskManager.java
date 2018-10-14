package com.example.graydon.chronometer;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.graydon.chronometer.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This is a class used to manage the user's saved tasks.
 */
public class StoredTaskManager {
    private final static Gson gson = new Gson();
    private final static String TASKS = "tasks";
    private final static String USER_TASKS = "user_tasks";

    /**************************************************************************
     *
     * PUBLIC STATIC METHODS
     *
     **************************************************************************/
    /**
     * Retrieves tasks saved on the user's phone from SharedPreferences and returns them
     * @param appContext the application context (used for sharedPreferences)
     * * @return the list of tasks saved on user's phone.
    **/
    public static ArrayList<Task> getAllTasks(Context appContext) {
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
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
            throw new NullPointerException("App context cannot be null");
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
            throw new NullPointerException("App context cannot be null");
        if (taskToAdd == null)
            throw new NullPointerException("Passed in a null task");
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
            throw new NullPointerException("App context cannot be null");
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
            throw new NullPointerException("App context cannot be null");
        saveTaskList(appContext, new ArrayList<Task>());
    }

    /**************************************************************************
     *
     * PRIVATE STATIC METHODS
     *
     **************************************************************************/
    /**
     * Saves a task list using shared preferences
     * @param appContext the application context (used for sharedPreferences)
     * @param taskListToSave the list of tasks to be saved
     */
    private static void saveTaskList(Context appContext, ArrayList<Task> taskListToSave){
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_TASKS, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        String jsonOfTasks = gson.toJson(taskListToSave);
        sharedPreferencesEditor.putString(TASKS,jsonOfTasks);
        sharedPreferencesEditor.apply();
    }
}
