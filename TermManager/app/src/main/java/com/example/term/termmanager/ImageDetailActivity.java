package com.example.term.termmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.term.termmanager.Controllers.ImageController;
import com.example.term.termmanager.Models.Image;

import java.io.ByteArrayOutputStream;

public class ImageDetailActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    ImageView imageView;
    Image i;
    ImageController ic;
    long id;
    long courseId;
    long assessmentId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entity_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                saveImage();
                break;
            case R.id.action_new:
                newImage();
                break;
            case R.id.action_delete:
                deleteImage();
                newImage();
                break;
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button cameraButton = findViewById(R.id.camera_button);
        imageView = findViewById(R.id.imageView);

        if(!hasCamera()){
            cameraButton.setEnabled(false);
        }

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCameraImage(v);
            }
        });

        Bundle extrasBundle = getIntent().getExtras();
        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            courseId = extrasBundle.getLong("courseId");
            assessmentId = extrasBundle.getLong("assessmentId");
        }
        ic = new ImageController(getApplicationContext());
        Uri uri = Uri.parse("content://" + ic._provider.getAuthority() + "/" + ic._provider.get_table() + "/" + id);
        i = (Image)ic.getById(uri);
        if (i==null){
            newImage();
        }
        else{
            imageView.setImageBitmap(i.get_bitMap());
        }

    }

    protected boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    protected void captureCameraImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap)extras.get("data");
            imageView.setImageBitmap(image);
        }
    }

    protected void saveImage(){
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] array = stream.toByteArray();
        i.setId(id);
        i.set_image(array);
        i.set_assessmentId(assessmentId);
        i.set_courseId(courseId);
        ic.saveImage(i);
    }

    protected void newImage(){
        i = new Image();
        i.setId(0);
        i.set_courseId(courseId);
        i.set_assessmentId(assessmentId);

    }

    protected void deleteImage(){
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ic.Delete(i);
                        newImage();
                        Toast.makeText(ImageDetailActivity.this, R.string.DeletedImages, Toast.LENGTH_LONG).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

}
