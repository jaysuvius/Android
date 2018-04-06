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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.term.termmanager.Controllers.NoteController;
import com.example.term.termmanager.Models.Note;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private ListView notesListView;
    private Note n;
    private long id;
    private NoteController nc;
    private long courseId;
    private long assessmentId;
    private List<Note> notes;

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
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notesListView = findViewById(R.id.notes_list_view);
        nc = new NoteController(getApplicationContext());

        Bundle extrasBundle = getIntent().getExtras();

        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            assessmentId = extrasBundle.getLong("assessmentId");
            courseId = extrasBundle.getLong("courseId");
        }

        fetchNotes();

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Note selectedItem = (Note) arg0.getItemAtPosition(position);
                launch_note_detail(selectedItem.getId());
            }
        });


    }

    protected void fetchNotes() {
        Uri uri;
        if (assessmentId != 0) {
            uri = Uri.parse("content://" + nc._provider.getAuthority() + "/" + nc._provider.get_table() + "/" + assessmentId);
            notes = nc.getByAssessmentId(uri);
        } else if (courseId != 0) {
            uri = Uri.parse("content://" + nc._provider.getAuthority() + "/" + nc._provider.get_table() + "/" + courseId);
            notes = nc.getByCourseId(uri);
        } else {
            notes = nc.getAll();
        }

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        notesListView.setAdapter(adapter);
    }

    protected void launch_note_detail(long noteId) {
        Intent intent = new Intent(this, NoteDetailActivity.class);
        intent.putExtra("id", noteId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("assessmentId", assessmentId);
        startActivity(intent);
    }

    protected void addNew() {
        Intent intent = new Intent(this, NoteDetailActivity.class);
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
        fetchNotes();
    }



}
