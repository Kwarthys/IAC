package com.inc.pwal.iac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateRituelsActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText hoursField;
    private EditText minutesField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rituels);
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }

    public void onClickValidate(View view) {

        nameField = (EditText) findViewById(R.id.nameRituel);
        hoursField = (EditText) findViewById(R.id.hourRituel);
        minutesField = (EditText) findViewById(R.id.minutesRituel);

        if ((nameField.getText().length() != 0) && (hoursField.getText().length() != 0) && (minutesField.getText().length() != 0)) {
            if ((Integer.valueOf(hoursField.getText().toString()) >= 0) && (Integer.valueOf(minutesField.getText().toString()) >= 0) && (Integer.valueOf(minutesField.getText().toString()) <= 60)) {
                Rituel rituel = new Rituel(1, nameField.getText().toString(), Integer.parseInt(String.valueOf(hoursField.getText())), Integer.parseInt(String.valueOf(minutesField.getText())), "default");
                //System.out.println(rituel.getName());

                this.finish();
            } else tost("champs mal renseignés");
        }
        else tost("champs non renseignés");
    }

    public void tost(String toToast)
    {
        Toast.makeText(this, toToast, Toast.LENGTH_SHORT).show();
    }
}
