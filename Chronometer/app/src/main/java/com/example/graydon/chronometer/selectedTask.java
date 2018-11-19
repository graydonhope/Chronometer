package com.example.graydon.chronometer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class selectedTask extends AppCompatActivity {
    private Task task;
    TextView taskName;
    TextView taskStartDur;
    TextView taskEndDur;
    private int position;
    private boolean fromEditTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_task);
        taskName=findViewById(R.id.taskName);
        taskStartDur=findViewById(R.id.startDur);
        taskEndDur=findViewById(R.id.endDur);
        Intent intent = getIntent();
        task=intent.getParcelableExtra("selectedTask");
        taskName.setText(task.getName());
        NumberFormat formatter = new DecimalFormat("00");
        taskStartDur.setText(formatter.format(task.getStartHour()) + ":" + formatter.format (task.getStartMinute()));
        taskEndDur.setText(formatter.format (task.getEndHour()) + ":" + formatter.format (task.getEndMinute()));
        position=intent.getIntExtra("position",1);

        //Checking to see if the task was passed from the Event or deleting task page
        fromEditTask = intent.getBooleanExtra("fromEditTask", false);
    }

    public void deleteTask (View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        alert.setCancelable(true);
        alert.setTitle("Delete");
        alert.setMessage ("Are you sure you want to delete the following task ?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Checks to see which activity the deletion request is coming from.
                if(!fromEditTask){
                    Intent intent =new Intent ();
                    intent.putExtra("pos",position);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    Context context = selectedTask.this;
                    Intent intent = new Intent();
                    StoredTaskManager storedTaskManager = new StoredTaskManager();
                    try{
                        storedTaskManager.removeTask(context, position);
                    }
                    catch(IndexOutOfBoundsException e){
                        Log.d("ghope04999", "onClick: Unable to remove task");
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent =new Intent ();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        alert.show();

    }
}
