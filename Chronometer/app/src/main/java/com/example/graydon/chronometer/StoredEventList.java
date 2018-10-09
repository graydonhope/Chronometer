package com.example.graydon.chronometer;

import java.util.ArrayList;

public class StoredEventList {

    /**
     * Retrieves event(s) saved on the user's phone from SharedPreferences and returns them*
     * * @return the list of events saved on user's phone.
    **/
    public static ArrayList<Event> getAllEvents() {
        return null;
    }

    /**
     * Retrieves saved event at specified location (Returns null if location not in range)
     * @param location the index of the event
     * @return the event found at the given index
     */
    public static Event getEvent(int location){
        return null;

    }

    /**
     * Adds an event to the end of the stored events list
     * @param eventToAdd the event to be added to the saved events
     * @return true if successful, otherwise false
     */
    public static boolean addEvent(Event eventToAdd){
        return false;
    }

    /**
     * Removes the event at the specified location from the events list
     * @param location the location of the event to remove
     * @return true if successful, otherwise false
     */

    public static boolean removeEvent(int location){
        return false;
    }

}
