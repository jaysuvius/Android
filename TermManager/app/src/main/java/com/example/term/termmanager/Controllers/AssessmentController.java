package com.example.term.termmanager.Controllers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Dal.DataProvider;
import com.example.term.termmanager.Models.Assessment;
import com.example.term.termmanager.Models.Entity;
import com.example.term.termmanager.Utils.Constants;
import com.example.term.termmanager.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.example.term.termmanager.Dal.AssessmentProvider.getInstance;

public class AssessmentController extends AbstractController {

    public AssessmentController(Context c){
        super(c, getInstance());
    }

    public List<Assessment> getByCourseId(Uri uri){
        String selectionClause = "";
        if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
            selectionClause = Constants.ASSESSMENT_COURSE_ID + " = " + uri.getLastPathSegment();
        }
        List<Assessment> entities = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }

    public boolean saveAssessment(Assessment entity){
        try{
            Uri uri = Uri.parse("entity://" + _provider.getAuthority() + "/" + _provider.get_table() + "/" + entity.getId());
            Assessment a = (Assessment) getById(uri);
            if (a == null || entity.getId() == 0){
                Uri rtnVal = Insert(entity);
                entity.setId(ContentUris.parseId(rtnVal));
            }
            else{
                int rtnVal = Update(entity);
            }
            if(entity.is_goalAlert() == 1)
                Utils.setAssessmentAlarm(_provider.getContext(), entity);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
    
    
    protected Assessment parse(Cursor cursor) {
        return new Assessment( cursor.getLong(cursor.getColumnIndex(Constants.ASSESSMENT_ID)),
                cursor.getString(cursor.getColumnIndex(Constants.ASSESSMENT_TITLE)),
                cursor.getInt(cursor.getColumnIndex(Constants.ASSESSMENT_COURSE_ID)),
                cursor.getInt(cursor.getColumnIndex(Constants.IS_OBJECTIVE)),
                cursor.getInt(cursor.getColumnIndex(Constants.IS_PERFORMANCE)),
                Utils.parseDate(cursor.getString(cursor.getColumnIndex(Constants.ASSESSMENT_GOAL_DATE))),
                cursor.getInt(cursor.getColumnIndex(Constants.ASSESSMENT_GOAL_ALERT)));
    }

    @Override
    ContentValues getContentValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Assessment assessment = (Assessment) entity;
        cv.put(Constants.ASSESSMENT_ID, assessment.getId());
        cv.put(Constants.ASSESSMENT_TITLE, assessment.get_title());
        cv.put(Constants.ASSESSMENT_COURSE_ID, assessment.get_courseId());
        cv.put(Constants.IS_OBJECTIVE, assessment.is_Objective());
        cv.put(Constants.IS_PERFORMANCE, assessment.is_Performance());
        cv.put(Constants.ASSESSMENT_GOAL_DATE, assessment.get_goalDate().toString());
        cv.put(Constants.ASSESSMENT_GOAL_ALERT, assessment.is_goalAlert());

        return cv;
    }

    @Override
    ContentValues getInsertValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Assessment assessment = (Assessment) entity;
        cv.put(Constants.ASSESSMENT_TITLE, assessment.get_title());
        cv.put(Constants.ASSESSMENT_COURSE_ID, assessment.get_courseId());
        cv.put(Constants.IS_OBJECTIVE, assessment.is_Objective());
        cv.put(Constants.IS_PERFORMANCE, assessment.is_Performance());
        cv.put(Constants.ASSESSMENT_GOAL_DATE, assessment.get_goalDate().toString());
        cv.put(Constants.ASSESSMENT_GOAL_ALERT, assessment.is_goalAlert());

        return cv;
    }

}
