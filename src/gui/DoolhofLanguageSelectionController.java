/*
 * Created by JVH 
 * The one and only jellyfish
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import locale.Taal;

/**
 * FXML Controller class
 *
 * @author jochem
 */
public class DoolhofLanguageSelectionController extends VBox
{

    @FXML
    private Label lblTitel;
    @FXML
    private Label lblTaal;
    @FXML
    private final Button btnNederlands;
    @FXML
    private final Button btnFrans;
    @FXML
    private final Button btnEngels;
    @FXML
    private Button btnNext;
    @FXML
    private Label lblText;
    private DomeinController dc;
    
    public DoolhofLanguageSelectionController(DomeinController dc)
    {
        this.dc = dc;
        btnNederlands = new Button(); 
        btnFrans = new Button();
        btnEngels = new Button();
        btnNext = new Button();
        lblText = new Label();        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalKiesScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

    }  

    @FXML
    private void btnNederLandsActionEvent(ActionEvent event)
    {
        lblText.setText("");
        lblText.setText("Welkom, u taal keuze is nederlands");
        Taal.stelTaalIn("locale_nl_BE");
            
    }

    @FXML
    private void btnFransActionEvent(ActionEvent event) {
        lblText.setText("");
        lblText.setText("Bonjour, votre langue est français");
        Taal.stelTaalIn("locale_fr_BE");
        
    }

    @FXML
    private void btnNextActionEvent(ActionEvent event)
    {
        Stage s = (Stage) this.getScene().getWindow();
        LoadorPlay3Controller root = new LoadorPlay3Controller(dc);
        Scene scene = new Scene(root);
        s.setScene(scene);         
    }
    @FXML
    private void btnEngelsActionEvent(ActionEvent event) {
        lblText.setText("");
        lblText.setText("Hello, you have chosen English");  
        Taal.stelTaalIn("locale_en_US");
    }
    
}
