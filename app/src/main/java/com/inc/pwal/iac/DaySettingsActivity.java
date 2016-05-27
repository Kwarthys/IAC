package com.inc.pwal.iac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DaySettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private final String DAY_CLICKED = null;

    private Day dayPassed;
    private static String dayClicked;

    public static String getDayClicked() {
        return dayClicked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_settings);

        Button buttonReturn = (Button)findViewById(R.id.buttonReturn);
        if (buttonReturn != null) {
            buttonReturn.setOnClickListener(DaySettingsActivity.this);
        }

        Intent intent = getIntent();

        Toast toast = new Toast(DaySettingsActivity.this);
        if (intent != null) {
            dayClicked=intent.getStringExtra(DAY_CLICKED);
            toast.makeText(DaySettingsActivity.this, dayClicked, Toast.LENGTH_SHORT).show();
            dayPassed = MainActivity.getDayPressed();
            dayPassed.addRituel(new Rituel(1,"douche",15,0,"pwal"));
        }
    }

    public void onClick(View v){
        if (v==findViewById(R.id.buttonReturn)) {
            MainActivity.updateInterface();
        }
    }
}
