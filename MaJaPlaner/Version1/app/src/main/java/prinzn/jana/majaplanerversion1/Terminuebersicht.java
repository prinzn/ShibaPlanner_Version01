package prinzn.jana.majaplanerversion1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.kalender.Kalender_GUI;
import prinzn.jana.majaplanerversion1.kalender.Kalender_Steuerung;
import prinzn.jana.majaplanerversion1.termin.Termin;
import prinzn.jana.majaplanerversion1.termin.Termindetails;

public class Terminuebersicht extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private ListView listAlleTermine;
    private BottomNavigationView navBottom;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private TermineDataSource dataSource;
    private Adapter_AlleTermine termineAdapter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------private Methoden---------------------------------------------------*/
    private void oeffneActifityStartseite() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);  //Startseiten- Activity wird geöffnet
    }

    private void oeffneActifityTerminDetails(long pTerminId) {
        Intent intent = new Intent(this, Termindetails.class);
        intent.putExtra("TerminId", pTerminId);
        startActivity(intent);
    }

    public void oeffneActifityKalender() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        intent.putExtra(Kalender_Steuerung.LETZTER_MONAT_UEBERGABE, "" + Kalender_Steuerung.KALENDER.get(Calendar.MONTH));
        intent.putExtra(Kalender_Steuerung.LETZTES_JAHR_UEBERGABE, "" + Kalender_Steuerung.KALENDER.get(Calendar.YEAR));
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_zumStartbildschirm:
                    oeffneActifityStartseite();
                    break;
                case R.id.navigation_zumKalender:
                    oeffneActifityKalender();
                    break;
            }
            return true;
        }
    };

    private void initialisieren() {
        listAlleTermine = findViewById(R.id.listView_alleTermine);
        dataSource = new TermineDataSource(this);
        navBottom = findViewById(R.id.nav_terminuebersicht);
    }

    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        listAlleTermine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView termineListView = findViewById(R.id.listView_alleTermine);
                einTerminWurdeGeklickt(position, termineListView);
            }
        });

        navBottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void listViewClear(){
        if (termineAdapter != null){
            termineAdapter.clear();
            termineAdapter.notifyDataSetChanged();
        }
    }

    private void einTerminWurdeGeklickt(int pPosition, ListView pTermineListView) {
        Termin termin = (Termin) pTermineListView.getItemAtPosition(pPosition);
        oeffneActifityTerminDetails(termin.getId());
    }

    private void zeigeTermine() {
        List<Termin> allesTermine = dataSource.getAlleTermine();

        listViewClear();
        //Überprüfung ob es Termine gibt
        if (!allesTermine.isEmpty()) {
            termineAdapter = new Adapter_AlleTermine(this, allesTermine);
            listAlleTermine.setAdapter(termineAdapter);
            termineAdapter.notifyDataSetChanged();
        }

    }

    /*-------------------------Override Methoden--------------------------------------------------*/
    @Override
    protected void onPause() {
        //Wird die Activity pausiert, führt sie folgendes aus:
        super.onPause();
        dataSource.close();
        listViewClear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        listViewClear();
        zeigeTermine();
    }

    @Override
    public void onBackPressed() {
        oeffneActifityStartseite();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung der Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminuebersicht);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        dataSource.open();  //Datenbank geöffnet
        setztenDerOnClickListener();
        listViewClear();
        zeigeTermine();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
