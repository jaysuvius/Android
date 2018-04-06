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

import com.example.term.termmanager.Controllers.MentorController;
import com.example.term.termmanager.Models.Mentor;

public class MentorDetailActivity extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText email;
    long mentor_id;
    Mentor m;
    MentorController mc;

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
                saveMentor();
                break;
            case R.id.action_new:
                newMentor();
                break;
            case R.id.action_delete:
                deleteMentor();
                newMentor();
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
        setContentView(R.layout.activity_mentor_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                saveMentor();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.mentor_name_input);
        phone =  findViewById(R.id.mentor_phone_input);
        email = findViewById(R.id.mentor_email_input);

        mc = new MentorController(getApplicationContext());
        Bundle extrasBundle = getIntent().getExtras();
        long id = 0;
        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
        }
        Uri uri = Uri.parse("content://" + mc._provider.getAuthority() + "/" + mc._provider.get_table() + "/" + id);
        m = (Mentor)mc.getById(uri);

        if (m == null){
            newMentor();
        }
        else{
            name.setText(m.get_name());
            phone.setText(m.get_phone());
            email.setText(m.get_email());
        }


    }

    private void newMentor(){
        m = new Mentor();
        m.setId(0);
        m.set_name("");
        name.setText("");
        m.set_phone("");
        phone.setText("");
        m.set_email("");
        email.setText("");
    }

    private void saveMentor(){
        m.set_name(name.getText().toString());
        m.set_phone(phone.getText().toString());
        m.set_email(email.getText().toString());
        if(mc.saveMentor(m)){
            Toast.makeText(MentorDetailActivity.this, "Saved Mentor", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteMentor(){
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mc.Delete(m);
                        newMentor();
                        Toast.makeText(MentorDetailActivity.this, "Deleted Mentor", Toast.LENGTH_LONG).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

}
