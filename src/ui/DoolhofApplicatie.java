package ui;

import domein.DomeinController;
import exceptions.ExistingNameException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNameException;
import exceptions.InvalidNumberException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import locale.Taal;

public class DoolhofApplicatie
{

    Scanner sc = new Scanner(System.in);
    DomeinController dc;

    public DoolhofApplicatie() throws Exception
    {

        dc = new DomeinController();
        int bevestiging = 0;
        int positie = 0;
        int richting = 0;
        /*gebruik dit attribuut voor de resource bundles aan te spreken
         voorbeeld
        
         System.out.print(lang.getString("hoeveelSpelers"));
        
         deze system.out print van de gekozen taal de key hoeveelSpelers
         de resourcebundles met keys kan je terug vinden in de <default package>
         */
        kiesTaal();
        //String spel;
        // laden of spelen indien keuze 1 is wordt de if tak aangesproken indien niet wordt de else code uitgevoerd
        int keuze = kiesLadenOfSpelen();
        sc.nextLine();
        if (keuze == 1)
        {
            laadSpel();
            dc.speelBeurt();
            System.out.println(dc.geefHuidigeSpeler());
        } else
        {
            dc.maakNieuwSpel();
            dc.maakDoolhof();
            int spelers = voerAantalSpelersIn();
            for (int i = 1; i <= spelers; i++)
            {
                voegSpelerToe(i);
            }
            dc.verdeelKaarten();
            // output met de eerste speler aan de beurt
            System.out.printf("%s %s%n", Taal.vertaal("beginSpel"), dc.toonEersteAanBeurt());
            dc.speelBeurt();
        }
        
        do
        {
            schuifGangkaartIn();
            System.out.println(dc.toonDoolhof());
            verplaatsSpeler();
            if (dc.geefWinnaar().isEmpty())
            {
                bewaarSpel();
                dc.speelBeurt();
                System.out.println(dc.geefHuidigeSpeler());
            }

        } while (dc.geefWinnaar().isEmpty());
        System.out.println(dc.geefWinnaar());

    }

    private void kiesTaal()
    {
        int taal = 0;
        do
        {
            System.out.printf("Which language do you prefer?%n1) Nederlands%n2) Français%n3) English%n");
            try
            {
                taal = sc.nextInt();

                if (taal != 1 && taal != 2 && taal != 3)
                {
                    System.out.printf("Enter 1 for Nederlands, 2 for Français and 3 for English!%n");
                    sc.nextLine();
                }
            } catch (InputMismatchException e)
            {
                System.out.printf("Enter 1 for Dutch, 2 for French and 3 for English!%n");
                sc.nextLine();
            }

        } while (taal != 1 && taal != 2 && taal != 3);

        switch (taal)
        {
            case 2:
                Taal.stelTaalIn("locale_fr_BE");
                break;
            case 3:
                Taal.stelTaalIn("locale_en_US");
                break;
            default:
                Taal.stelTaalIn("locale");
                break;
        }
    }

    private void laadSpel() throws Exception
    {
        boolean controle = true;
        int spelID = 0;
        List<String> spellen = dc.geefSpelNamen();
        String format = "";
        for (String s : spellen)
        {
            format += s + "\n";
        }
        do
        {
            try
            {
                do
                {
                    System.out.printf("%n%s%n%s", Taal.vertaal("laadSpel"), format);
                    spelID = sc.nextInt();
                    if (spelID < 1 || spelID > spellen.size())
                    {
                        System.out.printf("%s%n", Taal.vertaal("bestaatNiet"));
                    }
                } while (spelID < 1 || spelID > spellen.size());
                dc.laadSpel(spelID);
                controle = false;
            } catch (NumberFormatException | InputMismatchException ex)
            {
                System.out.printf("%s%n", Taal.vertaal("inpusMisMatchGeheelGetal"));
                sc.nextLine();
            } catch (SQLException e)
            {
                System.err.println(Taal.vertaal("foutLadenSpel"));
            }
        } while (controle);
    }

    private int kiesLadenOfSpelen()
    {
        int keuze = 0;
        boolean invoer = true;
        do
        {
            System.out.printf("%n1) %s%n2) %s%n", Taal.vertaal("keuzeLaden"), Taal.vertaal("keuzeSpelen"));
            try
            {
                keuze = sc.nextInt();
                if (keuze != 1 && keuze != 2)
                {
                    System.out.printf("%s%n", Taal.vertaal("teHoog"));
                    sc.nextLine();
                } else
                {
                    invoer = false;
                }
            } catch (InputMismatchException e)
            {
                System.out.printf("%s%n", Taal.vertaal("inpusMisMatchGeheelGetal"));
                sc.nextLine();
            }
        } while (invoer);
        return keuze;
    }

