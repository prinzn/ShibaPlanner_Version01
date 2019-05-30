package prinzn.jana.majaplanerversion1.termin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.kalender.Kalender_Steuerung;

public class Termin_Erstellung_Steuerung extends Termin_Verwaltung {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Termin_Erstellung_GUI dieGui;
    private Calendar kalender;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Termin_Erstellung_Steuerung(Termin_Erstellung_GUI pGui) {
        dieGui = pGui;
        initialisieren();
        setzenDerDaten();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Überschriebene Methoden
    /*-------------------------Die On-Click Methoden----------------------------------------------*/
    @Override
    public void abbruchGeklickt() {
        dieGui.oeffneActivityKalenderMonatsUebersicht();    // Kalender wird geöffnet
    }

    @Override
    public void switchGanztaegigGeklickt() {
        if (ganztaegig) {   // Termin war bisher ganztägig
            ganztaegig = false; // Termin ist jetzt nicht mehr ganztägig
            dieGui.allesSichtbarMachen();  // nicht mehr relevante Anzeigen verschwinden lassen
        } else {   // Termin war bisher nicht ganztägig
            ganztaegig = true;  // Termin ist jetzt ganztägig
            dieGui.lasseUnwichtigesVerschwinden();   // nun alle Anzeigen sichtbar machen
        }
    }

    @Override
    public void datumZeitAngabeGeklickt(View v) {
        try {
            int id = v.getId(); // Welche Angabe wurde geklickt?
            if (id == R.id.txt_Terminerstellung_Eingabe_Datum_Start) {
                datumAuswaehlerDialogFragment.setKennzeichnung(Fragment_DatumAuswaehlen.KENNZEICHNUNG_START_DATUM); // Kennzeichnung festlegen um später zu identifizieren
                datumAuswaehlerDialogFragment.show(dieGui.getSupportFragmentManager(), tagDatum);   //Datepicker wird geöffnet
            } else if (id == R.id.txt_Terminerstellung_Eingabe_Datum_Ende) {
                datumAuswaehlerDialogFragment.setKennzeichnung(Fragment_DatumAuswaehlen.KENNZEICHNUNG_ENDE_DATUM);  // Kennzeichnung festlegen um später zu identifizieren
                datumAuswaehlerDialogFragment.show(dieGui.getSupportFragmentManager(), tagDatum);   //Datepicker wird geöffnet
            } else if (id == R.id.txt_Terminerstellung_Eingabe_Zeit_Start) {
                fragmentZeitAuswaehlenFragment.setKennzeichnung(Fragment_ZeitAuswaehlen.KENNZEICHNUNG_START_ZEIT);  // Kennzeichnung festlegen um später zu identifizieren
                fragmentZeitAuswaehlenFragment.show(dieGui.getSupportFragmentManager(), tagZeit);   //Timepicker wird geöffnet
            } else if (id == R.id.txt_Terminerstellung_Eingabe_Zeit_Ende) {
                fragmentZeitAuswaehlenFragment.setKennzeichnung(Fragment_ZeitAuswaehlen.KENNZEICHNUNG_ENDE_ZEIT);   // Kennzeichnung festlegen um später zu identifizieren
                fragmentZeitAuswaehlenFragment.show(dieGui.getSupportFragmentManager(), tagZeit);   //Timepicker wird geöffnet
            }
        } catch (IllegalStateException e) {
            Log.e(logTag, "Error bei DatumZeitAngabe: " + e.getMessage());  // Bei Fehler Error- Meldung
        }
    }

    @Override
    public void datumGeklickt(int jahr, int monat, int tag) {
        if (datumAuswaehlerDialogFragment.getKennzeichnung() == Fragment_DatumAuswaehlen.KENNZEICHNUNG_START_DATUM) {   // Stimmt die Kennzeichnung, dann ...
            start.set(jahr, monat, tag);    //Startangaben setzten
            dieGui.getStartDatum().setText(datumFormat.format(start.getTime()));    //eingegebene Startangaben anzeigen

            TEMP = start;   // start temp. speichern um das Ende in der Anzeige zu aktualisieren
            TEMP.add(Calendar.DAY_OF_MONTH, 1); // Ende soll eine Stunde später sein
            dieGui.getEndDatum().setText(datumFormat.format(TEMP.getTime()));   // End- Anzeige aktualisieren
            ende = TEMP;    // Die Endangaben aktualisieren

        } else if (datumAuswaehlerDialogFragment.getKennzeichnung() == Fragment_DatumAuswaehlen.KENNZEICHNUNG_ENDE_DATUM) {   // Stimmt die Kennzeichnung, dann ...
            ende.set(jahr, monat, tag);     //Endangaben setzen
            dieGui.getEndDatum().setText(datumFormat.format(ende.getTime()));   //eingegebene Endangaben anzeigen
        }
    }

    @Override
    public void zeitGeklickt(int stunde, int minute) {
        if (fragmentZeitAuswaehlenFragment.getKennzeichnung() == Fragment_ZeitAuswaehlen.KENNZEICHNUNG_START_ZEIT) {   // Stimmt die Kennzeichnung, dann ...

            //Startangaben setzen
            start.set(Calendar.HOUR_OF_DAY, stunde);
            start.set(Calendar.MINUTE, minute);

            dieGui.getStartZeit().setText(zeitFormat.format(start.getTime()));  //eingegebene Startangaben anzeigen

        } else if (fragmentZeitAuswaehlenFragment.getKennzeichnung() == Fragment_ZeitAuswaehlen.KENNZEICHNUNG_ENDE_ZEIT) {   // Stimmt die Kennzeichnung, dann ...

            //Endangaben setzten
            ende.set(Calendar.HOUR_OF_DAY, stunde);
            ende.set(Calendar.MINUTE, minute);

            dieGui.getEndzeit().setText(zeitFormat.format(ende.getTime())); //eingegebene Endangaben anzeigen
        }
    }

    @Override
    public void oeffneColorPicker() {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();

        String[] farbenString = dieGui.getResources().getStringArray(R.array.farbenfuercolorpicker);    // Farben für die Auswahl aus dem String- Array zwischenspeichern
        int[] farben = new int[farbenString.length];    // Neuer Array, mit Größe = Anzahl der Farben
        for (int i = 0; i < 20; i++) {
            farben[i] = Color.parseColor(farbenString[i]);  // Jeden String in eine Int Farbangabe umwandeln & speichern
        }

        colorPickerDialog.initialize(R.string.waehlefarbe, farben, farbe, 4, farben.length);    // ColorPicker anpassen
        colorPickerDialog.show(dieGui.getFragmentManager(), tagColorpicker);    // Colorpicker anzeigen

        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                dieGui.getOeffneColorPicker().setBackgroundTintList(ColorStateList.valueOf(color));    // Wurde eine Farbe im ColorPicker ausgewählt -> neue Farbe des Buttons
                farbe = color;
            }
        });
    }


    /*-------------------------andere Methoden----------------------------------------------------*/
    @Override
    protected void setzenDerDaten() {
        //Gibt der Nutzer keine anderen Angaben an, sind die Beispieldaten die späteren Angaben
        Calendar aktuelleZeit = Calendar.getInstance();

        ganztaegig = true;
        switchGanztaegigGeklickt(); // Anzeige ändern

        dieGui.getStartDatum().setText(datumFormat.format(kalender.getTime()));    // Startdatum anzeigen
        dieGui.getStartZeit().setText(zeitFormat.format(aktuelleZeit.getTime()));  // Startzeit anzeigen
        start.set(kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH),
                aktuelleZeit.get(Calendar.HOUR_OF_DAY), aktuelleZeit.get(Calendar.MINUTE), aktuelleZeit.get(Calendar.SECOND));

        aktuelleZeit.add(Calendar.HOUR_OF_DAY, 1); //Zwischen Start und Ende soll 1h sein
        dieGui.getEndDatum().setText(datumFormat.format(kalender.getTime()));   // Enddatum anzeigen
        dieGui.getEndzeit().setText(zeitFormat.format(aktuelleZeit.getTime())); // Endzeit anzeigen
        ende.set(kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH),
                aktuelleZeit.get(Calendar.HOUR_OF_DAY), aktuelleZeit.get(Calendar.MINUTE), aktuelleZeit.get(Calendar.SECOND));
    }

    @Override
    protected void initialisieren() {
        kalender = Kalender_Steuerung.KALENDER;
        start = Calendar.getInstance();
        ende = Calendar.getInstance();
        farbe = R.color.blaugrau;

        datumAuswaehlerDialogFragment = new Fragment_DatumAuswaehlen();
        fragmentZeitAuswaehlenFragment = new Fragment_ZeitAuswaehlen();
    }

    @Override
    protected Boolean terminDatenAufFehlerUeberpruefen() {
        boolean fehlerVorhanden;

        if (ganztaegig) {
            fehlerVorhanden = false;    // Wenn ganztägig = kein Fehler in den Daten kann vorhanden sein

        } else {
            //Start muss vor Ende sein
            long zeitInMiliStart = start.getTimeInMillis();
            long zeitInMiliEnde = ende.getTimeInMillis();

            if (zeitInMiliStart > zeitInMiliEnde) {
                fehlerVorhanden = true; // Fehler ist vorhanden
                Toast toast = Toast.makeText(dieGui, R.string.toastDatenFalsch, Toast.LENGTH_SHORT);
                toast.show();   // Fehlermeldung anzeigen
            } else {
                fehlerVorhanden = false;    // Kein Fehler vorhanden
            }
        }
        return fehlerVorhanden; // ansonsten = Fehler vorhanden
    }

    @Override
    protected Boolean terminDatenVollstaendigUeberpruefen() {
        boolean titelFehlt = false;
        if (!titelVorhanden) {

            //Wenn kein Titel vorhanden Titel -> unterschiedliche Fehlermeldungen:
            dieGui.getTitel().setHintTextColor(Color.parseColor("#FFB60003"));  //Rot färben

            Vibrator v = (Vibrator) dieGui.getSystemService(Context.VIBRATOR_SERVICE);  // Eine Instanz von Vibrator im momentanen Context
            v.vibrate(300); // vibriere für 300 ms

            Toast toast = Toast.makeText(dieGui, R.string.toastTitelFehlt, Toast.LENGTH_SHORT);
            toast.show();   // Fehlermeldung anzeigen

            titelFehlt = true;
        }
        return titelFehlt;
    }

    @Override
    protected void terminDatenSpeichern() {
        dieGui.getDataSource().open();  //Datenbank öffnen

        //Erstellungszeitpunkt nach iCal-Format festlegen
        String erstellung = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(Calendar.getInstance().getTime());

        if (ganztaegig) {
            //StartUhrzeit = 0:00 Uhr
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);

            //Ende = Start, da Ganztägig; Endzeit = 23:59 Uhr
            ende.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 23, 59);

            //Neuer Termin in Datenbank
            dieGui.getDataSource().erstelleTermin(dieGui.getTitel().getText().toString(), start, ende, "true", farbe, notizen, erstellung);

        } else {
            //Neuer Termin in Datenbank
            dieGui.getDataSource().erstelleTermin(dieGui.getTitel().getText().toString(), start, ende, "false", farbe, notizen, erstellung);
        }
        dieGui.getDataSource().close(); //Datenbank schließen

        Toast toast = Toast.makeText(dieGui, R.string.toastTerminGespeichert, Toast.LENGTH_SHORT);
        toast.show();   // Fehlermeldung anzeigen

        Log.d(logTag, "Neuer Termin gespeichert: " + dieGui.getTitel().getText());  // Fehlermeldung im Log anzeigen

        dieGui.oeffneActivityKalenderMonatsUebersicht();    // Nachdem Termin gespeichert -> Kalender wieder öffnen
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
