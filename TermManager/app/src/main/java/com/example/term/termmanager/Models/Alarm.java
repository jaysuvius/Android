package com.example.term.termmanager.Models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.Toast;
import com.example.term.termmanager.R;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmText = "";
        long when = System.currentTimeMillis();
        String title = "Alarm";
        final int NOTIF_ID=1234;

        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("courseText")){
                alarmText = extras.getString("courseText");
            }
            else if (extras.containsKey("assessmentText")){
                alarmText = extras.getString("assessmentText");
            }
        }

        int icon = R.drawable.notification_icon_background;
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent nIntent = new Intent(context, Alarm.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, nIntent, 0);
        final Toast tag = Toast.makeText(context, "Alarm..." + alarmText, Toast.LENGTH_LONG);
        new CountDownTimer(9000, 1000)
        {

            public void onTick(long millisUntilFinished) {tag.show();}
            public void onFinish() {tag.show();}

        }.start();
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(1000);
    }
}
