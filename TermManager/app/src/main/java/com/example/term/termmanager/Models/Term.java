package com.example.term.termmanager.Models;

import java.util.Date;
import java.util.List;

public class Term extends Entity {

    private String _title;
    private Date _startDate;
    private Date _endDate;
    private List<Course> _courses;


    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
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

    public List<Course> get_courses() {
        return _courses;
    }

    public void set_courses(List<Course> _courses) {
        this._courses = _courses;
    }

    public Term(String title, Date startDate, Date endDate){
        _title = title;
        _startDate = startDate;
        _endDate = endDate;
    }

    public Term(long id, String title, Date startDate, Date endDate){
        _id = id;
        _title = title;
        _startDate = startDate;
        _endDate = endDate;
    }

    public Term(){}

    @Override
    public String toString(){
        return this.get_title();
    }

}
