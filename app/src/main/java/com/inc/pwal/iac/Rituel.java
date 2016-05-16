package com.inc.pwal.iac;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class Rituel {
    //définit les rituels

    private int id;
    private String name;
    private int lasting;
    private String icone;

    public int getId() {
        return id;
    }

    public Rituel(int id, String name, int lasting, String icone) {
        this.id = id;
        this.name = name;
        this.lasting = lasting;
        this.icone = icone;
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

    public int getLasting() {
        return lasting;
    }

    public void setLasting(int lasting) {
        this.lasting = lasting;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
