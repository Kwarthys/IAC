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
import android.widget.RelativeLayout;

import java.util.ArrayList;


//pwal

public class MainActivity extends AppCompatActivity implements View.OnClickListener{   //activité du menu principal (affichage des horaires de chaque jour)


    private Day defaultDay;
    private Day monday;
    private Week week1;
    private Alarm alarm;
    private Rituel douche;
    private final String DAY_CLICKED="DayPassed";

    @Override
   protected void onCreate(Bundle savedInstanceState)
    {
        ArrayList<Rituel> rituelsEmpty=new ArrayList<>();
        defaultDay=new Day(rituelsEmpty,42,"DEFAULT",new Hour(24,0));

        douche = new Rituel(1,"douche",25,0,"pwal");
        ArrayList<Rituel> rituels = new ArrayList<Rituel>();
        rituels.add(douche);
        monday=new Day(rituels,1,"Lundi",new Hour(12,0));
        super.onCreate(savedInstanceState);

        int view = R.layout.activity_main;
        setContentView(view);

        Button buttonMonday = (Button) findViewById(R.id.buttonMonday);
        int H = monday.getAlarmHour().getHours();
        int M = monday.getAlarmHour().getMinutes();
        buttonMonday.setText(monday.getName()+" " + H + ":" + M);
        buttonMonday.setOnClickListener(MainActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        /*Intent intent = new Intent(MainActivity.this, DaySettingsActivity.class);
        intent.putExtra(DAY_CLICKED,v);
        startActivity(intent);*/
    }
}
