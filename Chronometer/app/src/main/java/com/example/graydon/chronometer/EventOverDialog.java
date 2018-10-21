package com.example.graydon.chronometer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

public class EventOverDialog extends AppCompatDialogFragment {

    private EndOfEventListener endOfEventListener;
    private static final String TAG = "SGAGB074";


    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No More Tasks");
        builder.setMessage("There are no more tasks for your to complete");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                endOfEventListener.onEndOfEvent();
            }
        });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        endOfEventListener.onEndOfEvent();
    }

    /**
     * Moves activity to the new task activity
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            endOfEventListener = (EndOfEventListener) getActivity();

        } catch (ClassCastException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
    }
}