    private int voerAantalSpelersIn() throws InvalidNumberException
    {
        int aantalSpelers = 0;
        boolean b = true;
        do
        {
            System.out.printf("%s", Taal.vertaal("hoeveelSpelers"));
            try
            {
                aantalSpelers = sc.nextInt();
                if (aantalSpelers < 2 || aantalSpelers > 4)
                {
                    System.out.printf("%s%n", Taal.vertaal("aantalSpelers"));
                } else
                {
                    b = false;
                }
            } catch (InputMismatchException e)
            {
                System.out.printf("%s%n", Taal.vertaal("inpusMisMatchGeheelGetal"));
            } finally
            {
                sc.nextLine();
            }
        } while (b);

        dc.voerAantalSpelersIn(aantalSpelers);
        return aantalSpelers;
    }

    private void voegSpelerToe(int speler) throws Exception
    {
        final int year = Calendar.getInstance().get(Calendar.YEAR);

        //invoer van de gegevens van het aantal spelers ingevoerd in het spel
        String userName = "";
        int geboortejaar;
        int kleur = 0;
        boolean b = true;
        do
        {
            System.out.print(Taal.vertaal("naamSpeler") + speler + "? ");
            try
            {
                userName = sc.nextLine();
                dc.controleerSpeler(userName);
                b = false;
                if (userName.contains(" "))
                {
                    System.out.printf("%s%n", Taal.vertaal("geenSpaties"));
                    b = true;
                }
            } catch (ExistingNameException | InvalidNameException e)
            {
                System.out.println(e.getMessage());
            }

        } while (b || userName == null || userName.equals(""));

        do
        {
            geboortejaar = 2012;
            System.out.print(Taal.vertaal("gbjaarSpeler") + speler + "? ");
            try
            {
                geboortejaar = sc.nextInt();
                if (year - geboortejaar < 7 || year - geboortejaar > 95)
                {
                    System.out.printf("%s%n", Taal.vertaal("leeftijdSpelers"));
                }
            } catch (InputMismatchException e)
            {
                System.out.printf("%s%n", Taal.vertaal("inpusMisMatchGeheelGetal"));
                sc.nextLine();
            }
        } while (year - geboortejaar < 7 || year - geboortejaar > 95);
        do
        {
            b = true;
            System.out.printf("%s%d ?%n1) %s%n2) %s%n3) %s%n4) %s%n", Taal.vertaal("kleurSpeler"), speler, Taal.vertaal("rood"), Taal.vertaal("geel"), Taal.vertaal("groen"), Taal.vertaal(("blauw")));
            try
            {
                kleur = sc.nextInt();
                dc.controleerGekozenKleur(kleur);
                if (kleur < 1 || kleur > 4)
                {
                    System.out.printf("%s%n", Taal.vertaal("onbestaandeKleur"));
                }
                b = false;
            } catch (InputMismatchException e)
            {
                System.out.printf("%s%n", Taal.vertaal("inpusMisMatchGeheelGetal"));
            } catch (ExistingNameException e)
            {
                System.out.println(e.getMessage());
            } finally
            {
                sc.nextLine();
            }
        } while (kleur < 1 || kleur > 4 || b);
        dc.voegSpelerToe(userName, geboortejaar, kleur);
    }

