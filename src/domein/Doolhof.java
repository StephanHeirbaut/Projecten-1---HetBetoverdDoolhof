/*
 * Created by JVH 
 * The one and only jellyfish
 */
package domein;

import exceptions.InvalidMoveException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains methods to construct and show a labyrinth
 *
 * @author jochem
 */
public class Doolhof
{

    /**
     * Used to print a string in a red color
     */
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * Used to reset the color in the string back to black
     */
    public static final String ANSI_RESET = "\u001B[0m";
    private Gangkaart[][] gangkaarten;
    private Gangkaart vrijeGangkaart;
    private int vorigePositie;
    private int vorigeRichting;
    private Schatten sch;

    /**
     * Constructs a new labyrinth with standard cards which cannot be moved
     */
    public Doolhof()
    {
        this(255, 255, new RechtKaart(1, false));
    }

    public Doolhof(int vorigePositie, int vorigeRichting, Gangkaart vrijeGangkaart)
    {
        sch = new Schatten();
        List<String> schatten = sch.getSchatten();
        this.vrijeGangkaart = vrijeGangkaart;
        //Nieuwe doolhof aanmaken
        this.gangkaarten = new Gangkaart[7][7];
        //Vaste vakjes invoegen
        this.gangkaarten[0][0] = new HoekKaart(1, true);
        this.gangkaarten[0][2] = new TKaart(1, schatten.get(0), true);
        this.gangkaarten[0][4] = new TKaart(1, schatten.get(1), true);
        this.gangkaarten[0][6] = new HoekKaart(2, true);

        this.gangkaarten[2][0] = new TKaart(4, schatten.get(2), true);
        this.gangkaarten[2][2] = new TKaart(4, schatten.get(3), true);
        this.gangkaarten[2][4] = new TKaart(1, schatten.get(4), true);
        this.gangkaarten[2][6] = new TKaart(2, schatten.get(5), true);

        this.gangkaarten[4][0] = new TKaart(4, schatten.get(6), true);
        this.gangkaarten[4][2] = new TKaart(3, schatten.get(7), true);
        this.gangkaarten[4][4] = new TKaart(2, schatten.get(8), true);
        this.gangkaarten[4][6] = new TKaart(2, schatten.get(9), true);

        this.gangkaarten[6][0] = new HoekKaart(4, true);
        this.gangkaarten[6][2] = new TKaart(3, schatten.get(10), true);
        this.gangkaarten[6][4] = new TKaart(3, schatten.get(11), true);
        this.gangkaarten[6][6] = new HoekKaart(3, true);

        this.vorigePositie = vorigePositie;
        this.vorigeRichting = vorigeRichting;

    }

    public void setGangkaarten(Gangkaart[][] gangkaarten)
    {
        this.gangkaarten = gangkaarten;
    }

    /**
     * Fills the emtpy spots in the created labyrinth with random cards. The amount of different cards is predefined,
     * however.
     */
    public void maakNieuweDoolhof()
    {
        /*hzs: hoek zonder schat
        hms: hoek met schat
        r: recht stuk
        t: T-kruispunt met schat*/
        //vaste aantallen vakjes
        int hzs = 10, hms = 6, r = 11, t = 6;
        List<String> schatten = sch.getSchatten();
        List<String> tSchatten = schatten.subList(12, 18);
        Collections.shuffle(tSchatten);
        List<String> hSchatten = schatten.subList(18, 24);
        Collections.shuffle(hSchatten);

        int hKaartTeller = 0, tKaartTeller = 0;

        //losse vakken random invullen
        //oneven rijen
        for (int h = 0; h <= 6; h += 2)
        {
            for (int i = 1; i <= 5; i += 2)
            {
                int kaart = (int) (1 + (Math.random() * 4));
                switch (kaart)
                {
                    case 1:
                        if (hzs > 0)
                        {
                            this.gangkaarten[h][i] = new HoekKaart((int) Math.floor(Math.random() * 4) + 1, false);
                            hzs--;
                        } else
                        {
                            i--;
                        }
                        break;
                    case 2:
                        if (hms > 0)
                        {
                            this.gangkaarten[h][i] = new HoekKaart((int) Math.floor(Math.random() * 4) + 1, hSchatten.get(hKaartTeller), false);
                            hms--;
                            hKaartTeller++;
                        } else
                        {
                            i--;
                        }
                        break;
                    case 3:
                        if (r > 0)
                        {
                            this.gangkaarten[h][i] = new RechtKaart((int) Math.floor(Math.random() * 4) + 1, false);
                            r--;
                        } else
                        {
                            i--;
                        }
                        break;
                    case 4:
                        if (t > 0)
                        {
                            this.gangkaarten[h][i] = new TKaart((int) Math.floor(Math.random() * 4) + 1, tSchatten.get(tKaartTeller), false);
                            t--;
                            tKaartTeller++;
                        } else
                        {
                            i--;
                        }
                        break;

                }
            }
        }

        //even rijen
        for (int j = 1; j <= 5; j += 2)
        {
            for (int i = 0; i <= 6; i++)
            {
                int kaart = (int) (1 + (Math.random() * 4));
                switch (kaart)
                {
                    case 1:
                        if (hzs > 0)
                        {
                            this.gangkaarten[j][i] = new HoekKaart((int) Math.floor(Math.random() * 4) + 1, false);
                            hzs--;
                        } else
                        {
                            i--;
                        }
                        break;
                    case 2:
                        if (hms > 0)
                        {
                            this.gangkaarten[j][i] = new HoekKaart((int) Math.floor(Math.random() * 4) + 1, hSchatten.get(hKaartTeller), false);
                            hms--;
                            hKaartTeller++;
                        } else
                        {
                            i--;
                        }
                        break;
                    case 3:
                        if (r > 0)
                        {
                            this.gangkaarten[j][i] = new RechtKaart((int) Math.floor(Math.random() * 4) + 1, false);
                            r--;
                        } else
                        {
                            i--;
                        }
                        break;
                    case 4:
                        if (t > 0)
                        {
                            this.gangkaarten[j][i] = new TKaart((int) Math.floor(Math.random() * 4) + 1, tSchatten.get(tKaartTeller), false);
                            t--;
                            tKaartTeller++;
                        } else
                        {
                            i--;
                        }
                        break;

                }
            }
        }
    }

