package com.example.term.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.term.termmanager.Controllers.CourseController;
import com.example.term.termmanager.Models.Course;

import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entity_list_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CourseController cc = new CourseController(getApplicationContext());
        List<Course> courses = cc.getAll();

        ArrayAdapter<Course> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);

        ListView list = findViewById(R.id.course_list);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                Course selectedItem = (Course) arg0.getItemAtPosition(position);
                launch_course_detail(selectedItem.getId());
            }
        });

    }

    public void launch_course_detail(long id){
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


}
