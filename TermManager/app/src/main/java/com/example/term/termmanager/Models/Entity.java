package com.example.term.termmanager.Models;

public abstract class Entity implements iEntity {

    protected long _id;

    @Override
    public long getId(){
        return _id;
    }
    public void setId(long id){
        _id = id;
    }



}
