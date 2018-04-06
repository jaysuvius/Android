package com.example.term.termmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.term.termmanager.Controllers.NoteController;
import com.example.term.termmanager.Models.Note;

public class NoteDetailActivity extends AppCompatActivity {

    EditText noteDetail;
    Note n;
    long id;
    NoteController nc;
    long assessmentId;
    long courseId;

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
                saveNote();
                break;
            case R.id.action_new:
                newNote();
                break;
            case R.id.action_delete:
                deleteNote();
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
        setContentView(R.layout.activity_notes_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteDetail = findViewById(R.id.note_input);
        nc = new NoteController(getApplicationContext());
        Bundle extrasBundle = getIntent().getExtras();
        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            courseId = extrasBundle.getLong("courseId");
            assessmentId = extrasBundle.getLong("assessmentId");
        }
        Uri uri = Uri.parse("content://" + nc._provider.getAuthority() + "/" + nc._provider.get_table() + "/" + id);
        n = (Note)nc.getById(uri);

        if(n == null){
            newNote();
        }
        else{
            noteDetail.setText(n.get_note());
            courseId = n.get_courseId();
            assessmentId = n.get_assessmentId();
        }

    }

    protected void newNote(){
        n = new Note();
        noteDetail.setText("");
        n.set_assessmentId(assessmentId);
        n.set_courseId(courseId);
        n.set_note(noteDetail.getText().toString());
        n.setId(0);
    }

    protected void saveNote(){
        n.set_note(noteDetail.getText().toString());
        n.set_assessmentId(assessmentId);
        n.set_courseId(courseId);
        if(nc.saveNote(n))
            Toast.makeText(NoteDetailActivity.this, "Saved Note", Toast.LENGTH_LONG).show();
    }

    protected  void deleteNote(){
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        nc.Delete(n);
                        newNote();
                        Toast.makeText(NoteDetailActivity.this, "Deleted Note", Toast.LENGTH_LONG).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }


    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("id", n.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
