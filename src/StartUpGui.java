
import domein.DomeinController;
import gui.DoolhofLanguageSelectionController;
import gui.DoolhofSchermController;
import gui.LoadorPlay2Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 *
 * @author joche
 */
public class StartUpGui extends Application{

    @Override
    public void start(Stage primaryStage) {
        DoolhofLanguageSelectionController root = new DoolhofLanguageSelectionController(new DomeinController());
        primaryStage.setTitle("Het betoverende doolhof");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();        
//        scene.
//        LoadorPlay2Controller root2 = new LoadorPlay2Controller();
//        Scene scene2= new Scene(root2);
//        primaryStage.setScene(scene2);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
