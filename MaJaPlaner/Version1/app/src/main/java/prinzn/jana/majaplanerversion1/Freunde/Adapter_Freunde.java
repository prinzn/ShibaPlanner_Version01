package prinzn.jana.majaplanerversion1.Freunde;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import prinzn.jana.majaplanerversion1.Account.User;
import prinzn.jana.majaplanerversion1.R;
/**
 * Diese Klasse ist ein Adapter für die Freundesanzeige
 */
public class Adapter_Freunde extends ArrayAdapter<User> {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private int letzePosition;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Adapter_Freunde(Context pContext, List<User> pFreunde) {
        super(pContext, R.layout.freunde, pFreunde);
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
    public View getView(int position, View view, ViewGroup parent) {

        //Wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.freunde, parent, false);
        }

        if (letzePosition != position) {

            User user = getItem(position);
            final User temp = user; // Um zu loeschen

            TextView txtName = view.findViewById(R.id.txt_freunde_name);
            txtName.setText(user.getName()); // Name des Freundes anzeigen

            ImageButton btnEntfernen = view.findViewById(R.id.btn_freunde_ablehnen);
            btnEntfernen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    remove(temp);
                }
            });

            letzePosition = position;
        }

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
