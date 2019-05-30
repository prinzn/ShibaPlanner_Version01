package prinzn.jana.majaplanerversion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Account_Registrieren extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Button btnErstellen;
    private TextView txtBereitsAccount;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/
    private void oeffneActifityStartseite() {
        Intent intent = new Intent(this, Startmenue.class);
        startActivity(intent);  //Startseiten- Activity wird geöffnet
    }
    private void oeffneActifityLogIn() {
        Intent intent = new Intent(this, Account_Einloggen.class);
        startActivity(intent);  //NeuerAccount- Activity wird geöffnet
    }

    private void initialisieren(){
        btnErstellen = findViewById(R.id.btn_accErstellen);
        txtBereitsAccount = findViewById(R.id.txt_accErstellen_ZuLogIn);
    }

    private void setztenDerOnClickListener(){
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
