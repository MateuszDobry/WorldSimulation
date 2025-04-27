package pl.edu.pg.eti.ksg.po.project2.rosliny;

import pl.edu.pg.eti.ksg.po.project2.Roslina;
import pl.edu.pg.eti.ksg.po.project2.Organizm;
import pl.edu.pg.eti.ksg.po.project2.Komentator;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;

import java.awt.*;

public class Guarana extends Roslina {
    private static final int ZWIEKSZENIE_SILY = 3;
    private static final int SILA_GUARANY = 0;
    private static final int INICJATYWA_GUARANY = 0;

    public Guarana(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.GUARANA, swiat, pozycja,
                turaUrodzenia, SILA_GUARANY, INICJATYWA_GUARANY);
        setKolor(new Color(231, 76, 60));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Guarana";
    }

    @Override
    public boolean SpecjalneDzialaniePodczasAtaku(Organizm atakujacy, Organizm ofiara) {
        Punkt tmpPozycja = this.getPozycja();
        getSwiat().UsunOrganizm(this);
        atakujacy.WykonajRuch(tmpPozycja);
        Komentator.DodajKomentarz(atakujacy.OrganizmToSring() + " zjada " + this.OrganizmToSring()
                + "  i zwieksza swoja sile na " + Integer.toString(ZWIEKSZENIE_SILY));
        atakujacy.setSila(atakujacy.getSila() + ZWIEKSZENIE_SILY);
        return true;
    }
}

