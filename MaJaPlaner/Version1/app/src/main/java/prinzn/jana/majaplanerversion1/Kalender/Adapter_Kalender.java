package prinzn.jana.majaplanerversion1.Kalender;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Termin.Termin;

public class Adapter_Kalender extends ArrayAdapter<Calendar> {

    //Darstellung eines Monats
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private List<Termin> terminListe;
    private int letzePosition;

    private final String logTag = "LogTag_KalenderAdapter";    //Tag um Fehler im Logcat zu finden

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Adapter_Kalender(Context pContext, ArrayList<Calendar> pTage, List<Termin> pTerminListe) {
        super(pContext, R.layout.zelle_aktueller_monat, pTage);
        inflater = LayoutInflater.from(pContext);
        terminListe = pTerminListe;
        letzePosition = -5;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void termineAnzeigen(View pView, int pFarbe) {

        TextView terminAnzeige1 = pView.findViewById(R.id.txt_Termin1);
        TextView terminAnzeige2 = pView.findViewById(R.id.txt_Termin2);
        TextView terminAnzeige3 = pView.findViewById(R.id.txt_Termin3);
        TextView terminAnzeige4 = pView.findViewById(R.id.txt_Termin4);
        //Anzeige von bis zu 4 Terminen mit Farbe in der Monatsübersicht

        if (terminAnzeige1.getVisibility() != View.VISIBLE) {
            // bisher kein Termin an diesem Tag
            terminAnzeige1.setVisibility(View.VISIBLE);
            terminAnzeige1.setBackgroundTintList(ColorStateList.valueOf(pFarbe));    //bei einem Termin soll es eingefärbt werden

        } else if (terminAnzeige1.getVisibility() == View.VISIBLE
                && terminAnzeige2.getVisibility() != View.VISIBLE) {
            // bisher ein Termin an diesem Tag
            terminAnzeige2.setVisibility(View.VISIBLE);
            terminAnzeige2.setBackgroundTintList(ColorStateList.valueOf(pFarbe)); //bei einem Termin soll es eingefärbt werden

        } else if (terminAnzeige1.getVisibility() == View.VISIBLE
                && terminAnzeige2.getVisibility() == View.VISIBLE
                && terminAnzeige3.getVisibility() != View.VISIBLE) {
            // bisher zwei Termine an diesem Tag
            terminAnzeige3.setVisibility(View.VISIBLE);
            terminAnzeige3.setBackgroundTintList(ColorStateList.valueOf(pFarbe)); //bei einem Termin soll es eingefärbt werden

        } else if (terminAnzeige1.getVisibility() == View.VISIBLE
                && terminAnzeige2.getVisibility() == View.VISIBLE
                && terminAnzeige3.getVisibility() == View.VISIBLE
                && terminAnzeige4.getVisibility() != View.VISIBLE) {
            // bisher drei Termine an diesem Tag
            terminAnzeige4.setVisibility(View.VISIBLE);
            terminAnzeige4.setBackgroundTintList(ColorStateList.valueOf(pFarbe)); //bei einem Termin soll es eingefärbt werden
        }
    }

    private void ueberpruefenAufTermin(View pView, int pPosition, Calendar pTag) {
        //Überprüfung ob es Termine an diesem Tag gibt
        Termin termin;

        for (int i = 0; i <= terminListe.size(); i++) {
            termin = terminListe.get(i);
            int farbe = termin.getFarbe();

            //Termin Start und Ende in einem Monat
            if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                    && (pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)) {

                termineAnzeigen(pView, farbe);

            }
            //Termin Start in anderem Monat als das Ende
            else if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                    && (pTag.get(Calendar.MONTH) != termin.getEnde().get(Calendar.MONTH))) {

                termineAnzeigen(pView, farbe);

            }
            //Termin Ende in anderem Monat als der Start
            else if ((pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)
                    && (pTag.get(Calendar.MONTH) != termin.getStart().get(Calendar.MONTH))) {

                termineAnzeigen(pView, farbe);
            }

            //Termin Start und Ende in anderem Monat  -> Termin geht jeden Tag in diesem Monat
            else if ((pTag.get(Calendar.MONTH) > termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.MONTH) < termin.getEnde().get(Calendar.MONTH))
                    && (pTag.get(Calendar.YEAR) >= termin.getStart().get(Calendar.YEAR) && pTag.get(Calendar.YEAR) <= termin.getEnde().get(Calendar.YEAR))) {

                termineAnzeigen(pView, farbe);
            }

            Log.d(logTag, "Fertig ueberpreuft " + pPosition + " - " + pTag.get(Calendar.DAY_OF_MONTH) + " - ");  //Meldung wenn der Tag überprüft wurde
        }
    }

    private Boolean ueberpruefeObTagHeuteIst(Calendar pTag) {
        //Überprüfung ob es sich um den heutigen Tag handelt
        Calendar heute = Calendar.getInstance();
        return (pTag.get(Calendar.DAY_OF_MONTH) == heute.get(Calendar.DAY_OF_MONTH))
                && (pTag.get(Calendar.MONTH) == heute.get(Calendar.MONTH))
                && (pTag.get(Calendar.YEAR) == heute.get(Calendar.YEAR));
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {

            Calendar tag = getItem(position);    // Tag aus dem Array der diesen Monat mit den jeweiligen Tagen beschreibt
            // wenn convertView = 0 ist, wird eine neue Zelle festgelegt
            if (view == null) {
                view = inflater.inflate(R.layout.zelle_aktueller_monat, parent, false);
            }
            TextView textView = view.findViewById(R.id.textview_tag);

            if (letzePosition != position) {
                // Das TextView bekommt die Zahl des jeweiligen Tages (tag weiß den Tag des Monats,
                // durch Festlegung in der KalenderSteuerung) zugeteilt
                textView.setText(String.valueOf(tag.get(Calendar.DAY_OF_MONTH)));

                Boolean heute = ueberpruefeObTagHeuteIst(tag);
                if (heute) {
                    textView.setBackgroundResource(R.drawable.markierung_heutigertag);  // beim heutigen Tag soll es eingefärbt werden
                    textView.setTextColor(Color.parseColor("#FFFFFF"));  // beim heutigen Tag soll es eingefärbt werden
                }
                letzePosition = position;
                ueberpruefenAufTermin(view, position, tag);
            }


            return view;    // Gibt die Zelle (den Tag) aus der Monatsübersicht zurück -> zeigt an

        } catch (Exception e) {
            Log.e(logTag, "getView: " + e.getMessage() + " - " + e.getCause());


        }

        return view;   // Gibt nach einem Fehler in der Darstellung leeres View zurück
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
