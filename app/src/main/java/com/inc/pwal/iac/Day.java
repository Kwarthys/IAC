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
        if(r == null)return;
        if (!rituels.contains(r)) rituels.add(r);
    }

    public void removeRituel(Rituel r){
        rituels.remove(r);
    }

    public void calculateAlarmHour(){
        if(classHour == null) {
            System.out.println("TO DA PLANK, HERE'S NULL");
            return;
        }
        Hour newAlarmHour = new Hour(this.classHour.getHours(),this.classHour.getMinutes());
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
        this.setAlarmHour(newAlarmHour);
    }
}
