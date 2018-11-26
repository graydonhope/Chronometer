package com.gng.bcgsz.timetask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private final static String CHRONOMETER = "Time Task";
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("FROM SANTOS:","Device is vibrating for 5 seconds...");
        boolean isEndAlarm = intent.getBooleanExtra("isEndAlarm", false);
//        Intent ringtoneServiceIntent = new Intent(context,AlarmAlertService.class);
//        context.startService(ringtoneServiceIntent);
        if(isEndAlarm) {
            createNotification(context,"End of Task", "Time is up, must move onto next task.",CHRONOMETER);

        }
        else{
            String reminderMessage = "You have " + intent.getStringExtra("reminderTime") + " minutes to complete your task.";
            createNotification(context,"Task Almost Over", reminderMessage,CHRONOMETER);
        }
    }

   public void createNotification(Context context,String title, String message, String alert){
       PendingIntent notificationIntent =  PendingIntent.getActivity(context,0, new Intent(context,EventInfoProgressActivity.class),0);

       NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(context, App.CHANNEL_ID)
               .setContentTitle(title)
               .setSmallIcon(R.mipmap.ic_chrono_launcher)
               .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                       R.mipmap.ic_chrono_launcher))
               .setContentText(message)
               .setTicker(alert)
               .setSmallIcon(R.mipmap.ic_launcher)
               .setAutoCancel(true)
               .setVibrate(new long[] {0,1000,500,1000,500})
               .setContentIntent(notificationIntent)
               .setDefaults(NotificationCompat.DEFAULT_SOUND)
               .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
               .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI);



       NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       notificationManager.notify(0,notificationBuilder.build());
   }

}
