package pl.edu.pg.eti.ksg.po.project2.zwierzeta;

import pl.edu.pg.eti.ksg.po.project2.Zwierze;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;

import java.awt.*;

public class Wilk extends Zwierze {
    private static final int ZASIEG_RUCHU_WILKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_WILKA = 1;
    private static final int SILA_WILKA = 9;
    private static final int INICJATYWA_WILKA = 5;

    public Wilk(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.WILK, swiat, pozycja, turaUrodzenia, SILA_WILKA, INICJATYWA_WILKA);
        this.setZasiegRuchu(ZASIEG_RUCHU_WILKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_WILKA);
        setKolor(new Color(44, 62, 80));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Wilk";
    }
}
