package com.inc.pwal.iac;

import android.util.Log;
import android.util.Property;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Kwarthys on 03/05/2016.
 */
public class EDT
{

    private ArrayList<SchoolClass> cours = new ArrayList<SchoolClass>();
    public ArrayList<SchoolClass> classSoon = new ArrayList<SchoolClass>();

    static private long MILLISECOND_2WEEKS = 1209600000L;

    @SuppressWarnings("deprecation")
    public ArrayList<SchoolClass> makeEDT(String filename) throws IOException
    {
        Scanner sc = new Scanner(new File(filename));

        SchoolClass leCour = new SchoolClass();

        while(sc.hasNext())
        {
            String str = sc.next();
            String summary ="";
            String prof="";
            String dtstart="";
            String location="";

            if(str.contains("SUMMARY"))
            {
                //System.out.println("PWAL" + str);
                summary = new String(str);
                String[] strs = summary.split(":");
                summary = strs[1];
                str = sc.next();
                do
                {
                    summary = summary + " " + str;
                    str = sc.next();
                }while(!str.contains("DESCRIPTION"));

                //System.out.println(summary);
                leCour.setSubject(summary);
            }

            if(str.contains("Professeur"))
            {
                prof = str;

                prof = new String(str);
                String[] strs = prof.split(":");
	    		   /*for(String s : strs)
	    			   System.out.println(s);
    			   System.out.println();*/
                prof = strs[strs.length-1];

                str = sc.next();
                do
                {
                    prof = prof + " " + str;
                    str = sc.next();
                }while(!str.contains("Groupe"));

                leCour.setTeacher(prof);
                //System.out.println(prof);
            }

            if(str.contains("DTSTART"))
            {
                dtstart = str;
                //System.out.println(dtstart);
                dtstart = dtstart.replace("DTSTART:", "");
                //System.out.println(dtstart);
                String[] s = dtstart.split("T");
                String laDate = s[0];
                String lHeure = s[1];
                //System.out.println(laDate + " // " + lHeure);
                int annee = Integer.valueOf(laDate.substring(0, 4));
                int mois = Integer.valueOf(laDate.substring(4, 6));
                int jour = Integer.valueOf(laDate.substring(6, 8));
                int heure = Integer.valueOf(lHeure.substring(0, 2));
                int minute = Integer.valueOf(lHeure.substring(2, 4));
                //System.out.println(annee + "/" + mois + "/" + jour + " : " + heure + "h" + minute);
                Date sortie = new Date(annee-1900, mois, jour, heure, minute, 0);
                //cours.add(sortie);
                leCour.setDate(sortie);
            }

            if(str.contains("LOCATION"))
            {
                location = str;
                location = new String(str);
                String[] strs = location.split(":");
                location = strs[1];
                leCour.setRoom(location);
                //System.out.println(location);

                if(leCour.isValid())
                {
                    cours.add(new SchoolClass(leCour));
                    leCour.reset();
                }
                else
                    leCour.reset();
            }



        }

        processEDT();
        return classSoon;
    }

    @SuppressWarnings("deprecation")
    private void processEDT()
    {
        //MS1970 date - MS1970 today = MStoday->date
        //Nettoyage des cours hors des deux semaines suivantes
        Date today = new Date();
        for(int i = 0; i < cours.size(); i++)
        {
            if(cours.get(i).getDate().getTime() - today.getTime() <= MILLISECOND_2WEEKS && cours.get(i).getDate().getTime() - today.getTime() > 0) //getDate renvoi DAY O MONTH entre 1 et 31
            {
                classSoon.add(cours.get(i));
            }
        }

        //-------------------Nettoyage 1 UC---------------------
        for(int j = 0; j < classSoon.size(); j++) {
            SchoolClass c1 = classSoon.get(j);
            int i = 0;
            while (i < classSoon.size() && !c1.isMarked()) {
                if (i != j) {
                    SchoolClass c2 = classSoon.get(i);
                    if (c1.getDate().getDate() == c2.getDate().getDate()) {
                        c1.setMark(c1.getDate().getHours() > c2.getDate().getHours());
                    }
                }
                i++;
            }

        }
        for(int i = 0; i<classSoon.size(); i++)
        {
            if(classSoon.get(i).isMarked())
            {
                classSoon.remove(i--); //si on le supprime il faut faire reculer le curseur d'un cran puisque la taille du ArrayList vient de changer
            }
        }
    }
}
