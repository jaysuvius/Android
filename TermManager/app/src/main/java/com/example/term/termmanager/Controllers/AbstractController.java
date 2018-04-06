package com.example.term.termmanager.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Dal.DataProvider;
import com.example.term.termmanager.Models.Entity;

import java.util.ArrayList;
import java.util.List;


abstract class AbstractController<T extends  Entity> implements iController<T> {

    public Context _context;

    public DataProvider _provider;

    AbstractController(Context c, DataProvider p){
        _context = c;
        _provider = p;
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, null, null, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }

    @Override
    public List<T> getByFields(String selection, String[] selectionArgs) {
        List<T> entities = new ArrayList<>();

        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selection, selectionArgs, null);
        if (cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                entities.add(parse(cursor));
            }
        }
        return entities;
    }

    @Override
    public T getById(Uri uri) {
        String selectionClause = "";
        if(_provider.getUriMatcher().match(uri) == DataProvider.TERMMANAGER_ID){
            selectionClause = "_id" + " = " + uri.getLastPathSegment();
        }
        Cursor cursor = _context.getContentResolver().query(_provider.get_content_uri(), null, selectionClause, null, null);
        if(cursor == null || cursor.getCount() < 1){
            return null;
        }
        cursor.moveToFirst();
        return parse(cursor);
    }

    @Override
    public Uri Insert(T entity) {
        ContentValues cv = getInsertValues(entity);
        return _context.getContentResolver().insert(_provider.get_content_uri(), cv);
    }

    @Override
    public int Update(T entity) {
        String selectionClause = "_id = " + entity.getId();
        ContentValues cv = getContentValues(entity);
        return _context.getContentResolver().update(_provider.get_content_uri(), cv, selectionClause, null);
    }

    @Override
    public int Delete(T entity) {
        String selectionClause = "_id = " + entity.getId();
        ContentValues cv = getContentValues(entity);
        return _context.getContentResolver().delete(_provider.get_content_uri(), selectionClause, null);
    }

    public int DeleteAll(){
        return _context.getContentResolver().delete(_provider.get_content_uri(), null, null);
    }

    abstract T parse(Cursor cursor);

    abstract ContentValues getContentValues(T entity);

    abstract ContentValues getInsertValues(T entity);

}
