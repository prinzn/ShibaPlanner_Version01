package prinzn.jana.majaplanerversion1.Datenbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prinzn.jana.majaplanerversion1.Termin.Termin;

/**
 * Diese Klasse händelt den Umgang mit der Termindatenbank
 */
public class TermineDataSource {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private SQLiteDatabase database;
    private TermineDatenbankHelfer dbHelfer;
    private final String logTag = "LogTag_TerminDataSource";

    /** Spalten der Tabelle */
    private String[] spalten = {
            TermineDatenbankHelfer.SPALTE_ID,
            TermineDatenbankHelfer.SPALTE_TITEL,
            TermineDatenbankHelfer.SPALTE_START_TAG,
            TermineDatenbankHelfer.SPALTE_START_MONAT,
            TermineDatenbankHelfer.SPALTE_START_JAHR,
            TermineDatenbankHelfer.SPALTE_START_STUNDE,
            TermineDatenbankHelfer.SPALTE_START_MINUTE,
            TermineDatenbankHelfer.SPALTE_ENDE_TAG,
            TermineDatenbankHelfer.SPALTE_ENDE_MONAT,
            TermineDatenbankHelfer.SPALTE_ENDE_JAHR,
            TermineDatenbankHelfer.SPALTE_ENDE_STUNDE,
            TermineDatenbankHelfer.SPALTE_ENDE_MINUTE,
            TermineDatenbankHelfer.SPALTE_GANZTAEGIG,
            TermineDatenbankHelfer.SPALTE_FARBE,
            TermineDatenbankHelfer.SPALTE_ERSTELLUNG,
            TermineDatenbankHelfer.SPALTE_NOTIZEN
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public TermineDataSource(Context pContext) {
        dbHelfer = new TermineDatenbankHelfer(pContext);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/
    /**
     * Verbindung zur Datenbank öffnen
     */
    public void open() {
        Log.d(logTag, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelfer.getWritableDatabase();
        Log.d(logTag, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    /**
     * Verbindung zur Datenbank schließen
     */
    public void close() {
        dbHelfer.close();
        Log.d(logTag, "Datenbank mit Hilfe des DbHelfers geschlossen.");
    }

    /**
     * Termin aus der Datenbank entfernen
     * @param pTermin übergebener Termin soll gelöscht werden
     */
    public void loescheTermin(Termin pTermin) {
        long id = pTermin.getId();

        database.delete(TermineDatenbankHelfer.TABELLE_NAME_TERMINE,
                TermineDatenbankHelfer.SPALTE_ID + "=" + id,
                null);
        Log.d(logTag, "Eintrag gelöscht! ID: " + id + " Inhalt: " + pTermin.print());
    }

    /**
     * um Termine zu updaten
     *
     * @return geänderten Termin.
     */
    public Termin aendereTermin(long pId, String pTitel, Calendar pStart, Calendar pEnde, String pGanztaegig, int pFarbe, String pNotizen) {

        ContentValues values = new ContentValues();
        values.put(TermineDatenbankHelfer.SPALTE_TITEL, pTitel);
        values.put(TermineDatenbankHelfer.SPALTE_START_TAG, pStart.get(Calendar.DAY_OF_MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_START_MONAT, pStart.get(Calendar.MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_START_JAHR, pStart.get(Calendar.YEAR));
        values.put(TermineDatenbankHelfer.SPALTE_START_STUNDE, pStart.get(Calendar.HOUR_OF_DAY));
        values.put(TermineDatenbankHelfer.SPALTE_START_MINUTE, pStart.get(Calendar.MINUTE));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_TAG, pEnde.get(Calendar.DAY_OF_MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_MONAT, pEnde.get(Calendar.MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_JAHR, pEnde.get(Calendar.YEAR));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_STUNDE, pEnde.get(Calendar.HOUR_OF_DAY));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_MINUTE, pEnde.get(Calendar.MINUTE));
        values.put(TermineDatenbankHelfer.SPALTE_GANZTAEGIG, pGanztaegig);
        values.put(TermineDatenbankHelfer.SPALTE_FARBE, pFarbe);
        values.put(TermineDatenbankHelfer.SPALTE_NOTIZEN, pNotizen);

        database.update(TermineDatenbankHelfer.TABELLE_NAME_TERMINE,
                values,
                TermineDatenbankHelfer.SPALTE_ID + "=" + pId,
                null);

        Cursor cursor = database.query(TermineDatenbankHelfer.TABELLE_NAME_TERMINE,
                spalten, TermineDatenbankHelfer.SPALTE_ID + "=" + pId,
                null, null, null, null);

        cursor.moveToFirst();
        Termin termin = cursorZuTermin(cursor);
        cursor.close();

        return termin;

    }

    /**
     * Um Datensätze(Termine) in die Tabelle einzufügen
     *
     * @return erstellten Termin.
     */
    public Termin erstelleTermin(String pTitel, Calendar pStart, Calendar pEnde, String pGanztaegig, int pFarbe, String pNotizen, String pErstellung) {
        ContentValues values = new ContentValues();
        values.put(TermineDatenbankHelfer.SPALTE_TITEL, pTitel);
        values.put(TermineDatenbankHelfer.SPALTE_START_TAG, pStart.get(Calendar.DAY_OF_MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_START_MONAT, pStart.get(Calendar.MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_START_JAHR, pStart.get(Calendar.YEAR));
        values.put(TermineDatenbankHelfer.SPALTE_START_STUNDE, pStart.get(Calendar.HOUR_OF_DAY));
        values.put(TermineDatenbankHelfer.SPALTE_START_MINUTE, pStart.get(Calendar.MINUTE));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_TAG, pEnde.get(Calendar.DAY_OF_MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_MONAT, pEnde.get(Calendar.MONTH));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_JAHR, pEnde.get(Calendar.YEAR));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_STUNDE, pEnde.get(Calendar.HOUR_OF_DAY));
        values.put(TermineDatenbankHelfer.SPALTE_ENDE_MINUTE, pEnde.get(Calendar.MINUTE));
        values.put(TermineDatenbankHelfer.SPALTE_GANZTAEGIG, pGanztaegig);
        values.put(TermineDatenbankHelfer.SPALTE_FARBE, pFarbe);
        values.put(TermineDatenbankHelfer.SPALTE_NOTIZEN, pNotizen);
        values.put(TermineDatenbankHelfer.SPALTE_ERSTELLUNG, pErstellung);

        //Id wird erzeugt
        long insertId = database.insert(TermineDatenbankHelfer.TABELLE_NAME_TERMINE, null, values);

        //Mit Cursor zur Stelle in die die Id gehört und dort einfügen
        Cursor cursor = database.query(TermineDatenbankHelfer.TABELLE_NAME_TERMINE,
                spalten, TermineDatenbankHelfer.SPALTE_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();   //Cursor vor den Anfang
        Termin termin = cursorZuTermin(cursor);
        cursor.close();

        Log.d(logTag, "Termin erstellt! Inhalt: " + termin.print());

        return termin;
    }

    /**
     * Um alle vorhandenen Datensätze aus der Tabelle auszulesen
     *
     * @return List mit allen Terminen.
     */
    public List<Termin> getAlleTermine() {
        List<Termin> terminListe = new ArrayList<>();
        Cursor cursor = database.query(TermineDatenbankHelfer.TABELLE_NAME_TERMINE,
                spalten, null, null, null, null, null);

        cursor.moveToFirst();
        Termin termin;

        while (!cursor.isAfterLast()) {
            termin = cursorZuTermin(cursor);
            terminListe.add(termin);
            Log.d(logTag, "ID: " + termin.getId() + ", Inhalt: " + termin.print() + "|" + termin.getGanztaegig());
            cursor.moveToNext();
        }

        cursor.close();

        return terminListe;
    }

    /**
     * Um gesuchte Datensätze aus der Tabelle auszulesen
     *
     * @return List mit gesuchten Terminen.
     */
    public List<Termin> getGesuchteTermine(String pSuche) {
        List<Termin> terminListe = new ArrayList<>();

        String[] selectArgumente = {"%" + pSuche + "%"};
        Cursor cursor = database.query(TermineDatenbankHelfer.TABELLE_NAME_TERMINE,
                spalten,
                TermineDatenbankHelfer.SPALTE_TITEL + " LIKE ?", selectArgumente,   // Filtern nach Terminen welche der Suche entsprechen
                null, null, null);

        cursor.moveToFirst();
        Termin termin;

        while (!cursor.isAfterLast()) {
            termin = cursorZuTermin(cursor);
            terminListe.add(termin);
            Log.d(logTag, "ID: " + termin.getId() + ", Inhalt: " + termin.print() + "|" + termin.getGanztaegig());
            cursor.moveToNext();
        }

        cursor.close();

        return terminListe;
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    /**
     * Um einen Datensatz(einen Termin) aus der Tabelle auszulesen
     *
     * @return der Termin auf den gezeigt wurde.
     */
    private Termin cursorZuTermin(Cursor pCursor) {
        int idIndex = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ID);
        int idTerminTitel = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_TITEL);
        int idStartTag = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_START_TAG);
        int idStartMonat = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_START_MONAT);
        int idStartJahr = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_START_JAHR);
        int idStartStunde = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_START_STUNDE);
        int idStartMinute = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_START_MINUTE);
        int idEndTag = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ENDE_TAG);
        int idEndMonat = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ENDE_MONAT);
        int idEndJahr = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ENDE_JAHR);
        int idEndStunde = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ENDE_STUNDE);
        int idEndMinute = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ENDE_MINUTE);
        int idGanztaegig = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_GANZTAEGIG);
        int idFarbe = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_FARBE);
        int idNotizen = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_NOTIZEN);
        int idErstellung = pCursor.getColumnIndex(TermineDatenbankHelfer.SPALTE_ERSTELLUNG);

        String titel = pCursor.getString(idTerminTitel);

        //StartDaten des Termins aus Tabelle bekommen und abspeichern
        int startTag = pCursor.getInt(idStartTag);
        int startMonat = pCursor.getInt(idStartMonat);
        int startJahr = pCursor.getInt(idStartJahr);
        int startStunde = pCursor.getInt(idStartStunde);
        int startMinute = pCursor.getInt(idStartMinute);

        Calendar start = Calendar.getInstance();
        start.set(startJahr, startMonat, startTag, startStunde, startMinute);

        //EndDaten des Termins aus Tabelle bekommen und abspeichern
        int endTag = pCursor.getInt(idEndTag);
        int endMonat = pCursor.getInt(idEndMonat);
        int endJahr = pCursor.getInt(idEndJahr);
        int endStunde = pCursor.getInt(idEndStunde);
        int endMinute = pCursor.getInt(idEndMinute);

        Calendar ende = Calendar.getInstance();
        ende.set(endJahr, endMonat, endTag, endStunde, endMinute);


        //Aus dem Ganztägig String wird ein Boolean um später bessere Abfragen durchführen zu können
        String sGanztaegig = pCursor.getString(idGanztaegig);
        Boolean ganztaegig;
        switch (sGanztaegig) {
            case "true":
                ganztaegig = true;
                break;
            case "false":
                ganztaegig = false;
                break;
            default:
                ganztaegig = false; //Wenn ein Fehler mit dem String ist -> nicht Ganztägig
                break;
        }

        long id = pCursor.getLong(idIndex);
        int farbe = pCursor.getInt(idFarbe);
        String notizen = pCursor.getString(idNotizen);
        String erstellung = pCursor.getString(idErstellung);

        return new Termin(id, titel, start, ende, ganztaegig, farbe, notizen, erstellung);
    }

    /*-------------------------override Methoden--------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
