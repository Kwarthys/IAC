package com.inc.pwal.iac;

import java.util.ArrayList;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Week {             //TODO
    //1 semaine -> 7 jours

    private ArrayList<Day> days=new ArrayList<>();
    private String name;

    public ArrayList<Day> getDays() {
        return days;
    }

    public void addDay(Day d){this.days.add(d);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Week(String name) {
        this.name=name;
    }
}
