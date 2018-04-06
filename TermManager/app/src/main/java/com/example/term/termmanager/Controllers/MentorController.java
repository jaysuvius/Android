package com.example.term.termmanager.Controllers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Dal.MentorProvider;
import com.example.term.termmanager.Models.Entity;
import com.example.term.termmanager.Models.Mentor;
import com.example.term.termmanager.Utils.Constants;

public class MentorController extends AbstractController {

    public MentorController(Context c){
        super(c, MentorProvider.getInstance());
    }

    public boolean saveMentor(Mentor entity){
        try{
            Uri uri = Uri.parse("entity://" + _provider.getAuthority() + "/" + _provider.get_table() + "/" + entity.getId());
            Mentor a = (Mentor) getById(uri);
            if (a == null || entity.getId() == 0){
                Uri rtnVal = Insert(entity);
                entity.setId(ContentUris.parseId(rtnVal));
            }
            else{
                int rtnVal = Update(entity);
            }
            return true;
        }
        catch (Exception ex){
            throw ex;
        }
    }
    
    protected Mentor parse(Cursor mentorCursor) {
        return new Mentor(mentorCursor.getInt(mentorCursor.getColumnIndex(Constants.MENTOR_ID)),
                mentorCursor.getString(mentorCursor.getColumnIndex(Constants.MENTOR_NAME)),
                mentorCursor.getString(mentorCursor.getColumnIndex(Constants.MENTOR_PHONE)),
                mentorCursor.getString(mentorCursor.getColumnIndex(Constants.MENTOR_EMAIL)),
                mentorCursor.getInt(mentorCursor.getColumnIndex(Constants.MENTOR_ASSOCIATED_ENTITY_ID)));
    }

    @Override
    ContentValues getContentValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Mentor mentor = (Mentor) entity;
        cv.put(Constants.MENTOR_ID, mentor.getId());
        cv.put(Constants.MENTOR_NAME, mentor.get_name());
        cv.put(Constants.MENTOR_PHONE, mentor.get_phone());
        cv.put(Constants.MENTOR_EMAIL, mentor.get_email());
        cv.put(Constants.MENTOR_ASSOCIATED_ENTITY_ID, mentor.get_associatedEntityId());
        return cv;
    }

    @Override
    ContentValues getInsertValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Mentor mentor = (Mentor) entity;
        cv.put(Constants.MENTOR_NAME, mentor.get_name());
        cv.put(Constants.MENTOR_PHONE, mentor.get_phone());
        cv.put(Constants.MENTOR_EMAIL, mentor.get_email());
        cv.put(Constants.MENTOR_ASSOCIATED_ENTITY_ID, mentor.get_associatedEntityId());
        return cv;
    }

//    public List<Mentor> getCourseMentors(String id){
//        String selectionClause = "CourseId = ?";
//        String[] selectionArgs = {id};
//        List<Mentor> entities = new ArrayList<>();
//        Cursor cursor = _provider.query(CONTENT_URI, null, selectionClause, selectionArgs, null);
//        if (cursor != null && cursor.getCount() > 0){
//            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
//                entities.add(parse(cursor));
//            }
//        }
//        return entities;
//    }
}
