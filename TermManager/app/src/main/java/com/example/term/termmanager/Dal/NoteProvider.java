package com.example.term.termmanager.Dal;

import android.content.UriMatcher;
import android.net.Uri;

import com.example.term.termmanager.Utils.Constants;

public final class NoteProvider extends DataProvider {

    private static NoteProvider sInstance;
    private static String AUTHORITY = "com.example.term.termmanager.Dal.NoteProvider";
    private Uri content_uri = Uri.parse("content://" + AUTHORITY);
    private static String _table = Constants.NOTES_TABLE;
    private static String[] _allColumns = Constants.NOTE_COLUMNS;
    private static String _sortColumn = Constants.NOTE_SORT_COLUMN;
    private UriMatcher uriMatcher  = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public String getBase_path(){
        return _table;
    }

    @Override
    public Uri get_content_uri(){
        return content_uri;
    }
    @Override
    public String getAuthority(){
        return AUTHORITY;
    }
    @Override
    public String get_table(){
        return _table;
    }
    @Override
    public String[] get_allColumns(){
        return _allColumns;
    }
    @Override
    public String get_sortColumn(){
        return _sortColumn;
    }
    @Override
    public UriMatcher getUriMatcher(){return uriMatcher; }

    public static NoteProvider getInstance(){
        if(sInstance == null){
            sInstance = new NoteProvider();
        }
        return sInstance;
    }

    public NoteProvider(){
        super();
        getUriMatcher().addURI(get_content_uri().toString(), getBase_path(), DataProvider.TERMMANAGER_ID);
        getUriMatcher().addURI(getAuthority(), getBase_path() + "/#", DataProvider.TERMMANAGER_ID);
        sInstance = this;
    }
}
