package com.example.term.termmanager.Dal;

import android.content.UriMatcher;
import android.net.Uri;

import com.example.term.termmanager.Utils.Constants;

public final class AssessmentProvider extends DataProvider {

    private static AssessmentProvider sInstance;
    private String AUTHORITY = "com.example.term.termmanager.Dal.AssessmentProvider";
    private Uri content_uri = Uri.parse("content://" + AUTHORITY);
    private String _table = Constants.ASSESSMENTS_TABLE;
    private String[] _allColumns = Constants.ASSESSMENT_COLUMNS;
    private String _sortColumn = Constants.ASSESSMENT_SORT_COLUMN;
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

    public static AssessmentProvider getInstance(){
        if(sInstance == null){
            sInstance = new AssessmentProvider();
        }
        return sInstance;
    }

    public AssessmentProvider(){
        super();
        getUriMatcher().addURI(get_content_uri().toString(), getBase_path(), DataProvider.TERMMANAGER_ID);
        getUriMatcher().addURI(getAuthority(), getBase_path() + "/#", DataProvider.TERMMANAGER_ID);
        sInstance = this;
    }
}
