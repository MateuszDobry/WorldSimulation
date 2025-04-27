package pl.edu.pg.eti.ksg.po.project2.rosliny;

import pl.edu.pg.eti.ksg.po.project2.Roslina;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;

import java.awt.*;

public class Trawa extends Roslina {
    private static final int SILA_TRAWY = 0;
    private static final int INICJATYWA_TRAWY = 0;

    public Trawa(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.TRAWA, swiat, pozycja, turaUrodzenia, SILA_TRAWY, INICJATYWA_TRAWY);
        setKolor(new Color(46, 204, 113));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Trawa";
    }
}
