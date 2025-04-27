package pl.edu.pg.eti.ksg.po.project2.zwierzeta;

import pl.edu.pg.eti.ksg.po.project2.Zwierze;
import pl.edu.pg.eti.ksg.po.project2.Organizm;
import pl.edu.pg.eti.ksg.po.project2.Komentator;
import pl.edu.pg.eti.ksg.po.project2.Swiat;
import pl.edu.pg.eti.ksg.po.project2.Punkt;
import pl.edu.pg.eti.ksg.po.project2.Umiejetnosc;

import java.awt.*;

public class Czlowiek extends Zwierze {
    private int ZASIEG_RUCHU_CZLOWIEKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA = 1;
    private static final int SILA_CZLOWIEKA = 5;
    private static final int INICJATYWA_CZLOWIEKA = 4;
    private Kierunek kierunekRuchu;
    private Umiejetnosc umiejetnosc;

    public Czlowiek(Swiat swiat, Punkt pozycja, int turaUrodzenia) {
        super(TypOrganizmu.CZLOWIEK, swiat, pozycja, turaUrodzenia, SILA_CZLOWIEKA, INICJATYWA_CZLOWIEKA);
        this.setZasiegRuchu(ZASIEG_RUCHU_CZLOWIEKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA);
        kierunekRuchu = Kierunek.BRAK_KIERUNKU;
        setKolor(new Color(52, 152, 219));
        umiejetnosc = new Umiejetnosc();
    }

    private void SzybkoscAntylopy() {
        ZASIEG_RUCHU_CZLOWIEKA = 2;
    }

   private void LosujPoleDowolneC(Punkt pozycja) {
        OdblokujWszystkieKierunki();
        int pozX = pozycja.getX();
        int pozY = pozycja.getY();
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int ileKierunkowMozliwych = 0;

        if(getSwiat().getCzlowiek().getUmiejetnosc().getCzyJestAktywna() == true) {
            if (pozX <= 1) ZablokujKierunek(Kierunek.LEWO);
            else ileKierunkowMozliwych++;
            if (sizeX - pozycja.getX() <= 2) ZablokujKierunek(Kierunek.PRAWO);
            else ileKierunkowMozliwych++;
            if (pozY <= 1) ZablokujKierunek(Kierunek.GORA);
            else ileKierunkowMozliwych++;
            if (sizeY - pozycja.getY() <= 2) ZablokujKierunek(Kierunek.DOL);
            else ileKierunkowMozliwych++;
        }
        else{
            if (pozX == 0) ZablokujKierunek(Kierunek.LEWO);
            else ileKierunkowMozliwych++;
            if (pozX == sizeX - 1) ZablokujKierunek(Kierunek.PRAWO);
            else ileKierunkowMozliwych++;
            if (pozY == 0) ZablokujKierunek(Kierunek.GORA);
            else ileKierunkowMozliwych++;
            if (pozY == sizeY - 1) ZablokujKierunek(Kierunek.DOL);
            else ileKierunkowMozliwych++;
        }
    }

    @Override
    protected Punkt ZaplanujRuch() {
        int x = getPozycja().getX();
        int y = getPozycja().getY();
        LosujPoleDowolneC(getPozycja());//BLOKUJE KIERUNKI NIEDOZWOLONE PRZY GRANICY SWIATU
        if (kierunekRuchu == Kierunek.BRAK_KIERUNKU ||
                CzyKierunekZablokowany(kierunekRuchu)) return getPozycja();
        else {
              if (kierunekRuchu == Kierunek.DOL) return new Punkt(x, y + ZASIEG_RUCHU_CZLOWIEKA);
            if (kierunekRuchu == Kierunek.GORA) return new Punkt(x, y - ZASIEG_RUCHU_CZLOWIEKA);
            if (kierunekRuchu == Kierunek.LEWO) return new Punkt(x - ZASIEG_RUCHU_CZLOWIEKA, y);
            if (kierunekRuchu == Kierunek.PRAWO) return new Punkt(x + ZASIEG_RUCHU_CZLOWIEKA, y);
            else return new Punkt(x, y);
        }
    }

    @Override
    public void Akcja() {
        if (umiejetnosc.getCzyJestAktywna()) {
            Komentator.DodajKomentarz(OrganizmToSring() + " 'SzybkoscAntylopy' jest aktywne(Pozostaly czas "
                    + umiejetnosc.getCzasTrwania() + " tur)");
            SzybkoscAntylopy();
        }
        for (int i = 0; i < getZasiegRuchu(); i++) {
            Punkt przyszlaPozycja = ZaplanujRuch();

            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja)
                    && getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) WykonajRuch(przyszlaPozycja);
            if (umiejetnosc.getCzyJestAktywna()){
                SzybkoscAntylopy();
            }
            else{
                ZASIEG_RUCHU_CZLOWIEKA = 1;
            }
        }
        kierunekRuchu = Kierunek.BRAK_KIERUNKU;
        umiejetnosc.SprawdzWarunki();
    }

    @Override
    public String TypOrganizmuToString() {
        return "Czlowiek";
    }

    public Umiejetnosc getUmiejetnosc() {
        return umiejetnosc;
    }

    public void setKierunekRuchu(Kierunek kierunekRuchu) {
        this.kierunekRuchu = kierunekRuchu;
    }
}
