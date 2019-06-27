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
 * Diese Klasse händelt das Einloggen des Benutzers
 */

public class Account_Einloggen extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Button btnEinloggen;
    private TextView txtNeuerAccount;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnEinloggen = findViewById(R.id.btn_einloggen);
        txtNeuerAccount = findViewById(R.id.txt_einloggen_ZuaccountErstellen);
    }

    private void setztenDerOnClickListener() {
        btnEinloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityStartseite();
            }
        });
        txtNeuerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityNeuerAccount();
            }
        });
    }

    /*-------------------------oeffnen anderer Activities - Methoden------------------------------*/
    private void oeffneActifityStartseite() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);  //Startseiten- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    private void oeffneActifityNeuerAccount() {
        Intent intent = new Intent(this, Account_Registrieren.class);
        startActivity(intent);  //NeuerAccount- Activity wird geöffnet
        overridePendingTransition(R.anim.slide_in_nach_rechts, R.anim.slide_aus_nach_links); // Neue Animation einstellen
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
        overridePendingTransition(R.anim.slide_in_nach_links, R.anim.slide_aus_nach_rechts); // Neue Animation einstellen
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_einloggen);
        initialisieren();
        setztenDerOnClickListener();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
