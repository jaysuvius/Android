package com.example.term.termmanager.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.widget.Toast;

import com.example.term.termmanager.AlarmActivity;
import com.example.term.termmanager.Controllers.AssessmentController;
import com.example.term.termmanager.Controllers.CourseController;
import com.example.term.termmanager.MainActivity;
import com.example.term.termmanager.Models.Alarm;
import com.example.term.termmanager.Models.Assessment;
import com.example.term.termmanager.Models.Course;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class Utils {

    public static Date parseDate(String sDate) {
        if (sDate == null || sDate == "")
            return new Date();
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

        try {

            //if not valid, it will throw ParseException
            return formatter.parse(sDate);

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static Date parseShortDate(String sDate) {
        if (sDate == null || sDate == "")
            return new Date();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        try {

            //if not valid, it will throw ParseException
            return formatter.parse(sDate);

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static int getYearFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getDayFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }


    public static byte[] getBytes(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static void setCourseAlarms(Context context) {
        CourseController cc = new CourseController(context);
        List<Course> courses = cc.getAll();
        for (Course c : courses) {
            if (c.is_startAlert() && c.get_startDate().getTime() > System.currentTimeMillis()) {
                Intent intent = new Intent(context, AlarmActivity.class);
                intent.putExtra("date", c.get_startDate().toString());
                intent.putExtra("courseText", c.get_title() + " Start Date");
                context.startActivity(intent);

            }
            if (c.is_endAlert() && c.get_endDate().getTime() > System.currentTimeMillis()) {
                Intent intent = new Intent(context, AlarmActivity.class);
                intent.putExtra("date", c.get_endDate().toString());
                intent.putExtra("courseText", c.get_title() + " End Date");
                context.startActivity(intent);
            }
        }

    }

    public static void setCourseStartAlarm(Context context, Course course) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra("date", course.get_startDate().toString());
        intent.putExtra("courseText", course.get_title() + " Start Date");
        context.startActivity(intent);
    }

    public static void setCourseEndAlarm(Context context, Course course) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra("date", course.get_startDate().toString());
        intent.putExtra("courseText", course.get_title() + " End Date");
        context.startActivity(intent);
    }

    public static void setAssessmentGoalAlarm(Context context, Assessment assessment) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra("date", assessment.get_goalDate().toString());
        intent.putExtra("alertText", assessment.get_title() + " Goal Date");
        context.startActivity(intent);
    }

    public static void setAssessmentDueAlarm(Context context, Assessment assessment) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra("date", assessment.get_goalDate().toString());
        intent.putExtra("alertText", assessment.get_title() + " Goal Date");
        context.startActivity(intent);
    }


    public static void setAssessmentAlarms(Context context) {
        AssessmentController ac = new AssessmentController(context);
        List<Assessment> assessments = ac.getAll();
        for (Assessment a : assessments) {
            if (a.is_goalAlert() == 1 && a.get_goalDate().getTime() > System.currentTimeMillis()) {
                Intent intent = new Intent(context, AlarmActivity.class);
                intent.putExtra("date", a.get_goalDate().toString());
                intent.putExtra("assessmentText", a.get_title() + " Goal Date");
                context.startActivity(intent);
            }
        }
    }

    public static void sendSms(Context context, String smsBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context); //Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, smsBody);

            if (defaultSmsPackageName != null)//Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            context.startActivity(sendIntent);

        } else //For early versions, do what worked for you before.
        {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", smsBody);
            context.startActivity(sendIntent);
        }
    }


}
