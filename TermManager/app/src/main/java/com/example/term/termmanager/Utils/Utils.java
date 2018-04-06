package com.example.term.termmanager.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.term.termmanager.Activities.AlarmActivity;
import com.example.term.termmanager.Controllers.AssessmentController;
import com.example.term.termmanager.Controllers.CourseController;
import com.example.term.termmanager.Models.Assessment;
import com.example.term.termmanager.Models.Course;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    public static Date parseDate(String sDate){
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

        try {

            //if not valid, it will throw ParseException
            return formatter.parse(sDate);

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static Date parseShortDate(String sDate){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        try {

            //if not valid, it will throw ParseException
            return formatter.parse(sDate);

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static int getYearFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonthFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getDayFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }


    public static byte[] getBytes(Bitmap image){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getImage(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static void setCourseAlarms(Context context){
        CourseController cc = new CourseController(context);
        List<Course> courses = cc.getAll();
        for(Course c : courses){
            if(c.is_startAlert()){
                AlarmActivity a1 = new AlarmActivity();
                a1.setDate(c.get_startDate(), c.get_title() + " Start Alarm");
                a1.setAlarm();

            }
            if(c.is_endAlert()){
                AlarmActivity a2 = new AlarmActivity();
                a2.setDate(c.get_startDate(), c.get_title() + " End Alert");
                a2.setAlarm();
            }
        }

    }

    public static void setCourseStartAlarm(Context context, Course course){
        AlarmActivity a1 = new AlarmActivity();
        a1.setDate(course.get_startDate(), course.get_title() + " Start Date");
        a1.setAlarm();
    }

    public static void setCourseEndAlarm(Context context, Course course){
        AlarmActivity a2 = new AlarmActivity();
        a2.setDate(course.get_endDate(), course.get_title() + " End Date");
        a2.setAlarm();
    }

    public static void setAssessmentAlarm(Context context, Assessment assessment){
        AlarmActivity a1 =  new AlarmActivity();
        a1.setDate(assessment.get_goalDate(), assessment.get_title() + " Goal Date");
        a1.setAlarm();
    }


    public static void setAssessmentAlarms(Context context){
        AssessmentController ac = new AssessmentController(context);
        List<Assessment> assessments = ac.getAll();
        for(Assessment a : assessments){
            if(a.is_goalAlert() == 1){
                AlarmActivity alarm = new AlarmActivity();
                alarm.setDate(a.get_goalDate(), a.get_title() + " Goal Date");
                alarm.setAlarm();
            }
        }
    }
}
