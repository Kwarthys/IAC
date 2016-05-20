package com.inc.pwal.iac;

import android.util.Log;
import android.util.Property;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
    public String makeEDT(String filename) throws IOException
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
        return saveEDT();
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


    private ArrayList<SchoolClass> readEDT()
    {
        //return "START MATIERE " + mat + " PROF " + prof + " SALLE " + salle + " DATE " + date.getDate() + " " + date.getMonth() + " " + date.getYear() + " END ";
        ArrayList<SchoolClass> read = new ArrayList<SchoolClass>();
        //System.out.println("Je suis la");
        int size = 0;
        Scanner sc;
        try {
            sc = new Scanner(new File("edtsaved.txt"));

            String str = sc.next();
            //System.out.println("pwal :" + str);
            boolean passage = false;
            while(!str.contains("FILEEND"))
            {
                passage = false;
                if(str.contains(":START:"))
                {
                    //System.out.println("START");
                    read.add(new SchoolClass());
                    passage = true;
                    str = sc.next();
                }

                if(str.contains("MATIERE:"))
                {
                    //System.out.println("Matiere");
                    passage = true;
                    String subject = "";
                    str = sc.next();
                    while(!str.contains("PROF:"))
                    {

                        subject = subject + str + " ";
                        str = sc.next();
                    }
                    read.get(size).setSubject(subject);
                }

                if(str.contains("PROF:"))
                {
                    //System.out.println("PROF");
                    passage = true;
                    String prof = "";
                    str = sc.next();
                    while(!str.contains("SALLE:"))
                    {

                        prof = prof + str + " ";
                        str = sc.next();
                    }
                    read.get(size).setTeacher(prof);
                }


                if(str.contains("SALLE:"))
                {
                    //System.out.println("Salle");
                    passage = true;
                    String salle = "";
                    str = sc.next();
                    while(!str.contains("DATE:"))
                    {

                        salle = salle + str  + " ";
                        str = sc.next();
                    }
                    read.get(size).setRoom(salle);
                }

                if(str.contains("DATE:"))
                {
                    //System.out.println("DATE");
                    passage = true;
                    int day,month,year,hours,mins;
                    day = sc.nextInt();
                    month = sc.nextInt();
                    year = sc.nextInt();
                    hours = sc.nextInt();
                    mins = sc.nextInt();
                    read.get(size).setDate(new Date(year-1900, month, day,hours,mins,0));
                    str = sc.next();
                }

                if(str.contains(":END:"))
                {
                    //System.out.println("END");
                    passage = true;
                    size++;
                    str = sc.next();
                }

                if(!passage)
                {
                    //System.out.println("rien");
                    str = sc.next();
                }

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Un jour de mai");
        }
        //System.out.println("\n");
        return read;
    }

    private String saveEDT()
    {
        ArrayList<SchoolClass> read = new ArrayList<SchoolClass>();
        read = readEDT();

        String laModif ="";

        for(int j = 0; j<classSoon.size();j++)
        {
            for(int i = 0; i<read.size();i++)
            {
                SchoolClass r = read.get(i);
                SchoolClass s = classSoon.get(j);

                if(r.getDate().getDate() == s.getDate().getDate())
                {
                    if(r.getDate().getHours() != s.getDate().getHours())
                    {
                        laModif = laModif + "Modification le " + r.getDate().getDate() + "\n";
                    }
                }
            }
        }

        if(laModif.length() == 0)
            return null;

        File f = new File("edtsaved.txt");
        try
        {
            FileWriter w = new FileWriter(f);
            for(SchoolClass c : classSoon)
            {
                w.write(c.toString());
            }

            w.write("FILEEND");
            w.close();

        }catch (IOException e)
        {
            System.out.println ("Erreur lors de l'ecriture : " + e.getMessage());
        }

        System.out.println("\nSaved\n");
        return laModif;
    }
}
