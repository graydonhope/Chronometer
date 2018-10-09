package com.example.graydon.chronometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StoredEventList {
    private final static Gson gson = new Gson();
    private final static String EVENTS = "events";
    private final static String USER_EVENTS = "user_events";

    /**************************************************************************
     *
     * PUBLIC STATIC METHODS
     *
     **************************************************************************/
    /**
     * Retrieves event(s) saved on the user's phone from SharedPreferences and returns them
     * @param appContext the application context (used for sharedPreferences)
     * * @return the list of events saved on user's phone.
    **/
    public static ArrayList<Event> getAllEvents(Context appContext) {
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_EVENTS, Context.MODE_PRIVATE);
        String jsonOfEvents = sharedPreferences.getString(EVENTS,null);
        if (jsonOfEvents == null)
            return new ArrayList<>(); //returns an empty list of events
        Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
        return gson.fromJson(jsonOfEvents,eventListType);
    }

    /**
     * Retrieves saved event at specified location (Returns null if location not in range)
     * @param appContext the application context (used for sharedPreferences)
     * @param location the index of the event
     * @return the event found at the given index
     */
    public static Event getEvent(Context appContext, int location){
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        ArrayList<Event> events = getAllEvents(appContext);
        if (location < 0 || location >= events.size())
            throw new IndexOutOfBoundsException(location + " is out of range. The events list has a size of " + events.size());
        return events.get(location);
    }

    /**
     * Adds an event to the end of the stored events list
     * @param appContext the application context (used for sharedPreferences)
     * @param eventToAdd the event to be added to the saved events
     */
    public static void addEvent(Context appContext,Event eventToAdd){
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        if (eventToAdd == null)
            throw new NullPointerException("Passed in a null event");
        ArrayList<Event> events = getAllEvents(appContext);
        events.add(eventToAdd);
        saveEventList(appContext,events);
    }

    /**
     * Removes the event at the specified location from the events list
     * @param appContext the application context (used for sharedPreferences)
     * @param location the location of the event to remove
     */
    public static void removeEvent(Context appContext,int location){
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        ArrayList<Event> events = getAllEvents(appContext);
        if (location < 0 || location >= events.size())
            throw new IndexOutOfBoundsException(location + " is out of range. The events list has a size of " + events.size());
        events.remove(location);
        saveEventList(appContext,events);
    }

    /**
     * Removes all of the saved events
     * @param appContext
     */
    public static void removeAllEvents(Context appContext){
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        saveEventList(appContext, new ArrayList<Event>());
    }

    /**************************************************************************
     *
     * PRIVATE STATIC METHODS
     *
     **************************************************************************/
    /**
     * Saves an event list using shared preferences
     * @param appContext the application context (used for sharedPreferences)
     * @param eventListToSave the list of events to be saved
     */
    private static void saveEventList(Context appContext, ArrayList<Event> eventListToSave){
        if (appContext == null)
            throw new NullPointerException("App context cannot be null");
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(USER_EVENTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        String jsonOfEvents = gson.toJson(eventListToSave);
        sharedPreferencesEditor.putString(EVENTS,jsonOfEvents);
        sharedPreferencesEditor.apply();
    }
}
