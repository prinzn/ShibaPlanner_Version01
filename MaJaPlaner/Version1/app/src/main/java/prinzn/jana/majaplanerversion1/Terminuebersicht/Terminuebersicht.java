package prinzn.jana.majaplanerversion1.Terminuebersicht;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import prinzn.jana.majaplanerversion1.Freunde.Freunde;
import prinzn.jana.majaplanerversion1.Knuten.MaJa_LinkedList;
import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Startmenue;
import prinzn.jana.majaplanerversion1.Datenbank.TermineDataSource;
import prinzn.jana.majaplanerversion1.Kalender.Kalender_GUI;
import prinzn.jana.majaplanerversion1.Termin.Termin;
import prinzn.jana.majaplanerversion1.Termin.Termin_Erstellung_GUI;
import prinzn.jana.majaplanerversion1.Termin.Termindetails;

public class Terminuebersicht extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private ListView listAlleTermine;
    private Button btnStart, btnKalender, btnFreunde;
    private ImageButton btnSuche;
    private EditText txtSucheEingabe;
    private FloatingActionButton fabNeuerTermin;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private TermineDataSource dataSource;
    private Adapter_AlleTermine termineAdapter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------private Methoden---------------------------------------------------*/
    private void oeffneActifityStartseite() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);  //Startseiten- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    private void oeffneActifityTerminDetails(long pTerminId) {
        Intent intent = new Intent(this, Termindetails.class);
        intent.putExtra("TerminId", pTerminId);
        startActivity(intent);
    }

    private void oeffneActifityNeuerTermin() {
        Intent intent = new Intent(this, Termin_Erstellung_GUI.class);
        startActivity(intent);
    }

    private void oeffneActifityKalender() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    private void oeffneActifityFreunde() {
        Intent intent = new Intent(this, Freunde.class);
        startActivity(intent);  //Freunde- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    private void initialisieren() {
        listAlleTermine = findViewById(R.id.listView_alleTermine);
        dataSource = new TermineDataSource(this);
        btnStart = findViewById(R.id.btn_terminuebersicht_home);
        btnKalender = findViewById(R.id.btn_terminuebersicht_kalender);
        fabNeuerTermin = findViewById(R.id.fab_terminuebersicht_neuerTermin);
        btnSuche = findViewById(R.id.btn_terminuebersicht_suche);
        txtSucheEingabe = findViewById(R.id.btn_terminuebersicht_sucheEingabe);
        btnFreunde = findViewById(R.id.btn_terminuebersicht_freunde);
    }

    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        listAlleTermine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                einTerminWurdeGeklickt(position, listAlleTermine);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityStartseite();
            }
        });

        btnKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityKalender();
            }
        });

        btnFreunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityFreunde();
            }
        });

        fabNeuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityNeuerTermin();
            }
        });

        btnSuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSucheEingabe.getText().length() > 0){
                    String sucheKey = txtSucheEingabe.getText().toString();
                    zeigeTermineSuche(sucheKey);
                } else {
                    btnSuche.performLongClick();
                }
            }
        });

        btnSuche.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (txtSucheEingabe.getText().length() > 0){
                    txtSucheEingabe.setText("");
                    listViewClear();
                    zeigeAlleTermine();
                    return true;
                }
                return false;
            }
        });

        txtSucheEingabe.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    btnSuche.performClick();    //Bei Enter drücken soll gesucht werden
                    return true;
                }

                return false;
            }
        });
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

    private void zeigeAlleTermine() {
        List<Termin> alleTermine = dataSource.getAlleTermine();

        listViewClear();
        //Überprüfung ob es Termine gibt
        if (!alleTermine.isEmpty()) {
            MaJa_LinkedList llTermine = new MaJa_LinkedList();
            llTermine.fuegeListeHinzu(alleTermine);
            termineAdapter = new Adapter_AlleTermine(this, llTermine.getGeordneteListe());
            listAlleTermine.setAdapter(termineAdapter);
            termineAdapter.notifyDataSetChanged();
        }

    }

    private void zeigeTermineSuche(String pSucheKey) {
        List<Termin> alleTermine = dataSource.getGesuchteTermine(pSucheKey);

        listViewClear();
        //Überprüfung ob es Termine gibt
        if (!alleTermine.isEmpty()) {
            MaJa_LinkedList llTermine = new MaJa_LinkedList();
            llTermine.fuegeListeHinzu(alleTermine);
            termineAdapter = new Adapter_AlleTermine(this, llTermine.getGeordneteListe());
            listAlleTermine.setAdapter(termineAdapter);
            termineAdapter.notifyDataSetChanged();
        } else {
            Toast toast = Toast.makeText(this, R.string.toastKeineTermineGefunden, Toast.LENGTH_SHORT);
            toast.show();   // Fehlermeldung anzeigen
        }

    }

    /*-------------------------Override Methoden--------------------------------------------------*/
    @Override
    protected void onPause() {
        //Wird die Activity pausiert, führt sie folgendes aus:
        super.onPause();
        dataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        listViewClear();
        zeigeAlleTermine();
    }

    @Override
    public void onBackPressed() {
        finish();
        oeffneActifityStartseite();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
    }

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
        zeigeAlleTermine();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
