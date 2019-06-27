package prinzn.jana.majaplanerversion1.Termin;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.Kalender.Kalender_GUI;
import prinzn.jana.majaplanerversion1.Kalender.Kalender_Steuerung;

public class Termindetails extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    /*-------------------------Darstellung--------------------------------------------------------*/
    private Button btnBearbeiten, btnFarbe;
    private ImageButton btnLoeschen, btnZurueck;
    private TextView txtGanztaegig, txtStartdatum, txtEnddatum, txtStartzeit, txtEndzeit, txtTitel, txtStartBeschriftung, txtEndeBeschriftung, txtNotizen;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    //Je nach Zeitzone/ Land/... wird das Format angepasst
    protected DateFormat datumFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
    protected DateFormat zeitFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

    private Termin termin;
    private TermineDataSource dataSource;
    private Long id;
    private String logTag = "LogTag_Termindetails";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnLoeschen = findViewById(R.id.btn_Ein_Termin_Loeschen);
        btnBearbeiten = findViewById(R.id.btn_Ein_Termin_Bearbeiten);
        txtGanztaegig = findViewById(R.id.txt_Ein_Termin_Ganztaegig);
        btnZurueck = findViewById(R.id.btn_Terminerstellung_Abbrechen);

        txtTitel = findViewById(R.id.txt_Ein_Termin_Titel);

        txtStartBeschriftung = findViewById(R.id.txt_Ein_Termin_Beschriftung_Start);
        txtStartdatum = findViewById(R.id.txt_Ein_Termin_Datum_Start);
        txtStartzeit = findViewById(R.id.txt_Ein_Termin_Zeit_Start);

        txtEndeBeschriftung = findViewById(R.id.txt_Ein_Termin_Beschriftung_Ende);
        txtEnddatum = findViewById(R.id.txt_Ein_Termin_Datum_Ende);
        txtEndzeit = findViewById(R.id.txt_Ein_Termin_Zeit_Ende);

        btnFarbe = findViewById(R.id.btn_Ein_Termin_farbe);
        txtNotizen = findViewById(R.id.txt_Ein_Termin_Notizen);

        dataSource = new TermineDataSource(this);

        dataSource.open();   // Verbindung zur Datenbank herstellen
        List<Termin> terminListe = dataSource.getAlleTermine();     // Alle Termine bekommen
        for (int i = 0; i < terminListe.size(); i++) {  // alle durchgehen bis ...
            if (terminListe.get(i).getId() == id) { // ... die ID gleich ist = Termin gefunden
                termin = terminListe.get(i);    // diesen Termin zwischenspeichern
            }
        }
        dataSource.close();   // Verbindung zur Datenbank beenden
    }

    private void setztenDerTerminInfos() {
        txtTitel.setText(termin.getTitel());
        txtStartzeit.setText(zeitFormat.format(termin.getStart().getTime()));
        txtStartdatum.setText(datumFormat.format(termin.getStart().getTime()));
        btnFarbe.setBackgroundTintList(ColorStateList.valueOf(termin.getFarbe()));   // Farbe anzeigen
        txtNotizen.setText(termin.getNotizen());

        if (termin.getGanztaegig()) {
            txtGanztaegig.setText(R.string.ganztaegig);
            txtGanztaegig.setGravity(Gravity.END);

            txtStartBeschriftung.setText(R.string.am);

            txtEndzeit.setVisibility(View.INVISIBLE);
            txtEnddatum.setVisibility(View.INVISIBLE);
            txtStartzeit.setVisibility(View.INVISIBLE);
            txtEndeBeschriftung.setVisibility(View.INVISIBLE);

        } else if (!termin.getGanztaegig()) {
            txtGanztaegig.setText(R.string.zeit);
            txtGanztaegig.setGravity(Gravity.START);

            txtEndzeit.setText(zeitFormat.format(termin.getEnde().getTime()));
            txtEnddatum.setText(datumFormat.format(termin.getEnde().getTime()));

            txtStartBeschriftung.setText(R.string.start);
            txtStartzeit.setVisibility(View.VISIBLE);
            txtEndzeit.setVisibility(View.VISIBLE);
            txtEnddatum.setVisibility(View.VISIBLE);
            txtEndeBeschriftung.setVisibility(View.VISIBLE);
        }
    }

    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        btnLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.open();   // Verbindung zur Datenbank herstellen
                dataSource.loescheTermin(termin);   // Termin mit der richtigen ID löschen
                dataSource.close();   // Verbindung zur Datenbank beenden
                Log.d(logTag, termin.getTitel() + " wurde gelöscht.");  // Fehlermeldung anzeigen
                finish();   // Activity beenden

            }
        });

        btnZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityKalender();
            }
        });

        btnBearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityBearbeiten();
            }
        });
    }

    /*-------------------------oeffnen anderer Activities - Methoden------------------------------*/
    private void oeffneActifityKalender() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        intent.putExtra(Kalender_Steuerung.LETZTER_MONAT_UEBERGABE, "" + termin.getStart().get(Calendar.MONTH));
        intent.putExtra(Kalender_Steuerung.LETZTES_JAHR_UEBERGABE, "" + termin.getStart().get(Calendar.YEAR));
        startActivity(intent);
        finish();   // Activity beenden
    }

    private void oeffneActifityBearbeiten() {
        Intent intent = new Intent(this, Termin_Bearbeitung_GUI.class);
        intent.putExtra("TerminId", id);
        startActivity(intent);
        finish();   // Activity beenden
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    public void onBackPressed() {
        finish();
        oeffneActifityKalender();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ein_termin);

        id = getIntent().getExtras().getLong("TerminId");   // Id des Termins aus Intent bekommen

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        setztenDerOnClickListener();
        setztenDerTerminInfos();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
