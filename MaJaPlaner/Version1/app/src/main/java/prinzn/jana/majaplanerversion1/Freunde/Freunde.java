package prinzn.jana.majaplanerversion1.Freunde;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import prinzn.jana.majaplanerversion1.Account.User;
import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Startmenue;
import prinzn.jana.majaplanerversion1.Terminuebersicht.Terminuebersicht;
import prinzn.jana.majaplanerversion1.Kalender.Kalender_GUI;

public class Freunde extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Button btnZumKalender, btnZuAlleTermine, btnHome;
    private ListView listViewAnfragen, listViewAlleFreunde;
    private Adapter_FreundeAnfragen freundeAnfragenAdapter;
    private Adapter_Freunde freundeAdapter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnZumKalender = findViewById(R.id.btn_freunde_kalender);
        btnZuAlleTermine = findViewById(R.id.btn_freunde_termine);
        btnHome = findViewById(R.id.btn_freunde_home);

        listViewAnfragen = findViewById(R.id.listView_alleAnfragen);
        listViewAlleFreunde = findViewById(R.id.listView_alleFreunde);
    }

    private void setztenDerOnClickListener() {
        btnZumKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityKalender();
            }
        });

        btnZuAlleTermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityAlleTermine();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityHome();
            }
        });

    }

    private void anzeigenDerAnfragen() {
        List<User> alleAnfragen = new ArrayList<>();    // Liste der Anfragen
        alleAnfragen.add(new User("Luisa"));    // Füllen ... musst du ändern
        alleAnfragen.add(new User("James"));

        listViewFreundeAnfragenClear(); // leert die ListView zuerst

        //Überprüfung ob es Anfragen gibt
        if (!alleAnfragen.isEmpty()) {
            freundeAnfragenAdapter = new Adapter_FreundeAnfragen(this, alleAnfragen);    // Adapter für die Anzeige
            listViewAnfragen.setAdapter(freundeAnfragenAdapter);
            freundeAnfragenAdapter.notifyDataSetChanged();
        }
    }

    private void anzeigenDerFreunde() {
        List<User> alleFreunde = new ArrayList<>(); // Liste der Anfragen
        alleFreunde.add(new User("Peter")); // Füllen ... musst du ändern
        alleFreunde.add(new User("Stevan"));
        alleFreunde.add(new User("Andrea"));
        alleFreunde.add(new User("Silvia"));
        alleFreunde.add(new User("Luisa"));
        alleFreunde.add(new User("Tiffany"));
        alleFreunde.add(new User("Jessica"));


        listViewFreundeClear(); // leert die ListView zuerst

        //Überprüfung ob es Freunde gibt
        if (!alleFreunde.isEmpty()) {
            freundeAdapter = new Adapter_Freunde(this, alleFreunde);    // Adapter für die Anzeige
            listViewAlleFreunde.setAdapter(freundeAdapter);
            freundeAdapter.notifyDataSetChanged();
        }
    }

    private void listViewFreundeAnfragenClear() {
        if (freundeAnfragenAdapter != null) {
            freundeAnfragenAdapter.clear();
            freundeAnfragenAdapter.notifyDataSetChanged();
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

    private void oeffneActifityHome() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);  //Start- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

    private void listViewFreundeClear() {
        if (freundeAdapter != null) {
            freundeAdapter.clear();
            freundeAdapter.notifyDataSetChanged();
        }
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    protected void onResume() {
        super.onResume();
        listViewFreundeClear();
        listViewFreundeAnfragenClear();
        anzeigenDerFreunde();
        anzeigenDerAnfragen();
    }

    @Override
    public void onBackPressed() {
        finish();
        oeffneActifityHome();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freunde);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        setztenDerOnClickListener();

        anzeigenDerAnfragen();
        anzeigenDerFreunde();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
