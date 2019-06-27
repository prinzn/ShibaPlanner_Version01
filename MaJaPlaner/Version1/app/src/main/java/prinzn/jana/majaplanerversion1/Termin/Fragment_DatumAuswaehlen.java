package prinzn.jana.majaplanerversion1.Termin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

import prinzn.jana.majaplanerversion1.Kalender.Kalender_Steuerung;

public class Fragment_DatumAuswaehlen extends DialogFragment {

    //Datepicker
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    public static final int KENNZEICHNUNG_START_DATUM = 0;  //Schlüssel zur Identifizierung eines Startdatums in Terminerstellung
    public static final int KENNZEICHNUNG_ENDE_DATUM = 1;   //Schlüssel zur Identifizierung eines Enddatums in Terminerstellung
    private int kennzeichnung = 0;  //Kennzeichnung speichert den Identifizeierungsschlüssel für das jeweilige Objekt

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setKennzeichnung(int pKennzeichnung) {
        this.kennzeichnung = pKennzeichnung;    //Um Schlüssel des Objekts zu setzten
    }

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public int getKennzeichnung() {
        return kennzeichnung;
    }

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Datepickerdialog soll mit dem zuletzt ausgewählten (im normalen Kalender) Datum geöffnet werden
        Calendar kalender = Kalender_Steuerung.KALENDER;

        //Das Enddatum soll ein Tag später als das Startdatum sein, wenn das Startdatum bereits gesetzt wurde
        if (kennzeichnung == 1 && Termin_Verwaltung.TEMP != null) {
            kalender = Termin_Verwaltung.TEMP;
        }
        int jahr = kalender.get(Calendar.YEAR);
        int monat = kalender.get(Calendar.MONTH);
        int tag = kalender.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(Objects.requireNonNull(getActivity()),
                (DatePickerDialog.OnDateSetListener) getActivity(),
                jahr, monat, tag);   //Ein Datepickerdialog mit den richtigen Kalenderdaten wird zurückgegeben um angezeigt werden zu können
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
