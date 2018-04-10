package com.example.term.termmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.term.termmanager.Models.Alarm;
import com.example.term.termmanager.Utils.Utils;

import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();
        Date alarmDate = new Date();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.getString("date") != null)
                alarmDate.setTime(Utils.parseDate(extras.getString("date")).getTime());
        }
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(Utils.getYearFromDate(alarmDate),Utils.getMonthFromDate(alarmDate) - 1,Utils.getDayFromDate(alarmDate),07,20);
        if(alarmDate.after(cal.getTime())){

            AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, Alarm.class);
            int id = (int) System.currentTimeMillis();
            if(extras != null){
                if (extras.containsKey("courseText")){
                    intent.putExtra("courseText", extras.getString("courseText"));
                }
                else if (extras.containsKey("assessmentText")){
                    intent.putExtra("assessmentText",  extras.getString("assessmentText"));
                }
            }
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent,PendingIntent.FLAG_ONE_SHOT);
            // cal.add(Calendar.SECOND, 5);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
        finish();
    }
}
