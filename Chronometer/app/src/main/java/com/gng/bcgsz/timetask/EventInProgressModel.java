package com.gng.bcgsz.timetask;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class EventInProgressModel {
    private Event event;
    private Task currentTask;
    private Context appContext;
    public EventInProgressModel(Context appContext, Event event){
        if (event == null)
            throw new IllegalArgumentException("Event cannot be null");
        this.event = event;
        this.appContext = appContext;
        if(event.getCurrentTaskIndex() == -1){
            this.currentTask = event.nextTask(false);
        }
        else{
            this.currentTask = event.getTask(event.getCurrentTaskIndex());
        }
        StoredTaskManager.saveCurrentEvent(appContext,this.event);
        StoredTaskManager.setEventInProgress(appContext,true);
    }
    public Task nextTask(boolean isComplete){
        return currentTask = event.nextTask(isComplete);
    }

    public Calendar getCurrentTaskStartTime(){
        Calendar currentTaskStartTime = Calendar.getInstance();
        currentTaskStartTime.set(Calendar.HOUR_OF_DAY,currentTask.getStartHour());
        currentTaskStartTime.set(Calendar.MINUTE,currentTask.getStartMinute());
        currentTaskStartTime.set(Calendar.SECOND,0);
        currentTaskStartTime.set(Calendar.MILLISECOND,0);
        return currentTaskStartTime;
    }


    public Calendar getCurrentTaskEndTime(){
        Calendar currentTaskEndTime = Calendar.getInstance();
        currentTaskEndTime.set(Calendar.HOUR_OF_DAY,currentTask.getEndHour());
        currentTaskEndTime.set(Calendar.MINUTE,currentTask.getEndMinute());
        currentTaskEndTime.set(Calendar.SECOND,0);
        currentTaskEndTime.set(Calendar.MILLISECOND,0);
        return currentTaskEndTime;
    }

    public long getCurrentTimeLeft(){
        return getCurrentTaskEndTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }
    public Task getCurrentTask(){
        return currentTask;
    }

    public Event getEvent(){
        return  event;
    }
    public long getCurrentTaskReminderTimeInMili(){
        return currentTask.getReminderTimeMinutes() * 60000;
    }
    public void setCurrentTaskIsComplete(boolean isComplete){
        getCurrentTask().setIsComplete(isComplete);
    }


    public long getTotalTaskLength(){
        return getCurrentTaskEndTime().getTimeInMillis() - getCurrentTaskStartTime().getTimeInMillis();
    }

    public void sendReport() {
        if (event.getTasks().size() == 0){return;}
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        Boolean shouldSend = sharedPreferences.getBoolean("SendReport",false);

        final String to = sharedPreferences.getString("UserEmail",null);
        if(to == null) {return;}
        if (!shouldSend){return;}
        String accessKey = BuildConfig.AccessKey;
        String secretKey = BuildConfig.SecretKey;
        Log.d("SGAGB074",accessKey);
        Log.d("SGAGB074", secretKey);
        AWSCredentials credentialsProvider = new BasicAWSCredentials(accessKey,secretKey);

        // CREATES SES CLIENT TO MANAGE SENDING EMAIL
        final AmazonSimpleEmailServiceClient ses = new AmazonSimpleEmailServiceClient(credentialsProvider);
        ses.setRegion(Region.getRegion(Regions.US_EAST_1));

        // SUBJECT AND BODY
        Content subject = new Content("Time Task: Day In Review");
        String bodyHtml = createReportHtml(event.getTasks());
        Content bodyContent = new Content().withData(bodyHtml);
        Body body = new Body().withHtml(bodyContent);
        final Message message = new Message(subject, body);

        // REPLACE WITH FROM EMAIL AUTHORIZED IN AWS SES
        final String from = "chronometerapp@gmail.com";

        // SPLITS TO, CC AND BCC INPUTS BY , FOR MULTIPLE
        // RECEIVERS AND TAKES CARE OF EMPTY INPUTS
        final Destination destination = new Destination()
                .withToAddresses(to.contentEquals("") ? null : Arrays.asList(to.split("\\s*,\\s*")));

        // CREATES SEPARATE THREAD TO ATTEMPT TO SEND EMAIL
        Thread sendEmailThread = new Thread(new Runnable() {
            public void run() {
                try {
                    SendEmailRequest request = new SendEmailRequest(from, destination, message);
                    ses.sendEmail(request);
                } catch (Exception e) {
                    Log.d("SGAGB074",e.getLocalizedMessage());
                }
            }
        });

        // RUNS SEND EMAIL THREAD
        sendEmailThread.start();
    }

    private String createReportHtml(ArrayList<Task> tasks){
        String htmlReport = "";
        String greenHex = "#339966";
        String redHex = "#ff0000";
        String header = "<h1><strong>End of Day Report&nbsp;</strong></h1>\n" +
                "<h3><strong>-date-</strong></h3>\n";
        final String singleTaskFormat =
                "<ul>\n" +
                "<li><strong>-task-</strong> was <span style=\"color: -colourhex-;\">-completionstatus-</span> in time.</li>\n" +
                "</ul>\n";
        header = header.replaceFirst("-date-",getDate());

        final String bottomTag = "<p>created by the Time Task app available in the Google Play Store.</p>";


        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            String taskHtml = singleTaskFormat.replaceFirst("-task-", task.getName());
            if(task.getIsComplete()){
                String completionStatus = "completed";
                taskHtml = taskHtml.replaceFirst("-colourhex-",greenHex).replaceFirst("-completionstatus-",completionStatus);
            }
            else{
                String completionStatus = "not completed";
                taskHtml = taskHtml.replaceFirst("-colourhex-",redHex).replaceFirst("-completionstatus-",completionStatus);
            }
            htmlReport += taskHtml;
        }
        return header + htmlReport + bottomTag;
    }

    public String getDate() {
        String dayOfWeek = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String monthOfYear = Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
        String dayOfMonth = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return dayOfWeek + " " + monthOfYear + " " + dayOfMonth;
    }




}
