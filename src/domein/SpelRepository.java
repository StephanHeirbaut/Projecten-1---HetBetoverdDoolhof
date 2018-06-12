package domein;

import exceptions.ExistingNameException;
import exceptions.InvalidNameException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import locale.Taal;
import persistentie.SpelMapper;

/**
 * Class that contains methods to load a game or start a new one.
 * @author Belle
 */
public class SpelRepository
{

    private final SpelMapper sm;
    private List<Spel> spellen;

    /**
     * Creates a new object
     */
    public SpelRepository()
    {
        sm = new SpelMapper();
        spellen = new ArrayList<>();
    }

    /**
     * Method used when you want to load a game
     * @param spelID The id a game has been given when it was created
     * @return returns the chosen game
     * @throws Exception thrown when something went wrong
     */
    public Spel laadSpel(int spelID) throws Exception
    {
        return sm.laadSpel(spelID);
    }

    /**
     * Gives back a list with existing games when you want to load a game
     * @return returns the list of games which you can choose from
     * @throws Exception thrown when something went wrong
     */
    public List<String> geefSpelnamen() throws Exception
    {
        List<String> list = sm.geefSpelNamen();
        return list;
    }

    /**
     * Constructor to create a new game
     * @throws Exception thrown when something went wrong
     */
    public void maakNieuwSpel() throws Exception
    {
        Spel spel = new Spel();
        spellen.add(spel);
    }

    /**
     * Method to control if the name is correct
     * @param naam The name of the game
     * @throws exceptions.ExistingNameException Thrown when a player already exists
     * @throws exceptions.InvalidNameException Thrown when an invalid name has been detected
     */
    public void controleerSpelNaam(String naam) throws ExistingNameException, InvalidNameException, SQLException
    {
        if (sm.controleerSpelNaam(naam))
        {
            throw new ExistingNameException();
        }
        Pattern p = Pattern.compile("\\d{2}[A-Za-z]{8,}");
        Matcher m = p.matcher(naam);
        if (!m.matches())
        {
            throw new InvalidNameException(Taal.vertaal("ongeldigeNaam3"));
        }
    }

    public void bewaarSpel(Spel huidigSpel) throws SQLException
    {
        sm.bewaarSpel(huidigSpel);
    }

}
