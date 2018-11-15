package com.example.graydon.chronometer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class selectedTask extends AppCompatActivity {
    private Task task;
    TextView taskName;
    TextView taskStartDur;
    TextView taskEndDur;
    private int position;

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
        taskStartDur.setText(task.getStartHour() % 12 + ":" + task.getStartMinute());
        taskEndDur.setText(task.getEndHour() % 12 + ":" + task.getEndMinute());
        position=intent.getIntExtra("position",1);
    }
    public void deleteTask (View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        alert.setCancelable(true);
        alert.setTitle("Delete");
        alert.setMessage ("Are you sure you want to delete the following service ?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent =new Intent ();
                intent.putExtra("pos",position);
                setResult(RESULT_OK,intent);
                finish();
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
