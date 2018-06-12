package domein;

import exceptions.ExistingNameException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNameException;
import exceptions.InvalidNumberException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Point of contact for the Application and class which commmunicates with all
 * other classes to get the information it needs.
 *
 * @author Nicolas
 */
public class DomeinController
{

    private final SpelRepository spelRepo;
    private Spel huidigSpel;

    /**
     *Constructs a new instanse of SpelRepository, which the DomeinController communicates with
     * @see SpelRepository#SpelRepository()
     */
    public DomeinController()
    {
        spelRepo = new SpelRepository();
    }

    /**
     * Creates a new game with given name and adds it to the database.
     *
     * @throws Exception Thrown when spelNaam is empty or already exists in the
     * database
     * @see SpelRepository#maakNieuwSpel(java.lang.String)
     * @see Spel#Spel(java.lang.String)
     */
    public void maakNieuwSpel() throws Exception
    {
        huidigSpel = new Spel();
        spelRepo.maakNieuwSpel();
    }

    /**
     * Returns a list of existing games.
     *
     * @return String The list of existing games
     * @throws Exception When the list is empty
     * @see SpelRepository#geefSpelnamen()
     * @see persistentie.SpelMapper#geefSpelNamen()
     */
    public List<String> geefSpelNamen() throws Exception
    {
        return spelRepo.geefSpelnamen();
    }

    /**
     * Sets huidigSpel to the game with given name
     *
     * @param spelID The ID used to identify the current game
     * @throws Exception When spelNaam is empty or does not exist
     * @see SpelRepository#kiesSpel(int) 
     * @see persistentie.SpelMapper#kiesSpel(int) 
     */
    public void laadSpel(int spelID) throws Exception
    {
        huidigSpel = spelRepo.laadSpel(spelID);
        List<Speler> spelers = huidigSpel.getSpelers();
        for (Speler s: spelers)
        {
            huidigSpel.voegSpelerToe(s);
        }
    }

    /**
     * Sets the amount of players to the given amount
     *
     * @param aantalSpelers The amount of players
     * @throws InvalidNumberException When the given amount is outside of the
     * given bounds
     * @see Spel#setAantalSpelers(int)
     */
    public void voerAantalSpelersIn(int aantalSpelers) throws InvalidNumberException
    {
        huidigSpel.setAantalSpelers(aantalSpelers);
    }

    /**
     * Creates a new Speler object and adds it to the database
     *
     * @param userName The name of the player
     * @param geboortejaar The birth year of the player
     * @param kleur The color of the player
     * @throws Exception Thrown when the player already exists
     * @see Spel#voegSpelerToe(java.lang.String, int, int) 
     */
    public void voegSpelerToe(String userName, int geboortejaar, int kleur) throws Exception
    {
        huidigSpel.voegSpelerToe(userName, geboortejaar, kleur);
    }

    /**
     * Returns the name player who may start the game
     *
     * @return String The name of the player who is first to play
     * @throws exceptions.InvalidNameException Thrown when an invalid name has been detected
     * @throws exceptions.InvalidNumberException Thrown when an invalid number has been detected
     * @throws exceptions.ExistingNameException Thrown when a player already exists
     * @see Spel#toonEersteAanBeurt() 
     */
    public String toonEersteAanBeurt() throws InvalidNameException, InvalidNumberException, ExistingNameException
    {
        return huidigSpel.toonEersteAanBeurt();
    }

    /**
     * Creates a new instance of Doolhof
     *
     * @see Spel#maakDoolhof()
     * @see Doolhof#Doolhof()
     */
    public void maakDoolhof()
    {
        huidigSpel.maakDoolhof();
    }

    /**
     * Returns the newly created labyrinth
     *
     * @return String The labyrinth, capable of being printed in the console
     * @see Spel#toonDoolhof() 
     */
    public String toonDoolhof()
    {
        return huidigSpel.toonDoolhof();
    }

