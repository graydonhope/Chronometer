package com.example.graydon.chronometer;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Event implements Parcelable{
	private ArrayList<Task> tasks ;
	private int currentTaskIndex = -1;

	public Event(){
		this.tasks = new ArrayList<>();
	}

	protected Event(Parcel in) {
		tasks = in.createTypedArrayList(Task.CREATOR);
		currentTaskIndex = in.readInt();
	}

	public static final Creator<Event> CREATOR = new Creator<Event>() {
		@Override
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};

	/**
	 * Adds a task to the end of an event
	 * @param task the task to be added
	 */
	public void addTask (Task task){
		if (task == null)
			throw new IllegalArgumentException("Task cannot be null");
		tasks.add(task);
    }

	/**
	 * Adds a task at the given index
	 * @param index the index to add the task to
	 * @param task the task to be added
	 */

	public void addTask(int index, Task task){
		if (index < 0 || index >= tasks.size())
			throw new IndexOutOfBoundsException("Location value out of range");
		if(task == null)
			throw new IllegalArgumentException("Cannot add null task");
		tasks.add(index,task);
	}

	/**
	 * Removes and returns a task at a given index
	 * @param index the index of the task to remove
	 * @return the removed task
	 */
	public Task removeTask (int index){
		if(index < 0 || index >= tasks.size())
			throw new IndexOutOfBoundsException("index out of range of tasks");
		return tasks.remove(index);
	}

	/**
	 * Removes the given task from the event
	 * @param task the task to be removed
	 * @return true if the item existed and was removed, otherwise false
	 */
	public boolean removeTask (Task task){
		if(task == null)
			throw new IllegalArgumentException("task cannot be null");
		return tasks.remove(task);
	}

	/**
	 * Get a task at a specific index
	 * @param index the index of the task to be retrieved
	 * @return the task at the given location
	 */
	public Task getTask (int index){
		if (index < 0 || index >= tasks.size())
			throw new IndexOutOfBoundsException("No task at that location");
		return tasks.get(index);
	}

	/**
	 * Checks if the event has another task to be completed
	 * @return true if there is another task to be completed
	 */
    public boolean hasNext(){
	    return (currentTaskIndex + 1) < tasks.size();
    }

	/**
	 * Moves to next task and returns the new task
	 * @param currentTaskIsComplete sets the current task to complete/not comeplete before moving to next text
	 * @return the next task to be compeleted
	 */
	public Task nextTask (boolean currentTaskIsComplete) {
		currentTaskIndex++;
		if(currentTaskIndex >= tasks.size()){
			throw new IndexOutOfBoundsException("No more tasks.");
		}
		return tasks.get(currentTaskIndex);
	}

	/**
	 * Returns the number of of tasks in the event
	 * @return number of tasks in the event
	 */
	public int numberOfTasks () {
		return tasks.size();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(tasks);
		dest.writeInt(currentTaskIndex);
	}

	/**
	 * A string representation of an event
	 * @return the string representation of an event
	 */
	public String toString(){
		StringBuilder stringEvent = new StringBuilder("[");
		for(int i = 0; i < tasks.size()-1; i++){
			stringEvent.append(tasks.get(i).getName() + ",");
		}
		stringEvent.append(tasks.get(tasks.size()-1).getName() + "]");
		stringEvent.append(" Size: " + tasks.size());
		return stringEvent.toString();
	}
}