    /**
     *
     * @param speler an object of speler
     */
    public void voegSpelerToe(Speler speler)
    {
        int[] pos = speler.getPositie();
        this.gangkaarten[pos[0]][pos[1]].setSpeler(speler);
    }

    /**
     * Converts the labyrinth into a String, which then can be printed in the DoolhofApplicatie
     *
     * @return The String with the full labyrinth
     * @see ui.DoolhofApplicatie
     */
    @Override
    public String toString()
    {
        String res = "";
        int teller = 1;
        res += "\nrij:    " + ANSI_RED + "1" + ANSI_RESET + "       2       " + ANSI_RED + "3" + ANSI_RESET + "       4       " + ANSI_RED + "5" + ANSI_RESET + "       6       " + ANSI_RED + "7" + ANSI_RESET + "  \n";
        for (Gangkaart gg[] : gangkaarten)
        {
            res += "     ";
            for (Gangkaart g : gg)
            {
                res += String.format("%s ", g.toStringRij1());
            }
            res += "\n";
            res += " " + teller + "   ";
            for (Gangkaart g : gg)
            {
                res += String.format("%s ", g.toStringRij2());
            }
            res += "  " + teller;
            res += "\n";
            res += "     ";
            for (Gangkaart g : gg)
            {
                res += String.format("%s ", g.toStringRij3());
            }
            res += "\n";
            teller++;
        }
        res += "\nrij:    " + ANSI_RED + "1" + ANSI_RESET + "       2       " + ANSI_RED + "3" + ANSI_RESET + "       4       " + ANSI_RED + "5" + ANSI_RESET + "       6       " + ANSI_RED + "7" + ANSI_RESET + "  \n";
        return res;
    }

    /**
     *
     * @return returns the free card
     */
    public Gangkaart getVrijeGangkaart()
    {
        return vrijeGangkaart;
    }

    /**
     *
     * @param positie the position where the player wants to push the free card
     * @param richting the direction in which the corridor will move
     * @throws exceptions.InvalidMoveException Thrown when the card is inserted in a wrong row/column
     */
    public void controleerPositieInschuivenGangKaart(int positie, int richting) throws InvalidMoveException
    {
        if ((positie == 2 || positie == 4 || positie == 6) && (richting > 0 && richting <= 4))
        {
            if (richting % 2 == 0)
            {
                if ((positie != this.vorigePositie) || (positie == this.vorigePositie && (richting - 1 != this.vorigeRichting)))
                {
                    this.vorigePositie = positie;
                    this.vorigeRichting = richting;
                } else
                {
                    throw new InvalidMoveException();
                }
            } else if ((positie != this.vorigePositie) || (positie == this.vorigePositie && (richting + 1 != vorigeRichting)))
            {
                this.vorigePositie = positie;
                this.vorigeRichting = richting;
            } else
            {
                throw new InvalidMoveException();
            }
            /*Exception hieronder ontbrak, dus vandaar dat hij het goedrekende. De controle werd effectief uitgevoerd,
            maar er werd geen exception gegooid.*/
        }else throw new InvalidMoveException();

    }

    /**
     * the player changes the orientation/direction of the free card
     *
     * @return returns the free card after the player changed its orientation
     */
    public String draaiGangkaart()
    {
        if (vrijeGangkaart.getOrientatie() == 4)
        {
            vrijeGangkaart.setOrientatie(1);
        } else
        {
            vrijeGangkaart.setOrientatie((vrijeGangkaart.getOrientatie() + 1));
        }

        return vrijeGangkaart.toString();
    }

