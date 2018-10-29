package com.example.graydon.chronometer;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.graydon.chronometer.R;
import java.util.ArrayList;


public class EventInfoActivity extends AppCompatActivity {
   ListView listview;
   TextView taskView;
   Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        taskView=findViewById(R.id.taskview);
        listview=findViewById(R.id.eventList);
        event=new Event ();
    }
    public void addButtonClick (View view){
        Intent intent =new Intent (this,NewTaskActivity.class);
       startActivityForResult(intent,0);
    }
    public void startButtonClick (View view){
        if (!event.isEmpty ()){
            Intent intent = new Intent(this, EventInfoProgressActivity.class);
            intent.putExtra("Event",event);
            startActivity(intent);

        }
        else {
            Toast.makeText(this, "No tasks to perform", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode==0&&resultCode==RESULT_OK){
            taskView= findViewById(R.id.taskview);
            Task newTask=data.getParcelableExtra("Task");
            event.addTask (newTask);
            taskView.setVisibility(View.GONE);
            populateListView();
        }
    }
    private void populateListView (){
        ArrayList <Task> tasks = event.getTasks();
        EventAdaptor adaptor=new EventAdaptor(this, tasks);
        listview.setAdapter(adaptor);
    }
    public class EventAdaptor extends ArrayAdapter <Task>{
        EventAdaptor (Context context, ArrayList<Task> taskList){
            super (context,0,taskList);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Task task= getItem (position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_task, parent, false);
            }
            TextView eventName= convertView.findViewById(R.id.taskName);
            eventName.setText (task.getName());
            return convertView;
        }
    }

}
