package com.inc.pwal.iac;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DaySettingsActivity extends AppCompatActivity{

    private final String DAY_CLICKED = null;
    private final String CLASS_FROM = null;

    private Day dayPassed;
    private static String dayClicked;

    public static String getDayClicked() {
        return dayClicked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_settings);
        Intent intent = getIntent();

        ListView mListView = (ListView) findViewById(R.id.listViewRituels);
        ArrayList<String> listNameRituels = new ArrayList<>();

        if (intent != null && mListView != null) {

            for (Rituel r : MainActivity.listRituels) listNameRituels.add(r.getName());

            ArrayAdapter<String> adap = new ArrayAdapter<>(DaySettingsActivity.this,
                    android.R.layout.simple_list_item_1, listNameRituels);
            mListView.setAdapter(adap);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (dayPassed.getRituels().contains(MainActivity.listRituels.get(position))) {
                        dayPassed.removeRituel(MainActivity.listRituels.get(position));
                        parent.getChildAt(position).setBackgroundColor(Color.WHITE);
                    } else {
                        dayPassed.addRituel(MainActivity.listRituels.get(position));
                        parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                    }
                }
            });

            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent1 = new Intent(DaySettingsActivity.this, CreateRituelsActivity.class);
                    intent1.putExtra(CLASS_FROM, DaySettingsActivity.class.toString());
                    startActivity(intent1);
                    return true;
                }
            });

            dayClicked = intent.getStringExtra(DAY_CLICKED);
            tost(dayClicked);
            dayPassed = MainActivity.getDayPressed();


            /*if (!dayPassed.getRituels().isEmpty()) {
                for (int i = 0; i < MainActivity.listRituels.size(); i++) {
                    if (dayPassed.getRituels().contains(MainActivity.listRituels.get(i))) {
                        mListView.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }
            else mListView.setBackgroundColor(Color.WHITE);*/

        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }


    public void tost(String toToast)
    {
        Toast.makeText(this, toToast, Toast.LENGTH_SHORT).show();
    }
}
