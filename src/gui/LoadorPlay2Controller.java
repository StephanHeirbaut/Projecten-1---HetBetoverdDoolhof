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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import locale.Taal;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joche
 */
public class LoadorPlay2Controller extends Pane {

    @FXML
    private Button btnLoad;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnBack;
    
    private DomeinController dc;
    
    public LoadorPlay2Controller(DomeinController dc)
    {
        btnPlay = new Button();
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadorPlay3.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        }catch(IOException ex)
        {
            throw new RuntimeException(ex);
        }
        btnLoad.setText(Taal.vertaal("keuzeLaden"));
        btnPlay.setText(Taal.vertaal("keuzeSpelen"));
    }

    @FXML
    private void btnLoadOnAction(ActionEvent event) 
    {
    }

    @FXML
    private void btnPlayOnAction(ActionEvent event) throws Exception 
    {
        dc.maakNieuwSpel();
        dc.maakDoolhof();
        Stage s = (Stage) this.getScene().getWindow();
        InvoerSpelerSchermController root = new InvoerSpelerSchermController(dc);
        Scene scene = new Scene(root);
        s.setScene(scene);         
    }

    @FXML
    private void btnBackOnAction(ActionEvent event) 
    {
        Stage s = (Stage) this.getScene().getWindow();
        DoolhofLanguageSelectionController root = new DoolhofLanguageSelectionController(dc);
        Scene scene = new Scene(root);
        s.setScene(scene); 
    }
}
