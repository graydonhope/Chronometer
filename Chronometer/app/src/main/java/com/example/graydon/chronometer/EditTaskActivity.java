package com.example.graydon.chronometer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EditTaskActivity extends AppCompatActivity {
    private ArrayList<String> savedItems = new ArrayList<>();
    private StoredTaskManager storedTaskManager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        final Context CONTEXT  = EditTaskActivity.this;

        storedTaskManager = new StoredTaskManager();
        retrieveSavedTasks();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedItems);
        listView.setAdapter(adapter);


        //To be implemented in next version. This will allow user to delete a single task - Graydon

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = storedTaskManager.getTask(CONTEXT, position);
                String nameAtClickLocation = savedItems.get(position);
                Intent intent = new Intent (EditTaskActivity.this, selectedTask.class);
                intent.putExtra ("selectedTask", selectedTask);
                intent.putExtra("position",position);
                intent.putExtra("fromEditTask", true);
                startActivityForResult(intent,1);
                savedItems.remove(nameAtClickLocation);
                adapter.notifyDataSetChanged();
            }
        });
    }


    public void retrieveSavedTasks(){
        ArrayList<Task> tasks = storedTaskManager.getAllTasks(this);
        int numberOfTasks = tasks.size();

        for(int i = 0; i < numberOfTasks; i++){
            savedItems.add(tasks.get(i).getName());
        }
    }

    public void deleteTasks(View view){
        savedItems.clear();
        adapter.notifyDataSetChanged();
        storedTaskManager.removeAllTasks(this);
    }
}
