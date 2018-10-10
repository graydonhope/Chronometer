package com.example.graydon.chronometer;
import java.util.ArrayList;
import java.util.Iterator;

public class Event {
	private ArrayList <Task> tasks;
	private String eventName;
	private Iterator <Task> iter;
	public Event (String eventName){
		this.eventName=eventName;
		tasks=new ArrayList<Task> ();
		iter=tasks.iterator ();
	}
	public void addTask (Task task){
		tasks.add (task);
	}
	//removes object at specified index
	public Task removeTask (int index){
		return tasks.remove (index);
	}
	//removes the first occurence of the specified task. if false returned object was not found
	public boolean removeTask (Task task){
		return tasks.removeTask (task);
	}
	//throws IndecOutOfBounds if tasks does not have a next
	//throws IllegalStateException if tasks list is empty
	public Task getTask (int index){
		return tasks.get (index);
	}
	public void manageTask (int index, Task task){
		if (!(index > -1 && index < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
		
		int tempIndex=tasks.indexOf (task);
		
		if (tempIndex==-1){ throw new IllegalArgumentException (); }

		tasks.add (index, removeTask(tempIndex));
	}
	public void editTaskName (int location, String name){
		if (name==null){  throw new NullPointerException ();}
		
		if (tasks.isEmpty ()){  throw new IllegalStateException();}
		
		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
		
		int index=-1;
		Task=temp;
		while (index <location+1){
			temp=iter.next ();
			index ++;
		}
		iter=tasks.iterator ();
		temp.setName (name);
	}
	public void editTaskDuration (int location, Duration duration){
		
		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}
		
		Task tempTask = getTask (location);
		tempTask.setDuration (duration);
	}
	public void editTaskIsComplete (int location, boolean isComplete){

		if (!(location > -1 && location < tasks.size ()+1)){  throw new IndexOutOfBoundsException ();}

		Task tempTask = getTask (location);
		tempTask.setIsComplete (isComplete);

	}
	public Duration eventDuration (){
		Duration temp;
		Duration totalDur;
		while (iter.hasNext ()){
			temp=iter.next ().getDuration ();
			totalDur.setDuration(totalDur.getDuration()+temp.getDuration ());
		}
		iter=tasks.iterator ();
		return totalDur;

	} 


	public Task nextTask (boolean currentTaskIsComplete){
		if (currentTaskIsComplete==false){
			throw new IncompleteTaskException ("Task is Incomplete");
		}
		if (!(iter.hasNext ())){
			throw new IndexOutOfBoundsException ();
		}
		return iter.next ();		

	}
	public String getName (){
		return eventName;
	}
	public int numberOfTasks (){
		tasks.size ();
	}

}
public class IncompleteTaskException extends Exception {
	public IncompleteTaskException (String message){
		super (message);
	}
}








