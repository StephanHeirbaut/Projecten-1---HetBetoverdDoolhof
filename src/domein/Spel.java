package domein;

import exceptions.ExistingNameException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNameException;
import exceptions.InvalidNumberException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import locale.Taal;

/**
 * This class contains methods to create a game and to get the info of a game
 *
 * @author Belle
 */
public class Spel
{

    private String spelNaam;
    private int aantalSpelers;
    private Doolhof doolhof;
    private List<Speler> spelers;
    private Speler huidigeSpeler;
    private boolean eindeSpel;
    private Schatten sch;

    /**
     *
     * @throws exceptions.InvalidNameException Thrown if the name of the game is invalid
     * @throws exceptions.InvalidNumberException Thrown if the amount of players is invalid
     */
    public Spel() throws InvalidNameException, InvalidNumberException
    {
        this("tempName", new ArrayList<>(), null, 2, null);
    }

    /**
     *
     * @param spelNaam
     * @param spelers
     * @param huidigeSpeler
     * @param aantalSpelers The number of players who are playing in the game
     * @param doolhof
     * @throws exceptions.InvalidNameException Thrown if the name of the game is invalid
     * @throws exceptions.InvalidNumberException Thrown if the amount of players is invalid
     */
    public Spel(String spelNaam, List<Speler> spelers, Speler huidigeSpeler, int aantalSpelers, Doolhof doolhof) throws InvalidNameException, InvalidNumberException
    {
        this.spelNaam = spelNaam;
        controleerAantalSpelers(aantalSpelers);
        this.aantalSpelers = aantalSpelers;
        this.spelers = spelers;
        this.huidigeSpeler = huidigeSpeler;
        this.doolhof = doolhof;
        sch = new Schatten();
    }

    public void setSpelNaam(String spelNaam) throws InvalidNameException
    {
        controleerSpelNaam(spelNaam);
        this.spelNaam = spelNaam;
    }

    /**
     *
     * @return returns a list of players
     */
    public List<Speler> getSpelers()
    {
        return spelers;
    }

    /**
     *
     * @return int returns how many players there are within the game
     */
    public int getAantalSpelers()
    {
        return aantalSpelers;
    }

    /**
     * this method is used to set the class attribute aantalSpelers
     *
     * @param aantalSpelers the value sets the number of players within the game
     * @throws InvalidNumberException there can only be 2-4 players within this game, in case more or less players are
     * entered an exception is thrown
     */
    public void setAantalSpelers(int aantalSpelers) throws InvalidNumberException
    {
        controleerAantalSpelers(aantalSpelers);
        this.aantalSpelers = aantalSpelers;
    }

    /**
     *
     * @return String returns the name of the game
     */
    public String getSpelNaam()
    {
        return spelNaam;
    }

    /**
     *
     * @return returns the player who's on turn
     */
    public Speler getHuidigeSpeler()
    {
        return huidigeSpeler;
    }

    /**
     *
     * @param huidigeSpeler the player who's on turn
     */
    public void setHuidigeSpeler(Speler huidigeSpeler)
    {
        this.huidigeSpeler = huidigeSpeler;
    }

    /**
     *
     * @return returns if the game is over or not
     */
    public boolean isEindeSpel()
    {
        return eindeSpel;
    }

    /**
     * method to set eindeSpel to true or false (end of the game or not)
     *
     * @param eindeSpel parameter which knows if the game has ended or not
     */
    public void setEindeSpel(boolean eindeSpel)
    {
        this.eindeSpel = eindeSpel;
    }

    /**
     *
     * @return
     */
    public Doolhof getDoolhof()
    {
        return doolhof;
    }

    /**
     *
     * @param spelNaam the name of the game is given to the method which is than checked for exceptions
     * @throws InvalidNameException incase the value of the game is null or any empty string an exception is thrown
     */
    private void controleerSpelNaam(String spelNaam) throws InvalidNameException
    {
        if (spelNaam == null || spelNaam.equals(""))
        {
            throw new InvalidNameException(Taal.vertaal("legeNaam"));
        }

    }

