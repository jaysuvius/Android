package com.example.term.termmanager.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.term.termmanager.R;
import com.example.term.termmanager.Utils.Utils;

import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends Activity {
        /** Called when the activity is first created. */

        private int _year;
        private int _month;
        private int _dayOfMonth;
        private int _hourOfDay;
        private int _minute;
        private String _alarmText;
        Calendar cal;
        AlarmManager alarmMgr;
        Intent _intent;
        Activity _activity;

        public AlarmActivity(){

        }

//
//        @Override
//        public void onAttach(Activity activity){
//            this._activity = activity;
//        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            cal = Calendar.getInstance();

            cal.setTimeInMillis(System.currentTimeMillis());
            cal.clear();
            cal.set(_year,_month,_dayOfMonth,7,59);

            alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            _intent = new Intent(this, AlarmActivity.class);

            setAlarm();

        }

        public void setDate(Date d, String alertText){
            _year = Utils.getYearFromDate(d);
            _month = Utils.getMonthFromDate(d);
            _dayOfMonth = Utils.getDayFromDate(d);
            _hourOfDay = 8;
            _minute = 10;
            _alarmText = alertText;
        }


        public void setAlarm(){

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, _intent, 0);
            try{
                alarmMgr.cancel(pendingIntent);
            }
            catch (Exception e){
                Log.e("", "Alarm mgr update was not cancelled"+ e.toString());
            }
            _intent.putExtra("alarmText", _alarmText);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }
