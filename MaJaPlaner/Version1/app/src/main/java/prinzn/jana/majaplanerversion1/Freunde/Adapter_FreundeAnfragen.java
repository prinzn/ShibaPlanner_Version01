package prinzn.jana.majaplanerversion1.Freunde;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import prinzn.jana.majaplanerversion1.Account.User;
import prinzn.jana.majaplanerversion1.R;

public class Adapter_FreundeAnfragen extends ArrayAdapter<User> {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private int letzePosition;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Adapter_FreundeAnfragen(Context pContext, List<User> pFreundesAnfragen) {
        super(pContext, R.layout.freunde_anfrage, pFreundesAnfragen);
        inflater = LayoutInflater.from(pContext);
        letzePosition = -5; // Fehlerbehebung
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        //Wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.freunde_anfrage, parent, false);
        }

        if (letzePosition != position) {

            User user = getItem(position);
            final User temp = user; // Um zu loeschen

            TextView txtName = view.findViewById(R.id.txt_freundeAnfrage_name);
            txtName.setText(user.getName()); // Name des Anfragers anzeigen

            ImageButton btnAblehnen = view.findViewById(R.id.btn_freundeAnfrage_ablehnen);
            btnAblehnen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    remove(temp);
                }
            });

            ImageButton btnAnnehmen = view.findViewById(R.id.btn_freundeAnfrage_annehmen);
            btnAnnehmen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Freund hinzufügen
                    Toast toast = Toast.makeText(getContext(), "Anfrage angenommen", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            letzePosition = position;
        }

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
