package com.inc.pwal.iac;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Alarm {
    //définit les sonneries

    private int id;
    private String path;
    private boolean isChosen;
    private int volume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public int getVolume() {
        return volume;
    }

    public Alarm(int id, String path, boolean isChosen, int volume) {
        this.id = id;
        this.path = path;
        this.isChosen = isChosen;
        this.volume = volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
