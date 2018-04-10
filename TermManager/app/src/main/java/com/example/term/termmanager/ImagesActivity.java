package com.example.term.termmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.term.termmanager.Adapters.ImageListAdapter;
import com.example.term.termmanager.Controllers.ImageController;
import com.example.term.termmanager.Models.Image;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    private ListView imagesListView;
    private Image i;
    private ImageController ic;
    private long id;
    private long courseId;
    private long assessmentId;
    private List<Image> images;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entity_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                addNew();
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
        setContentView(R.layout.activity_images);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagesListView = findViewById(R.id.images_gridview);
        ic = new ImageController(getApplicationContext());

        Bundle extrasBundle = getIntent().getExtras();

        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            assessmentId = extrasBundle.getLong("assessmentId");
            courseId = extrasBundle.getLong("courseId");
        }

        fetchImages();

        imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Image selectedItem = (Image) arg0.getItemAtPosition(position);
                launch_image_detail(selectedItem.getId());
            }
        });


    }

    protected void fetchImages() {
        Uri uri;
        if (assessmentId != 0) {
            uri = Uri.parse("content://" + ic._provider.getAuthority() + "/" + ic._provider.get_table() + "/" + assessmentId);
            images =  ic.getByAssessmentId(uri);
        } else if (courseId != 0) {
            uri = Uri.parse("content://" + ic._provider.getAuthority() + "/" + ic._provider.get_table() + "/" + courseId);
            images =  ic.getByCourseId(uri);
        } else {
            images =  ic.getAll();
        }

        ImageListAdapter adapter = new ImageListAdapter(this, R.layout.image_list_layout,(ArrayList<Image>) images);
//
        imagesListView.setAdapter(adapter);

    }

    protected void launch_image_detail(long id){
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    protected void addNew() {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("id", 0);
        intent.putExtra("courseId", courseId);
        intent.putExtra("assessmentId", assessmentId);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("courseId", courseId);
        savedInstanceState.putLong("assessmentId", assessmentId);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchImages();
    }

}
