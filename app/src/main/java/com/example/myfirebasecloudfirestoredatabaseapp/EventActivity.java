package com.example.myfirebasecloudfirestoredatabaseapp;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EventActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setSubtitle("Firestore");

        fm = getFragmentManager();
        addEventFrgmt();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event_m:
                addEventFrgmt();
                return true;
            case R.id.view_events_m:
                viewEventsFrgmt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addEventFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.events_content, new AddEventFragment());
        ft.commit();
    }
    public void viewEventsFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.events_content, new ViewEventsFragment());
        ft.commit();
    }
}