package com.example.term.termmanager.Models;

/**
 * Created by Dad on 2/10/2018.
 */

public class Note extends Entity {

    private  long _courseId;
    private  long _assessmentId;
    private  String _note;

    public long get_courseId() {
        return _courseId;
    }

    public void set_courseId(long _courseId) {
        this._courseId = _courseId;
    }

    public long get_assessmentId() {
        return _assessmentId;
    }

    public void set_assessmentId(long _assessmentId) {
        this._assessmentId = _assessmentId;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public Note(long id, long courseId, long assessmentId, String note){
        super.setId(id);
        _courseId = courseId;
        _assessmentId = assessmentId;
        _note = note;
        _note = note;
    }

    public Note(long courseId, long assemssmentId, String note){
        _courseId = courseId;
        _assessmentId = assemssmentId;
        _note = note;
        _note = note;
    }

    public Note(){
    }

    public String toString(){
        return _note;
    }

}
