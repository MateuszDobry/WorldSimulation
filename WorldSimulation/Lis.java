package pl.edu.pg.eti.ksg.po.project2.zwierzeta;

import pl.edu.pg.eti.ksg.po.project2.Zwierze;
import pl.edu.pg.eti.ksg.po.project2.Organizm;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;

import java.util.Random;
import java.awt.*;

public class Lis extends Zwierze {
    private static final int ZASIEG_RUCHU_LISA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_LISA = 1;
    private static final int SILA_LISA = 3;
    private static final int INICJATYWA_LISA = 7;

    public Lis(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.LIS, swiat, pozycja, turaUrodzenia, SILA_LISA, INICJATYWA_LISA);
        this.setZasiegRuchu(ZASIEG_RUCHU_LISA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_LISA);
        setKolor(new Color(211, 84, 0));
    }

    @Override
    public String TypOrganizmuToString() {
        return "Lis";
    }

    @Override
    public Punkt LosujPoleDowolne(Punkt pozycja) {
        OdblokujWszystkieKierunki();
        int pozX = pozycja.getX();
        int pozY = pozycja.getY();
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int ileKierunkowMozliwych = 0;
        Organizm tmpOrganizm;

        if (pozX == 0) ZablokujKierunek(Kierunek.LEWO);
        else {
            tmpOrganizm = getSwiat().getPlansza()[pozY][pozX - 1];
            if (tmpOrganizm != null && tmpOrganizm.getSila() > this.getSila()) {
                ZablokujKierunek(Kierunek.LEWO);
            } else ileKierunkowMozliwych++;
        }

        if (pozX == sizeX - 1) ZablokujKierunek(Kierunek.PRAWO);
        else {
            tmpOrganizm = getSwiat().getPlansza()[pozY][pozX + 1];
            if (tmpOrganizm != null && tmpOrganizm.getSila() > this.getSila()) {
                ZablokujKierunek(Kierunek.PRAWO);
            } else ileKierunkowMozliwych++;
        }

        if (pozY == 0) ZablokujKierunek(Kierunek.GORA);
        else {
            tmpOrganizm = getSwiat().getPlansza()[pozY - 1][pozX];
            if (tmpOrganizm != null && tmpOrganizm.getSila() > this.getSila()) {
                ZablokujKierunek(Kierunek.GORA);
            } else ileKierunkowMozliwych++;
        }

        if (pozY == sizeY - 1) ZablokujKierunek(Kierunek.DOL);
        else {
            tmpOrganizm = getSwiat().getPlansza()[pozY + 1][pozX];
            if (tmpOrganizm != null && tmpOrganizm.getSila() > this.getSila()) {
                ZablokujKierunek(Kierunek.DOL);
            } else ileKierunkowMozliwych++;
        }

        if (ileKierunkowMozliwych == 0) return new Punkt(pozX, pozY);
        while (true) {
            Random rand = new Random();
            int upperbound = 100;
            int tmpLosowanie = rand.nextInt(upperbound);
            //RUCH W LEWO
            if (tmpLosowanie < 25 && !CzyKierunekZablokowany(Kierunek.LEWO))
                return new Punkt(pozX - 1, pozY);
                //RUCH W PRAWO
            else if (tmpLosowanie >= 25 && tmpLosowanie < 50 && !CzyKierunekZablokowany(Kierunek.PRAWO))
                return new Punkt(pozX + 1, pozY);
                //RUCH W GORE
            else if (tmpLosowanie >= 50 && tmpLosowanie < 75 && !CzyKierunekZablokowany(Kierunek.GORA))
                return new Punkt(pozX, pozY - 1);
                //RUCH W DOL
            else if (tmpLosowanie >= 75 && !CzyKierunekZablokowany(Kierunek.DOL))
                return new Punkt(pozX, pozY + 1);
        }
    }
}
