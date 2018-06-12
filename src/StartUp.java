
import javafx.application.Application;
import javafx.stage.Stage;
import ui.DoolhofApplicatie;

/**
 * This class contains the main and is used to start the game
 * @author Belle
 */
public class StartUp extends Application
{

    /**
     * Main method
     * @param args is not used
     * @throws Exception is thrown when somehting went wrong
     */
    public static void main(String[] args) throws Exception
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        new DoolhofApplicatie();
        System.exit(0);
    }
}
