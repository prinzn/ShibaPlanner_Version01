package prinzn.jana.majaplanerversion1;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import prinzn.jana.majaplanerversion1.termin.Termin;

public class Adapter_AlleTermine extends ArrayAdapter<Termin> {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Adapter_AlleTermine(Context pContext, List<Termin> pTermine) {

        super(pContext, R.layout.alle_termine_anzeige, pTermine);
        inflater = LayoutInflater.from(pContext);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Get Methoden-------------------------------------------------------*/
    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.alle_termine_anzeige, parent, false);
        }

        TextView txtTitel = view.findViewById(R.id.termineAktuellerTag_txt_Titel_Termin);
        txtTitel.setText(getItem(position).getTitel()); // Titel des Termins anzeigen
        TextView txtBeschreibung = view.findViewById(R.id.termineAktuellerTag_txt_Beschreibung_Termin);
        txtBeschreibung.setText(getItem(position).printFuerList()); // Informationen über Termin anzeigen

        TextView txtFarbe = view.findViewById(R.id.termineAktuellerTag_farbe_Des_Termins);
        txtFarbe.setBackgroundTintList(ColorStateList.valueOf(getItem(position).getFarbe()));    // Farbe des Termins anzeigen
        return view;
    }
    /*-------------------------Andere Methoden----------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
