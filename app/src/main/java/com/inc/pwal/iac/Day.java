package com.inc.pwal.iac;

import android.widget.Button;
import java.util.ArrayList;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Day {
    //définit les jours

    private ArrayList<Rituel> rituels;                           //liste des rituels
    private int id;                                             //id des jours
    private String name;                                        //nom des jours
    private Hour alarmHour;                                     //heure de réveil
    private Hour classHour;                                     //heure de début des cours
    private Button button;

    public Day(int id, String name, Hour classHour, Button button) {
        this.rituels = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.classHour = classHour;
        this.alarmHour = new Hour();
        this.calculateAlarmHour();
        this.button = button;
        MainActivity.week1.add(this);
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
        boolean isDifferent = false;
        for (Rituel rit : rituels) {
            if (rit == r) isDifferent=true;
        }
        if (!isDifferent)rituels.add(r);
    }

    public void calculateAlarmHour() {
        this.alarmHour.setHours(this.classHour.getHours()); this.alarmHour.setMinutes(this.classHour.getMinutes());
        if (!rituels.isEmpty()) {
            for (Rituel r : rituels) {
                System.out.println(r.getName());
                this.alarmHour.setHours(this.alarmHour.getHours() - r.getLasting().getHours());
                System.out.println(alarmHour.getHours());
                this.alarmHour.setMinutes(this.alarmHour.getMinutes() - r.getLasting().getMinutes());
                System.out.println(alarmHour.getMinutes());
                if (this.alarmHour.getMinutes() < 0) {
                    this.alarmHour.setMinutes(this.alarmHour.getMinutes() + 60);
                    this.alarmHour.setHours(this.alarmHour.getHours() - 1);
                    System.out.println(alarmHour.getHours() + " " + alarmHour.getMinutes());
                }
            }
        }
    }
}
