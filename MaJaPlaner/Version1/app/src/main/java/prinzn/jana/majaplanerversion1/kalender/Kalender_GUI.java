package prinzn.jana.majaplanerversion1.kalender;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Startmenue;
import prinzn.jana.majaplanerversion1.Terminuebersicht;
import prinzn.jana.majaplanerversion1.datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.termin.Termin_Erstellung_GUI;
import prinzn.jana.majaplanerversion1.termin.Termindetails;

public class Kalender_GUI extends AppCompatActivity {
////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    /*-------------------------Darstellung--------------------------------------------------------*/
    private TextView txtMonatAnzeige, txtMomentanesDatum;
    private ListView listViewTermineDesTages;

    private FloatingActionButton fabNeuerTermin;
    private GridView tabelleAktuellerMonat;
    private ImageButton btnHeutigerTag;
    private BottomNavigationView navBottom;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private Kalender_Steuerung strg;
    private final String logTag = "LogTag_KalenderGui";
    private TermineDataSource dataSource;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

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

    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setTxtMonatAnzeige(String pTxtMonatAnzeige) {
        this.txtMonatAnzeige.setText(pTxtMonatAnzeige);
    }

    public void setTxtMomentanesDatum(String pTxtMomentanesDatum) {
        this.txtMomentanesDatum.setText(pTxtMomentanesDatum);
    }

    /*-------------------------public Methoden----------------------------------------------------*/
    public void oeffneActifityNeuerTermin() {
        Intent intent = new Intent(this, Termin_Erstellung_GUI.class);
        startActivity(intent);
    }

    public void oeffneActifityStartbildschirm() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);
    }

    public void oeffneActifityTerminDetails(long pTerminId) {
        Intent intent = new Intent(this, Termindetails.class);
        intent.putExtra("TerminId", pTerminId);
        startActivity(intent);
    }

    public void oeffneActifityTerminUebersicht() {
        Intent intent = new Intent(this, Terminuebersicht.class);
        startActivity(intent);
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        tabelleAktuellerMonat = findViewById(R.id.gridView_Kalender_Tabelle_AktuellerMonat);
        listViewTermineDesTages = findViewById(R.id.listView_Termine);

        txtMonatAnzeige = findViewById(R.id.txt_Kalender_Monat);
        btnHeutigerTag = findViewById(R.id.txt_Kalender_HeutigerTag);
        txtMomentanesDatum = findViewById(R.id.txt_Kalender_Momentanes_Datum);
        fabNeuerTermin = findViewById(R.id.fab_neuerTermin);
        navBottom = findViewById(R.id.nav_kalender);

        strg = new Kalender_Steuerung(this);
        dataSource = new TermineDataSource(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_zumStartbildschirm:
                    oeffneActifityStartbildschirm();
                    break;
                case R.id.navigation_zuTermineUebersicht:
                    oeffneActifityTerminUebersicht();
                    break;
            }
            return true;
        }
    };

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

        tabelleAktuellerMonat.setOnTouchListener(new Listener_Wischen(this){
            @Override
            public Boolean onSwipeRight(){
                strg.btnZuvorGeklickt();
                return true;
            }
            @Override
            public Boolean onSwipeLeft(){
                strg.btnWeiterGeklickt();
                return true;
            }
        });

        navBottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /*-------------------------andere Methoden----------------------------------------------------*/

    /*-------------------------Override Methoden--------------------------------------------------*/
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
        oeffneActifityStartbildschirm();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung der Activity
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
    //Ende der Klasse
}
