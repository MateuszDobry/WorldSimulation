package pl.edu.pg.eti.ksg.po.project2.zwierzeta;

import pl.edu.pg.eti.ksg.po.project2.Zwierze;
import pl.edu.pg.eti.ksg.po.project2.Organizm;
import pl.edu.pg.eti.ksg.po.project2.Komentator;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;

import java.awt.*;

public class Zolw extends Zwierze {
    private static final int ZASIEG_RUCHU_ZOLWA = 1;
    private static final double SZANSA_WYKONYWANIA_RUCHU_ZOLWA = 0.25;
    private static final int SILA_ZOLWA = 2;
    private static final int INICJATYWA_ZOLWA = 1;

    public Zolw(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.ZOLW, swiat, pozycja, turaUrodzenia, SILA_ZOLWA, INICJATYWA_ZOLWA);
        this.setZasiegRuchu(ZASIEG_RUCHU_ZOLWA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_ZOLWA);
        setKolor(new Color(0, 102, 0));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Zolw";
    }

    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        if (this == ofiara) {
            if (atakujacy.getSila() < 5 && atakujacy.CzyJestZwierzeciem()) {
                Komentator.DodajKomentarz(OrganizmToSring() + " odpiera atak " + atakujacy.OrganizmToSring());
                return true;
            } else return false;
        } else {
            if (atakujacy.getSila() >= ofiara.getSila()) return false;
            else {
                if (ofiara.getSila() < 5 && ofiara.CzyJestZwierzeciem()) {
                    Komentator.DodajKomentarz(OrganizmToSring() + " odpiera atak " + ofiara.OrganizmToSring());
                    return true;
                } else return false;
            }
        }
    }
}
