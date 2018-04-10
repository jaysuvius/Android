package com.example.term.termmanager;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.term.termmanager.Controllers.AssessmentController;
import com.example.term.termmanager.Controllers.CourseController;
import com.example.term.termmanager.Controllers.MentorController;
import com.example.term.termmanager.Controllers.TermController;
import com.example.term.termmanager.Models.Assessment;
import com.example.term.termmanager.Models.Course;
import com.example.term.termmanager.Models.Mentor;
import com.example.term.termmanager.Models.Term;
import com.example.term.termmanager.Utils.Utils;

import static com.example.term.termmanager.Utils.Utils.parseDate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button load_date_button = findViewById(R.id.loadDataButton);
        load_date_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadTestData();
            }
        });

        final Button terms_button = findViewById(R.id.terms_button);
        terms_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                launch_terms_activity();
            }
        });

        final Button courses_button = findViewById(R.id.courses_button);
        courses_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                launch_courses_activity();
            }
        });

        final Button mentors_button = findViewById(R.id.mentors_button);
        mentors_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                launch_mentors_activity();
            }
        });

        Utils.setCourseAlarms(getApplicationContext());
        Utils.setAssessmentAlarms(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void launch_terms_activity(){
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }
    public void launch_courses_activity(){
        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
    }
    public void launch_mentors_activity(){
        Intent intent = new Intent(this, MentorsActivity.class);
        startActivity(intent);
    }

    public void loadTestData(){
        TermController tc = new TermController(getApplicationContext());
        tc.DeleteAll();
        Term t1 = new Term("Term1", parseDate("Mon Jan 01 00:00:00 MST 2018"), parseDate("Sat Jun 30 00:00:00 MST 2018"));

        Uri rtnVal = tc.Insert(t1);
        t1.setId(ContentUris.parseId(rtnVal));
        Term t2 = new Term("Term2", parseDate("Mon Jan 01 00:00:00 MST 2018"), parseDate("Sat Jun 30 00:00:00 MST 2018"));
        rtnVal = tc.Insert(t2);
        t2.setId(ContentUris.parseId(rtnVal));
        Term t3 = new Term("Term3", parseDate("Mon Jan 01 00:00:00 MST 2019"), parseDate("Sat Jun 30 00:00:00 MST 2019"));
        rtnVal = tc.Insert(t3);
        t3.setId(ContentUris.parseId(rtnVal));

        CourseController cc = new CourseController(getApplicationContext());
        cc.DeleteAll();
        Course c1 = new Course("Algebra", parseDate("Mon Jan 01 00:00:00 MST 2018") ,1, parseDate("Sat Jun 30 00:00:00 MST 2018"), 1, "In Progress", t1.getId());
        rtnVal = cc.Insert(c1);
        c1.setId(ContentUris.parseId(rtnVal));
        Course c2 = new Course("English", parseDate("Mon Jan 01 00:00:00 MST 2018") ,1, parseDate("Sat Jun 30 00:00:00 MST 2018"), 1, "In Progress", t1.getId());
        rtnVal = cc.Insert(c2);
        c2.setId(ContentUris.parseId(rtnVal));
        Course c3 = new Course("Sociology",parseDate("Fri Jun 01 00:00:00 MST 2018"),1,parseDate("Mon Dec 31 00:00:00 MST 2018"), 1, "Plan To Take", t1.getId());
        rtnVal = cc.Insert(c3);
        c3.setId(ContentUris.parseId(rtnVal));
        Course c4 = new Course("Psychology",parseDate("Fri Jun 01 00:00:00 MST 2018"),1,parseDate("Mon Dec 31 00:00:00 MST 2018"), 1, "Plan To Take", t1.getId());
        rtnVal = cc.Insert(c4);
        c4.setId(ContentUris.parseId(rtnVal));
        Course c5 = new Course("Philosophy", parseDate("Mon Jan 01 00:00:00 MST 2019") ,1,parseDate("Sat Jun 30 00:00:00 MST 2019"), 1, "Plan To Take", t1.getId());
        rtnVal = cc.Insert(c5);
        c5.setId(ContentUris.parseId(rtnVal));
        Course c6 = new Course("Art", parseDate("Mon Jan 01 00:00:00 MST 2019"),1,parseDate("Sat Jun 30 00:00:00 MST 2019"), 1, "Plan To Take", t1.getId());
        rtnVal = cc.Insert(c6);
        c6.setId(ContentUris.parseId(rtnVal));

        AssessmentController ac = new AssessmentController(getApplicationContext());
        ac.DeleteAll();
        Assessment a1 = new Assessment("Assessment1",c1.getId(), 1, 0, parseDate("Thu May 31 00:00:00 MST 2018"), 1, parseDate("Thu May 31 00:00:00 MST 2018"), 1);
        rtnVal = ac.Insert(a1);
        a1.setId(ContentUris.parseId(rtnVal));
        Assessment a2 = new Assessment("Assessment1",c2.getId(), 1, 0, parseDate("Thu May 31 00:00:00 MST 2018"), 1, parseDate("Thu May 31 00:00:00 MST 2018"), 1);
        rtnVal =  ac.Insert(a2);
        a2.setId(ContentUris.parseId(rtnVal));
        Assessment a3 = new Assessment("Assessment1",c3.getId(), 1, 0, parseDate("Thu May 31 00:00:00 MST 2018"), 1, parseDate("Thu May 31 00:00:00 MST 2018"), 1);
        rtnVal = ac.Insert(a3);
        a3.setId(ContentUris.parseId(rtnVal));

        MentorController mc = new MentorController(getApplicationContext());
        int dbResponse = mc.DeleteAll();
        Mentor m1 = new Mentor("First Mentor", "520/015/7233",  "mentor1@mentor.edu", c1.getId());
        rtnVal = mc.Insert(m1);
        m1.setId(ContentUris.parseId(rtnVal));
        Mentor m2 = new Mentor("Second Mentor", "520/015/7233",  "mentor2@mentor.edu", c2.getId());
        rtnVal = mc.Insert(m2);
        m2.setId(ContentUris.parseId(rtnVal));
        Mentor m3 = new Mentor("Third Mentor", "520/015/7233",  "mentor3@mentor.edu", c3.getId());
        rtnVal = mc.Insert(m3);
        m3.setId(ContentUris.parseId(rtnVal));



    }

}
