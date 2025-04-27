package pl.edu.pg.eti.ksg.po.project2.rosliny;

import pl.edu.pg.eti.ksg.po.project2.Roslina;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;

import java.util.Random;
import java.awt.*;

public class Mlecz extends Roslina {
    private static final int SILA_MLECZ = 0;
    private static final int INICJATYWA_MLECZ = 0;
    private static final int ILE_PROB = 3;

    public Mlecz(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.MLECZ, swiat, pozycja,
                turaUrodzenia, SILA_MLECZ, INICJATYWA_MLECZ);
        setKolor(new Color(241, 196, 15));
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        for (int i = 0; i < ILE_PROB; i++) {
            int tmpLosowanie = rand.nextInt(100);
            if (tmpLosowanie < getSzansaRozmnazania()) Rozprzestrzenianie();
        }
    }

    @Override
    public String TypOrganizmuToString() {
        return "Mlecz";
    }
}
