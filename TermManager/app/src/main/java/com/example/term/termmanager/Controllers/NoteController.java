package com.example.term.termmanager.Controllers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Dal.DataProvider;
import com.example.term.termmanager.Dal.NoteProvider;
import com.example.term.termmanager.Models.Entity;
import com.example.term.termmanager.Models.Note;
import com.example.term.termmanager.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class NoteController extends AbstractController {

    public NoteController(Context c){
        super(c, NoteProvider.getInstance());
    }

    public boolean saveNote(Note entity){
        try{
            Uri uri = Uri.parse("entity://" + _provider.getAuthority() + "/" + _provider.get_table() + "/" + entity.getId());
            Note a = (Note) getById(uri);
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
            return false;
        }
    }

    @Override
    public Note parse(Cursor cursor) {
        return new Note(cursor.getLong(cursor.getColumnIndex(Constants.NOTE_ID)),
                cursor.getLong(cursor.getColumnIndex(Constants.NOTE_COURSE_ID)),
                cursor.getLong(cursor.getColumnIndex(Constants.NOTE_ASSESSMENT_ID)),
                cursor.getString(cursor.getColumnIndex(Constants.NOTE)));
    }

    @Override
    public ContentValues getContentValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Note note = (Note)entity;
        cv.put(Constants.NOTE_ID, note.getId());
        cv.put(Constants.NOTE_COURSE_ID, note.get_courseId());
        cv.put(Constants.NOTE_ASSESSMENT_ID, note.get_assessmentId());
        cv.put(Constants.NOTE, note.get_note().toString());
        return cv;
    }

    @Override
    public ContentValues getInsertValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Note note = (Note)entity;
        cv.put(Constants.NOTE_COURSE_ID, note.get_courseId());
        cv.put(Constants.NOTE_ASSESSMENT_ID, note.get_assessmentId());
        cv.put(Constants.NOTE, note.get_note().toString());
        return cv;
    }

    public List<Note> getByCourseId(Uri uri){
        String selectionClause = "";
        if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
            selectionClause = Constants.ASSESSMENT_COURSE_ID + " = " + uri.getLastPathSegment();
        }
        List<Note> entities = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }
    public List<Note> getByAssessmentId(Uri uri){
        String selectionClause = "";
        if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
            selectionClause = Constants.ASSESSMENT_ID + " = " + uri.getLastPathSegment();
        }
        List<Note> entities = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }

}
