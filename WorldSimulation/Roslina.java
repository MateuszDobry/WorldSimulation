package pl.edu.pg.eti.ksg.po.project2;

import java.util.Random;

public abstract class Roslina extends Organizm {

    protected Roslina(TypOrganizmu typOrganizmu, Swiat swiat,
                      Punkt pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(typOrganizmu, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.3);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return false;
    }


    protected void Rozprzestrzenianie() {
        Punkt tmp1Punkt = this.LosujPoleNiezajete(getPozycja());
        if (tmp1Punkt.equals(getPozycja())) return;
        else {
            Organizm tmpOrganizm = FabrykaOrganizmow.StworzNowyOrganizm(getTypOrganizmu(), this.getSwiat(), tmp1Punkt);
            Komentator.DodajKomentarz("Wzrasta nowa roslina " + tmpOrganizm.OrganizmToSring());
            getSwiat().DodajOrganizm(tmpOrganizm);
        }
    }

    @Override
    public void Kolizja(Organizm other) {
    }
    /*NIE JEST POTRZEBNA,
	patrz funkcje "SpecjalneDzialaniePodczasAtaku()",
	ktora jest zdefiniowana u roznych roslin
	.*/
}
