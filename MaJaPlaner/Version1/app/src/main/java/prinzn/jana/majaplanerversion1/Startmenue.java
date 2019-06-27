package prinzn.jana.majaplanerversion1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import prinzn.jana.majaplanerversion1.Account.Account_Einloggen;
import prinzn.jana.majaplanerversion1.Freunde.Freunde;
import prinzn.jana.majaplanerversion1.Terminuebersicht.Terminuebersicht;
import prinzn.jana.majaplanerversion1.Kalender.Kalender_GUI;

public class Startmenue extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private ImageButton btnZuFreunden, btnZumKalender, btnZuAlleTermine, btnAccount;
    private TextView txtDatum, txtBegruessung1, txtBegruessung2;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    //Je nach Zeitzone/ Land/... wird das Format angepasst
    private DateFormat datumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    private Calendar calHeute;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnZumKalender = findViewById(R.id.btn_Startbildschirm_ZuKalender);
        btnZuFreunden = findViewById(R.id.btn_Startbildschirm_ZuFreunden);
        btnAccount = findViewById(R.id.empty);
        txtDatum = findViewById(R.id.txt_Startbildschirm_Datum);
        txtBegruessung2 = findViewById(R.id.txt_Startbildschirm_Begrueßung_2);
        txtBegruessung1 = findViewById(R.id.txt_Startbildschirm_Begrueßung_1);
        btnZuAlleTermine = findViewById(R.id.btn_Startbildschirm_ZuAlleTermine);

        calHeute = Calendar.getInstance(); //aktuelle Kalenderinstanz
    }

    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        btnZuFreunden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZuFreunden.setEnabled(false);   //Button soll nicht mehrfach gecklickt werden können
                oeffneActifityFreunde();
            }
        });
        btnZumKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZumKalender.setEnabled(false);   //Button soll nicht mehrfach gecklickt werden können
                oeffneActifityKalender();
            }
        });

        btnZuAlleTermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZuAlleTermine.setEnabled(false);   //Button soll nicht mehrfach gecklickt werden können
                oeffneActifityAlleTermine();
            }
        });
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAccount.setEnabled(false);   //Button soll nicht mehrfach gecklickt werden können
                oeffneActifityEinloggen();
            }
        });
    }

    private void begruessungEinstellen() {
        //Verschiedene Begrüßungen je nach Uhrzeit
        int stunde = calHeute.get(Calendar.HOUR_OF_DAY);
        if (stunde >= 4 && stunde <= 11) {
            txtBegruessung1.setText(R.string.guten);
            txtBegruessung2.setText(R.string.morgen);

        } else if (stunde >= 12 && stunde <= 14) {
            txtBegruessung1.setText(R.string.schoenen);
            txtBegruessung2.setText(R.string.mittag);

        } else if (stunde >= 15 && stunde <= 17) {
            txtBegruessung1.setText("");
            txtBegruessung2.setText(R.string.hallo);

        } else if (stunde >= 18 && stunde <= 21) {
            txtBegruessung1.setText(R.string.guten);
            txtBegruessung2.setText(R.string.abend);

        } else if (stunde >= 22 && stunde <= 24) {
            txtBegruessung1.setText(R.string.gute);
            txtBegruessung2.setText(R.string.nacht);

        } else if (stunde >= 0 && stunde <= 3) {
            txtBegruessung1.setText(R.string.gute);
            txtBegruessung2.setText(R.string.nacht);
        }
    }

    /*-------------------------oeffnen anderer Activities - Methoden------------------------------*/
    private void oeffneActifityKalender() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        startActivity(intent);  //Kalender- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

    private void oeffneActifityAlleTermine() {
        Intent intent = new Intent(this, Terminuebersicht.class);
        startActivity(intent);  //Alle Termine- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

    private void oeffneActifityEinloggen() {
        Intent intent = new Intent(this, Account_Einloggen.class);
        startActivity(intent);  //Einloggen- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

    private void oeffneActifityFreunde() {
        Intent intent = new Intent(this, Freunde.class);
        startActivity(intent);  //Freunde- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    public void onBackPressed() {
        finish();
    } //App wird beendet, wenn die Rücktaste benutzt wurde

    @Override
    public void finish() {
        super.finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmenue);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        setztenDerOnClickListener();
        txtDatum.setText(datumFormat.format(calHeute.getTime()));
        begruessungEinstellen();

        btnZumKalender.setEnabled(true);
        btnZuAlleTermine.setEnabled(true);
        btnAccount.setEnabled(true);
        btnZuFreunden.setEnabled(true);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
