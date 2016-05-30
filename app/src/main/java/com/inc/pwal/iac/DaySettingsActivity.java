package com.inc.pwal.iac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DaySettingsActivity extends AppCompatActivity{

    private final String DAY_CLICKED = null;

    private Day dayPassed;
    private static String dayClicked;
    private ArrayList<String> listNameRituels;

    private ListView mListView;

    public static String getDayClicked() {
        return dayClicked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_settings);
        Intent intent = getIntent();
        Toast toast = new Toast(DaySettingsActivity.this);

        listNameRituels = new ArrayList<>();

        if (intent != null) {
            mListView = (ListView) findViewById(R.id.listViewRituels);
            for (Rituel r : MainActivity.listRituels)listNameRituels.add(r.getName());

            ArrayAdapter<String> adap = new ArrayAdapter<>(DaySettingsActivity.this,
                    android.R.layout.simple_list_item_1,listNameRituels);
            mListView.setAdapter(adap);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dayPassed.addRituel(MainActivity.listRituels.get(position));
                }
            });

            dayClicked=intent.getStringExtra(DAY_CLICKED);
            toast.makeText(DaySettingsActivity.this, dayClicked, Toast.LENGTH_SHORT).show();
            dayPassed = MainActivity.getDayPressed();
        }
    }
}
