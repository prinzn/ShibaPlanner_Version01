package prinzn.jana.majaplanerversion1.kalender;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.termin.Termin;

public class Kalender_Fragment_Monat extends Fragment {

    private ArrayList<Calendar> tage = new ArrayList<>();
    private GridView tabelleAktuellerMonat;
    private TermineDataSource dataSource;
    private View altesView;


    public View getAltesView() {
        return altesView;
    }

    public void setAltesView(View pAltesView) {
        this.altesView = pAltesView;
    }

    public ArrayList<Calendar> getTage() {
        return tage;
    }

    public void aktualisiereKalender(GridView pTabelleAktuellerMonat) {
        setztenDesMonatsArray();
        dataSource.open();
        List<Termin> terminListe = dataSource.getAlleTermine();
        Adapter_Kalender adapterAktuellerMonat = new Adapter_Kalender(this.getContext(), tage, terminListe); //KalenderAdapter um den Kalender in der Tabelle darzustellen
        tabelleAktuellerMonat = pTabelleAktuellerMonat;
        tabelleAktuellerMonat.setAdapter(adapterAktuellerMonat);    //Kalender wird dargestellt
        dataSource.close();

    }

    public void einTagInDerTabelleDesAktuellenMonatsGeklickt(View pView, int pPosition) {
        //nur wenn angeklickte Zelle nicht null ist (Wenn es ein Tag im Monat ist)
        if (tage.get(pPosition) != null) {
            if (altesView == null) {
                altesView = pView;  //wenn noch keine Zelle angeklickt wurde, ist die momentane auch die letzte
            }

            //zuvor angeklickte Zelle wird unmarkiert
            altesView.setBackgroundColor(0x00000000);

            //neu angeklickte Zelle wird markiert -> ausgewählt
            pView.setBackgroundResource(R.drawable.markierung_ausgewaehlt);

            //neu angeklickte Zelle wird jetzt alte Zelle für später
            altesView = pView;
        }
    }


    private void setztenDesMonatsArray() {
        Kalender_Steuerung.KALENDER.set(Calendar.DAY_OF_MONTH, 1);
        int ersterTag = Kalender_Steuerung.KALENDER.get(Calendar.DAY_OF_WEEK) - 2;   //da 1. Position 0 ist und - dem Tag
        tage = new ArrayList<>();
        int datum = 1;
        for (int i = 0; i < 42; i++) {

            /*--------------------Fehlerbehebung--------------------------------------------------*/
            if (i == 0) {
                if (Kalender_Steuerung.KALENDER.get(Calendar.DAY_OF_WEEK) == 0) {
                    datum = 1;  //Da Fehler mit dem ersten des Monats auftrat
                }
                if (ersterTag == -1) {
                    ersterTag = 6;
                }
                if (ersterTag == 7) {
                    ersterTag = 0;
                }
            }
            /*--------------------Array füllen----------------------------------------------------*/
            if ((i >= ersterTag) && !(i >= Kalender_Steuerung.KALENDER.getActualMaximum(Calendar.DAY_OF_MONTH) + ersterTag)) {    //wenn die aktuelle Position groeßer ist als der erste Tag des Monats & wenn die position groeßer ist als das Ende des Monats
                Calendar tag = Calendar.getInstance();
                tag.set(Kalender_Steuerung.KALENDER.get(Calendar.YEAR), Kalender_Steuerung.KALENDER.get(Calendar.MONTH), datum, 0, 0);
                datum++;
                tage.add(tag);
            } else {
                tage.add(null);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataSource = new TermineDataSource(getContext());
        return inflater.inflate(R.layout.fragment_kalender_monat, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        dataSource = new TermineDataSource(getContext());
    }

}
