package prinzn.jana.majaplanerversion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Account_Einloggen extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Button btnEinloggen;
    private TextView txtNeuerAccount;

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
    private void oeffneActifityNeuerAccount() {
        Intent intent = new Intent(this, Account_Registrieren.class);
        startActivity(intent);  //NeuerAccount- Activity wird geöffnet
    }

    private void initialisieren(){
        btnEinloggen = findViewById(R.id.btn_einloggen);
        txtNeuerAccount = findViewById(R.id.txt_einloggen_ZuaccountErstellen);
    }

    private void setztenDerOnClickListener(){
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
