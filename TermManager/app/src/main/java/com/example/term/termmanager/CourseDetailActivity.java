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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.term.termmanager.Controllers.AssessmentController;
import com.example.term.termmanager.Controllers.CourseController;
import com.example.term.termmanager.Controllers.MentorController;
import com.example.term.termmanager.Models.Assessment;
import com.example.term.termmanager.Models.Course;
import com.example.term.termmanager.Models.Mentor;
import com.example.term.termmanager.Utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.term.termmanager.Utils.Utils.getDayFromDate;
import static com.example.term.termmanager.Utils.Utils.getMonthFromDate;
import static com.example.term.termmanager.Utils.Utils.getYearFromDate;
import static com.example.term.termmanager.Utils.Utils.parseShortDate;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView startDate;
    private TextView endDate;
    private Spinner statusSpinner;
    private CourseController cc;
    private Course c;
    private EditText courseTextBox;
    private CheckBox startAlert;
    private CheckBox endAlert;
    private CourseDetailActivity courseDeatilActivity;
    private ListView assessmentListView;
    private AssessmentController ac;
    private Spinner mentorSpinner;
    private Button addAssessmentButton;
    private long termId;
    private MentorController mc;
    private List<Mentor> mentors;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entity_detail, menu);
        getMenuInflater().inflate(R.menu.view_term_mentor_menu, menu);
        getMenuInflater().inflate(R.menu.view_notes_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse();
                Toast.makeText(CourseDetailActivity.this, "Saved Course", Toast.LENGTH_LONG).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startDate = findViewById(R.id.course_start_date);
        endDate = findViewById(R.id.course_end_date);
        statusSpinner = findViewById(R.id.status_spinner);
        mentorSpinner = findViewById(R.id.mentor_spinner);
        assessmentListView = findViewById(R.id.assessments_list_view);

        List<String> statuses = new ArrayList<>();
        for (String status : Constants.STATUS_TYPES) {
            statuses.add(status);
        }

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statuses);

        statusSpinner.setAdapter(statusAdapter);

        cc = new CourseController(getApplicationContext());
        Bundle extrasBundle = getIntent().getExtras();
        long id = 0;
        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            termId = extrasBundle.getLong("termId");
        }
        Uri uri = Uri.parse("content://" + cc._provider.getAuthority() + "/" + cc._provider.get_table() + "/" + id);
        c = (Course) cc.getById(uri);

        final DatePickerDialog.OnDateSetListener startDateSetListener;
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startDate.setText((month + 1) + "/" + day + "/" + year);
            }
        };
        final DatePickerDialog.OnDateSetListener endDateSetListener;
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                endDate.setText((month + 1)  + "/" + day + "/" + year);
            }
        };
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(CourseDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        getYearFromDate(c.get_startDate()), getMonthFromDate(c.get_startDate()), getDayFromDate(c.get_startDate()));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(CourseDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        getYearFromDate(c.get_endDate()), getMonthFromDate(c.get_endDate()), getDayFromDate(c.get_endDate()));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();
            }
        });



        courseTextBox = findViewById(R.id.course_name_edit);
        startAlert = findViewById(R.id.start_date_alert_checkbox);
        endAlert = findViewById(R.id.end_date_alert_checkbox);
        addAssessmentButton = findViewById(R.id.add_assessment_button);
        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_assessment_detail(0);
            }
        });
        if (c == null) {
            newCourse();

        } else {
            courseTextBox.setText(c.get_title());
            startDate.setText(getMonthFromDate(c.get_startDate())+ 1 + "/" + getDayFromDate(c.get_startDate()) + "/" + getYearFromDate(c.get_startDate()));
            endDate.setText(getMonthFromDate(c.get_endDate())+ 1 + "/" + getDayFromDate(c.get_endDate()) + "/" + getYearFromDate(c.get_endDate()));

            startAlert.setChecked(c.is_startAlert());
            endAlert.setChecked(c.is_endAlert());

            statusSpinner.setSelection(statusAdapter.getPosition(c.get_status()));
            assessmentListView = findViewById(R.id.assessments_list_view);
            assessmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Assessment selectedItem = (Assessment) arg0.getItemAtPosition(position);
                    launch_assessment_detail(selectedItem.getId());
                }
            });

            fetchAssessments();
            fetchMentors();
            addAssessmentButton.setEnabled(true);
        }

    }

    public void launch_assessment_detail(long assessmentId) {
        Intent intent = new Intent(this, AssessmentDetailActivity.class);
        intent.putExtra("id", assessmentId);
        intent.putExtra("courseId", c.getId());
        startActivity(intent);
    }

    public void fetchAssessments() {
        ac = new AssessmentController(getApplicationContext());
        Uri uri = Uri.parse("content://" + ac._provider.getAuthority() + "/" + ac._provider.get_table() + "/" + c.getId());
        List<Assessment> assessments = ac.getByCourseId(uri);

        ArrayAdapter<Assessment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessments);

        assessmentListView.setAdapter(adapter);
    }

    public void fetchMentors() {
        mc = new MentorController(getApplicationContext());
        mentors = mc.getAll();
        ArrayAdapter<Mentor> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mentors);
        mentorSpinner.setAdapter(adapter);
        mentorSpinner.setSelection(0);
        for(Mentor m : mentors){
            if(m.get_associatedEntityId() == c.getId()){
                mentorSpinner.setSelection(adapter.getPosition(m));
            }
        }

    }
    public void saveMentor() {
        mc = new MentorController(getApplicationContext());
        Mentor courseMentor =  (Mentor)mentorSpinner.getSelectedItem();
        for(Mentor m : mentors){
            m.set_associatedEntityId(0);
            mc.saveMentor(m);
        }
        courseMentor.set_associatedEntityId(c.getId());
        mc.saveMentor(courseMentor);
    }


    public void saveCourse() {
        c.set_title(courseTextBox.getText().toString());
        c.set_startDate(parseShortDate(startDate.getText().toString()));
        c.set_endDate(parseShortDate(endDate.getText().toString()));
        if (startAlert.isChecked()) {
            c.set_startAlert(1);
        } else {
            c.set_startAlert(0);
        }
        if (endAlert.isChecked()) {
            c.set_endAlert(1);
        } else {
            c.set_endAlert(0);
        }
        c.set_status(statusSpinner.toString());
        saveMentor();
        if (cc.saveCourse(c))
            Toast.makeText(CourseDetailActivity.this, "Saved Course", Toast.LENGTH_LONG).show();

        addAssessmentButton.setEnabled(true);
    }

    private void newCourse() {
        if (c == null) c = new Course();
        c.setId(0);
        courseTextBox.setText("");
        Date today = Calendar.getInstance().getTime();
        c.set_startDate(today);
        startDate.setText(getMonthFromDate(today) + "/" + getDayFromDate(today) + "/" + getYearFromDate(today));
        endDate.setText(getMonthFromDate(today) + "/" + getDayFromDate(today) + "/" + getYearFromDate(today));
        c.set_endDate(today);
        startAlert.setChecked(true);
        endAlert.setChecked(true);
        statusSpinner.setSelection(0);
        c.set_termId(termId);
        addAssessmentButton.setEnabled(false);
    }

    private void deleteCourse() {

        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        cc.Delete(c);
                        newCourse();
                        Toast.makeText(CourseDetailActivity.this, "Deleted Course", Toast.LENGTH_LONG).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                saveCourse();
                break;
            case R.id.action_new:
                newCourse();
                break;
            case R.id.action_delete:
                deleteCourse();
                newCourse();
                break;
            case R.id.action_view_mentor:
                launch_mentor_view();
                break;
            case R.id.action_view_notes:
                launch_view_notes();
                break;
            case R.id.action_view_images:
                launch_view_images();
                break;
            case R.id.action_view_term:
                launch_view_term();
                break;
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launch_mentor_view(){
        Intent intent = new Intent(this, MentorDetailActivity.class);
        intent.putExtra("id", ((Mentor)mentorSpinner.getSelectedItem()).getId());
        startActivity(intent);
    }

    public void launch_view_notes(){
        if(c.getId() == 0){
            Toast.makeText(CourseDetailActivity.this, "Save Course First", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("courseId", c.getId());
        startActivity(intent);
    }

    public void launch_view_images(){
        if(c.getId() == 0){
            Toast.makeText(CourseDetailActivity.this, "Save Course First", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, ImagesActivity.class);
        intent.putExtra("courseId", c.getId());
        startActivity(intent);
    }

    public void launch_view_term(){
        if(c.getId() == 0){
            Toast.makeText(CourseDetailActivity.this, "Save Course First", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, TermDetailActivity.class);
        intent.putExtra("id", c.get_termId());
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("id", c.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAssessments();
        fetchMentors();
    }


}
