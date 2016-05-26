package com.inc.pwal.iac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static Day monday;
    private static Day tuesday;
    private static Day wednesday;
    private static Day thursday;
    private static Day friday;
    private static Day saturday;
    private static Day sunday;
    private final String DAY_CLICKED = null;

    private void CreateInterface(){
        Button buttonMonday = (Button) findViewById(R.id.buttonMonday);
        if (buttonMonday != null) {
            buttonMonday.setText(monday.getName() + " " + monday.getAlarmHour().getHours() + ":" + monday.getAlarmHour().getMinutes());
            buttonMonday.setOnClickListener(MainActivity.this);
        }

        Button buttonTuesday = (Button) findViewById(R.id.buttonTuesday);
        if (buttonTuesday != null) {
            buttonTuesday.setText(tuesday.getName() + " " + tuesday.getAlarmHour().getHours() + ":" + tuesday.getAlarmHour().getMinutes());
            buttonTuesday.setOnClickListener(MainActivity.this);
        }

        Button buttonWednesday = (Button) findViewById(R.id.buttonWednesday);
        if (buttonWednesday != null) {
            buttonWednesday.setText(wednesday.getName() + " " + wednesday.getAlarmHour().getHours() + ":" + wednesday.getAlarmHour().getMinutes());
            buttonWednesday.setOnClickListener(MainActivity.this);
        }
        Button buttonThursday = (Button) findViewById(R.id.buttonThursday);
        if (buttonThursday != null) {
            buttonThursday.setText(thursday.getName() + " " + thursday.getAlarmHour().getHours() + ":" + thursday.getAlarmHour().getMinutes());
            buttonThursday.setOnClickListener(MainActivity.this);
        }

        Button buttonFriday = (Button) findViewById(R.id.buttonFriday);
        if (buttonFriday != null) {
            buttonFriday.setText(friday.getName() + " " + friday.getAlarmHour().getHours() + ":" + friday.getAlarmHour().getMinutes());
            buttonFriday.setOnClickListener(MainActivity.this);
        }

        Button buttonSaturday = (Button) findViewById(R.id.buttonSaturday);
        if (buttonSaturday != null) {
            buttonSaturday.setText(saturday.getName() + " " + saturday.getAlarmHour().getHours() + ":" + saturday.getAlarmHour().getMinutes());
            buttonSaturday.setOnClickListener(MainActivity.this);
        }

        Button buttonSunday = (Button) findViewById(R.id.buttonSunday);
        if (buttonSunday != null) {
            buttonSunday.setText(sunday.getName() + " " + sunday.getAlarmHour().getHours() + ":" + sunday.getAlarmHour().getMinutes());
            buttonSunday.setOnClickListener(MainActivity.this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setDefaultDay (){
        monday = new Day(null, 1, "Lundi", new Hour(12, 0));
        tuesday = new Day(null, 1, "Mardi", new Hour(12, 0));
        wednesday = new Day(null, 1, "Mercredi", new Hour(12, 0));
        thursday = new Day(null, 1, "Jeudi", new Hour(12, 0));
        friday = new Day(null, 1, "Vendredi", new Hour(12, 0));
        saturday = new Day(null, 1, "Samedi", new Hour(12, 0));
        sunday = new Day(null, 1, "Dimanche", new Hour(12, 0));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        this.setDefaultDay();


        super.onCreate(savedInstanceState);

        int view = R.layout.activity_main;
        setContentView(view);

        this.CreateInterface();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                EDT edt = new EDT();
                try {
                    String lastring = edt.makeEDT();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DaySettingsActivity.class);
        if (v == findViewById(R.id.buttonMonday)) intent.putExtra(DAY_CLICKED, monday.getName());
        else if (v == findViewById(R.id.buttonTuesday)) intent.putExtra(DAY_CLICKED, tuesday.getName());
        else if (v == findViewById(R.id.buttonWednesday)) intent.putExtra(DAY_CLICKED, wednesday.getName());
        else if (v == findViewById(R.id.buttonThursday)) intent.putExtra(DAY_CLICKED, thursday.getName());
        else if (v == findViewById(R.id.buttonFriday)) intent.putExtra(DAY_CLICKED, friday.getName());
        else if (v == findViewById(R.id.buttonSaturday)) intent.putExtra(DAY_CLICKED, saturday.getName());
        else if (v == findViewById(R.id.buttonSunday)) intent.putExtra(DAY_CLICKED, sunday.getName());
        startActivity(intent);
    }

    public static Day getDayPressed() {
        Day d = null;
        switch (DaySettingsActivity.getDayClicked()) {
            case "Lundi":
                d = monday;
                break;
            case "Mardi":
                d = tuesday;
                break;
            case "Mercredi":
                d = wednesday;
                break;
            case "Jeudi":
                d = thursday;
                break;
            case "Vendredi":
                d = friday;
                break;
            case "Samedi":
                d = saturday;
                break;
            case "Dimanche":
                d = sunday;
                break;
        }
        return d;
    }

}
