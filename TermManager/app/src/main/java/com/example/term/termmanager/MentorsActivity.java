package com.example.term.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.term.termmanager.Controllers.MentorController;
import com.example.term.termmanager.Models.Mentor;

import java.util.List;

public class MentorsActivity extends AppCompatActivity {

    private List<Mentor> mentors;
    private ListView list;

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
                launch_mentor_detail(0);
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
        setContentView(R.layout.activity_mentors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetch_mentors();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                Mentor selectedItem = (Mentor) arg0.getItemAtPosition(position);
                launch_mentor_detail(selectedItem.getId());
            }
        });

    }

    public void fetch_mentors(){
        MentorController mc = new MentorController(getApplicationContext());

        mentors = mc.getAll();

        ArrayAdapter<Mentor> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mentors);

        list = findViewById(R.id.mentor_list);

        list.setAdapter(adapter);
    }

    public void launch_mentor_detail(long id){
        Intent intent = new Intent(this, MentorDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putLong("id", t.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetch_mentors();
    }


}
