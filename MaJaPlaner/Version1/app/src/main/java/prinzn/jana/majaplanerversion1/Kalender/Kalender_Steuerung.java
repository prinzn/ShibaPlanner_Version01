package prinzn.jana.majaplanerversion1.Kalender;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.Knuten.MaJa_LinkedList;
import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Termin.Termin;

public class Kalender_Steuerung {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Kalender_GUI dieGui;
    private final String logTag = "LogTag_KalenderSteuerung";
    private Kalender_Fragment_DatumSpringen datumAuswaehlerDialogFragment;

    /*-------------------------Attribute für den Kalender-----------------------------------------*/
    public static Calendar KALENDER;

    private Calendar heute;
    private String[] bezeichnungen = new String[11];
    private Adapter_TermineDesTages termineAdapter;
    private Kalender_Fragment_Monat monatFragment;

    //Je nach Zeitzone/ Land/... wird das Format angepasst
    private DateFormat datumFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);

    /*-------------------------Schlüssel----------------------------------------------------------*/
    public static final String LETZTER_MONAT_UEBERGABE = "LetzterMonatÜbergabe";
    public static final String LETZTES_JAHR_UEBERGABE = "LetztesJahrÜbergabe";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Kalender_Steuerung(Kalender_GUI pGui) {
        dieGui = pGui;
        initialisieren();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/
    public void btnZuvorGeklickt() {
        monatFragment.setAltesView(null);
        KALENDER.add(Calendar.MONTH, -1);    //der Monat des Kalenders wird um eins reduziert
        aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
        listViewClear();    // ListView leeren
    }

    public void btnWeiterGeklickt() {
        monatFragment.setAltesView(null);
        KALENDER.add(Calendar.MONTH, 1); //der Monat des Kalenders wird um eins addiert
        aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
        listViewClear();    // ListView leeren
    }

    public void btnHeutigerTagGeklickt() {
        listViewClear();
        KALENDER.set(Calendar.YEAR, heute.get(Calendar.YEAR));
        KALENDER.set(Calendar.MONTH, heute.get(Calendar.MONTH));
        aktualisiereKalender();
    }

    public void zuDatumSpringen() {
        datumAuswaehlerDialogFragment.show(dieGui.getSupportFragmentManager(), logTag);   //Datepicker wird geöffnet
    }

    public void btnNeuerTerminGeklickt() {
        dieGui.oeffneActifityNeuerTermin();
    }

    public void einTagInDerTabelleDesAktuellenMonatsGeklickt(View pView, int pPosition) {
        //nur wenn angeklickte Zelle nicht null ist (Wenn es ein Tag im Monat ist)
        if (monatFragment.getTage().get(pPosition) != null) {
            Calendar tag = monatFragment.getTage().get(pPosition);
            KALENDER = tag;

            dieGui.setTxtMomentanesDatum(datumFormat.format(tag.getTime()));
            monatFragment.einTagInDerTabelleDesAktuellenMonatsGeklickt(pView, pPosition);
            zeigeTermineDesTages(tag);
        }
    }

    public void einTagInDerTabelleDesAktuellenMonatsLangGeklickt(View pView, int pPosition, long pId) {
        if (monatFragment.getTage().get(pPosition) != null) {
            dieGui.getTabelleAktuellerMonat().performItemClick(pView, pPosition, pId); //Zelle wird geklickt
            dieGui.oeffneActifityNeuerTermin(); //Terminerstellung wird geöffnet
        }
    }

    public void einTerminWurdeGeklickt(int pPosition, ListView pTermineListView) {
        Termin termin = (Termin) pTermineListView.getItemAtPosition(pPosition);
        dieGui.oeffneActifityTerminDetails(termin.getId());
    }

    public void aktualisiereKalender() {
        monatFragment.aktualisiereKalender(dieGui.getTabelleAktuellerMonat());
        dieGui.setTxtMonatAnzeige(bezeichnungen[KALENDER.get(Calendar.MONTH)]);  //setzt die neue Monatsbezeichnung fest

        dieGui.setTxtMomentanesDatum("" + (KALENDER.get(Calendar.YEAR)));

        Log.d(logTag, "Kalender wurde aktualisiert");
    }

    public void zeigeTermineDesTages(Calendar pTag) {
        List<Termin> alleTermine = dieGui.getDataSource().getAlleTermine();
        List<Termin> termineDesTages = new ArrayList<>();

        if (termineAdapter != null) {
            termineAdapter.clear();
            termineAdapter.notifyDataSetChanged();
        }

        if (alleTermine != null) {
            //Überprüfung ob es Termine an diesem Tag gibt
            Termin termin;

            for (int i = 0; i < alleTermine.size(); i++) {
                termin = alleTermine.get(i);

                //Termin Start und Ende in einem Monat
                if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                        && (pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)) {

                    termineDesTages.add(termin);

                }
                //Termin Start in anderem Monat als das Ende
                else if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                        && (pTag.get(Calendar.MONTH) != termin.getEnde().get(Calendar.MONTH))) {

                    termineDesTages.add(termin);

                }
                //Termin Ende in anderem Monat als der Start
                else if ((pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)
                        && (pTag.get(Calendar.MONTH) != termin.getStart().get(Calendar.MONTH))) {

                    termineDesTages.add(termin);
                }

                //Termin Start und Ende in anderem Monat  -> Termin geht jeden Tag in diesem Monat
                else if ((pTag.get(Calendar.MONTH) > termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.MONTH) < termin.getEnde().get(Calendar.MONTH))
                        && (pTag.get(Calendar.YEAR) >= termin.getStart().get(Calendar.YEAR) && pTag.get(Calendar.YEAR) <= termin.getEnde().get(Calendar.YEAR))) {

                    termineDesTages.add(termin);
                }
            }


            if (!termineDesTages.isEmpty()) {
                MaJa_LinkedList llTermine = new MaJa_LinkedList();
                llTermine.fuegeListeHinzu(termineDesTages);
                termineAdapter = new Adapter_TermineDesTages(dieGui, llTermine.getGeordneteListe(), pTag);
                dieGui.getListViewTermineDesTages().setAdapter(termineAdapter);
                termineAdapter.notifyDataSetChanged();
            }
        }
    }

    public void listViewClear() {
        if (termineAdapter != null) {
            termineAdapter.clear();
            termineAdapter.notifyDataSetChanged();
        }
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        bezeichnungen = dieGui.getResources().getStringArray(R.array.monate);
        heute = Calendar.getInstance();
        KALENDER = Calendar.getInstance();
        KALENDER.set(Calendar.DAY_OF_MONTH, 1);
        monatFragment = (Kalender_Fragment_Monat) dieGui.getSupportFragmentManager().findFragmentById(R.id.fragment_monatsanzeige);

        datumAuswaehlerDialogFragment = new Kalender_Fragment_DatumSpringen();
    }

    /*-------------------------override Methoden--------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
