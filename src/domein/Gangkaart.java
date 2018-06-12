/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 * Abstract class which is used to fill the labyrinth
 *
 * @see Doolhof#Doolhof()
 * @see HoekKaart
 * @see TKaart
 * @see RechtKaart
 * @author Nicolas
 */
public abstract class Gangkaart
{

    private final boolean vast;
    private int orientatie;
    private final String schat;
    private Speler speler;

    /**
     * Constructs a new instance of Gangkaart
     *
     * @param orientatie The orientation of the card
     * @param vast Boolean to determine whether the card is moveable or not
     */
    public Gangkaart(int orientatie, boolean vast)
    {
        this.orientatie = orientatie;
        this.vast = vast;
        this.schat = "";
    }

    /**
     * Constructs a new instance of Gangkaart with a treasure on it
     * @param orientatie The orientation of the card
     * @param schat The treasure on the card
     * @param vast Boolean to determine whether the card is moveable or not
     */
    public Gangkaart(int orientatie, String schat, boolean vast)
    {
        this.orientatie = orientatie;
        this.schat = schat;
        this.vast = vast;
    }

    /**
     * Determines whether an object is stuck to the labyrinth or not
     *
     * @return True if it is, false if it isn't
     */
    public boolean isVast()
    {
        return vast;
    }

    /**
     * Getter for class attribute schat
     *
     * @return String The class attribute schat, which defines the treasure on a Gangkaart object
     */
    public String getSchat()
    {
        return schat;
    }

    /**
     * Getter for class attribute orientatie
     *
     * @return String The class attribute orientatie, which determines the orientation of a Gangkaart object
     */
    public int getOrientatie()
    {
        return orientatie;
    }

    /**
     * Setter for class attribute orientatie
     *
     * @param orientatie String The class attribute orientatie, which determines the orientation of a Gangkaart object
     */
    public void setOrientatie(int orientatie)
    {
        this.orientatie = orientatie;
    }

    /**
     * Getter for the class attribute speler
     * @return returns the player
     */
    public Speler getSpeler()
    {
        return speler;
    }

    /**
     * Setter for the class attribute speler
     * @param speler The player on the card
     */
    public void setSpeler(Speler speler)
    {
        this.speler = speler;
    }

    @Override
    public abstract String toString();

    /**
     * Method to print the first line of a card
     * @return an array of string
     */
    public abstract String toStringRij1();

    /**
    * Method to print the second line of a card
     * @return an array of string
     */
    public abstract String toStringRij2();

    /**
     * Method to print the third line of a card
     * @return an array of string
     */
    public abstract String toStringRij3();
    
    public abstract boolean kanLinks();
    public abstract boolean kanRechts();
    public abstract boolean kanBoven();
    public abstract boolean kanOnder();
}
