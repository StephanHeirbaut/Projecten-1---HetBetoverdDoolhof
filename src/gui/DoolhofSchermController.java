/*
 * Created by JVH 
 * The one and only jellyfish
 */
package gui;

import domein.DomeinController;
import domein.Gangkaart;
import domein.HoekKaart;
import domein.RechtKaart;
import domein.Speler;
import domein.TKaart;
import exceptions.ExistingNameException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNameException;
import exceptions.InvalidNumberException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import locale.Taal;

/**
 * FXML Controller class
 *
 * @author joche
 */
public class DoolhofSchermController extends BorderPane
{

    @FXML
    private GridPane GPDoolhof;
    private DomeinController dc;
    @FXML
    private Label lblHuidigeSpeler;
    @FXML
    private Label lblSchat;
    @FXML
    private StackPane SPVrijeGangkaart;
    @FXML
    private ImageView IVSchat;
    @FXML
    private Button btnDraai;
    @FXML
    private Label lblPositie;
    @FXML
    private ComboBox<?> cmbPositie;
    @FXML
    private Label lblRichting;
    @FXML
    private ComboBox<?> cmbRichting;
    @FXML
    private Button btnSchuif;
    @FXML
    private Label lblVrijeGangkaart;
    
    private ObservableList listRichting;

    private ObservableList listPositie;

    private ObservableList listVerplaats;
    @FXML
    private ComboBox<?> cmbVerplaatsRichting;
    @FXML
    private Button btnVerplaats;
    @FXML
    private Label lblVerplaats;
    @FXML
    private Button btnNext;
    @FXML
    private Label lblSchatGevonden;
    @FXML
    private Label lblWinnaar;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txaNaamSpel;

