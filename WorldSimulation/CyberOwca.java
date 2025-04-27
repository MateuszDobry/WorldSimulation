package pl.edu.pg.eti.ksg.po.project2.zwierzeta;

import pl.edu.pg.eti.ksg.po.project2.Organizm;
import pl.edu.pg.eti.ksg.po.project2.Punkt;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.rosliny.BarszczSosnowskiego;

import java.awt.*;

public class CyberOwca extends Owca {
    private static final int ZASIEG_RUCHU_CYBER_OWCY = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CYBER_OWCY = 1;
    private static final int SILA_CYBER_OWCY = 11;
    private static final int INICJATYWA_CYBER_OWCY = 4;

    public CyberOwca(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(swiat, pozycja, turaUrodzenia);
        setTypOrganizmu(TypOrganizmu.CYBER_OWCA);
        setSila(SILA_CYBER_OWCY);
        setInicjatywa(INICJATYWA_CYBER_OWCY);
        setSzansaRozmnazania(0.1);

        this.setZasiegRuchu(ZASIEG_RUCHU_CYBER_OWCY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CYBER_OWCY);
        setKolor(new Color(127, 140, 141));
    }

    @Override
    public Punkt LosujPoleDowolne(Punkt pozycja) {
        if (getSwiat().czyIstniejeBarszczSosnowskiego()) {

            Punkt cel = znajdzNajblizszyBarszczSosnowskiego().getPozycja();
            int dx = Math.abs(pozycja.getX() - cel.getX());
            int dy = Math.abs(pozycja.getY() - cel.getY());
            if (dx >= dy) {
                if (pozycja.getX() > cel.getX()) {
                    return new Punkt(pozycja.getX() - 1, pozycja.getY());
                } else {
                    return new Punkt(pozycja.getX() + 1, pozycja.getY());
                }
            } else {
                if (pozycja.getY() > cel.getY()) {
                    return new Punkt(pozycja.getX(), pozycja.getY() - 1);
                } else {
                    return new Punkt(pozycja.getX(), pozycja.getY() + 1);
                }
            }
        } else return super.LosujPoleDowolne(pozycja);
    }

    private BarszczSosnowskiego znajdzNajblizszyBarszczSosnowskiego() {
        BarszczSosnowskiego tmpBarszcz = null;
        int najmniejszaOdleglosc = getSwiat().getSizeX() + getSwiat().getSizeY() + 1;
        for (int i = 0; i < getSwiat().getSizeY(); i++) {
            for (int j = 0; j < getSwiat().getSizeX(); j++) {
                Organizm tmpOrganizm = getSwiat().getPlansza()[i][j];
                if (tmpOrganizm != null &&
                        tmpOrganizm.getTypOrganizmu() == TypOrganizmu.BARSZCZ_SOSNOWSKIEGO) {
                    int tmpOdleglosc = znajdzOdleglosc(tmpOrganizm.getPozycja());
                    if (najmniejszaOdleglosc > tmpOdleglosc) {
                        najmniejszaOdleglosc = tmpOdleglosc;
                        tmpBarszcz = (BarszczSosnowskiego) tmpOrganizm;
                    }
                }
            }
        }
        return tmpBarszcz;
    }

    private int znajdzOdleglosc(Punkt otherPozycja) {
        int dx = Math.abs(getPozycja().getX() - otherPozycja.getX());
        int dy = Math.abs(getPozycja().getY() - otherPozycja.getY());
        return dx + dy;
    }

    @Override
    public String TypOrganizmuToString() {
        return "Cyber owca";
    }
}
