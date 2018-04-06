package com.example.term.termmanager.Models;


import java.util.Date;

public class Course extends Entity {

    private String _title;
    private Date _startDate;
    private int _startAlert;
    private Date _endDate;
    private int _endAlert;
    private String _status;
    private long _termId;


    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public boolean is_startAlert() {
        return _startAlert == 1;
    }

    public void set_startAlert(int _startAlert) {
        this._startAlert = _startAlert;
    }

    public Date get_startDate() {
        return _startDate;
    }

    public void set_startDate(Date _startDate) {
        this._startDate = _startDate;
    }

    public Date get_endDate() {
        return _endDate;
    }

    public void set_endDate(Date _endDate) {
        this._endDate = _endDate;
    }

    public boolean is_endAlert() {
        return _endAlert == 1;
    }

    public void set_endAlert(int _endAlert) {
        this._endAlert = _endAlert;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public Course(String title, Date startDate, int startAlert, Date endDate, int endAlert, String status, long termId) {
        _title = title;
        _startDate = startDate;
        _startAlert = startAlert;
        _endDate = endDate;
        _endDate = endDate;
        _endAlert = endAlert;
        _status = status;
        _termId = termId;
    }

    public Course(int id, String title, Date startDate, int startAlert, Date endDate, int endAlert, String status, long termId) {
        _id = id;
        _title = title;
        _startDate = startDate;
        _startAlert = startAlert;
        _endDate = endDate;
        _endAlert = endAlert;
        _status = status;
        _termId = termId;
    }

    public Course(){
    }

    public long get_termId() {
        return _termId;
    }

    public void set_termId(long _termId) {
        this._termId = _termId;
    }

    @Override
    public String toString(){
        return _title;
    }
}
