package pl.edu.pg.eti.ksg.po.project2;

public class Komentator {
    private static String tekst = "";

    public static void DodajKomentarz(String komentarz) {
        tekst += komentarz + "\n";
    }

    public static String getTekst() {
        return tekst;
    }

    public static void WyczyscKomentarzy() {
        tekst = "";
    }
}
