package com.example.term.termmanager.Models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
            if (extras.containsKey("alarmText")){
                alarmText = extras.getString("alarmText");
            }
        }

        int icon = R.drawable.notification_icon_background;
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent nIntent = new Intent(context, Alarm.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, nIntent, 0);
        Notification n = new Notification(icon, alarmText, when);
        n.flags = Notification.FLAG_INSISTENT;
        nm.notify(NOTIF_ID, n);
        Toast.makeText(context, "Alarm...", Toast.LENGTH_LONG).show();
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(1000);
    }
}
