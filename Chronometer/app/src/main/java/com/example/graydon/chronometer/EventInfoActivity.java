package com.example.graydon.chronometer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class EventInfoActivity extends AppCompatActivity {
    private static final int CHRONO_BLACK = Color.parseColor("#1F1F1F");
    ListView listview;
    TextView taskView;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (StoredTaskManager.eventIsInProgress(getApplicationContext()))
            moveToEventInProgressActivity();

        setContentView(R.layout.activity_event_info);
        //changes the app bar menu to black
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(CHRONO_BLACK));
        setTitle("Daily Tasks");
        taskView=findViewById(R.id.taskview);
        listview=findViewById(R.id.eventList);
        event=new Event ();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask;
                selectedTask=event.getTask(position);
                Intent intent = new Intent (EventInfoActivity.this, selectedTask.class);
                intent.putExtra ("selectedTask",selectedTask);
                intent.putExtra("position",position);
                startActivityForResult(intent,1);
            }
        });
    }

    //Makes settings icon visible
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_appbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Called when an app bar icon is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Log.d("SGAGB074", "Settings button was clicked" + item.getItemId());
                moveToSettings();
                break;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * changes activites from the info to the settings
     */
    private void moveToSettings() {
        Intent moveToSettingsActivity = new Intent(this,SettingsActivity.class);
        startActivity(moveToSettingsActivity);
    }


    public void addButtonClick (View view){
        Intent intent =new Intent (this,NewTaskActivity.class);
        intent.putExtra ("event",event);
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case (0) :{
                if (resultCode == RESULT_OK) {
                    taskView = findViewById(R.id.taskview);
                    Task newTask = data.getParcelableExtra("Task");
                    event.addTask(newTask);
                    taskView.setVisibility(View.GONE);
                    populateListView();
                } else if (resultCode == RESULT_CANCELED) {
                    if (event.isEmpty()){
                        taskView.setVisibility(View.VISIBLE);
                    }
                }
            }break;

            case (1): {
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("pos", 1);
                    event.removeTask(position);
                    populateListView();
                    if (event.isEmpty()) {
                        taskView.setVisibility(View.VISIBLE);
                    }
                } else if (resultCode == RESULT_CANCELED) {
                }
            }break;
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
            TextView eventTime=convertView.findViewById(R.id.time);
            eventName.setText(task.getName());
            NumberFormat formatter = new DecimalFormat("00");
            String startHour = formatter.format(task.getStartHour());
            String startMin =formatter.format (task.getStartMinute());
            String endHour=formatter.format (task.getEndHour());
            String endMin=formatter.format (task.getEndMinute());
            eventTime.setText (startHour+":"+startMin+"-"+endHour+":"+endMin);
            return convertView;
        }
        public void onClick (View view){
            int position= (Integer) view.getTag();
            Object object=getItem(position);
            Task  service = (Task) object;

        }
    }

    private void moveToEventInProgressActivity() {
        Intent toInfoEventinProgressActivity = new Intent(this,EventInfoProgressActivity.class);
        startActivity(toInfoEventinProgressActivity);
    }

}
