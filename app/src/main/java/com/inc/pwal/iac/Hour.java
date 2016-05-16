package com.inc.pwal.iac;

/**
 * Created by Portable Pierre on 03/05/2016.
 */
public class Hour {
    //outil pour faciliter les calculs d'horaires

    private int hours;
    private int minutes;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Hour(int hours, int minutes) {
        this.hours = hours;
        if (minutes < 60) this.minutes = minutes;
        else {
            this.hours+=minutes%60;
            this.minutes+=(minutes-60*minutes%60);
        }
    }
}
