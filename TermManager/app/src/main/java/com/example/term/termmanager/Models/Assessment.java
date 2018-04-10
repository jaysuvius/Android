package com.example.term.termmanager.Models;

import java.util.Date;

public class Assessment extends Entity {

    private String _title;
    private long _courseId;
    private int _isObjective;
    private int _isPerformance;
    private Date _goalDate;
    private int _goalAlert;
    private Date _dueDate;
    private int _dueAlert;

    public long get_courseId() {
        return _courseId;
    }

    public void set_courseId(long _courseId) {
        this._courseId = _courseId;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int is_Objective() {
        return _isObjective;
    }

    public void set_isObjective(int isObjective) {
        this._isObjective = isObjective;
    }

    public int is_Performance() {
        return _isPerformance;
    }

    public void set_isPerformance(int isPerformance) {
        this._isPerformance = isPerformance;

    }

    public Date get_goalDate() {
        return _goalDate;
    }

    public void set_goalDate(Date _goalDate) {
        this._goalDate = _goalDate;
    }

    public int is_goalAlert() {
        return _goalAlert;
    }

    public void set_goalAlert(int isGoalAlert) {
        this._goalAlert = isGoalAlert;
    }

    public Date get_dueDate() {
        return _dueDate;
    }

    public void set_dueDate(Date _dueDate) {
        this._dueDate = _dueDate;
    }

    public int is_dueAlert() {
        return _dueAlert;
    }

    public void set_dueAlert(int _dueAlert) {
        this._dueAlert = _dueAlert;
    }

    public Assessment(String title, long courseId, int isObjective, int isPerformance, Date goalDate, int goalAlert, Date dueDate, int dueAlert) {
        _title = title;
        _courseId = courseId;
        _isObjective = isObjective;
        _isPerformance = isPerformance;
        _goalDate = goalDate;
        _goalAlert = goalAlert;
        _dueDate = dueDate;
        _dueAlert = dueAlert;

    }

    public Assessment(long id, String title, long courseId, int isObjective, int isPerformance, Date goalDate, int goalAlert, Date dueDate, int dueAlert) {
        _id = id;
        _title = title;
        _courseId = courseId;
        _isObjective = isObjective;
        _isPerformance = isPerformance;
        _goalDate = goalDate;
        _goalAlert = goalAlert;
        _dueDate = dueDate;
        _dueAlert = dueAlert;
    }

    public Assessment() {

    }

    @Override
    public String toString() {
        return _title;
    }


}
