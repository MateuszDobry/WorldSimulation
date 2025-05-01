package pl.edu.pg.eti.ksg.po.project2;

import pl.edu.pg.eti.ksg.po.project2.zwierzeta.Czlowiek;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwiatGUI implements ActionListener, KeyListener {
    private Toolkit toolkit;
    private Dimension dimension;
    private JFrame jFrame;
    private JMenu menu;
    private JMenuItem newGame, load, save, exit;
    private PlanszaGraphics planszaGraphics = null;
    private KomentatorGraphics komentatorGraphics = null;
    private Oznaczenia oznaczenia = null;
    private JPanel mainPanel;
    private final int ODSTEP;
    private Swiat swiat;

    public SwiatGUI(String title) {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        ODSTEP = dimension.height / 90;


        jFrame = new JFrame(title);
        jFrame.setBounds((dimension.width - 800) / 2, (dimension.height - 600) / 2, 800, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("Nowa Gra");
        load = new JMenuItem("Wczytaj");
        save = new JMenuItem("Zapisz");
        exit = new JMenuItem("Wyjśćie");
        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(newGame);
        menu.add(load);
        menu.add(save);
        menu.add(exit);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GREEN);
        mainPanel.setLayout(null);

        jFrame.addKeyListener(this);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            Komentator.WyczyscKomentarzy();
            int sizeX = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Podaj szerokosc swiata", "20"));
            int sizeY = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Podaj wysokosc swiata", "20"));
            double zapelnienieSwiatu = Double.parseDouble(JOptionPane.showInputDialog
                    (jFrame, "Podaj zapelnienie swiata(wartosc od 0 do 1)", "0.3"));
            swiat = new Swiat(sizeX, sizeY, this);
            swiat.GenerujSwiat(zapelnienieSwiatu);
            if (planszaGraphics != null)
                mainPanel.remove(planszaGraphics);
            if (komentatorGraphics != null)
                mainPanel.remove(komentatorGraphics);
            if (oznaczenia != null)
                mainPanel.remove(oznaczenia);
            startGame();
        }
        if (e.getSource() == load) {
            Komentator.WyczyscKomentarzy();
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Podaj nazwe pliku");
            swiat = Swiat.OdtworzSwiat(nameOfFile);
            swiat.setSwiatGUI(this);
            planszaGraphics = new PlanszaGraphics(swiat);
            komentatorGraphics = new KomentatorGraphics();
            oznaczenia = new Oznaczenia();
            if (planszaGraphics != null)
                mainPanel.remove(planszaGraphics);
            if (komentatorGraphics != null)
                mainPanel.remove(komentatorGraphics);
            if (oznaczenia != null)
                mainPanel.remove(oznaczenia);
            startGame();
        }
        if (e.getSource() == save) {
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Podaj nazwe pliku");
            swiat.ZapiszSwiat(nameOfFile);
            Komentator.DodajKomentarz("Swiat zostal zapisany");
            komentatorGraphics.odswiezKomentarzy();
        }
        if (e.getSource() == exit) {
            jFrame.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (swiat != null && swiat.isPauza()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

            } else if (swiat.getCzyCzlowiekZyje()) {
                if (keyCode == KeyEvent.VK_UP) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.GORA);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.DOL);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.LEWO);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.PRAWO);
                } else if (keyCode == KeyEvent.VK_P) {
                    Umiejetnosc tmpUmiejetnosc = swiat.getCzlowiek().getUmiejetnosc();
                    if (tmpUmiejetnosc.getCzyMoznaAktywowac()) {
                        tmpUmiejetnosc.Aktywuj();
                        Komentator.DodajKomentarz("Umiejetnosc 'SzybkośćAntylopy' zostala wlaczona (Pozostaly" +
                                " czas trwania wynosi " + tmpUmiejetnosc.getCzasTrwania() + " tur)");

                    } else if (tmpUmiejetnosc.getCzyJestAktywna()) {
                        Komentator.DodajKomentarz("Umiejetnosc juz zostala aktywowana " + "(Pozostaly" +
                                " czas trwania wynosi " + tmpUmiejetnosc.getCzasTrwania() + " tur)");
                        komentatorGraphics.odswiezKomentarzy();
                        return;
                    } else {
                        Komentator.DodajKomentarz("Umiejetnosc mozna wlaczyc tylko po "
                                + tmpUmiejetnosc.getCooldown() + " turach");
                        komentatorGraphics.odswiezKomentarzy();
                        return;
                    }
                } else {
                    Komentator.DodajKomentarz("\nNieoznaczony symbol, sprobuj ponownie");
                    komentatorGraphics.odswiezKomentarzy();
                    return;
                }
            } else if (!swiat.getCzyCzlowiekZyje() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P)) {
                Komentator.DodajKomentarz("Czlowiek umarl, nie mozesz im wiecej sterowac");
                komentatorGraphics.odswiezKomentarzy();
                return;
            } else {
                Komentator.DodajKomentarz("\nNieoznaczony symbol, sprobuj ponownie");
                komentatorGraphics.odswiezKomentarzy();
                return;
            }
            Komentator.WyczyscKomentarzy();
            swiat.setPauza(false);
            swiat.WykonajTure();
            odswiezSwiat();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class PlanszaGraphics extends JPanel {
        private final int sizeX;
        private final int sizeY;
        private PolePlanszy[][] polaPlanszy;
        private Swiat SWIAT;

        public PlanszaGraphics(Swiat swiat) {
            super();
            setBounds(mainPanel.getX() + ODSTEP, mainPanel.getY() + ODSTEP,
                    mainPanel.getHeight() * 5 / 6 - ODSTEP, mainPanel.getHeight() * 5 / 6 - ODSTEP);
            SWIAT = swiat;
            this.sizeX = swiat.getSizeX();
            this.sizeY = swiat.getSizeY();

            polaPlanszy = new PolePlanszy[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    polaPlanszy[i][j] = new PolePlanszy(j, i);
                    polaPlanszy[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof PolePlanszy) {
                                PolePlanszy tmpPole = (PolePlanszy) e.getSource();
                                if (tmpPole.isEmpty == true) {
                                    ListaOrganizmow listaOrganizmow = new ListaOrganizmow
                                            (tmpPole.getX() + jFrame.getX(),
                                                    tmpPole.getY() + jFrame.getY(),
                                                    new Punkt(tmpPole.getPozX(), tmpPole.getPozY()));
                                }
                            }
                        }
                    });
                }
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(polaPlanszy[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }

        private class PolePlanszy extends JButton {
            private boolean isEmpty;
            private Color kolor;
            private final int pozX;
            private final int pozY;

            public PolePlanszy(int X, int Y) {
                super();
                kolor = Color.WHITE;
                setBackground(kolor);
                isEmpty = true;
                pozX = X;
                pozY = Y;
            }

            public boolean isEmpty() {
                return isEmpty;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }


            public Color getKolor() {
                return kolor;
            }

            public void setKolor(Color kolor) {
                this.kolor = kolor;
                setBackground(kolor);
            }

            public int getPozX() {
                return pozX;
            }

            public int getPozY() {
                return pozY;
            }
        }

        public void odswiezPlansze() {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organizm tmpOrganizm = swiat.getPlansza()[i][j];
                    if (tmpOrganizm != null) {
                        polaPlanszy[i][j].setEmpty(false);
                        polaPlanszy[i][j].setEnabled(false);
                        polaPlanszy[i][j].setKolor(tmpOrganizm.getKolor());
                    } else {
                        polaPlanszy[i][j].setEmpty(true);
                        polaPlanszy[i][j].setEnabled(true);
                        polaPlanszy[i][j].setKolor(Color.WHITE);
                    }
                }
            }
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public PolePlanszy[][] getPolaPlanszy() {
            return polaPlanszy;
        }
    }

    private class KomentatorGraphics extends JPanel {
        private String tekst;
        private final String instriction = "Mateusz Dobry \nStrzalki - kierowanie Czlowiekiem\n" +
                "P - aktywacja umiejetnosci\nEnter - przejscie do nastepnej tury\n";
        private JTextArea textArea;

        public KomentatorGraphics() {
            super();
            setBounds(planszaGraphics.getX() + planszaGraphics.getWidth() + ODSTEP,
                    mainPanel.getY() + ODSTEP,
                    mainPanel.getWidth() - planszaGraphics.getWidth() - ODSTEP * 3,
                    mainPanel.getHeight() * 5 / 6 - ODSTEP);
            tekst = Komentator.getTekst();
            textArea = new JTextArea(tekst);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void odswiezKomentarzy() {
            tekst = instriction + Komentator.getTekst();
            textArea.setText(tekst);
        }
    }

    private class ListaOrganizmow extends JFrame {
        private String[] listaOrganizmow;
        private Organizm.TypOrganizmu[] typOrganizmuList;
        private JList jList;

        public ListaOrganizmow(int x, int y, Punkt punkt) {
            super("Lista organizmow");
            setBounds(x, y, 100, 300);
            listaOrganizmow = new String[]{"Barszcz Sosnowskiego", "Guarana", "Mlecz", "Trawa",
                    "Wilcze jagody", "Antylopa", "Lis", "Owca", "Wilk", "Zolw", "Cyber owca"};
            typOrganizmuList = new Organizm.TypOrganizmu[]{Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO,
                    Organizm.TypOrganizmu.GUARANA,
                    Organizm.TypOrganizmu.MLECZ,
                    Organizm.TypOrganizmu.TRAWA,
                    Organizm.TypOrganizmu.WILCZE_JAGODY,
                    Organizm.TypOrganizmu.ANTYLOPA,
                    Organizm.TypOrganizmu.LIS,
                    Organizm.TypOrganizmu.OWCA,
                    Organizm.TypOrganizmu.WILK,
                    Organizm.TypOrganizmu.ZOLW,
                    Organizm.TypOrganizmu.CYBER_OWCA
            };

            jList = new JList(listaOrganizmow);
            jList.setVisibleRowCount(listaOrganizmow.length);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Organizm tmpOrganizm = FabrykaOrganizmow.StworzNowyOrganizm
                            (typOrganizmuList[jList.getSelectedIndex()], swiat, punkt);
                    swiat.DodajOrganizm(tmpOrganizm);
                    Komentator.DodajKomentarz("Stworzono " + tmpOrganizm.OrganizmToSring());
                    odswiezSwiat();
                    dispose();

                }
            });

            JScrollPane sp = new JScrollPane(jList);
            add(sp);

            setVisible(true);
        }
    }

    private class Oznaczenia extends JPanel {
        private final int ILOSC_TYPOW = 12;
        private JButton[] jButtons;

        public Oznaczenia() {
            super();
            setBounds(mainPanel.getX() + ODSTEP, mainPanel.getHeight() * 5 / 6 + ODSTEP,
                    mainPanel.getWidth() - ODSTEP * 2,
                    mainPanel.getHeight() * 1 / 6 - 2 * ODSTEP);
            setBackground(Color.WHITE);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            jButtons = new JButton[ILOSC_TYPOW];
            jButtons[0] = new JButton("Barszcz Sosnowskiego");
            jButtons[0].setBackground(new Color(155, 89, 182)); // Purple

            jButtons[1] = new JButton("Guarana");
            jButtons[1].setBackground(new Color(231, 76, 60)); // Red

            jButtons[2] = new JButton("Mlecz");
            jButtons[2].setBackground(new Color(241, 196, 15)); // Yellow

            jButtons[3] = new JButton("Trawa");
            jButtons[3].setBackground(new Color(46, 204, 113)); // Green

            jButtons[4] = new JButton("Wilcze jagody");
            jButtons[4].setBackground(new Color(52, 73, 94)); // Dark Blue

            jButtons[5] = new JButton("Antylopa");
            jButtons[5].setBackground(new Color(230, 126, 34)); // Orange

            jButtons[6] = new JButton("Czlowiek");
            jButtons[6].setBackground(new Color(52, 152, 219)); // Light Blue

            jButtons[7] = new JButton("Lis");
            jButtons[7].setBackground(new Color(211, 84, 0)); // Dark Orange

            jButtons[8] = new JButton("Owca");
            jButtons[8].setBackground(new Color(192, 57, 43)); // Dark Red

            jButtons[9] = new JButton("Wilk");
            jButtons[9].setBackground(new Color(44, 62, 80)); // Dark Gray

            jButtons[10] = new JButton("Zolw");
            jButtons[10].setBackground(new Color(46, 204, 113)); // Green (the same as Trawa)

            jButtons[11] = new JButton("Cyber owca");
            jButtons[11].setBackground(new Color(127, 140, 141)); // Silver

            for (int i = 0; i < ILOSC_TYPOW; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }
        }
    }


    private void startGame() {
        planszaGraphics = new PlanszaGraphics(swiat);
        mainPanel.add(planszaGraphics);

        komentatorGraphics = new KomentatorGraphics();
        mainPanel.add(komentatorGraphics);

        oznaczenia = new Oznaczenia();
        mainPanel.add(oznaczenia);

        odswiezSwiat();
    }

    public void odswiezSwiat() {
        planszaGraphics.odswiezPlansze();
        komentatorGraphics.odswiezKomentarzy();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public Swiat getSwiat() {
        return swiat;
    }

    public PlanszaGraphics getPlanszaGraphics() {
        return planszaGraphics;
    }

    public KomentatorGraphics getKomentatorGraphics() {
        return komentatorGraphics;
    }
}
