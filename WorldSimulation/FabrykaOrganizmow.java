package pl.edu.pg.eti.ksg.po.project2;

import pl.edu.pg.eti.ksg.po.project2.rosliny.*;
import pl.edu.pg.eti.ksg.po.project2.zwierzeta.*;


public class FabrykaOrganizmow {
    public static Organizm StworzNowyOrganizm
            (Organizm.TypOrganizmu typOrganizmu, Swiat swiat, Punkt pozycja) {
        switch (typOrganizmu) {
            case WILK:
                return new Wilk(swiat, pozycja, swiat.getNumerTury());
            case OWCA:
                return new Owca(swiat, pozycja, swiat.getNumerTury());
            case LIS:
                return new Lis(swiat, pozycja, swiat.getNumerTury());
            case ZOLW:
                return new Zolw(swiat, pozycja, swiat.getNumerTury());
            case ANTYLOPA:
                return new Antylopa(swiat, pozycja, swiat.getNumerTury());
            case CZLOWIEK:
                return new Czlowiek(swiat, pozycja, swiat.getNumerTury());
            case TRAWA:
                return new Trawa(swiat, pozycja, swiat.getNumerTury());
            case MLECZ:
                return new Mlecz(swiat, pozycja, swiat.getNumerTury());
            case GUARANA:
                return new Guarana(swiat, pozycja, swiat.getNumerTury());
            case WILCZE_JAGODY:
                return new WilczeJagody(swiat, pozycja, swiat.getNumerTury());
            case BARSZCZ_SOSNOWSKIEGO:
                return new BarszczSosnowskiego(swiat, pozycja, swiat.getNumerTury());
            case CYBER_OWCA:
                return new CyberOwca(swiat, pozycja, swiat.getNumerTury());
            default:
                return null;//UNDEFINED TYP
        }
    }
}
