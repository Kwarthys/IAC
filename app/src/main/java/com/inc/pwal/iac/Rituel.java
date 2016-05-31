package com.inc.pwal.iac;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Rituel {
    //définit les rituels

    private int id;
    private String name;
    private Hour lasting;
    private String icone;

    public Rituel(int id, String name, int lastingMin, int lastingHour, String icone) {
        this.id = id;
        this.name = name;
        this.lasting = new Hour (lastingHour,lastingMin);
        this.icone = icone;
        MainActivity.listRituels.add(this);
        //System.out.println(this.name);
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

    public Hour getLasting() {
        return lasting;
    }

    public void setLasting(int lastingH,int lastingM) {
        this.lasting.setHours(lastingH);
        this.lasting.setMinutes(lastingM);
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
