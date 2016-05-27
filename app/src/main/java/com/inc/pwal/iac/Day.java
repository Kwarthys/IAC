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

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    private Button button;


    public Day(int id, String name, Hour classHour,Button button) {
        this.rituels = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.classHour = classHour;
        this.alarmHour = calculateAlarmHour();
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
        rituels.add(r);
    }

    public Hour calculateAlarmHour() {
        Hour newAlarmHour = this.classHour;
        if (rituels!=null) {
            for (Rituel r : rituels) {
                newAlarmHour.setHours(newAlarmHour.getHours() - r.getLasting().getHours());
                newAlarmHour.setMinutes(newAlarmHour.getMinutes() - r.getLasting().getMinutes());
                if (newAlarmHour.getMinutes() < 0) {
                    newAlarmHour.setMinutes(newAlarmHour.getMinutes() + 60);
                    newAlarmHour.setHours(newAlarmHour.getHours() - 1);
                }
            }
        }
        return newAlarmHour;
    }

}
