package domein;

import exceptions.ExistingNameException;
import exceptions.InvalidNameException;
import exceptions.InvalidNumberException;
import java.util.ArrayList;
import java.util.List;
import locale.Taal;

/**
 * Class which contains methods to create a new player or to get the info of an already existing player
 *
 * @author Belle
 */
public class Speler
{

    private final String userName;
    private final int geboortejaar;
    private final int kleur;
    private List<Doelkaart> doelkaarten;
    private int[] positie;
    private int userID;

    /**
     *
     * @param userName this value is used to set the class attribute userName;
     * @param geboortejaar this value is used to set the class attribute geboortejaar;
     * @param kleur this value is used to set the class attribute kleur;
     * @throws InvalidNameException When an invalid name has been entered
     * @throws InvalidNumberException When an invalid number has been entered
     * @throws ExistingNameException When an existing name has been entered
     */
    public Speler(String userName, int geboortejaar, int kleur) throws InvalidNameException, InvalidNumberException, ExistingNameException
    {
        this.userID = 0;
        controleerUserName(userName);
        this.userName = userName;
        controleerGeboorteJaar(geboortejaar);
        this.geboortejaar = geboortejaar;
        controleerKleur(kleur);
        this.kleur = kleur;
        doelkaarten = new ArrayList<>();
        switch (kleur)
        {
            case 1:
                this.positie = new int[]
                {
                    6, 0
                };
                break;
            case 2:
                this.positie = new int[]
                {
                    0, 0
                };
                break;
            case 3:
                this.positie = new int[]
                {
                    6, 6
                };
                break;
            case 4:
                this.positie = new int[]
                {
                    0, 6
                };
                break;
        }

    }
    
    public Speler(int userID, String userName, int geboortejaar, int kleur, int[] positie, List<Doelkaart> doelkaarten) throws InvalidNameException, InvalidNumberException, ExistingNameException
    {
        this(userName, geboortejaar, kleur);
        this.doelkaarten = doelkaarten;
        this.positie = positie;
        this.userID = userID;
    }

    public int getUserID()
    {
        return userID;
    }

    /**
     *
     * @return returns the value of class attribute geboortejaar
     */
    public int getGeboortejaar()
    {
        return geboortejaar;
    }

    /**
     *
     * @return returns the value of class attribute kleur
     */
    public int getKleur()
    {
        return kleur;
    }

    /**
     *
     * @return String returns the value of class attribute userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     *
     * @return returns the value of class attribute doelKaarten
     */
    public List<Doelkaart> getDoelkaarten()
    {
        return doelkaarten;
    }

    public Doelkaart getHudigeDoelkaart()
    {
        if (!(doelkaarten.isEmpty()))
        {
            return doelkaarten.get(doelkaarten.size() - 1);
        } else
        {
            return null;
        }
    }

    public int[] getPositie()
    {
        return positie;
    }

    public void setPositie(int[] positie)
    {
        this.positie = positie;
    }

    /**
     *
     * @param aantalSpelers the number of players in the game
     * @param schatten a list of treasures
     * @param spelernr the (id)number of the player
     */
    public void addDoelkaarten(int aantalSpelers, List<String> schatten, int spelernr)
    {
        for (int i = (0 + ((24 / aantalSpelers) * spelernr)); i < ((24 / aantalSpelers) * (spelernr + 1)); i++)
        {
            doelkaarten.add(new Doelkaart(schatten.get(i)));
        }

    }

    /**
     *
     * @param naam this value if used to check for exceptions
     * @throws InvalidNameException throws an exception is case the value of naam is null of empty
     */
    private void controleerUserName(String naam) throws InvalidNameException
    {
        if (naam == null || naam.equals(""))
        {
            throw new InvalidNameException(Taal.vertaal("legeNaam"));
        }

    }

    /**
     *
     * @param geboortejaar the value which is checked for exceptions
     * @throws InvalidNumberException in case the age of the player is greater than 2009 or less then 1926 an exception
     * is thrown
     */
    private void controleerGeboorteJaar(int geboortejaar) throws InvalidNumberException
    {
        if (geboortejaar > 2009 || geboortejaar < 1926)
        {
            throw new InvalidNumberException(Taal.vertaal("ongeldigeLeeftijd"));
        }
    }

    /**
     *
     * @param kleur this value is check for exceptions
     * @throws InvalidNameException in case the value is smaller dan 0 or greater then 4 an exception is thrown
     * @throws ExistingNameException if the value already color is already chosen an exception is thrown
     */
    private void controleerKleur(int kleur) throws InvalidNameException, ExistingNameException
    {
        if (kleur < 0 || kleur > 4)
        {
            throw new InvalidNameException(Taal.vertaal("ongeldigGetal"));
        }
    }

    public void verwijderSchat()
    {
        this.doelkaarten.remove((this.doelkaarten.size()-1));
    }

}
