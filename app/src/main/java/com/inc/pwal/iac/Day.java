package com.inc.pwal.iac;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Day {
    //définit les jours

    private ArrayList<Rituel> rituels = new ArrayList<Rituel>();     //liste des rituels
    private int id;                                             //id des jours
    private String name;                                        //nom des jours
    private Hour alarmHour;                                     //heure de réveil
    private Hour classHour;                                     //heure de début des cours

    public Day(ArrayList<Rituel> rituels, int id, String name, Hour alarmHour, Hour classHour) {
        this.rituels = rituels;
        this.id = id;
        this.name = name;
        this.alarmHour = alarmHour;
        this.classHour = classHour;
    }

    public ArrayList<Rituel> getRituels() {
        return rituels;
    }

    public void setRituels(ArrayList<Rituel> rituels) {
        this.rituels = rituels;
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

    public void AddRituel(Rituel r) {
        rituels.add(r);
    }

    public void ShowDay() {
    }                                    //TODO

    public Hour CalculateAlarmHour() {
        Hour newAlarmHour = this.classHour;
        for (Rituel r : rituels) {
            newAlarmHour.setHours(newAlarmHour.getHours() - (r.getLasting() % 60));
            newAlarmHour.setMinutes(newAlarmHour.getMinutes() - (r.getLasting() - 60));
        }
        return newAlarmHour;
    }

}
