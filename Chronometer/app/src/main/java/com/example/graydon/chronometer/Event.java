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

	public void addTask (Task task){
		tasks.add (task);

    }
	//removes object at specified index
	public Task removeTask (int index){
		return tasks.remove (index);
	}
	//removes the first occurence of the specified task. if false returned object was not found
	public boolean removeTask (Task task){
		return tasks.remove (task);
	}
	//throws IndecOutOfBounds if tasks does not have a next
	//throws IllegalStateException if tasks list is empty
	public Task getTask (int index){
		return tasks.get (index);
	}
	public void manageTask (int index, Task task){
		if (!(index > -1 && index < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}

		int tempIndex=tasks.indexOf (task);

		if (tempIndex==-1){ throw new NoSuchElementException(); }

		tasks.add (index, removeTask(tempIndex));
	}
//	public void editTaskName (int location, String name){
//		if (name==null){  throw new NullPointerException ();}
//
//		if (tasks.isEmpty ()){  throw new IllegalStateException();}
//
//		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
//
//		int index=-1;
//		Task temp=null;
//		while (index <location+1){
//			temp=iter.next ();
//			index ++;
//		}
//		iter=tasks.iterator ();
//		temp.setName (name);
//	}
//	public void editTaskHours (int location, int hours ){
//
//		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
//
//		Task tempTask = getTask (location);
//		tempTask.setHours (hours);
//	}
//	public void editTaskMinutes (int location, int minutes ){
//
//		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
//
//		Task tempTask = getTask (location);
//		tempTask.setMinutes (minutes);
//	}
//	public void editTaskIsComplete (int location, boolean isComplete){
//
//		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
//
//		Task tempTask = getTask (location);
//		tempTask.setIsComplete (isComplete);
//
//	}
//	public long totalMinutes(){
//		long temp=0;
//		while (iter.hasNext ()){
//			temp=temp+iter.next ().getMinutes_inMilli ();
//		}
//		iter=tasks.iterator ();
//		return temp;
//
//	}
//	public long totalHours (){
//		long temp=0;
//		while (iter.hasNext ()){
//			temp=temp+iter.next ().getHours_inMilli ();
//		}
//		iter=tasks.iterator ();
//		return temp;
//	}

    public boolean hasNext(){
	    return (currentTaskIndex + 1) < tasks.size();
    }
	public Task nextTask (boolean currentTaskIsComplete) {
		currentTaskIndex++;
		if(currentTaskIndex >= tasks.size()){
			throw new IndexOutOfBoundsException("No more tasks.");
		}
		return tasks.get(currentTaskIndex);
	}

	/*
	public void editTaskName (int location, String name){
		if (name==null){  throw new NullPointerException ();}

		if (tasks.isEmpty ()){  throw new IllegalStateException();}

		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}

		int index = tasks.indexOf (name);
		if (index == -1) { throw new NoSuchElementException ();}
		Task task=getTask  (index);
		tmp.setName (name);

	}

	*/

	/*
	public void editTaskHours (int location, int hours ){

		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}

		Task tempTask = getTask (location);
		tempTask.setHour (hours);
	}




	public void editTaskMinutes (int location, int minutes ){

		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}

		Task tempTask = getTask (location);
		tempTask.setMinutes (minutes);
	}

	*/



	/*



	public long totalMinutes(){
		long temp=0;
		Task tempTask;
		for (int i=0; i <numberOfTasks();i++){
			tempTask=tasks.get (i);
			temp=temp+tempTask.getMinutes_inMilli();
		}
		return temp;

	}
	public long totalHours (){
		long temp=0;
		Task tempTask;
		for (int i=0; i <numberOfTasks();i++){
			tempTask=tasks.get (i);
			temp=temp+tempTask.getHours_inMilli();
		}
		return temp;


 */

		public int numberOfTasks () {
			return tasks.size();
		}


		@Override
		public int describeContents () {
			return 0;
		}

		@Override
		public void writeToParcel (Parcel dest,int flags){
		}
	}










