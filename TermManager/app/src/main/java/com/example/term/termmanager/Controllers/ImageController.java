package com.example.term.termmanager.Controllers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Dal.DataProvider;
import com.example.term.termmanager.Dal.ImageProvider;
import com.example.term.termmanager.Models.Entity;
import com.example.term.termmanager.Models.Image;
import com.example.term.termmanager.Utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ImageController extends AbstractController {

    public ImageController(Context c){
        super(c, ImageProvider.getInstance());
    }

    public boolean saveImage(Image entity){
        try{
            Uri uri = Uri.parse("entity://" + _provider.getAuthority() + "/" + _provider.get_table() + "/" + entity.getId());
            Image a = (Image) getById(uri);
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

    public List<Image> getByCourseId(Uri uri){
        String selectionClause = "";
        if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
            selectionClause = Constants.ASSESSMENT_COURSE_ID + " = " + uri.getLastPathSegment();
        }
        List<Image> entities = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }
    public List<Image> getByAssessmentId(Uri uri){
        String selectionClause = "";
        if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
            selectionClause = Constants.ASSESSMENT_ID + " = " + uri.getLastPathSegment();
        }
        List<Image> entities = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }

    @Override
    public Image parse(Cursor cursor) {
        return new Image(cursor.getLong(cursor.getColumnIndex(Constants.IMAGE_ID)),
                cursor.getLong(cursor.getColumnIndex(Constants.IMAGE_COURSE_ID)),
                cursor.getLong(cursor.getColumnIndex(Constants.IMAGE_ASSESSMENT_ID)),
                cursor.getBlob(cursor.getColumnIndex(Constants.IMAGE)));
    }

    @Override
    public ContentValues getContentValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Image image = (Image)entity;
        cv.put(Constants.IMAGE_ID, image.getId());
        cv.put(Constants.IMAGE_COURSE_ID, image.get_courseId());
        cv.put(Constants.IMAGE_ASSESSMENT_ID, image.get_assessmentId());
        cv.put(Constants.IMAGE, image.get_image());
        return cv;
    }

    @Override
    public ContentValues getInsertValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Image image = (Image)entity;
        cv.put(Constants.IMAGE_COURSE_ID, image.get_courseId());
        cv.put(Constants.IMAGE_ASSESSMENT_ID, image.get_assessmentId());
        cv.put(Constants.IMAGE, image.get_image());
        return cv;
    }

}
