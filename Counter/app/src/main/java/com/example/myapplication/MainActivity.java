package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private  Calendar zsm = Calendar.getInstance();
    private Calendar heute = Calendar.getInstance();

    private int anzMonate = 0;
    private int anzTage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtZeit = findViewById(R.id.zeit);
        zsm.set(2018, Calendar.AUGUST, 25);

        Calendar temp = Calendar.getInstance();
        temp.set(2018, Calendar.AUGUST, 25);

        while (temp.before(heute)){
            temp.add(Calendar.DAY_OF_MONTH, 1);
            anzTage ++;
            if (temp.get(Calendar.DAY_OF_MONTH) == 25){
                anzMonate ++;
                anzTage = 0;
            }

        }
        if (anzTage == 1){
            txtZeit.setText("" + anzMonate + " Monaten und " + anzTage + " Tag");

        } else {
            txtZeit.setText("" + anzMonate + " Monaten und " + anzTage + " Tagen");
        }

    }
}
