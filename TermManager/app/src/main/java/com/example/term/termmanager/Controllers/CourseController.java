package com.example.term.termmanager.Controllers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Dal.CourseProvider;
import com.example.term.termmanager.Dal.DataProvider;
import com.example.term.termmanager.Models.Course;
import com.example.term.termmanager.Models.Entity;
import com.example.term.termmanager.Utils.Constants;
import com.example.term.termmanager.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CourseController extends AbstractController {

    public CourseController(Context c){
        super(c, CourseProvider.getInstance());
    }

    public List<Course> getByTermId(Uri uri){
       String selectionClause = "";
       if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
           selectionClause = Constants.COURSE_TERM_ID + " = " + uri.getLastPathSegment();
       }
       List<Course> entities = new ArrayList<>();

       Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
       if (cursor.getCount() > 0){
           for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
               entities.add(parse(cursor));
           }
       }
       return entities;
    }

    public boolean saveCourse(Course course){
        try{
            Uri uri = Uri.parse("content://" + _provider.getAuthority() + "/" + _provider.get_table() + "/" + course.getId());
            Course c = (Course)getById(uri);
            if (c == null || course.getId() == 0){
                Uri rtnVal = Insert(course);
                course.setId(ContentUris.parseId(rtnVal));
            }
            else{
                int rtnVal = Update(course);
            }
            if(course.is_startAlert())
                Utils.setCourseStartAlarm(_provider.getContext(), course);
            if(course.is_endAlert())
                Utils.setCourseEndAlarm(_provider.getContext(), course);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public Course parse(Cursor cursor) {
        return new Course(cursor.getInt(cursor.getColumnIndex(Constants.COURSE_ID)),
                cursor.getString(cursor.getColumnIndex(Constants.COURSE_TITLE)),
                Utils.parseDate(cursor.getString(cursor.getColumnIndex(Constants.COURSE_START_DATE))),
                cursor.getInt(cursor.getColumnIndex(Constants.COURSE_START_ALERT)),
                Utils.parseDate(cursor.getString(cursor.getColumnIndex(Constants.COURSE_END_DATE))),
                cursor.getInt(cursor.getColumnIndex(Constants.COURSE_END_ALERT)),
                cursor.getString(cursor.getColumnIndex(Constants.COURSE_STATUS)),
                cursor.getInt(cursor.getColumnIndex(Constants.COURSE_TERM_ID)));
    }

    @Override
    public ContentValues getContentValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Course course = (Course)entity;
        cv.put(Constants.COURSE_ID, course.getId());
        cv.put(Constants.COURSE_TITLE, course.get_title());
        cv.put(Constants.COURSE_START_DATE, course.get_startDate().toString());
        cv.put(Constants.COURSE_START_ALERT, course.is_startAlert());
        cv.put(Constants.COURSE_END_DATE, course.get_endDate().toString());
        cv.put(Constants.COURSE_END_ALERT, course.is_endAlert());
        cv.put(Constants.COURSE_STATUS, course.get_status());
        cv.put(Constants.COURSE_TERM_ID, course.get_termId());
        return cv;
    }

    @Override
    public ContentValues getInsertValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Course course = (Course)entity;
        cv.put(Constants.COURSE_TITLE, course.get_title());
        cv.put(Constants.COURSE_START_DATE, course.get_startDate().toString());
        cv.put(Constants.COURSE_START_ALERT, course.is_startAlert());
        cv.put(Constants.COURSE_END_DATE, course.get_endDate().toString());
        cv.put(Constants.COURSE_END_ALERT, course.is_endAlert());
        cv.put(Constants.COURSE_STATUS, course.get_status());
        cv.put(Constants.COURSE_TERM_ID, course.get_termId());
        return cv;
    }
}
