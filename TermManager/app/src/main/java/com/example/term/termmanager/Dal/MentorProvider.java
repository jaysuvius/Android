package com.example.term.termmanager.Dal;

import android.content.UriMatcher;
import android.net.Uri;

import com.example.term.termmanager.Utils.Constants;

public class MentorProvider extends DataProvider {

    private static MentorProvider sInstance;
    private static String AUTHORITY = "com.example.term.termmanager.Dal.MentorProvider";
    private Uri content_uri = Uri.parse("content://" + AUTHORITY);
    private static String _table = Constants.MENTORS_TABLE;
    private static String[] _allColumns = Constants.MENTOR_COLUMNS;
    private UriMatcher uriMatcher  = new UriMatcher(UriMatcher.NO_MATCH);

    private static String _sortColumn = Constants.MENTOR_SORT_COLUMN;

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

    public static MentorProvider getInstance(){
        if(sInstance == null){
            sInstance = new MentorProvider();
        }
        return sInstance;
    }

    public MentorProvider(){
        super();
        getUriMatcher().addURI(get_content_uri().toString(), getBase_path(), DataProvider.TERMMANAGER_ID);
        getUriMatcher().addURI(getAuthority(), getBase_path() + "/#", DataProvider.TERMMANAGER_ID);
        sInstance = this;
    }
}
