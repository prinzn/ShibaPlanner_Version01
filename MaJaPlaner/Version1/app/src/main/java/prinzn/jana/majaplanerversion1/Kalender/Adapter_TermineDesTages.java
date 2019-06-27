package prinzn.jana.majaplanerversion1.Kalender;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Termin.Termin;

public class Adapter_TermineDesTages extends ArrayAdapter<Termin> {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private Calendar tag;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Adapter_TermineDesTages(Context pContext, List<Termin> pTermine, Calendar pTag) {
        super(pContext, R.layout.termine_aktueller_tag, pTermine);
        inflater = LayoutInflater.from(pContext);
        tag = pTag;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void anpassungDarstellung(View pView, Termin pTermin) {

        //Je nach Zeitzone/ Land/... wird das Format angepasst
        DateFormat zeitFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        TextView txtStartzeit = pView.findViewById(R.id.termineAktuellerTag_txt_Startzeit);
        TextView txtEndzeit = pView.findViewById(R.id.termineAktuellerTag_txt_Endzeit);

        //Termin Start & Ende an anderem Tag
        if (tag.get(Calendar.DAY_OF_MONTH) != pTermin.getStart().get(Calendar.DAY_OF_MONTH) &&
                tag.get(Calendar.DAY_OF_MONTH) != pTermin.getEnde().get(Calendar.DAY_OF_MONTH)) {

            txtStartzeit.setText(R.string.ganzer);
            txtEndzeit.setVisibility(View.INVISIBLE);
        }
        //Termin Start an anderem Tag
        else if (tag.get(Calendar.DAY_OF_MONTH) != pTermin.getStart().get(Calendar.DAY_OF_MONTH)) {

            txtStartzeit.setText("-");
            txtEndzeit.setText(zeitFormat.format(pTermin.getStart().getTime()));
        }

        //Termin Ende an anderem Tag
        else if (tag.get(Calendar.DAY_OF_MONTH) != pTermin.getEnde().get(Calendar.DAY_OF_MONTH)) {

            txtEndzeit.setText("-");
            txtStartzeit.setText(zeitFormat.format(pTermin.getStart().getTime()));
        }
        //Termin ganztägig
        else if (pTermin.getGanztaegig()) {

            txtStartzeit.setText(R.string.ganzer);
            txtEndzeit.setVisibility(View.INVISIBLE);

        } else {
            txtStartzeit.setText(zeitFormat.format(pTermin.getStart().getTime()));
            txtEndzeit.setText(zeitFormat.format(pTermin.getEnde().getTime()));
        }


    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.termine_aktueller_tag, parent, false);
        }
        Termin termin = getItem(position);
        anpassungDarstellung(view, termin);

        TextView txtTitel = view.findViewById(R.id.termineAktuellerTag_txt_Titel_Termin);
        txtTitel.setText(termin.getTitel()); // Titel des Termins anzeigen

        TextView txtFarbe = view.findViewById(R.id.termineAktuellerTag_farbe_Des_Termins);
        txtFarbe.setBackgroundColor(termin.getFarbe());    // Farbe des Termins anzeigen

        TextView txtBeschreibung = view.findViewById(R.id.termineAktuellerTag_txt_Beschreibung_Termin);
        if (termin.getNotizen() != null) {
            txtBeschreibung.setText(termin.getNotizen()); // Informationen über Termin anzeigen
        } else {
            txtBeschreibung.setText(R.string.keinenotizen);
        }

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse


}
