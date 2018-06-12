/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Doelkaart;
import domein.Doolhof;
import domein.Gangkaart;
import domein.HoekKaart;
import domein.RechtKaart;
import domein.Spel;
import domein.Speler;
import domein.TKaart;
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
public class SpelMapper
{

    private SpelerMapper spm;

    /**
     * Creates a new SpelerMapper
     */
    public SpelMapper()
    {
        spm = new SpelerMapper();
    }

    /**
     *
     * @return returns a list of all the games that exist
     * @throws SQLException thrown when something went wrong with the database
     * @throws Exception thrown when something went wrong
     */
    public List<String> geefSpelNamen() throws SQLException, Exception
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ArrayList<String> res = new ArrayList<>();

        try
        {
            conn = DriverManager.getConnection(Connectie.JDBC_URL);
            preparedStatement = conn.prepareStatement("SELECT spelID, spelNaam FROM Spel ORDER BY spelID;");
            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                String naam = String.format("%-5s" + rs.getString("spelNaam"), rs.getInt("spelID"));
                res.add(naam);
            }

        } catch (SQLException e)
        {
            throw new SQLException(e);
        }
        return res;
    }

    /**
     *
     * @param spelID the id of the chosen game
     * @return returns the chosen game
     */
    public Spel laadSpel(int spelID) throws SQLException, Exception
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ArrayList<String> res = new ArrayList<>();
        Spel spel;
        List<Speler> spelers;
        Speler huidigeSpeler = null;
        List<Doelkaart> doelkaarten;
        String sn = null;
        int aantalSpelers, userIDhuidig = 0, vorigePositie = 0, vorigeRichting = 0;
        Doolhof doolhof;
        Gangkaart vrijeGangKaart = null;
        Gangkaart[][] gangkaarten;

        spelers = spm.geefSpelers(spelID);
        conn = DriverManager.getConnection(Connectie.JDBC_URL);

        preparedStatement = conn.prepareStatement("SELECT * FROM Spel WHERE spelID = '" + spelID + "'");
        rs = preparedStatement.executeQuery();
        if (rs.next())
        {
            sn = rs.getString("spelNaam");
            aantalSpelers = rs.getInt("aantalSpelers");
            userIDhuidig = rs.getInt("spelerAanBeurt");
        }
        for (Speler s : spelers)
        {
            if (s.getUserID() == userIDhuidig)
            {
                huidigeSpeler = s;
                break;
            }
        }

        preparedStatement = conn.prepareStatement("SELECT vorigePositie, vorigeRichting, vrijeTKaart, vrijeHoekKaart, vrijeRechtKaart, d1.schat, d2.schat FROM Doolhof d\n"
                + "    LEFT JOIN TKaart t ON t.kaartID = d.vrijeTKaart\n"
                + "    LEFT JOIN HoekKaart h ON h.kaartID = d.vrijeHoekKaart\n"
                + "    LEFT JOIN RechtKaart r ON r.kaartID = d.vrijeRechtKaart\n"
                + "    LEFT JOIN Doelkaart d1 ON t.schat = d1.schatID\n"
                + "    LEFT JOIN Doelkaart d2 ON h.schat = d2.schatID\n"
                + "WHERE spelID = " + spelID);
        rs = preparedStatement.executeQuery();
        if (rs.next())
        {
            vorigePositie = rs.getInt("vorigePositie");
            vorigeRichting = rs.getInt("vorigeRichting");
            rs.getInt("vrijeTKaart");
            if (!rs.wasNull())
            {
                vrijeGangKaart = new TKaart(1, rs.getString(6), false);
            } else
            {
                rs.getInt("vrijeHoekKaart");
                if (!rs.wasNull())
                {
                    rs.getString(7);
                    if (!rs.wasNull())
                    {
                        vrijeGangKaart = new HoekKaart(1, rs.getString(7), false);
                    } else
                    {
                        vrijeGangKaart = new HoekKaart(1, false);
                    }
                } else
                {
                    rs.getInt("vrijeRechtKaart");
                    if (!rs.wasNull())
                    {
                        vrijeGangKaart = new RechtKaart(1, false);
                    }
                }

            }
        }
        doolhof = new Doolhof(vorigePositie, vorigeRichting, vrijeGangKaart);
        gangkaarten = doolhof.getGangkaarten();

        preparedStatement = conn.prepareStatement("SELECT Rkaart, Hkaart, Tkaart, oriëntatie, rij, kolom, d1.schat, d2.schat FROM GangKaartGegevens gg\n"
                + "LEFT JOIN HoekKaart h ON h.kaartID = gg.Hkaart\n"
                + "LEFT JOIN RechtKaart r ON r.kaartID = gg.Rkaart\n"
                + "LEFT JOIN TKaart t ON t.kaartID = gg.Tkaart\n"
                + "LEFT JOIN Doelkaart d1 ON t.schat = d1.schatID\n"
                + "LEFT JOIN Doelkaart d2 ON h.schat = d2.schatID\n"
                + "WHERE doolhof = " + spelID + "\n"
                + "ORDER BY rij, kolom;");
        rs = preparedStatement.executeQuery();
        while (rs.next())
        {
            rs.getInt("Rkaart");
            if (!rs.wasNull())
            {
                gangkaarten[rs.getInt("rij")][rs.getInt("kolom")] = new RechtKaart(rs.getInt("oriëntatie"), false);
            } else
            {
                rs.getInt("Hkaart");
                if (!rs.wasNull())
                {
                    rs.getString(8);
                    if (!rs.wasNull())
                    {
                        gangkaarten[rs.getInt("rij")][rs.getInt("kolom")] = new HoekKaart(rs.getInt("oriëntatie"), rs.getString(8), false);
                    } else
                    {
                        gangkaarten[rs.getInt("rij")][rs.getInt("kolom")] = new HoekKaart(rs.getInt("oriëntatie"), false);
                    }
                } else
                {
                    rs.getInt("Tkaart");
                    if (!rs.wasNull())
                    {
                        gangkaarten[rs.getInt("rij")][rs.getInt("kolom")] = new TKaart(rs.getInt("oriëntatie"), rs.getString(7), false);
                    }
                }
            }
        }

        doolhof.setGangkaarten(gangkaarten);

        return new Spel(sn, spelers, huidigeSpeler, spelers.size(), doolhof);
    }

    /**
     *
     * @param spelNaam the name of the game
     * @return returns if the spelNaam is correct
     */
    public boolean controleerSpelNaam(String spelNaam) throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        boolean b = false;
        String controle = "";
        conn = DriverManager.getConnection(Connectie.JDBC_URL);
        preparedStatement = conn.prepareStatement("SELECT spelNaam FROM Spel WHERE spelNaam = '" + spelNaam + "'");
        rs = preparedStatement.executeQuery();
        while (rs.next())
        {
            controle = rs.getString("spelNaam");
        }
        if (controle.equals(spelNaam))
        {
            return true;
        }

        return b;
    }

    public void bewaarSpel(Spel huidigSpel) throws SQLException
    {
        int spelerAanBeurt, spelID, schatID, userID;
        String schat;
        List<Speler> spelers = huidigSpel.getSpelers();
        Doolhof doolhof = huidigSpel.getDoolhof();
        List<Doelkaart> doelkaarten = null;
        Gangkaart[][] gangkaarten = doolhof.getGangkaarten();
        int[] spelerIDs;

        Connection conn = null;
        PreparedStatement preparedStatement = null, tempStatement = null;
        ResultSet rs = null;

        if (!controleerSpelNaam(huidigSpel.getSpelNaam()))
        {
            conn = DriverManager.getConnection(Connectie.JDBC_URL);

            //huidigSpel wegschrijven
            preparedStatement = conn.prepareStatement("INSERT INTO Spel (spelNaam, aantalSpelers) VALUES (?, ?);");
            preparedStatement.setString(1, huidigSpel.getSpelNaam());
            preparedStatement.setInt(2, huidigSpel.getAantalSpelers());
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement("SELECT spelID FROM Spel WHERE spelNaam = '" + huidigSpel.getSpelNaam() + "'");
            rs = preparedStatement.executeQuery();
            rs.next();
            spelID = rs.getInt("spelID");
            //spelers wegschrijven + spelerID's opvragen
            spelerIDs = spm.bewaarSpelers(spelers, spelID);

            //spelerAanBeurt toevoegen aan spel
            spelerAanBeurt = spelerIDs[spelers.indexOf(huidigSpel.getHuidigeSpeler())];
            preparedStatement = conn.prepareStatement("UPDATE Spel SET spelerAanBeurt = '" + spelerAanBeurt + "' WHERE spelID = '" + spelID + "'");
            preparedStatement.executeUpdate();

            //doelkaartenDetails toevoegen
            spm.bewaarDoelkaarten(spelers, doelkaarten, spelerIDs);

            //doolhof wegschrijven
            preparedStatement = conn.prepareStatement("INSERT INTO Doolhof (spelID, vorigePositie, vorigeRichting, vrijeTKaart, vrijeHoekKaart, vrijeRechtKaart) VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, spelID);
            preparedStatement.setInt(2, doolhof.getVorigePositie());
            preparedStatement.setInt(3, doolhof.getVorigeRichting());
            int kaartID;
            if (doolhof.getVrijeGangkaart() instanceof HoekKaart)
            {
                schat = doolhof.getVrijeGangkaart().getSchat();
                if (schat != null)
                {
                    tempStatement = conn.prepareStatement("SELECT kaartID FROM HoekKaart h JOIN Doelkaart d ON h.schat = d.schatID "
                            + "WHERE d.schat LIKE \"" + doolhof.getVrijeGangkaart().getSchat() + "\"");
                    rs = tempStatement.executeQuery();
                    rs.next();
                    kaartID = rs.getInt("kaartID");
                    preparedStatement.setInt(5, kaartID);
                } else
                {
                    preparedStatement.setInt(5, 7);
                }
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            } else if (doolhof.getVrijeGangkaart() instanceof TKaart)
            {
                tempStatement = conn.prepareStatement("SELECT kaartID FROM TKaart t JOIN Doelkaart d ON t.schat = d.schatID "
                        + "WHERE d.schat LIKE \"" + doolhof.getVrijeGangkaart().getSchat() + "\"");
                rs = tempStatement.executeQuery();
                rs.next();
                kaartID = rs.getInt("kaartID");
                preparedStatement.setInt(4, kaartID);
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            } else //vrijeGangKaart is een RechtKaart
            {
                preparedStatement.setInt(6, 1);
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }
            preparedStatement.executeUpdate();

            //gangkaarten wegschrijven
            preparedStatement = conn.prepareStatement("INSERT INTO GangKaartGegevens (Rkaart, Hkaart, Tkaart, oriëntatie, rij, kolom, doolhof) VALUES (?, ?, ?, ?, ?, ?, ?);");
            for (int i = 0; i < gangkaarten.length; i++)
            {
                for (int j = 0; j < gangkaarten[i].length; j++)
                {
                    if (!gangkaarten[i][j].isVast())
                    {
                        preparedStatement.setInt(7, spelID);
                        if (gangkaarten[i][j] instanceof RechtKaart)
                        {
                            preparedStatement.setInt(1, 1);
                            preparedStatement.setNull(2, java.sql.Types.INTEGER);
                            preparedStatement.setNull(3, java.sql.Types.INTEGER);
                        } else if (gangkaarten[i][j] instanceof HoekKaart)
                        {
                            tempStatement = conn.prepareStatement("SELECT kaartID FROM HoekKaart h JOIN Doelkaart d ON h.schat = d.schatID "
                                    + "WHERE d.schat LIKE \"" + gangkaarten[i][j].getSchat() + "\"");
                            rs = tempStatement.executeQuery();
                            if (rs.next())
                            {
                                kaartID = rs.getInt("kaartID");
                                preparedStatement.setInt(2, kaartID);
                            } else
                            {
                                preparedStatement.setInt(2, 7);
                            }
                            preparedStatement.setNull(1, java.sql.Types.INTEGER);
                            preparedStatement.setNull(3, java.sql.Types.INTEGER);
                        } else if (gangkaarten[i][j] instanceof TKaart)
                        {
                            tempStatement = conn.prepareStatement("SELECT kaartID FROM TKaart t JOIN Doelkaart d ON t.schat = d.schatID "
                                    + "WHERE d.schat LIKE \"" + gangkaarten[i][j].getSchat() + "\"");
                            rs = tempStatement.executeQuery();
                            if (rs.next());
                            {
                                kaartID = rs.getInt("kaartID");
                                preparedStatement.setInt(3, kaartID);
                            }
                            preparedStatement.setNull(1, java.sql.Types.INTEGER);
                            preparedStatement.setNull(2, java.sql.Types.INTEGER);
                        }
                        preparedStatement.setInt(4, gangkaarten[i][j].getOrientatie());
                        preparedStatement.setInt(5, i);
                        preparedStatement.setInt(6, j);
                        preparedStatement.setInt(7, spelID);
                        preparedStatement.addBatch();
                    }
                }
            }
            preparedStatement.executeBatch();

        }
    }
}
