package com.inc.pwal.iac;

import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Day {
    //définit les jours

    private ArrayList<Rituel> rituels = new ArrayList<>();     //liste des rituels
    private int id;                                             //id des jours
    private String name;                                        //nom des jours
    private Hour alarmHour;                                     //heure de réveil
    private Hour classHour;                                     //heure de début des cours
    private Button button;

    public Day(String name, Hour classHour,Button button) {
        this.rituels = new ArrayList<>();
        this.name = name;
        this.classHour = classHour;
        this.calculateAlarmHour();
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public ArrayList<Rituel> getRituels() {
        return rituels;
    }

    public void setRituels(ArrayList<Rituel> rituels) {
        this.rituels = rituels;
    }

    public Hour getAlarmHour() {
        return alarmHour;
    }

    public Hour getClassHour() {
        return classHour;
    }

    public void setAlarmHour(Hour alarmHour) {
        this.alarmHour = alarmHour;
    }

    public void setClassHour(Hour classHour) {
        this.classHour = classHour;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRituel(Rituel r) {
        boolean isDifferent = true;
        for (Rituel rit : rituels) {
            if (rit == r) isDifferent=false;
        }
        if (isDifferent)rituels.add(r);
    }

    public void calculateAlarmHour() {
        Hour newAlarmHour = this.classHour;
        if (rituels!=null) {
            for (Rituel r : rituels) {
                System.out.println(r.getName());
                newAlarmHour.setHours(this.classHour.getHours() - r.getLasting().getHours());
                newAlarmHour.setMinutes(this.classHour.getMinutes() - r.getLasting().getMinutes());
                if (newAlarmHour.getMinutes() < 0) {
                    newAlarmHour.setMinutes(this.classHour.getMinutes() + 60);
                    newAlarmHour.setHours(this.classHour.getHours() - 1);
                }
            }
        }
        this.setAlarmHour(newAlarmHour);
    }
}
