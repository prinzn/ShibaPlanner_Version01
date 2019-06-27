package prinzn.jana.majaplanerversion1.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import prinzn.jana.majaplanerversion1.R;
import prinzn.jana.majaplanerversion1.Startmenue;
/**
 * Diese Klasse händelt das Regestrieren des Benutzers
 */
public class Account_Registrieren extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Button btnErstellen;
    private TextView txtBereitsAccount;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnErstellen = findViewById(R.id.btn_accErstellen);
        txtBereitsAccount = findViewById(R.id.txt_accErstellen_ZuLogIn);
    }

    private void setztenDerOnClickListener() {
        btnErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityStartseite();
            }
        });
        txtBereitsAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityLogIn();
            }
        });
    }

    /*-------------------------oeffnen anderer Activities - Methoden------------------------------*/
    private void oeffneActifityStartseite() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);  //Startseiten- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    private void oeffneActifityLogIn() {
        Intent intent = new Intent(this, Account_Einloggen.class);
        startActivity(intent);  //NeuerAccount- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    public void onBackPressed() {
        finish();
        oeffneActifityStartseite();
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
        setContentView(R.layout.activity_account_registrieren);
        initialisieren();
        setztenDerOnClickListener();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
