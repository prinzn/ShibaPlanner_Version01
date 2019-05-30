package prinzn.jana.majaplanerversion1.termin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.kalender.Kalender_GUI;
import prinzn.jana.majaplanerversion1.kalender.Kalender_Steuerung;

public class Termin_Erstellung_GUI extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private EditText titel, txtNotizen;

    private TextView startDatum, startZeit, enddatum, endzeit, txtEnde, txtStart, txtGanztaegig;
    private Switch ganztaegigJaNein;
    private Button speichern, oeffneColorPicker;
    private ImageButton abbruch;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private Termin_Erstellung_Steuerung strg;
    private TermineDataSource dataSource;
    private final String logTag = "LogTag_TerminErstellungGui";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------get-Methoden-------------------------------------------------------*/
    public EditText getTitel() {
        return titel;
    }

    public TextView getStartDatum() {
        return startDatum;
    }

    public TextView getStartZeit() {
        return startZeit;
    }

    public TextView getEndDatum() {
        return enddatum;
    }

    public TextView getEndzeit() {
        return endzeit;
    }

    public TermineDataSource getDataSource() {
        return dataSource;
    }

    public Button getOeffneColorPicker() {
        return oeffneColorPicker;
    }

    /*-------------------------public Methoden----------------------------------------------------*/
    public void oeffneActivityKalenderMonatsUebersicht() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        //Info über den zuletzt geöffneten Monat(& Jahr) vor öffnen der Terminerstellung inn Intent für KalenderGUI
        intent.putExtra(Kalender_Steuerung.LETZTER_MONAT_UEBERGABE, "" + strg.start.get(Calendar.MONTH));
        intent.putExtra(Kalender_Steuerung.LETZTES_JAHR_UEBERGABE, "" + strg.start.get(Calendar.YEAR));

        startActivity(intent); //KalenderGUI wieder öffnen
        finish();   // Activity soll beendet werden
    }

    public void lasseUnwichtigesVerschwinden() {
        // Wenn der Termin ganztägig ist sollen dementspechende Daten gezeigt werden
        startZeit.setVisibility(View.INVISIBLE);
        endzeit.setVisibility(View.INVISIBLE);
        enddatum.setVisibility(View.INVISIBLE);
        txtEnde.setVisibility(View.INVISIBLE);
        txtStart.setText(R.string.am);
        txtGanztaegig.setText(R.string.ganztaegig);
        txtGanztaegig.setGravity(Gravity.END);
    }

    public void allesSichtbarMachen() {
        // Wenn der Termin nicht ganztägig ist sollen dementspechende Daten gezeigt werden
        startZeit.setVisibility(View.VISIBLE);
        endzeit.setVisibility(View.VISIBLE);
        enddatum.setVisibility(View.VISIBLE);
        txtEnde.setVisibility(View.VISIBLE);
        txtStart.setText(R.string.start);
        txtGanztaegig.setText(R.string.zeit);
        txtGanztaegig.setGravity(Gravity.START);
    }

    /*-------------------------private Methoden---------------------------------------------------*/

    private void initialisieren() {
        titel = findViewById(R.id.txt_Terminerstellung_Eingabe_Titel);
        ganztaegigJaNein = findViewById(R.id.switch_Terminerstellung_Eingabe_JaNein);
        startDatum = findViewById(R.id.txt_Terminerstellung_Eingabe_Datum_Start);
        startZeit = findViewById(R.id.txt_Terminerstellung_Eingabe_Zeit_Start);
        enddatum = findViewById(R.id.txt_Terminerstellung_Eingabe_Datum_Ende);
        endzeit = findViewById(R.id.txt_Terminerstellung_Eingabe_Zeit_Ende);
        speichern = findViewById(R.id.btn_Terminerstellung_Speichern);
        abbruch = findViewById(R.id.btn_Terminerstellung_Abbrechen);
        txtEnde = findViewById(R.id.txt_Terminerstellung_Beschriftung_Ende);
        txtStart = findViewById(R.id.txt_Terminerstellung_Beschriftung_Start);
        txtGanztaegig = findViewById(R.id.txt_Terminerstellung_Beschriftung_Ganztaegig);
        oeffneColorPicker = findViewById(R.id.btn_Terminerstellung_oeffne_ColorPicker);
        txtNotizen = findViewById(R.id.txt_Terminerstellung_Notizen);

        /*-------------------------Andere Variablen---------------------------------------------------*/
        strg = new Termin_Erstellung_Steuerung(this);
        dataSource = new TermineDataSource(this);
    }


    private void setztenDerOnClickListener() {
        abbruch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.abbruchGeklickt();
            }
        });
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.speichernGeklickt();
            }
        });
        oeffneColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.oeffneColorPicker();
            }
        });
        startDatum.setOnClickListener(this);
        enddatum.setOnClickListener(this);
        startZeit.setOnClickListener(this);
        endzeit.setOnClickListener(this);
        ganztaegigJaNein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.switchGanztaegigGeklickt();
            }
        });
        titel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                strg.titelEingegeben(s);
            }
        });

        txtNotizen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strg.notizenEingegeben(s);
            }
        });
    }

    /*-------------------------Überschriebene Methode---------------------------------------------*/
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        try {
            strg.datumZeitAngabeGeklickt(v);    // Jeweiligen Dialog öffnen
        } catch (IllegalStateException e) {
            Log.e(logTag, "Error bei Datum/Zeitangabe geklickt: " + e.getMessage());
        }

    }

    @Override
    public void onDateSet(DatePicker view, int jahr, int monat, int tag) {
        strg.datumGeklickt(jahr, monat, tag);   // Ein Datum wurde im Dialog ausgewählt
    }

    @Override
    public void onTimeSet(TimePicker view, int stunde, int minute) {
        strg.zeitGeklickt(stunde, minute);  // Eine Zeit wurde im Dialog ausgewählt
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_erstellung_gui);

        initialisieren();
        setztenDerOnClickListener();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
