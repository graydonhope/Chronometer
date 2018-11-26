package com.gng.bcgsz.timetask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditTaskActivity extends AppCompatActivity {
    private ArrayList<String> savedItems = new ArrayList<>();
    private StoredTaskManager storedTaskManager;
    private ArrayAdapter<String> adapter;
    private String nameAtClickLocation;


    /**
     * This activity populates a list view of all previously saved Tasks on the device.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        final Context CONTEXT  = EditTaskActivity.this;
        storedTaskManager = new StoredTaskManager();
        retrieveSavedTasks();
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedItems){

            //Changing each item in the ListView to a TextView so the color is editable.
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                //Get item from ListView
                View view = super.getView(position, convertView, parent);

                //Initialize textView for each item in listView
                TextView textview = (TextView) view.findViewById(android.R.id.text1);

                //Set color of textView
                textview.setTextColor(Color.WHITE);

                //Generate listview item with textview
                return view;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = storedTaskManager.getTask(CONTEXT, position);
                nameAtClickLocation = savedItems.get(position);
                Intent intent = new Intent (EditTaskActivity.this, selectedTask.class);
                intent.putExtra ("selectedTask", selectedTask);
                intent.putExtra("position",position);
                intent.putExtra("fromEditTask", true);
                startActivityForResult(intent,1);
            }
        });
    }


    /**
     * Waits to see if the user confirmed the deletion of the task before it removes it from the list view in the
     * remove tasks activity page.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case (0) :{

                if (resultCode == RESULT_OK) {
                    savedItems.remove(nameAtClickLocation);
                    adapter.notifyDataSetChanged();
                }
            }
            break;

            case (1): {

                if (resultCode == RESULT_OK) {
                    savedItems.remove(nameAtClickLocation);
                    adapter.notifyDataSetChanged();
                }
            }
            break;
        }
    }


    /**
     * Retrieves all of the saved tasks on the device and populates into the saved items array.
     */
    public void retrieveSavedTasks(){
        ArrayList<Task> tasks = storedTaskManager.getAllTasks(this);
        int numberOfTasks = tasks.size();

        for(int i = 0; i < numberOfTasks; i++){
            savedItems.add(tasks.get(i).getName());
        }
    }


    /**
     * Deletes all the tasks which are saved on the device.
     * @param view
     */
    public void deleteTasks(View view){
        savedItems.clear();
        adapter.notifyDataSetChanged();
        storedTaskManager.removeAllTasks(this);
    }
}