    /**
     * Checks if the given game name already exists in the database
     *
     * @param naam The game name which is going to be checked
     * @throws ExistingNameException When the game already exists in the
     * database
     * @throws exceptions.InvalidNameException Thrown when a game with given name already exists
     * @see SpelRepository#controleerBestaandeSpelNaam(java.lang.String) 
     */
    public void controleerSpelNaam(String naam) throws ExistingNameException, InvalidNameException, SQLException
    {
        spelRepo.controleerSpelNaam(naam);
    }
    
    /**
     * Checks if a player with the given username already exists in current game
     * @param userName The name which is going to be checked
     * @throws exceptions.ExistingNameException Thrown when a player with given name already exists in the selected game
     * @throws exceptions.InvalidNameException Thrown if the player name doesn't meet the given requirements
     */
    public void controleerSpeler(String userName) throws ExistingNameException, InvalidNameException
    {
        huidigSpel.controleerSpeler(userName);
    }

    /**
     * Checks if a color is already chosen within current game
     * @param kleur The color which is going to be checked
     * @throws ExistingNameException Thrown when the current color is already chosen
     */
    public void controleerGekozenKleur(int kleur) throws ExistingNameException
    {
        huidigSpel.controleerGekozenKleur(kleur);
    }
    
    /**
     *The player starts his turn
     */
    public void speelBeurt()
    {
        huidigSpel.speelBeurt();
    }

    /**
     *the game calculates and returns who's turn it is
     * @return returns the current player
     */
    public String geefHuidigeSpeler()
    {
        return huidigSpel.geefHuidigeSpeler();
    }

    /**
     *the game returns who's the winner of the game
     * @return returns the name of the player who has won the game
     */
    public String geefWinnaar() 
    {
        return huidigSpel.geefWinnaar();
    }

    /**
     * the game shows how the board looks like
     * @return returns the board with the free card and the treasure
     */
    public String geefSpelSituatie() 
    {
        return huidigSpel.geefSpelSituatie();
    }

    /**
     * controls if it is possible to push the free card where the player wants it to go
     * @param positie the position where the player wants the free card to go
     * @param richting the direction in which way the corridor will move
     * @throws exceptions.InvalidMoveException
     */
    public void controleerPositieInschuivenGangKaart(int positie, int richting) throws InvalidMoveException 
    {
        huidigSpel.controleerPositieInschuivenGangKaart(positie,richting);
    }

    /**
     * The player changes the direction of the free card and the game shows it again
     * @return returns the free card after it turned around
     */
    public String draaiGangkaart() 
    {
        return huidigSpel.draaiGangkaart();
    }

    /**
     * the game will change the board because the player pushes in the free card
     * @param positie the position where the player wants to push the free card
     * @param richting the direction in which the corridor will move
     */
    public void schuifGangkaartIn(int positie, int richting) 
    {
        huidigSpel.schuifGangkaartIn(positie, richting);    
    }

    /**
     * The game deals the cards with treasures
     */
    public void verdeelKaarten()
    {
        huidigSpel.verdeelKaarten();
    }
    
    public ArrayList<Integer> toonMogelijkeRichtingen()
    {
        return huidigSpel.toonMogelijkeRichtingen();
    }

    public void verplaats(int richting) throws InvalidMoveException
    {
        huidigSpel.verplaats(richting);
    }
    
    public String geefHuidigeSchat()
    {
        return huidigSpel.geefHuidigeSchat();
    }

    public boolean heeftSchatGevonden()
    {
        return huidigSpel.heeftSchatGevonden();
    }

    public void verwijderSchat()
    {
        huidigSpel.verwijderSchat();
    }

    public Spel getHuidigSpel() {
        return huidigSpel;
    }

    public void setHuidigSpel(Spel huidigSpel) 
    {
        this.huidigSpel = huidigSpel;
    }

    public void bewaarSpel(String naam) throws InvalidNameException, SQLException
    {
        huidigSpel.setSpelNaam(naam);
        spelRepo.bewaarSpel(huidigSpel);
    }
    
}
