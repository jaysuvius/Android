package com.example.term.termmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.term.termmanager.Controllers.AssessmentController;
import com.example.term.termmanager.Models.Assessment;

import java.util.Calendar;
import java.util.Date;

import static com.example.term.termmanager.Utils.Utils.getDayFromDate;
import static com.example.term.termmanager.Utils.Utils.getMonthFromDate;
import static com.example.term.termmanager.Utils.Utils.getYearFromDate;
import static com.example.term.termmanager.Utils.Utils.parseShortDate;

public class AssessmentDetailActivity extends AppCompatActivity {

    private long id;
    private long courseId;
    private Assessment a;
    private EditText titleInput;
    private RadioButton objectiveRadio;
    private RadioButton performanceRadio;
    private TextView goalDateTextview;
    private CheckBox goalAlertCheckbox;
    private AssessmentController ac;
    private Date today;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entity_detail, menu);
        getMenuInflater().inflate(R.menu.view_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                save();
                break;
            case R.id.action_new:
                newAssessment();
                break;
            case R.id.action_delete:
                delete();
               newAssessment();
                Toast.makeText(AssessmentDetailActivity.this, "Deleted Course", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_view_notes:
                launch_view_notes();
                break;
            case R.id.action_view_images:
                launch_view_images();
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
        setContentView(R.layout.activity_assessment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                save();
            }
        });

        titleInput = findViewById(R.id.assessment_title_input);
        objectiveRadio = findViewById(R.id.objective_radio);
        performanceRadio = findViewById(R.id.performance_radio);
        goalDateTextview = findViewById(R.id.goal_date_textview);
        goalAlertCheckbox = findViewById(R.id.goal_alert_checkbox);
        today = Calendar.getInstance().getTime();

        ac = new AssessmentController(getApplicationContext());
        Bundle extrasBundle = getIntent().getExtras();
        long id = 0;
        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            courseId = extrasBundle.getLong("courseId");
        }
        Uri uri = Uri.parse("content://" + ac._provider.getAuthority() + "/" + ac._provider.get_table() + "/" + id);
        a = (Assessment) ac.getById(uri);
        if (a == null) {
            newAssessment();
        }
        else{
            titleInput.setText(a.get_title());
            objectiveRadio.setChecked(a.is_Objective() == 1);
            performanceRadio.setChecked(a.is_Performance() == 1);
            goalDateTextview.setText(getMonthFromDate(a.get_goalDate())+1 + "/" + getDayFromDate(a.get_goalDate()) + "/" + getYearFromDate(a.get_goalDate()));
            goalAlertCheckbox.setChecked(a.is_goalAlert() == 1);

        }
        final DatePickerDialog.OnDateSetListener goalDateSetListener;
        goalDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                goalDateTextview.setText((month + 1) + "/" + day + "/" + year);
            }
        };

        goalDateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(AssessmentDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        goalDateSetListener,
                        getYearFromDate(a.get_goalDate()), getMonthFromDate(a.get_goalDate()), getDayFromDate(a.get_goalDate()));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();
            }
        });

    }

    private void launch_view_course(){
        if(a.getId() == 0){
            Toast.makeText(AssessmentDetailActivity.this, "Save Assessment First", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("id", a.get_courseId());
        startActivity(intent);
    }

    private void newAssessment(){
        if (a == null)   a = new Assessment();
        a.setId(0);
        titleInput.setText("");
        a.set_title(titleInput.getText().toString());
        objectiveRadio.setChecked(false);
        a.set_isObjective(objectiveRadio.isChecked() ? 1 : 0);
        performanceRadio.setChecked(true);
        a.set_isPerformance(performanceRadio.isChecked() ? 1 : 0);
        a.set_goalDate(today);
        goalDateTextview.setText(getMonthFromDate(today) + "/" + getDayFromDate(today) + "/" + getYearFromDate(today));
        goalAlertCheckbox.setChecked(true);
        a.set_goalAlert(goalAlertCheckbox.isChecked() ? 1 : 0);
        a.set_courseId(courseId);

    }

    private void save(){
        a.set_title(titleInput.getText().toString());
        a.set_isObjective(objectiveRadio.isChecked() ? 1 : 0);
        a.set_isPerformance(performanceRadio.isChecked() ? 1 : 0);
        a.set_goalDate(parseShortDate(goalDateTextview.getText().toString()));
        a.set_goalAlert(goalAlertCheckbox.isChecked() ? 1 : 0);
        a.set_courseId(courseId);
        if(ac.saveAssessment(a))
            Toast.makeText(AssessmentDetailActivity.this, "Saved Assessment", Toast.LENGTH_LONG).show();
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ac.Delete(a);
                        newAssessment();
                        Toast.makeText(AssessmentDetailActivity.this, "Deleted Assessment", Toast.LENGTH_LONG).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    public void launch_view_notes(){
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("assessmentId", a.getId());
        startActivity(intent);
    }

    public void launch_view_images(){

    }

}
