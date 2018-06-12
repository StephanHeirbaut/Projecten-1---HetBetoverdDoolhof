/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *Class for the collectable cards. Each players has a standard amount of cards with treasures which they have to collect. The cards and treasures are defined in this class.
 * @author Nicolas
 */
public class Doelkaart
{
    private final String schat;

    /**
     *Returns the treasure which is shown on the card
     * @return the treasure which the player has to collect
     */
    public String getSchat()
    {
        return schat;
    }
    
    /**
     *Creates a new collectable card
     * @param schat the treasure which the player has to collect
     */
    public Doelkaart(String schat)
    {
        this.schat = schat;
    }
}