    /**
     * the game pushes the free card in the chosen place and changes the board
     *
     * @param positie the position where the player wants to push the free card
     * @param richting the direction in which the corridor will move
     */
    public Speler schuifGangkaartIn(int positie, int richting)
    {
        positie = positie - 1;
        Gangkaart oudeKaart;
        switch (richting)
        {
            case 1:
                for (int rij = 0; rij < gangkaarten.length; rij++)
                {
                    oudeKaart = gangkaarten[positie][rij];
                    gangkaarten[positie][rij] = vrijeGangkaart;
                    vrijeGangkaart = oudeKaart;
                }
                break;
            case 2:
                for (int rij = gangkaarten.length - 1; rij >= 0; rij--)
                {
                    oudeKaart = gangkaarten[positie][rij];
                    gangkaarten[positie][rij] = vrijeGangkaart;
                    vrijeGangkaart = oudeKaart;
                }
                break;
            case 3:
                for (int kolom = 0; kolom < gangkaarten.length; kolom++)
                {
                    oudeKaart = gangkaarten[kolom][positie];
                    gangkaarten[kolom][positie] = vrijeGangkaart;
                    vrijeGangkaart = oudeKaart;
                }
                break;
            case 4:
                for (int kolom = gangkaarten.length - 1; kolom >= 0; kolom--)
                {
                    oudeKaart = gangkaarten[kolom][positie];
                    gangkaarten[kolom][positie] = vrijeGangkaart;
                    vrijeGangkaart = oudeKaart;
                }
                break;
        }
        Speler speler = null;
        if (vrijeGangkaart.getSpeler() != null)
        {
            int[] pos = {-1,-1};
            speler = vrijeGangkaart.getSpeler();
            vrijeGangkaart.setSpeler(null);
            switch(richting)
            {
                case 1: pos[0] = positie;
                        pos[1] = 0;
                        speler.setPositie(pos);
                        gangkaarten[positie][0].setSpeler(speler);
                        break;
                case 2: pos[0] = positie;
                        pos[1] = 6;
                        speler.setPositie(pos);
                        gangkaarten[positie][6].setSpeler(speler);
                        break;
                case 3: pos[0] = 0;
                        pos[1] = positie;
                        gangkaarten[0][positie].setSpeler(speler);
                        break;
                case 4: pos[0] = 6;
                        pos[1] = positie;
                        speler.setPositie(pos);
                        gangkaarten[6][positie].setSpeler(speler);
                        break;
            }
        }
        return speler;

    }

    public ArrayList<Integer> toonMogelijkeRichtingen(Speler speler)
    {
        ArrayList<Integer> array = new ArrayList<>();
        int[] pos = speler.getPositie();
        Gangkaart huidigePositie = gangkaarten[pos[0]][pos[1]];
        if (huidigePositie.kanLinks())
        {
            array.add(1);
        }
        if (huidigePositie.kanRechts())
        {
            array.add(2);
        }
        if (huidigePositie.kanBoven())
        {
            array.add(3);
        }
        if (huidigePositie.kanOnder())
        {
            array.add(4);
        }
        return array;
    }

    public int[] verplaats(int richting, Speler speler) throws InvalidMoveException
    {
        int[] pos = speler.getPositie();
        Gangkaart huidigePositie = gangkaarten[pos[0]][pos[1]];
        switch (richting)
        {
            case 1:
                if ((pos[1] - 1) >= 0 && gangkaarten[pos[0]][pos[1] - 1].kanRechts())
                {
                    gangkaarten[pos[0]][pos[1]].setSpeler(null);
                    gangkaarten[pos[0]][pos[1] - 1].setSpeler(speler);
                    pos[1] -= 1;
                } else
                {
                    throw new InvalidMoveException();
                }
                break;
            case 2:
                if ((pos[1] + 1) <= 6 && gangkaarten[pos[0]][pos[1] + 1].kanLinks())
                {
                    gangkaarten[pos[0]][pos[1]].setSpeler(null);
                    gangkaarten[pos[0]][pos[1] + 1].setSpeler(speler);
                    pos[1] += 1;
                } else
                {
                    throw new InvalidMoveException();
                }
                break;
            case 3:
                if ((pos[0] - 1) >= 0 && gangkaarten[pos[0] - 1][pos[1]].kanOnder())
                {
                    gangkaarten[pos[0]][pos[1]].setSpeler(null);
                    gangkaarten[pos[0] - 1][pos[1]].setSpeler(speler);
                    pos[0] -= 1;
                } else
                {
                    throw new InvalidMoveException();
                }
                break;
            case 4:
                if ((pos[0] + 1) <= 6 && gangkaarten[pos[0] + 1][pos[1]].kanBoven())
                {
                    gangkaarten[pos[0]][pos[1]].setSpeler(null);
                    gangkaarten[pos[0] + 1][pos[1]].setSpeler(speler);
                    pos[0] += 1;
                } else
                {
                    throw new InvalidMoveException();
                }
                break;
        }
        return pos;
    }

    public boolean heeftSchatGevonden(int[] pos, String schat)
    {
        if (this.gangkaarten[pos[0]][pos[1]].getSchat().equals(schat))
        {
            return true;
        } else
        {
            return false;
        }
    }

    public int getVorigePositie()
    {
        return vorigePositie;
    }

    public int getVorigeRichting()
    {
        return vorigeRichting;
    }

    public Gangkaart[][] getGangkaarten()
    {
        return gangkaarten;
    }
}