    /**
     * this method checks for exceptions
     *
     * @param aantalSpelers this value is used to checked for exceptions
     * @throws InvalidNumberException incase the value is less than 2 of greater than 4
     */
    private void controleerAantalSpelers(int aantalSpelers) throws InvalidNumberException
    {
        if (aantalSpelers < 2 || aantalSpelers > 4)
        {
            throw new InvalidNumberException();
        }
    }

    /**
     * This method check every player in the class attribute spelers for its age. The name of the youngest player is
     * returned.
     *
     * @return this contains the name of player which plays the first turn
     * @throws exceptions.InvalidNameException Thrown when the name is invalid
     * @throws exceptions.InvalidNumberException Thrown when an invalid number has been inserted
     * @throws exceptions.ExistingNameException Thrown if a player already exists
     */
    public String toonEersteAanBeurt() throws InvalidNameException, InvalidNumberException, ExistingNameException
    {
        this.spelers.sort(Collections.reverseOrder(Comparator.comparing(Speler::getGeboortejaar)).thenComparing(Speler::getUserName));
        return this.spelers.get(0).getUserName();
    }

    /**
     *
     * @param userName the name of the player
     * @param geboortejaar the date of birth of the player
     * @param kleur the color of the player
     * @throws exceptions.InvalidNameException Thrown when the name is invalid
     * @throws exceptions.InvalidNumberException Thrown when an invalid number has been inserted
     * @throws exceptions.ExistingNameException Thrown if a player already exists
     * @throws Exception Thrown if there is an other exception
     */
    public void voegSpelerToe(String userName, int geboortejaar, int kleur) throws InvalidNameException, InvalidNumberException, ExistingNameException, Exception
    {
        Speler speler = new Speler(userName, geboortejaar, kleur);
        spelers.add(speler);
        voegSpelerToe(speler);
    }

    public void voegSpelerToe(Speler speler)
    {
        doolhof.voegSpelerToe(speler);
    }

    /**
     * checks if the name you've given is correct
     *
     * @param userName the name of the player
     * @throws exceptions.ExistingNameException Thrown if a player already exists
     * @throws exceptions.InvalidNameException Thrown when the name is invalid
     */
    public void controleerSpeler(String userName) throws ExistingNameException, InvalidNameException
    {
        if (spelers != null)
        {
            for (Speler s : spelers)
            {
                if (s.getUserName().equals(userName))
                {
                    throw new ExistingNameException();
                }
            }
        }
        Pattern p = Pattern.compile("\\p{Punct}");
        Matcher m = p.matcher(userName);
        if (m.find())
        {
            throw new InvalidNameException(Taal.vertaal("ongeldigeNaam2"));
        }

    }

    /**
     * checks if the color chosen is available
     *
     * @param kleur the color of the player
     * @throws exceptions.ExistingNameException Thrown if a player already exists
     */
    public void controleerGekozenKleur(int kleur) throws ExistingNameException
    {
        if (spelers != null)

        {
            for (Speler s : spelers)
            {
                if (s.getKleur() == kleur)
                {
                    throw new ExistingNameException(Taal.vertaal("gekozenKleur"));
                }
            }
        }
    }

    /**
     * this initializes the class attribute doolhof and gives a string representation of the maze
     *
     * @see domein.Doolhof
     */
    public void maakDoolhof()
    {
        this.doolhof = new Doolhof();
        this.doolhof.maakNieuweDoolhof();
    }

    /**
     *
     * @return returns the board
     */
    public String toonDoolhof()
    {
        return this.doolhof.toString();
    }

    /**
     * Method to start a player's turn
     */
    public void speelBeurt()
    {
        if (spelers.indexOf(huidigeSpeler) + 1 >= spelers.size())
        {
            this.setHuidigeSpeler(spelers.get(0));
        } else
        {
            this.setHuidigeSpeler(spelers.get(spelers.indexOf(huidigeSpeler) + (1 % aantalSpelers)));
        }
    }

