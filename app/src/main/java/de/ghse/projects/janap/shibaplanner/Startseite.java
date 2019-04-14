package de.ghse.projects.janap.shibaplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Startseite extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    /*-------------------------Darstellung--------------------------------------------------------*/
    private Button btnZuEinstellungen, btnZumKalender;
    private TextView datum, begruessung;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Calendar heute;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/
    public void oeffneActifityKalender() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        startActivity(intent);
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnZumKalender = findViewById(R.id.btn_Startbildschirm_ZuKalender);
        btnZuEinstellungen = findViewById(R.id.btn_Startbildschirm_ZuEinstellungen);
        datum = findViewById(R.id.txt_Startbildschirm_Datum);
        begruessung = findViewById(R.id.txt_Startbildschirm_Begrueßung);

        heute = Calendar.getInstance();
    }

    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        /*btnZuEinstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        btnZumKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZumKalender.setEnabled(false);
                oeffneActifityKalender();
            }
        });
    }

    private void begruessungEinstellen(){
        int stunde = heute.get(Calendar.HOUR_OF_DAY);
        if (stunde >= 0 && stunde <= 11){
            begruessung.setText(R.string.gutenMorgen);

        }else if (stunde >= 12 && stunde <= 17){
            begruessung.setText(R.string.gutenMittag);

        }else if (stunde >= 18 && stunde <= 24){
            begruessung.setText(R.string.gutenAbend);

        }
    }

    /*-------------------------andere Methoden----------------------------------------------------*/

    /*-------------------------Override Methoden--------------------------------------------------*/
    @Override
    public void onBackPressed() {
        finish();
    } //App wird beendet, wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung der Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startbildschirm);


        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        setztenDerOnClickListener();


        datum.setText(datumFormat.format(heute.getTime()));

        begruessungEinstellen();
        btnZumKalender.setEnabled(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