    public DoolhofSchermController(DomeinController dc)
    {
        listPositie = FXCollections.observableArrayList("2", "4", "6");
        listRichting = FXCollections.observableArrayList(Taal.vertaal("links"), Taal.vertaal("rechts"), Taal.vertaal("boven"), Taal.vertaal("onder"));
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoolhofScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        buildGui();
        buildVrijeKaart();
        buildDoolhof();
        geefVerplaatsRichtingen();
        String beurt = "";
        try
        {
            beurt = dc.toonEersteAanBeurt();
        } catch (InvalidNameException | InvalidNumberException | ExistingNameException ex)
        {
            Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        lblHuidigeSpeler.setText(String.format("%s%s%s", beurt," ", Taal.vertaal("jouwBeurt")));        
//        this.vrijeGangkaart = new RechtKaart(1, false);
    }

    private void buildGui()
    {
        lblPositie.setText(Taal.vertaal("inschuiven"));
        lblRichting.setText(Taal.vertaal("inschuiven"));
        lblVerplaats.setText(Taal.vertaal("richtingVerplaatsen"));
        cmbPositie.setItems(listPositie);
        cmbRichting.setItems(listRichting);
        dc.speelBeurt();
        lblSchat.setText(String.format("%s",Taal.vertaal("teZoekenSchat")));
        Image image = new Image("/gui/images/"+ dc.geefHuidigeSchat()+".png");
        IVSchat.setImage(image);
        btnDraai.setText(Taal.vertaal("draai"));
        btnSchuif.setText(Taal.vertaal("schuif"));
        lblVrijeGangkaart.setText(Taal.vertaal("vrijeGangkaart"));
        btnVerplaats.setDisable(true);
    }

    private String geefUrlHoekkaartString(int positie)
    {
        String url = "";
        switch (positie)
        {
            case 1:
                url = "-fx-background-image: url(\"gui/Hoek4.jpg\");";
                break;
            case 2:
                url = "-fx-background-image: url(\"gui/Hoek1.jpg\");";
                break;
            case 3:
                url = "-fx-background-image: url(\"gui/Hoek2.jpg\");";
                break;
            case 4:
                url = "-fx-background-image: url(\"gui/Hoek3.jpg\");";
                break;
        }
        return url;
    }

    private String geefUrlTKaartString(int positie)
    {
        String url = "";
        switch (positie)
        {
            case 1:
                url = "-fx-background-image: url(\"gui/TKaart4.jpg\");";
                break;
            case 2:
                url = "-fx-background-image: url(\"gui/TKaart3.jpg\");";
                break;
            case 3:
                url = "-fx-background-image: url(\"gui/TKaart2.jpg\");";
                break;
            case 4:
                url = "-fx-background-image: url(\"gui/TKaart1.jpg\");";
                break;
        }
        return url;
    }

    private String geefUrlRechteKaartString(int positie)
    {
        String url = "";
        switch (positie)
        {
            case 1:
                url = "-fx-background-image: url(\"gui/RechteKaart2.jpg\");";
                break;
            case 2:
                url = "-fx-background-image: url(\"gui/RechteKaart.jpg\");";
                break;
            case 3:
                url = "-fx-background-image: url(\"gui/RechteKaart2.jpg\");";
                break;
            case 4:
                url = "-fx-background-image: url(\"gui/RechteKaart.jpg\");";
                break;
        }
        return url;
    }

    @FXML
    private void btnDraaiOnAction(ActionEvent event)
    {
        dc.draaiGangkaart();
        buildVrijeKaart();
    }

    private void buildVrijeKaart()
    {
        if (dc.getHuidigSpel().getDoolhof().getVrijeGangkaart() instanceof RechtKaart)
        {
            SPVrijeGangkaart.setStyle(geefUrlRechteKaartString(dc.getHuidigSpel().getDoolhof().getVrijeGangkaart().getOrientatie()) + "-fx-background-size: 200 200");
        }
        if (dc.getHuidigSpel().getDoolhof().getVrijeGangkaart() instanceof HoekKaart)
        {
            SPVrijeGangkaart.setStyle(geefUrlHoekkaartString(dc.getHuidigSpel().getDoolhof().getVrijeGangkaart().getOrientatie()) + "-fx-background-size: 200 200");
        }
        if (dc.getHuidigSpel().getDoolhof().getVrijeGangkaart() instanceof TKaart)
        {
            SPVrijeGangkaart.setStyle(geefUrlTKaartString(dc.getHuidigSpel().getDoolhof().getVrijeGangkaart().getOrientatie()) + "-fx-background-size: 200 200");
        }
    }

    private void buildDoolhof()
    {
        List<Speler> lijst = dc.getHuidigSpel().getSpelers();
        Gangkaart[][] temp = dc.getHuidigSpel().getDoolhof().getGangkaarten();
        for (int i = 0; i <= 6; i++)
        {
            for (int j = 0; j <= 6; j++)
            {
                if (temp[i][j] instanceof HoekKaart)
                {
                    StackPane tem = new StackPane();
                    tem.setStyle(geefUrlHoekkaartString(temp[i][j].getOrientatie()) + "-fx-background-size: 100 100");
                    GPDoolhof.add(tem, j, i);
                    if (!(temp[i][j].getSchat().equals("")))
                    {
                        Image image = new Image("/gui/images/" + temp[i][j].getSchat() + ".png");
                        tem.getChildren().addAll(new ImageView(image));
                    }
                    if (temp[i][j].getSpeler() != null)
                    {
                        switch (temp[i][j].getSpeler().getKleur())
                        {
                            case 1:
                                Image img1 = new Image("/gui/images/spelerRood.png");
                                tem.getChildren().addAll(new ImageView(img1));
                                break;
                            case 2:
                                Image img2 = new Image("/gui/images/spelerGeel.png");
                                tem.getChildren().addAll(new ImageView(img2));
                                break;
                            case 3:
                                Image img3 = new Image("/gui/images/spelerGroen.png");
                                tem.getChildren().addAll(new ImageView(img3));
                                break;
                            case 4:
                                Image img4 = new Image("/gui/images/spelerBlauw.png");
                                tem.getChildren().addAll(new ImageView(img4));
                                break;
                        }
                    }
                }
                if (temp[i][j] instanceof RechtKaart)
                {
                    StackPane tem = new StackPane();
                    tem.setStyle(geefUrlRechteKaartString(temp[i][j].getOrientatie()) + "-fx-background-size: 100 100");
                    GPDoolhof.add(tem, j, i);
                    if (temp[i][j].getSpeler() != null)
                    {
                        switch (temp[i][j].getSpeler().getKleur())
                        {
                            case 1:
                                Image img1 = new Image("/gui/images/spelerRood.png");
                                tem.getChildren().addAll(new ImageView(img1));
                                break;
                            case 2:
                                Image img2 = new Image("/gui/images/spelerGeel.png");
                                tem.getChildren().addAll(new ImageView(img2));
                                break;
                            case 3:
                                Image img3 = new Image("/gui/images/spelerGroen.png");
                                tem.getChildren().addAll(new ImageView(img3));
                                break;
                            case 4:
                                Image img4 = new Image("/gui/images/spelerBlauw.png");
                                tem.getChildren().addAll(new ImageView(img4));
                                break;
                        }
                    }                    
                }
                
                if (temp[i][j] instanceof TKaart)
                {
                    StackPane tem = new StackPane();
                    tem.setStyle(geefUrlTKaartString(temp[i][j].getOrientatie()) + "-fx-background-size: 100 100");
                    GPDoolhof.add(tem, j, i);
                    if (!(temp[i][j].getSchat().equals("")))
                    {
                        Image image = new Image("/gui/images/" + temp[i][j].getSchat() + ".png");
                        tem.getChildren().addAll(new ImageView(image));
                    }
                    if (temp[i][j].getSpeler() != null)
                    {
                        switch (temp[i][j].getSpeler().getKleur())
                        {
                            case 1:
                                Image img1 = new Image("/gui/images/spelerRood.png");
                                tem.getChildren().addAll(new ImageView(img1));
                                break;
                            case 2:
                                Image img2 = new Image("/gui/images/spelerGeel.png");
                                tem.getChildren().addAll(new ImageView(img2));
                                break;
                            case 3:
                                Image img3 = new Image("/gui/images/spelerGroen.png");
                                tem.getChildren().addAll(new ImageView(img3));
                                break;
                            case 4:
                                Image img4 = new Image("/gui/images/spelerBlauw.png");
                                tem.getChildren().addAll(new ImageView(img4));
                                break;
                        }
                    }                    
                }
            }
        }
    }

    @FXML
    private void btnSchuifOnAction(ActionEvent event)
    {
        dc.schuifGangkaartIn(Integer.parseInt(String.format("%s", cmbPositie.getSelectionModel().getSelectedItem())), cmbRichting.getSelectionModel().getSelectedIndex() + 1);
        buildDoolhof();
        buildVrijeKaart();
        btnDraai.setDisable(true);
        btnSchuif.setDisable(true);
        btnVerplaats.setDisable(false);
    }

    @FXML
    private void btnVerplaatsOnAction(ActionEvent event)
    {
        switch (String.format("%s", cmbVerplaatsRichting.getSelectionModel().getSelectedItem()))
        {
            case "Links":
            {
                try
                {
                    dc.verplaats(1);
                } catch (InvalidMoveException ex)
                {
                    Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }break;
            case "Rechts":
            {
                try
                {
                    dc.verplaats(2);
                } catch (InvalidMoveException ex)
                {
                    Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }break;
            case "Boven":
            {
                try
                {
                    dc.verplaats(3);
                } catch (InvalidMoveException ex)
                {
                    Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }break;
            case "Onder":
            {
                try
                {
                    dc.verplaats(4);
                } catch (InvalidMoveException ex)
                {
                    Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }break;
        }
        if(dc.heeftSchatGevonden())
        {
            dc.verwijderSchat();  
            lblSchatGevonden.setText(Taal.vertaal("schatGevonden") + " " + dc.geefHuidigeSchat());        
            if(!dc.geefWinnaar().isEmpty())
            {
                lblWinnaar.setText(dc.geefWinnaar());
            }
            btnNextOnAction(new ActionEvent());
        }
            
        buildDoolhof();
        geefVerplaatsRichtingen();
    }
    private void geefVerplaatsRichtingen()
    {
        ArrayList<Integer> lijst = dc.toonMogelijkeRichtingen();
        List<String> temp = new ArrayList<>();
        for (Integer i : lijst)
        {
            switch (i)
            {
                case 1:
                    temp.add("Links");
                    break;
                case 2:
                    temp.add("Rechts");
                    break;
                case 3:
                    temp.add("Boven");
                    break;
                case 4:
                    temp.add("Onder");
                    break;
            }
        }
        listVerplaats = FXCollections.observableList(temp);
        cmbVerplaatsRichting.setItems(listVerplaats);
    }

    @FXML
    private void btnNextOnAction(ActionEvent event)
    {
        if(dc.geefWinnaar().isEmpty())
        {
            buildGui();
            buildVrijeKaart();
            buildDoolhof();
            geefVerplaatsRichtingen();
            btnDraai.setDisable(false);
            btnSchuif.setDisable(false);
            lblHuidigeSpeler.setText(String.format("%s", dc.geefHuidigeSpeler())); 
        }       
        else
        {
            lblSchatGevonden.setText("");
            btnDraai.setDisable(true);
            btnSchuif.setDisable(true);
            btnNext.setDisable(true);
            lblHuidigeSpeler.setText(""); 
        }
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event)
    {
        try
        {
            dc.bewaarSpel(txaNaamSpel.getText());
        } catch (InvalidNameException ex)
        {
            Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(DoolhofSchermController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