    /**
     * Method to tell who's on turn
     *
     * @return returns the current player who's turn it is
     */
    public String geefHuidigeSpeler()
    {
        return huidigeSpeler.getUserName() + String.format(" %s", Taal.vertaal("jouwBeurt"));
    }

    /**
     * Method to tell which player has won
     *
     * @return returns the winner of the game
     */
    public String geefWinnaar()
    {
        String winnaar = "";
        if (isEindeSpel())
        {
            winnaar = huidigeSpeler.getUserName() + String.format(" %s", Taal.vertaal("winnaar"));
        }
        return winnaar;
    }

    /**
     * Method that shows you the free card and which treasure the player has to hunt
     *
     * @return returns the free card and the treasure to the player who's on turn
     */
    public String geefSpelSituatie()
    {
        String res = "";
        res += doolhof.toString();
        res += String.format("%n%s%n", Taal.vertaal("vrijeGangkaart"));
        res += doolhof.getVrijeGangkaart().toString();
        res += String.format("%n%s%n", Taal.vertaal("teZoekenSchat"));
        res += String.format("%s (%c) %n", Taal.vertaal(huidigeSpeler.getHudigeDoelkaart().getSchat()), huidigeSpeler.getHudigeDoelkaart().getSchat().charAt(0));
        return res;
    }

    /**
     * Method to control if the player can insert the card in the chosen location
     *
     * @param positie the position where the player wants to push the free card
     * @param richting the direction in which the corridor will move
     * @throws exceptions.InvalidMoveException
     */
    public void controleerPositieInschuivenGangKaart(int positie, int richting) throws InvalidMoveException
    {
        doolhof.controleerPositieInschuivenGangKaart(positie, richting);
    }

    /**
     * Method to turn a card around
     *
     * @return returns the free card after the player changed its orientation
     */
    public String draaiGangkaart()
    {
        return doolhof.draaiGangkaart();
    }

    /**
     * Method to insert a card and change the corridor
     *
     * @param positie the position where the player wants to push the free card
     * @param richting the direction in which the corridor will move
     */
    public void schuifGangkaartIn(int positie, int richting)
    {
        Speler temp = doolhof.schuifGangkaartIn(positie, richting);
        if (temp != null)
        {
            int i = 0;
            for (Speler s : spelers)
            {
                if (s == temp)
                {
                    i = spelers.indexOf(s);
                }
            }
            spelers.set(i, temp);
        }
    }

    /**
     * deals the cards with treasures to all the players
     */
    public void verdeelKaarten()
    {
        sch = new Schatten();
        sch.schudKaarten();
        List<String> schatten = sch.getSchatten();
        for (Speler s : spelers)
        {
            s.addDoelkaarten(aantalSpelers, schatten, spelers.indexOf(s));
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> toonMogelijkeRichtingen()
    {
        return doolhof.toonMogelijkeRichtingen(huidigeSpeler);
    }

    /**
     *
     * @param richting
     * @throws InvalidMoveException
     */
    public void verplaats(int richting) throws InvalidMoveException
    {
        huidigeSpeler.setPositie(doolhof.verplaats(richting, huidigeSpeler));

    }

    /**
     *
     * @return
     */
    public boolean heeftSchatGevonden()
    {
        int[] pos = huidigeSpeler.getPositie();
        String schat = huidigeSpeler.getHudigeDoelkaart().getSchat();
        return doolhof.heeftSchatGevonden(pos, schat);
    }

    /**
     *
     * @return
     */
    public String geefHuidigeSchat()
    {
        if (!huidigeSpeler.getDoelkaarten().isEmpty())
        {
            return huidigeSpeler.getHudigeDoelkaart().getSchat();
        } else
        {
            setEindeSpel(true);
            return null;
        }
    }

    /**
     *
     */
    public void verwijderSchat()
    {
        huidigeSpeler.verwijderSchat();
    }
}
