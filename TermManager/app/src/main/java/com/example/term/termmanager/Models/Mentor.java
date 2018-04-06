package com.example.term.termmanager.Models;

public class Mentor extends Entity {

    private String _name;
    private String _phone;
    private String _email;
    private long _associatedEntityId;


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }


    public long get_associatedEntityId() {
        return _associatedEntityId;
    }

    public void set_associatedEntityId(long _associatedEntityId) {
        this._associatedEntityId = _associatedEntityId;
    }

    public Mentor(String name, String phone, String email, long associatedEntityId){
        _name = name;
        _phone = phone;
        _email = email;
        _associatedEntityId = associatedEntityId;
    }

    public Mentor(int id, String name, String phone, String email, long associatedEntityId){
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _associatedEntityId = associatedEntityId;
    }

    public Mentor(){

    }

    public String toString(){
        return _name;
    }

}
