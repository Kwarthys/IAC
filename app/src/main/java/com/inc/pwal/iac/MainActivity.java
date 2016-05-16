package com.inc.pwal.iac;

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

public class MainActivity extends AppCompatActivity {   //activité du menu principal (affichage des horaires de chaque jour)


    private Day day1;
    private Week week1;
    private Alarm alarm;
    private Rituel rit1;

    @Override
   protected void onCreate(Bundle savedInstanceState)
    {
        rit1 = new Rituel(1,"douche",15,"pwal");
        ArrayList<Rituel> rituels = new ArrayList<Rituel>();
        rituels.add(rit1);
        day1=new Day(rituels,1,"Lundi",new Hour(8,5),new Hour(12,0));
        super.onCreate(savedInstanceState);
        //int view = R.layout.activity_main;
        //setContentView(view);
        Button button = new Button(this);
        RelativeLayout pwal = new RelativeLayout(this);
        pwal.addView(button);
        setContentView(pwal);
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
}
