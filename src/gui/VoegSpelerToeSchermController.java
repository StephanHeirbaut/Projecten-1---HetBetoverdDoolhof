/*
 * Created by JVH 
 * The one and only jellyfish
 */
package gui;

import domein.DomeinController;
import exceptions.ExistingNameException;
import exceptions.InvalidNameException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import locale.Taal;

/**
 * FXML Controller class
 *
 * @author joche
 */
public class VoegSpelerToeSchermController extends SplitPane
{

    @FXML
    private Label lblNaam;
    @FXML
    private Button btnNext;
    @FXML
    private TextField txfNaamSpeler;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblGeboortejaar;
    @FXML
    private Label lblKleur;
    @FXML
    private TextField txfGeboortejaarSpeler;
    @FXML
    private TextArea txaSplers;
    @FXML
    private ComboBox<String> cmbKleuren;

    private ObservableList list;
    DomeinController dc;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblError;

    public VoegSpelerToeSchermController(DomeinController dc)
    {
        list = FXCollections.observableArrayList(Taal.vertaal("rood"), Taal.vertaal("geel"), Taal.vertaal("groen"), Taal.vertaal("blauw"));
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VoegSpelerToeScherm.fxml"));
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
    }

    private void buildGui()
    {
        cmbKleuren.setItems(list);
        cmbKleuren.setPromptText("Kies");
        lblNaam.setText(Taal.vertaal("naamSpeler"));
        lblGeboortejaar.setText(Taal.vertaal("gbjaarSpeler"));
        lblKleur.setText(Taal.vertaal("kleurSpeler"));
        lblError.setVisible(false);
//        Taal.vertaal("rood"),Taal.vertaal("geel"),Taal.vertaal("groen"),Taal.vertaal("blauw")

    }

    @FXML
    private void btnNextOnAction(ActionEvent event)
    {
        if (dc.getHuidigSpel().getAantalSpelers() == dc.getHuidigSpel().getSpelers().size())
        {
            dc.verdeelKaarten();
            Stage s = (Stage) this.getScene().getWindow();
            DoolhofSchermController root = new DoolhofSchermController(dc);
            Scene scene = new Scene(root);
            s.setScene(scene);
        }
    }

    @FXML
    private void btnAddOnAction(ActionEvent event)
    {
        int geboortejaar = 0;
        int kleur = 0;
        String speler = null;
        String naam = null;
        if (dc.getHuidigSpel().getAantalSpelers() > dc.getHuidigSpel().getSpelers().size())
        {
            try
            {
                buildGui();
                speler = txaSplers.getText();
                naam = txfNaamSpeler.getText();
                dc.controleerSpeler(naam);
                geboortejaar = Integer.parseInt(txfGeboortejaarSpeler.getText());
                kleur = cmbKleuren.getSelectionModel().getSelectedIndex()+1;
                dc.controleerGekozenKleur(kleur);
                dc.voegSpelerToe(naam, geboortejaar, kleur);
                speler += String.format("%s%n%d%n%s%n%n", naam, geboortejaar, cmbKleuren.getValue());
                txfNaamSpeler.setText("");
                txfGeboortejaarSpeler.setText("");
                txaSplers.setText(speler);
            } catch (InvalidNameException | ExistingNameException ex)
            {
                lblError.setVisible(true);
                lblError.setText(ex.getMessage());
            } catch (Exception ex)
            {
                lblError.setVisible(true);
                lblError.setText(ex.getMessage());
            }
        }
    }

    @FXML
    private void btnBackOnAction(ActionEvent event)
    {
        Stage s = (Stage) this.getScene().getWindow();
        LoadorPlay3Controller root = new LoadorPlay3Controller(dc);
        Scene scene = new Scene(root);
        s.setScene(scene);
    }

}
