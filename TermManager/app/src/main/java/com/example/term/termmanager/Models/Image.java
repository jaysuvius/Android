package com.example.term.termmanager.Models;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Image extends Entity {

    private long _courseId;
    private long _assessmentId;
    private byte[] _image;
    private Bitmap _bitMap;

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

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public Bitmap get_bitMap(){
        return getBitMap();
    }

    public Image(long id, long courseId, long assessmentId, byte[] image){
        super.setId(id);
        _courseId = courseId;
        _assessmentId = assessmentId;
        _image = image;
    }

    public Image(long courseId, long assessmentId, byte[] image){
        _courseId = courseId;
        _assessmentId = assessmentId;
        _image = image;
    }

    public Image(){
        
    }

    protected Bitmap getBitMap(){
        return BitmapFactory.decodeByteArray(this._image, 0, _image.length, null);
    }


}
