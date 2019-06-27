package prinzn.jana.majaplanerversion1.Datenbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Diese Klasse vereinfacht den Zugriff auf die Datenbank
 */
public class TermineDatenbankHelfer extends SQLiteOpenHelper {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /** Name der Datenbank */
    public static final String DB_NAME = "termine.db";
    /** Versionsnummer der Datenbank */
    public static final int DB_VERSION = 1;

    /** Name der Tabelle */
    public static final String TABELLE_NAME_TERMINE = "termine";

    /*-------------------------andere Spalten-----------------------------------------------------*/
    /** späterer Primärschlüssel */
    public static final String SPALTE_ID = "_id";
    /** Spaltenbezeichnung Termin Titel */
    public static final String SPALTE_TITEL = "titel";

    /*-------------------------Spalte Start-------------------------------------------------------*/
    /** Speicherort für Starttage */
    public static final String SPALTE_START_TAG = "starttag";
    /** Speicherort für Startmonate */
    public static final String SPALTE_START_MONAT = "startmonat";
    /** Speicherort für Startjahre */
    public static final String SPALTE_START_JAHR = "startjahr";
    /** Speicherort für Startstunden */
    public static final String SPALTE_START_STUNDE = "startstunde";
    /** Speicherort für Startminuten */
    public static final String SPALTE_START_MINUTE = "startminute";

    /*-------------------------Spalte Ende--------------------------------------------------------*/
    /** Speicherort für Endtage */
    public static final String SPALTE_ENDE_TAG = "endtag";
    /** Speicherort für Endmonate */
    public static final String SPALTE_ENDE_MONAT = "endmonat";
    /** Speicherort für Endjahre */
    public static final String SPALTE_ENDE_JAHR = "endjahr";
    /** Speicherort für Endstunden */
    public static final String SPALTE_ENDE_STUNDE = "endstunde";
    /** Speicherort für Endminuten */
    public static final String SPALTE_ENDE_MINUTE = "endminute";

    /** Speicherort ob es ein ganztägiger Termin ist */
    public static final String SPALTE_GANZTAEGIG = "ganztaegig";
    /** Speicherort für Farbe */
    public static final String SPALTE_FARBE = "farbe";
    /** Speicherort für Notizen */
    public static final String SPALTE_NOTIZEN = "notizen";

    /** Speicherort für Erstellungsdatum */
    public static final String SPALTE_ERSTELLUNG = "erstellung";

    /*-------------------------Datenbankeneigenschaften-------------------------------------------*/
    /** Einzelne Spalten dürfen nicht null sein; Id wird automatisch nach oben gezählt*/
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABELLE_NAME_TERMINE +
                    "(" + SPALTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    SPALTE_TITEL + " TEXT NOT NULL, " +

                    SPALTE_START_TAG + " INTEGER NOT NULL, " +
                    SPALTE_START_MONAT + " INTEGER NOT NULL, " +
                    SPALTE_START_JAHR + " INTEGER NOT NULL, " +
                    SPALTE_START_STUNDE + " INTEGER NOT NULL, " +
                    SPALTE_START_MINUTE + " INTEGER NOT NULL, " +

                    SPALTE_ENDE_TAG + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_MONAT + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_JAHR + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_STUNDE + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_MINUTE + " INTEGER NOT NULL, " +

                    SPALTE_FARBE + " INTEGER NOT NULL, " +
                    SPALTE_GANZTAEGIG + " TEXT NOT NULL, " +
                    SPALTE_ERSTELLUNG + " TEXT NOT NULL, " +
                    SPALTE_NOTIZEN + ");";
    /*-------------------------Andere Variablen---------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public TermineDatenbankHelfer(Context pContext) {
        super(pContext, DB_NAME, null, DB_VERSION);  //Konstruktor der Elternklasse (SQLiteOpenHelper)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/
    /**
     * Die onCreate-Methode wird nur aufgerufen, wenn die Datenbank noch nicht existiert
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("da", "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        } catch (Exception ex) {
            Log.e("da", "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
