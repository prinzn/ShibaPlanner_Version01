package prinzn.jana.majaplanerversion1.Kalender;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class Kalender_Fragment_DatumSpringen extends DialogFragment {

    //Datepicker
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Datepickerdialog soll mit dem zuletzt ausgewählten (im normalen Kalender) Datum geöffnet werden
        Calendar kalender = Kalender_Steuerung.KALENDER;

        int jahr = kalender.get(Calendar.YEAR);
        int monat = kalender.get(Calendar.MONTH);
        int tag = kalender.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(Objects.requireNonNull(getActivity()), AlertDialog.THEME_HOLO_LIGHT,
                (DatePickerDialog.OnDateSetListener) getActivity(),
                jahr, monat, tag);   //Ein Datepickerdialog mit den richtigen Kalenderdaten wird zurückgegeben um angezeigt werden zu können
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
