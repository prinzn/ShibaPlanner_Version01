package prinzn.jana.majaplanerversion1.Terminuebersicht;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Termin.Termin;

public class Adapter_AlleTermine extends ArrayAdapter<Termin> {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private int letzePosition;

    //Je nach Zeitzone/ Land/... wird das Format angepasst
    private DateFormat datumFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
    private DateFormat zeitFormat = DateFormat.getTimeInstance(DateFormat.SHORT);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Adapter_AlleTermine(Context pContext, List<Termin> pTermine) {
        super(pContext, R.layout.alle_termine_anzeige, pTermine);
        inflater = LayoutInflater.from(pContext);
        letzePosition = -5;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.alle_termine_anzeige, parent, false);
        }

        if (letzePosition != position) {

            Termin termin = getItem(position);

            TextView txtTitel = view.findViewById(R.id.alleTermine_txt_Titel_Termin);
            txtTitel.setText(termin.getTitel()); // Titel des Termins anzeigen

            TextView txtBeschreibung = view.findViewById(R.id.alleTermine_txt_Beschreibung_Termin);
            if (termin.getNotizen() != null) {
                txtBeschreibung.setText(termin.getNotizen()); // Informationen über Termin anzeigen
            } else {
                txtBeschreibung.setText(R.string.keinenotizen);
            }

            TextView txtFarbe = view.findViewById(R.id.alleTermine_farbe_Des_Termins);
            txtFarbe.setBackgroundColor(termin.getFarbe());    // Farbe des Termins anzeigen

            TextView txtStartdatum = view.findViewById(R.id.alleTermine_txt_Startdatum);
            txtStartdatum.setText(datumFormat.format(termin.getStart().getTime()));    // Startdatum des Termins anzeigen

            TextView txtStartzeit = view.findViewById(R.id.alleTermine_txt_Startzeit);
            TextView txtEnddatum = view.findViewById(R.id.alleTermine_txt_Enddatum);
            TextView txtEndzeit = view.findViewById(R.id.alleTermine_txt_Endzeit);

            if (termin.getGanztaegig()) {
                txtStartzeit.setVisibility(View.INVISIBLE);
                txtEnddatum.setVisibility(View.INVISIBLE);
                txtEndzeit.setVisibility(View.INVISIBLE);

            } else {
                txtStartzeit.setText(zeitFormat.format(termin.getStart().getTime()));   // Startzeit des Termins anzeigen
                txtEnddatum.setText(datumFormat.format(termin.getEnde().getTime()));    // Enddatum des Termins anzeigen
                txtEndzeit.setText(zeitFormat.format(termin.getEnde().getTime()));    // Endzeit des Termins anzeigen
            }

            letzePosition = position;
        }

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
