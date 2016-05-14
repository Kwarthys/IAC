package com.inc.pwal.iac;

import java.util.Date;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class SchoolClass {
    //définit le premier cours de la journée

    private String subject;
    private String teacher;
    private String room;
    private Date date;
    private boolean mark;

    public SchoolClass()
    {
        date = null; teacher = null; subject = null; room = null;
    }

    public SchoolClass(SchoolClass c) {
        // TODO Auto-generated constructor stub
        date = c.date;
        teacher = c.teacher;
        subject = c.subject;
        room = c.room;
    }

    public boolean isValid() {
        // TODO Auto-generated method stub
            return ( (date!=null) && (teacher!=null) && (subject!=null) && (room!=null));
    }

    public void reset()
    {
        // TODO Auto-generated method stub
        date = null; teacher = null; subject = null; room = null;
    }


    //--------------------GETTERS & SETTERS------------------------------

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setMark(boolean m){mark = m;}

    public boolean isMarked(){return mark;}

}
