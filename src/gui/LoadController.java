package gui;

import domein.DomeinController;
import exceptions.InvalidNumberException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import locale.Taal;

/**
 * FXML Controller class
 *
 * @author Belle
 */
public class LoadController extends Pane
{

    @FXML
    private Button btnBack;
    @FXML
    private Button btnLoad;

    private DomeinController dc;
    @FXML
    private ListView<String> lvSpellen;

    LoadController(DomeinController dc) throws Exception
    {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Load.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
            initialize();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() throws Exception
    {
        List<String> spellen = dc.geefSpelNamen();
        lvSpellen.setItems((FXCollections.observableArrayList(spellen)));
    }

    @FXML
    private void btnBackOnAction(ActionEvent event)
    {
        Stage s = (Stage) this.getScene().getWindow();
        LoadorPlay3Controller root = new LoadorPlay3Controller(dc);
        Scene scene = new Scene(root);
        s.setScene(scene);
    }

    @FXML
    private void btnLoadOnAction(ActionEvent event) throws InvalidNumberException, Exception
    {
        try
        {
            String spel = lvSpellen.getSelectionModel().getSelectedItem();
            String temp = spel.substring(0, 1);
            int ID = Integer.parseInt(temp);
            dc.laadSpel(ID);
        } catch (NullPointerException e)
        {
            Alert alert = new Alert(ERROR);
            alert.setHeaderText(Taal.vertaal("foutGeenSpel"));
            alert.setContentText(Taal.vertaal("foutGeenSpel2"));
            alert.showAndWait();
        } catch (SQLException e)
        {
            Alert alert = new Alert(ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(Taal.vertaal("foutLadenSpel"));
            alert.showAndWait();
        }

        Stage s = (Stage) this.getScene().getWindow();
        DoolhofSchermController root = new DoolhofSchermController(dc);
        Scene scene = new Scene(root);
        s.setScene(scene);
    }

}