    private void schuifGangkaartIn() throws InvalidMoveException
    {
        int positie = 0, richting = 0, keuze = 0, bevestiging = 0;
        boolean b = true;
        System.out.println(dc.geefSpelSituatie());

        do
        {
            do
            {
                try
                {
                    System.out.printf("%s%n", Taal.vertaal("waarInschuiven"));
                    try
                    {
                        positie = sc.nextInt();
                    } catch (InputMismatchException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    System.out.printf("%s%n%s%n%s%n%s%n%s%n", Taal.vertaal("inschuiven"), Taal.vertaal("links"), Taal.vertaal("rechts"), Taal.vertaal("boven"), Taal.vertaal("onder"));
                    try
                    {
                        richting = sc.nextInt();
                    } catch (InputMismatchException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    dc.controleerPositieInschuivenGangKaart(positie, richting);
                    sc.nextLine();
                    b = false;
                } catch (InvalidMoveException e)
                {
                    System.err.println(e.getMessage());
                    sc.nextLine();
                    b = true;
                }
            } while (b);
            do
            {
                System.out.printf("%s%n%s%n%s%n", Taal.vertaal("gankaartDraaien"), Taal.vertaal("ja"), Taal.vertaal("nee"));
                try
                {
                    keuze = sc.nextInt();
                } catch (InputMismatchException e)
                {
                    System.out.println(e.getMessage());
                } finally
                {
                    if (keuze == 1)
                    {
                        System.out.printf("%s%n%s%n", Taal.vertaal("vrijeGangkaart"), dc.draaiGangkaart());
                    }
                }
            } while (keuze != 2);
            do
            {
                System.out.printf("%s%n%s%n%s%n", Taal.vertaal("inschuivenBevestig"), Taal.vertaal("ja"), Taal.vertaal("nee"));
                try
                {
                    bevestiging = sc.nextInt();
                } catch (InputMismatchException e)
                {
                    System.out.println(e.getMessage());
                }
            } while (!(bevestiging == 1) && !(bevestiging == 2));
        } while (!(bevestiging == 1));
        dc.schuifGangkaartIn(positie, richting);
    }

    private void verplaatsSpeler()
    {
        int bevestiging;
        String res = "";
        int keuze;
        ArrayList<Integer> mogelijkeRichtingen;
        boolean b = false;
        do
        {
            System.out.printf("%s%n%s%n%s%n", Taal.vertaal("spelerVerplaatsen"), Taal.vertaal("ja"), Taal.vertaal("nee"));
            bevestiging = sc.nextInt();
            if (bevestiging == 1)
            {
                mogelijkeRichtingen = dc.toonMogelijkeRichtingen();
                for (int l : mogelijkeRichtingen)
                {
                    switch (l)
                    {
                        case 1:
                            res += Taal.vertaal("links");
                            break;
                        case 2:
                            res += Taal.vertaal("rechts");
                            break;
                        case 3:
                            res += Taal.vertaal("boven");
                            break;
                        case 4:
                            res += Taal.vertaal("onder");
                            break;
                    }
                    res += "\n";

                }
                do
                {
                    System.out.printf("%s%n%s%n", Taal.vertaal("richtingVerplaatsen"), res);
                    keuze = sc.nextInt();
                    b = false;
                    if (!(mogelijkeRichtingen.contains(keuze)))
                    {
                        System.out.println(Taal.vertaal("ongeldigeKeuze"));
                        b = true;
                        sc.nextLine();
                    }
                } while (b);
                res = "";
                try
                {
                    dc.verplaats(keuze);
                } catch (InvalidMoveException e)
                {
                    System.out.println(Taal.vertaal("ongeldigeKeuze"));
                }
                if (dc.heeftSchatGevonden())
                {
                    System.out.println(Taal.vertaal("schatGevonden") + Taal.vertaal(dc.geefHuidigeSchat()));
                    dc.verwijderSchat();
                    if (dc.geefHuidigeSchat() != null)
                    {
                        System.out.println(Taal.vertaal("volgendeSchat") + Taal.vertaal(dc.geefHuidigeSchat()) + "\n");
                    }
                    bevestiging = 2;
                } else
                {
                    System.out.println(dc.toonDoolhof());
                }
                sc.nextLine();
            } else if (bevestiging != 2)
            {
                System.out.println(Taal.vertaal("ongeldigeKeuze"));
            }
        } while (bevestiging != 2);
    }

    private void bewaarSpel() throws InvalidNameException
    {
        int keuze;
        boolean b = true;
        String naam = "";

        System.out.printf("%s%n%s%n%s%n", Taal.vertaal("bewaarSpel"), Taal.vertaal("ja"), Taal.vertaal("nee"));
        keuze = sc.nextInt();
        sc.nextLine();
        if (keuze == 1)
        {
            do
            {
                try
                {
                    System.out.print(Taal.vertaal("naamSpel"));
                    naam = sc.nextLine();
                    dc.controleerSpelNaam(naam);
                    dc.bewaarSpel(naam);
                    b = false;
                } catch (ExistingNameException | InvalidNameException e)
                {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e)
                {
                    System.out.printf("%s%n", Taal.vertaal("alleenLetters"));
                } catch (SQLException e)
                {
                    System.err.println(Taal.vertaal("foutBewarenSpel"));
                }
            } while (b);
            System.out.printf("%n%s%n", Taal.vertaal("spelBewaard"));

            //3 seconden wachten om het bericht duidelijk zichtbaar te maken
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException ex)
            {

            }
        }
    }

}
