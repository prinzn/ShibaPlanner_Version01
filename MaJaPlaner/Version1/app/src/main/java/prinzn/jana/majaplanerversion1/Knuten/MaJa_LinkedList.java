package prinzn.jana.majaplanerversion1.Knuten;

import java.util.ArrayList;
import java.util.List;

import prinzn.jana.majaplanerversion1.Termin.Termin;

public class MaJa_LinkedList {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private MaJa_LLKnut start, ende;
    private int laenge = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/
    private Termin getItem(int pIndex) {
        MaJa_LLKnut temp = start;
        for (int i = 0; i < laenge; i++) {
            if (pIndex == i) {
                return temp.getTerminDesKnuten();
            } else {
                temp = temp.getNaechst();
            }
        }
        return null;
    }

    /*-------------------------public Methoden----------------------------------------------------*/
    public void add(Termin pTermin){
        if (start == null){
            start = new MaJa_LLKnut(pTermin);    //Wenn bisher kein Start vorhanden ist, soll der übergebene Termin der Start und das Ende werden
        } else {
            findePlatz(pTermin);
        }
        laenge ++;
    }

    public void fuegeListeHinzu (List<Termin> pListe){
        for (int i = 0; i < pListe.size(); i++) {
            add(pListe.get(i));
        }
    }

    public List<Termin> getGeordneteListe(){
        List<Termin> liste = new ArrayList<>();
        if (laenge != 0){
            for (int i = 0; i < laenge; i++) {
                liste.add(getItem(i));
            }
            return liste;
        } else {
            return null;
        }
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    private void findePlatz(Termin pTermin){
        MaJa_LLKnut neuerKnuten = new MaJa_LLKnut(pTermin);
        MaJa_LLKnut vorDem = start;
        MaJa_LLKnut temp;
        Boolean gefunden = false;
        for (int i = 0; i < laenge && !gefunden; i++) {
            if (vorDem.getTerminDesKnuten().getStart().getTimeInMillis() < pTermin.getStart().getTimeInMillis()) {    //Wenn der Termin vor dem übergebenen liegt
                if (vorDem.getNaechst() != null){   //Ende noch nicht erreicht
                    vorDem = vorDem.getNaechst();   //Zum nächsten Knuten
                } else {    //Ende erreicht
                    gefunden = true;
                    vorDem.setNaechst(neuerKnuten);
                    neuerKnuten.setZuvor(vorDem);
                    ende = neuerKnuten;
                }

            } else {    //Wenn der Termin nach dem übergebenen liegt, wird er eingeschoben
                gefunden = true;
                if (vorDem.getZuvor() != null){   //liegt nicht vor momentanem start
                    temp = vorDem.getZuvor();
                    temp.setNaechst(neuerKnuten);
                    neuerKnuten.setZuvor(temp);
                    neuerKnuten.setNaechst(vorDem);
                    vorDem.setZuvor(neuerKnuten);
                } else {    //liegt  vor momentanem start
                    vorDem.setZuvor(neuerKnuten);
                    neuerKnuten.setNaechst(vorDem);
                    start = neuerKnuten;
                }

            }
        }
    }

    /*-------------------------override Methoden--------------------------------------------------*/
    @Override
    public String toString(){
        String ausgabe = "Inhalt: ";
        for (int i = 0; i < laenge; i++) {
            ausgabe = ausgabe + "|" + getItem(i);
        }
        return ausgabe;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Acticity erstellung

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
