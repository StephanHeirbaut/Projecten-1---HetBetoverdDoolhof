/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Doelkaart;
import domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolas
 */
public class SpelerMapper
{

    /**
     *
     * @param spelID The id of the game
     * @return returns a list of all the players in the game
     * @throws SQLException thrown when something went wrong with the database
     * @throws Exception thrown when something went wrong
     */
    public List<Speler> geefSpelers(int spelID) throws SQLException, Exception
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null, tempStatement = null;
        ResultSet rs = null, rs2 = null;
        ArrayList<Speler> res = new ArrayList<>();
        String naam = "";
        int geboortejaar, kleur, userID;
        conn = DriverManager.getConnection(Connectie.JDBC_URL);
        preparedStatement = conn.prepareStatement("SELECT * FROM Speler WHERE spel = '" + spelID + "' ORDER BY geboortejaar DESC, userName");
        rs = preparedStatement.executeQuery();
        while (rs.next())
        {
            userID = rs.getInt("userID");
            naam = rs.getString("userName");
            geboortejaar = rs.getInt("geboortejaar");
            kleur = rs.getInt("kleur");
            int[] positie = new int[2];
            positie[0] = rs.getInt("rij");
            positie[1] = rs.getInt("kolom");

            List<Doelkaart> doelkaarten = new ArrayList<>();
            tempStatement = conn.prepareStatement("SELECT schat FROM DoelkaartDetails \n"
                    + "JOIN Doelkaart ON doelkaartID = schatID\n"
                    + "WHERE speler = " + userID + "\n"
                    + "ORDER BY ID;");
            rs2 = tempStatement.executeQuery();
            while (rs2.next())
            {
                doelkaarten.add(new Doelkaart(rs2.getString("schat")));
            }
            res.add(new Speler(userID, naam, geboortejaar, kleur, positie, doelkaarten));
        }

        return res;
    }

    public int[] bewaarSpelers(List<Speler> spelers, int spelID) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        int[] res = new int[spelers.size()];
        int i = 0;
        conn = DriverManager.getConnection(Connectie.JDBC_URL);
        preparedStatement = conn.prepareStatement("INSERT INTO Speler (userName, geboortejaar, kleur, rij, kolom, spel) VALUES (?,?,?,?,?,?)");
        for (Speler s : spelers)
        {
            preparedStatement.setString(1, s.getUserName());
            preparedStatement.setInt(2, s.getGeboortejaar());
            preparedStatement.setInt(3, s.getKleur());
            preparedStatement.setInt(4, s.getPositie()[0]);
            preparedStatement.setInt(5, s.getPositie()[1]);
            preparedStatement.setInt(6, spelID);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        preparedStatement = conn.prepareStatement("SELECT userID FROM Speler WHERE spel = '" + spelID + "' ORDER BY geboortejaar DESC, userName");
        rs = preparedStatement.executeQuery();
        while (rs.next())
        {
            res[i] = rs.getInt("userID");
            i++;
        }

        return res;
    }

    public void bewaarDoelkaarten(List<Speler> spelers, List<Doelkaart> doelkaarten, int[] spelerIDs) throws SQLException
    {
        PreparedStatement preparedStatement = null, tempStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        int userID, schatID;
        String schat;
        conn = DriverManager.getConnection(Connectie.JDBC_URL);
        preparedStatement = conn.prepareStatement("INSERT INTO DoelkaartDetails (doelkaartID, speler) VALUES (?,?)");
        for (Speler s : spelers)
        {
            userID = spelerIDs[spelers.indexOf(s)];
            doelkaarten = s.getDoelkaarten();
            for (Doelkaart d : doelkaarten)
            {
                schat = d.getSchat();
                tempStatement = conn.prepareStatement("SELECT schatID FROM Doelkaart d WHERE schat = \"" + schat + "\"");
                rs = tempStatement.executeQuery();
                rs.next();
                schatID = rs.getInt("schatID");
                preparedStatement.setInt(1, schatID);
                preparedStatement.setInt(2, userID);
                preparedStatement.addBatch();
            }
        }
        preparedStatement.executeBatch();
    }
}
