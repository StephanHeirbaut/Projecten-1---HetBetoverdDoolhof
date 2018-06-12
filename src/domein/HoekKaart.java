/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 * A specialisation of Gangkaart, which it inherits most methods from.
 *
 * @see Gangkaart
 * @author Nicolas
 */
public class HoekKaart extends Gangkaart
{

    /**
     * Creates an instance of HoekKaart
     *
     * @param orientatie The orientation of given card
     * @param schat The treasure of given card
     * @param vast Boolean which determines whether the card is stuck to the labyrinth or not
     * @see Gangkaart#Gangkaart(int, java.lang.String, boolean)
     */
    public HoekKaart(int orientatie, String schat, boolean vast)
    {
        super(orientatie, schat, vast);
    }

    /**
     * Creates an instance of HoekKaart
     *
     * @param orientatie The orientation of given card
     * @param vast Boolean which determines whether the card is stuck to the labyrinth or not
     * @see Gangkaart#Gangkaart(int, boolean)
     */
    public HoekKaart(int orientatie, boolean vast)
    {
        super(orientatie, vast);
    }

    /**
     * Gives the String representation of a Hoekkaart
     *
     * @return returns a HoekKaart
     */
    private String[][] geefArray()
    {
        String[][] res = new String[3][3];
        int orientatie = super.getOrientatie();

        String sp;
        if (super.getSpeler() != null)
        {
            sp = String.format(" %c ", super.getSpeler().getUserName().charAt(0));
        } else if (!(super.getSchat().equals("")) && super.getSchat() != null)
        {
            sp = String.format(" %c ", super.getSchat().charAt(0));
        } else
        {
            sp = "   ";
        }
        res[0][0] = "x ";
        res[0][2] = " x";
        res[1][1] = sp;
        res[2][0] = "x ";
        res[2][2] = " x";
        switch (orientatie)
        {
            case 1:
                res[0][1] = " x ";
                res[1][0] = "x ";
                res[1][2] = "  ";
                res[2][1] = "   ";
                break;
            case 2:
                res[0][1] = " x ";
                res[1][0] = "  ";
                res[1][2] = " x";
                res[2][1] = "   ";
                break;
            case 3:
                res[0][1] = "   ";
                res[1][0] = "  ";
                res[1][2] = " x";
                res[2][1] = " x ";
                break;
            case 4:
                res[0][1] = "   ";
                res[1][0] = "x ";
                res[1][2] = "  ";
                res[2][1] = " x ";
                break;

        }

        return res;
    }

    /**
     * Method to print the first line of a card
     *
     * @return an array of string
     */
    @Override
    public String toStringRij1()
    {
        String[][] temp = geefArray();
        String res = "";
        for (int i = 0; i <= 2; i++)
        {
            res += temp[0][i];
        }
        return res;
    }

    /**
     * Method to print the second line of a card
     *
     * @return an array of string
     */
    @Override
    public String toStringRij2()
    {
        String[][] temp = geefArray();
        String res = "";
        for (int i = 0; i <= 2; i++)
        {
            res += temp[1][i];
        }
        return res;
    }

    /**
     * Method to print the third line of a card
     *
     * @return an array of string
     */
    @Override
    public String toStringRij3()
    {
        String[][] temp = geefArray();
        String res = "";
        for (int i = 0; i <= 2; i++)
        {
            res += temp[2][i];
        }
        return res;
    }

    @Override
    public String toString()
    {
        String res = "";
        String[][] temp = geefArray();
        for (String[] rij : temp)
        {
            for (String kolom : rij)
            {
                res += kolom;
            }
            res += "\n";
        }
        return res;
    }

    @Override
    public boolean kanLinks()
    {
        if (getOrientatie() == 2 || getOrientatie() == 3)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean kanRechts()
    {
        if (getOrientatie() == 1 || getOrientatie() == 4)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean kanBoven()
    {
        if (getOrientatie() == 3 || getOrientatie() == 4)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean kanOnder()
    {
        if (getOrientatie() == 1 || getOrientatie() == 2)
        {
            return true;
        } else
        {
            return false;
        }
    }
}
