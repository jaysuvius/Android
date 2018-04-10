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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.term.termmanager.Controllers.CourseController;
import com.example.term.termmanager.Controllers.TermController;
import com.example.term.termmanager.Models.Course;
import com.example.term.termmanager.Models.Term;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.term.termmanager.Utils.Utils.getDayFromDate;
import static com.example.term.termmanager.Utils.Utils.getMonthFromDate;
import static com.example.term.termmanager.Utils.Utils.getYearFromDate;
import static com.example.term.termmanager.Utils.Utils.parseShortDate;

public class TermDetailActivity extends AppCompatActivity {

    private TextView startDate;
    private TextView endDate;
    private ArrayAdapter<Course> adapter;
    private ListView list;
    private CourseController cc;
    private Uri uri;
    private Term t;
    private Button addButton;
    private EditText termTextBox;
    private TermController tc;


    private long id;
    private long termId;

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
            case R.id.action_new:
                newTerm();
                break;
            case R.id.action_delete:
                deleteTerm();
                break;
            case R.id.action_save:
                saveTerm();
                termId = t.getId();
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
        setContentView(R.layout.activity_term_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                saveTerm();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);


        tc = new TermController(getApplicationContext());
        Bundle extrasBundle = getIntent().getExtras();
        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            id = savedInstanceState.getLong("id");
            termId = id;
        } else if (extrasBundle != null && !extrasBundle.isEmpty()) {
            id = extrasBundle.getLong("id");
            termId = id;
        }

        uri = Uri.parse("content://" + tc._provider.getAuthority() + "/" + tc._provider.get_table() + "/" + id);

        t = (Term) tc.getById(uri);


        final DatePickerDialog.OnDateSetListener startDateSetListener;
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startDate.setText(month + 1 + "/" + day + "/" + year);
            }
        };
        final DatePickerDialog.OnDateSetListener endDateSetListener;
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                endDate.setText(month + 1 + "/" + day + "/" + year);
            }
        };
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(TermDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        getYearFromDate(t.get_startDate()), getMonthFromDate(t.get_startDate()), getDayFromDate(t.get_startDate()));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(TermDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        getYearFromDate(t.get_endDate()), getMonthFromDate(t.get_endDate()), getDayFromDate(t.get_endDate()));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.show();
            }
        });


        addButton = findViewById(R.id.add_course_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_course_detail(0);
            }
        });

        termTextBox = findViewById(R.id.termTitleTextView);
        if (t == null) {
            newTerm();
        } else {
            termId = t.getId();
            termTextBox.setText(t.get_title());

            startDate.setText(getMonthFromDate(t.get_startDate()) + 1 + "/" + getDayFromDate(t.get_startDate()) + "/" + getYearFromDate(t.get_startDate()));
            endDate.setText(getMonthFromDate(t.get_endDate()) + 1 + "/" + getDayFromDate(t.get_endDate()) + "/" + getYearFromDate(t.get_endDate()));

            fetchCourses();

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Course selectedItem = (Course) arg0.getItemAtPosition(position);
                    launch_course_detail(selectedItem.getId());
                }
            });
            addButton.setEnabled(true);
        }

    }

    private void saveTerm() {
        t.set_title(termTextBox.getText().toString());
        t.set_startDate(parseShortDate(startDate.getText().toString()));
        t.set_endDate(parseShortDate(endDate.getText().toString()));
        if(tc.saveTerm(t)){
            Toast.makeText(TermDetailActivity.this, "Saved Term", Toast.LENGTH_LONG).show();
            addButton.setEnabled(true);
        }
    }

    private void deleteTerm(){
        if(list.getCount() > 0){
            new AlertDialog.Builder(this)
                    .setTitle("Invalid")
                    .setMessage("To delete term, it must have no courses")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        tc.Delete(t);
                        newTerm();
                        Toast.makeText(TermDetailActivity.this, "Deleted Term", Toast.LENGTH_LONG).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    private void newTerm() {
        if (t == null) t = new Term();
        t.setId(0);
        termTextBox.setText("");
        Date today = Calendar.getInstance().getTime();
        t.set_startDate(today);
        startDate.setText(getMonthFromDate(today) + 1 + "/" + getDayFromDate(today) + "/" + getYearFromDate(today));
        endDate.setText(getMonthFromDate(today) + 1 + "/" + getDayFromDate(today) + "/" + getYearFromDate(today));
        t.set_endDate(today);
        addButton.setEnabled(false);
    }


    public void fetchCourses() {
        cc = new CourseController(getApplicationContext());
        uri = Uri.parse("content://" + cc._provider.getAuthority() + "/" + cc._provider.get_table() + "/" + t.getId());
        List<Course> courses = cc.getByTermId(uri);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);


        list = findViewById(R.id.courses_list_view);

        list.setAdapter(adapter);
    }

    public void launch_course_detail(long courseId) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("id", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("id", t.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchCourses();
    }
}
