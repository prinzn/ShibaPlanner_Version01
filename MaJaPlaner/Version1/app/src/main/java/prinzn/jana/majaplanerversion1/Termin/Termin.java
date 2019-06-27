package prinzn.jana.majaplanerversion1.Termin;

import java.text.DateFormat;
import java.util.Calendar;

public class Termin {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private long id;
    private Boolean ganztaegig;
    private String titel, notizen, erstellung;
    private Calendar start, ende;
    private int farbe;

    //Je nach Zeitzone/ Land/... wird das Format angepasst
    private DateFormat datumFormat = DateFormat.getDateInstance(DateFormat.SHORT);
    private DateFormat zeitFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Termin(long pId, String pTitel, Calendar pStart, Calendar pEnde, Boolean pGanztaegig, int pFarbe, String pNotizen, String pErstellung) {
        id = pId;
        titel = pTitel;
        start = pStart;
        ende = pEnde;
        ganztaegig = pGanztaegig;
        farbe = pFarbe;
        notizen = pNotizen;
        erstellung = pErstellung;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public Calendar getStart() {
        return start;
    }

    public Calendar getEnde() {
        return ende;
    }

    public long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public Boolean getGanztaegig() {
        return ganztaegig;
    }

    public int getFarbe() {
        return farbe;
    }

    public String getNotizen() {
        return notizen;
    }

    public String getErstellung() {
        return erstellung;
    }

    /*-------------------------public Methoden----------------------------------------------------*/
    //Print einen Termin für's Logcat
    public String print() {
        String ausgabe;
        if (ganztaegig) {
            ausgabe = titel + " findet am " +
                    datumFormat.format(start.getTime()) + " den ganzen Tag statt. ";
        } else {
            ausgabe = titel + " findet am " +
                    datumFormat.format(start.getTime()) + " um " +
                    zeitFormat.format(start.getTime()) + " bis zum " +
                    datumFormat.format(ende.getTime()) + " um " +
                    zeitFormat.format(ende.getTime()) + " statt. ";
        }
        ausgabe = ausgabe + " (" + erstellung + ") ";
        return ausgabe;
    }

    //Print einen Termin für die Termindarstellung pro Tag
    public String printFuerList() {
        String ausgabe;
        if (ganztaegig) {
            ausgabe = "" + datumFormat.format(start.getTime());
        } else {
            ausgabe = "" +
                    datumFormat.format(start.getTime()) + " um " +
                    zeitFormat.format(start.getTime()) + " Uhr - " +
                    datumFormat.format(ende.getTime()) + " um " +
                    zeitFormat.format(ende.getTime()) + " Uhr. ";
        }
        return ausgabe;
    }

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
