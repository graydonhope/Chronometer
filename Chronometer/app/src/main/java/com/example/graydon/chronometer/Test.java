package com.example.graydon.chronometer;

public class Test {

	public static void main (String [] args ){	
		Task task1 =new Task ("shop", 0, 25);
		Task task2 =new Task ("drop", 2,5);
		Event event=new  Event ("event1");

		event.addTask (task1);
		event.addTask (task2);
	}
}