package com.example.term.termmanager.Dal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DataProvider extends ContentProvider {

    private String authority;
    private String base_path = "";
    private Uri content_uri;
    private UriMatcher uriMatcher;

    // Constant to identify the requested operation
    public static final int TERMMANAGER = 1;
    public static final int TERMMANAGER_ID = 2;

    private String _table;
    private String[] _allColumns;
    private String _sortColumn;

    public String getBase_path(){
        return base_path;
    }

    public Uri get_content_uri(){return content_uri; }

    public String getAuthority(){
        return authority;
    }

    public String get_table(){
        return _table;
    }

    public String[] get_allColumns(){
        return _allColumns;
    }

    public String get_sortColumn(){
        return _sortColumn;
    }

    public UriMatcher getUriMatcher(){return uriMatcher;}

    private SQLiteDatabase _db;


    public DataProvider(){

    }

    @Override
    public boolean onCreate(){
        DbOpenHelper helper = new DbOpenHelper(getContext());
        _db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortColumn) {
        return _db.query(get_table(), get_allColumns(), selection, null, null, null, get_sortColumn() + " ASC");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = _db.insert(get_table(), null,  contentValues);
        return Uri.parse(getBase_path() + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return _db.delete(get_table(), selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        return _db.update(get_table(), contentValues, selection, selectionArgs);
    }
}
