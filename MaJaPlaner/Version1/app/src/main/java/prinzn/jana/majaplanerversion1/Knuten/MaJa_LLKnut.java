package prinzn.jana.majaplanerversion1.Knuten;

import prinzn.jana.majaplanerversion1.Termin.Termin;

public class MaJa_LLKnut {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Termin terminDesKnuten;
    private MaJa_LLKnut naechst, zuvor;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public MaJa_LLKnut(Termin pTermin){
        terminDesKnuten = pTermin;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setNaechst(MaJa_LLKnut pNaechst) {
        this.naechst = pNaechst;
    }

    public void setZuvor(MaJa_LLKnut pZuvor) {
        this.zuvor = pZuvor;
    }

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public MaJa_LLKnut getNaechst() {
        return naechst;
    }

    public MaJa_LLKnut getZuvor() {
        return zuvor;
    }

    public Termin getTerminDesKnuten() {
        return terminDesKnuten;
    }

    /*-------------------------public Methoden----------------------------------------------------*/

    /*-------------------------private Methoden---------------------------------------------------*/

    /*-------------------------override Methoden--------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
