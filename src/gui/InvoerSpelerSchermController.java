/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.InvalidNumberException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import locale.Taal;

/**
 * FXML Controller class
 *
 * @author Belle
 */
public class InvoerSpelerSchermController extends Pane
{

    @FXML
    private Button btnBack;
    DomeinController dc;
    @FXML
    private Label lblText;
    @FXML
    private TextField txfNumberPlayers;
    @FXML
    private Button btnNext;
    @FXML
    private Label lblError;

    public InvoerSpelerSchermController(DomeinController dc)
    {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InvoerSpelerScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        lblText.setText(Taal.vertaal("hoeveelSpelers"));
        txfNumberPlayers.requestFocus();
    }

    @FXML
    private void btnBackOnAction(ActionEvent event)
    {
        Stage s = (Stage) this.getScene().getWindow();
        LoadorPlay2Controller root = new LoadorPlay2Controller(dc);
        Scene scene = new Scene(root);
        s.setScene(scene);
    }

    @FXML
    private void btnNextOnAction(ActionEvent event)
    {
        int aantalSpelers = 0;
        try
        {
            aantalSpelers = Integer.parseInt(txfNumberPlayers.getText());
            dc.voerAantalSpelersIn(Integer.parseInt(txfNumberPlayers.getText()));
        } catch (NumberFormatException | InvalidNumberException ex)
        {
            lblError.setText(Taal.vertaal("aantalSpelers"));
        }
        if (aantalSpelers >= 2 && aantalSpelers <= 4)
        {
            Stage s = (Stage) this.getScene().getWindow();
            VoegSpelerToeSchermController root = new VoegSpelerToeSchermController(dc);
            Scene scene = new Scene(root);
            s.setScene(scene);
        }
    }

}
