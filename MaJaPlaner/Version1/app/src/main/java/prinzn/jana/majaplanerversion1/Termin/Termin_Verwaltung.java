package prinzn.jana.majaplanerversion1.Termin;

import android.text.Editable;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;

public abstract class Termin_Verwaltung {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    public static Calendar TEMP = null;
    protected Calendar start, ende;

    protected Fragment_DatumAuswaehlen datumAuswaehlerDialogFragment;
    protected Fragment_ZeitAuswaehlen fragmentZeitAuswaehlenFragment;

    protected final String logTag = "LogTag_TerminVerwaltung";
    protected final String tagDatum = "DatumEingeben";
    protected final String tagZeit = "ZeitEingeben";
    protected final String tagColorpicker = "FarbeWaehlen";

    protected Boolean titelVorhanden = false;
    protected String notizen;
    protected Boolean ganztaegig;
    protected int farbe;

    //Je nach Zeitzone/ Land/... wird das Format angepasst
    protected DateFormat datumFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
    protected DateFormat zeitFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    protected Termin_Verwaltung() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Die On-Click Methoden----------------------------------------------*/
    protected void speichernGeklickt() {
        Boolean etwasFehlt = terminDatenVollstaendigUeberpruefen(); // Überprüfung ob alle notwendigen Daten vorhanden sind
        Boolean irgendwasIstFalsch = terminDatenAufFehlerUeberpruefen();    // Überprüfen ob Fehler in den Daten vorhanden sind
        if (!etwasFehlt) {  // Wenn keine Daten fehlen ...
            if (!irgendwasIstFalsch) {  // ... und keine Fehler vorhanden sind ...
                terminDatenSpeichern(); // ... Termin wird gespeichert.
            }
        }
    }

    protected void titelEingegeben(Editable s) {
        if (s.length() > 0) {   // Wenn der Titel mehr als 0 Zeichen besitzt ...
            titelVorhanden = true;  // ... ist ein Titel ist vorhanden.
        }

    }

    protected void notizenEingegeben(Editable s) {
        if (s.length() > 0) {   // Wenn die Notizen mehr als 0 Zeichen besitzen ...
            notizen = s.toString();  // ... sind Notizen vorhanden.
        } else {
            notizen = null;
        }

    }

    //Abstrakte Methoden
    /*-------------------------Die On-Click Methoden----------------------------------------------*/
    public abstract void abbruchGeklickt();

    public abstract void freundeGeklickt();

    public abstract void switchGanztaegigGeklickt();

    public abstract void datumZeitAngabeGeklickt(View v);

    public abstract void datumGeklickt(int jahr, int monat, int tag);

    public abstract void zeitGeklickt(int stunde, int minute);

    public abstract void oeffneColorPicker();

    /*-------------------------andere Methoden----------------------------------------------------*/
    protected abstract void setzenDerDaten();

    protected abstract void initialisieren();

    protected abstract Boolean terminDatenAufFehlerUeberpruefen();

    protected abstract Boolean terminDatenVollstaendigUeberpruefen();

    protected abstract void terminDatenSpeichern();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
