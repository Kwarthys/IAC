package com.inc.pwal.iac;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Day
{
    private ArrayList<Rituel> rituels = new ArrayList<Rituel>();     //liste des rituels
    private int id;                                             //id des jours
    private String name;                                        //nom des jours
    private Date alarmHour;                                     //heure de réveil
    private Date classHour;                                     //heure de début des cours

    public ArrayList<Rituel> getRituels() {
        return rituels;
    }

    public void setRituels(ArrayList<Rituel> rituels) {
        this.rituels = rituels;
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

    public Date getAlarmHour() {
        return alarmHour;
    }

    public void setAlarmHour(Date alarmHour) {
        this.alarmHour = alarmHour;
    }

    public Date getClassHour() {
        return classHour;
    }

    public void setClassHour(Date classHour) {
        this.classHour = classHour;
    }

    public void AddRituel(Rituel r)
    {
        rituels.add(r);
    }

    public void ShowDay() {}

    public Date CalculateAlarmHour(){
        Date newAlarmHour =this.classHour;
        for(Rituel r:rituels){
            newAlarmHour.getTime();
        }
        return newAlarmHour;
    }

}
