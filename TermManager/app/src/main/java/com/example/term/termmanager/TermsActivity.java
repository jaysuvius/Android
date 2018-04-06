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

import com.example.term.termmanager.Controllers.TermController;
import com.example.term.termmanager.Models.Term;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private static final int EDITOR_REQUEST_CODE = 1001;
    private ListView list;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entity_list_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = findViewById(R.id.terms_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                Term selectedItem = (Term) arg0.getItemAtPosition(position);
                launch_term_detail(selectedItem.getId());
            }
        });

        fetchTerms();
    }

    public void launch_term_detail(long termId){
        Intent intent = new Intent(this, TermDetailActivity.class);
        intent.putExtra("id", termId);
        startActivity(intent);

    }

    public void fetchTerms(){
        TermController tc = new TermController(getApplicationContext());
        List<Term> terms = tc.getAll();

        ArrayAdapter<Term> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, terms);


        list.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                launch_term_detail(0);
                break;
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putLong("id", t.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchTerms();
    }

}
