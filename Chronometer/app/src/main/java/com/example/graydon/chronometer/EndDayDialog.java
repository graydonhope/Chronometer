package com.example.graydon.chronometer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

public class EndDayDialog extends AppCompatDialogFragment {

    private EndOfEventListener endOfEventListener;
    private static final String TAG = "SGAGB074";


    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ending Day Early");
        builder.setMessage("Are you sure you want to end your day early?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                endOfEventListener.onEndOfEvent();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

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
