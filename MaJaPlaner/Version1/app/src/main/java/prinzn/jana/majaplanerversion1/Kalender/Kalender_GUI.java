package prinzn.jana.majaplanerversion1.Kalender;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import prinzn.jana.majaplanerversion1.Freunde.Freunde;
import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Startmenue;
import prinzn.jana.majaplanerversion1.Terminuebersicht.Terminuebersicht;
import prinzn.jana.majaplanerversion1.Datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.Termin.Termin_Erstellung_GUI;
import prinzn.jana.majaplanerversion1.Termin.Termindetails;

public class Kalender_GUI extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    /*-------------------------Darstellung--------------------------------------------------------*/
    private TextView txtMonatAnzeige, txtMomentanesDatum;
    private ListView listViewTermineDesTages;

    private FloatingActionButton fabNeuerTermin;
    private GridView tabelleAktuellerMonat;
    private ImageButton btnHeutigerTag;
    private Button btnStart, btnTermine, btnFreunde;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private Kalender_Steuerung strg;
    private final String logTag = "LogTag_KalenderGui";
    private TermineDataSource dataSource;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setTxtMonatAnzeige(String pTxtMonatAnzeige) {
        this.txtMonatAnzeige.setText(pTxtMonatAnzeige);
    }

    public void setTxtMomentanesDatum(String pTxtMomentanesDatum) {
        this.txtMomentanesDatum.setText(pTxtMomentanesDatum);
    }

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public ListView getListViewTermineDesTages() {
        return listViewTermineDesTages;
    }

    public GridView getTabelleAktuellerMonat() {
        return tabelleAktuellerMonat;
    }

    public TermineDataSource getDataSource() {
        return dataSource;
    }

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        tabelleAktuellerMonat = findViewById(R.id.gridView_Kalender_Tabelle_AktuellerMonat);
        listViewTermineDesTages = findViewById(R.id.listView_Termine);

        txtMonatAnzeige = findViewById(R.id.txt_Kalender_Monat);
        btnHeutigerTag = findViewById(R.id.txt_Kalender_HeutigerTag);
        txtMomentanesDatum = findViewById(R.id.txt_Kalender_Momentanes_Datum);
        fabNeuerTermin = findViewById(R.id.fab_kalender_neuerTermin);
        btnStart = findViewById(R.id.btn_kalender_home);
        btnTermine = findViewById(R.id.btn_kalender_termine);
        btnFreunde = findViewById(R.id.btn_kalender_freunde);

        strg = new Kalender_Steuerung(this);
        dataSource = new TermineDataSource(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        btnHeutigerTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.btnHeutigerTagGeklickt();
            }
        });
        fabNeuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.btnNeuerTerminGeklickt();
            }
        });
        listViewTermineDesTages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ListView termineListView = findViewById(R.id.listView_Termine);
                strg.einTerminWurdeGeklickt(position, termineListView);
            }
        });

        tabelleAktuellerMonat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                strg.einTagInDerTabelleDesAktuellenMonatsGeklickt(view, position);
            }
        });
        tabelleAktuellerMonat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                strg.einTagInDerTabelleDesAktuellenMonatsLangGeklickt(view, position, id);
                return true;
            }
        });

        tabelleAktuellerMonat.setOnTouchListener(new Listener_Wischen(this) {
            @Override
            public Boolean onSwipeRight() {
                strg.btnZuvorGeklickt();
                return true;
            }

            @Override
            public Boolean onSwipeLeft() {
                strg.btnWeiterGeklickt();
                return true;
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityStartbildschirm();
            }
        });

        btnTermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityTerminUebersicht();
            }
        });

        btnFreunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityFreunde();
            }
        });

        txtMonatAnzeige.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                strg.zuDatumSpringen();
                return false;
            }
        });
    }

    /*-------------------------oeffnen anderer Activities - Methoden------------------------------*/
    public void oeffneActifityNeuerTermin() {
        Intent intent = new Intent(this, Termin_Erstellung_GUI.class);
        startActivity(intent);
    }

    private void oeffneActifityFreunde() {
        Intent intent = new Intent(this, Freunde.class);
        startActivity(intent);  //Freunde- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    public void oeffneActifityStartbildschirm() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    public void oeffneActifityTerminDetails(long pTerminId) {
        Intent intent = new Intent(this, Termindetails.class);
        intent.putExtra("TerminId", pTerminId);
        startActivity(intent);
    }

    public void oeffneActifityTerminUebersicht() {
        Intent intent = new Intent(this, Terminuebersicht.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Kalender_Steuerung.KALENDER.set(year, month, dayOfMonth);
        strg.aktualisiereKalender();
    }

    @Override
    protected void onResume() {
        //Wird die Activity aus der Pause wieder geöffnet, führt sie folgendes aus:
        super.onResume();
        dataSource.open();
        strg.aktualisiereKalender();
        strg.listViewClear();


        //Wurde die Activity in einem anderen Monat als dem aktuellen verlassen, wird der Kalender mit dem zuletzt geöffneten
        //Monat und Jahr gesetzt -> Info durch übergebenes Intent
        try {
            try {
                Kalender_Steuerung.KALENDER.set(Calendar.MONTH, Integer.parseInt(getIntent().getExtras().getString(Kalender_Steuerung.LETZTER_MONAT_UEBERGABE)));
                Kalender_Steuerung.KALENDER.set(Calendar.YEAR, Integer.parseInt(getIntent().getExtras().getString(Kalender_Steuerung.LETZTES_JAHR_UEBERGABE)));
                strg.aktualisiereKalender();
            } catch (NumberFormatException s) {
                Log.e(logTag, "Error: " + s.getMessage());
            }
        } catch (NullPointerException e) {
            Log.e(logTag, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        //Wird die Activity pausiert, führt sie folgendes aus:
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onBackPressed() {
        finish();
        oeffneActifityStartbildschirm();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_gui);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet

        dataSource.open();  //Datenbank geöffnet
        setztenDerOnClickListener();
        strg.aktualisiereKalender();     //der Monat wird mit den momentanen Daten des Kalenders dargestellt

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
