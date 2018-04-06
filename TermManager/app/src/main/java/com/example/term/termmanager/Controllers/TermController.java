package com.example.term.termmanager.Controllers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.term.termmanager.Models.Entity;
import com.example.term.termmanager.Models.Term;
import com.example.term.termmanager.Utils.Constants;
import com.example.term.termmanager.Utils.Utils;

import static com.example.term.termmanager.Dal.TermProvider.getInstance;


public class TermController extends AbstractController implements iController {


    public TermController(Context c){
        super(c, getInstance());
    }

    public boolean saveTerm(Term term){
        try{
            Uri uri = Uri.parse("content://" + _provider.getAuthority() + "/" + _provider.get_table() + "/" + term.getId());
            Term c = (Term)getById(uri);
            if (c == null || term.getId() == 0){
                Uri rtnVal = Insert(term);
                term.setId(ContentUris.parseId(rtnVal));
            }
            else{
                int rtnVal = Update(term);
            }
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }


    protected Term parse(Cursor termCursor) {
        return new Term( termCursor.getLong(termCursor.getColumnIndex(Constants.TERM_ID)),
                termCursor.getString(termCursor.getColumnIndex(Constants.TERM_TITLE)),
                Utils.parseDate(termCursor.getString(termCursor.getColumnIndex(Constants.TERM_START_DATE))),
                Utils.parseDate(termCursor.getString(termCursor.getColumnIndex(Constants.TERM_END_DATE))));
    }

    @Override
    protected ContentValues getContentValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Term term = (Term) entity;
        cv.put(Constants.TERM_ID, term.getId());
        cv.put(Constants.TERM_TITLE, term.get_title());
        cv.put(Constants.TERM_START_DATE, term.get_startDate().toString());
        cv.put(Constants.TERM_END_DATE, term.get_endDate().toString());
        return cv;
    }

    @Override
    protected ContentValues getInsertValues(Entity entity) {
        ContentValues cv = new ContentValues();
        Term term = (Term) entity;
        cv.put(Constants.TERM_TITLE, term.get_title());
        cv.put(Constants.TERM_START_DATE, term.get_startDate().toString());
        cv.put(Constants.TERM_END_DATE, term.get_endDate().toString());
        return cv;
    }


}
