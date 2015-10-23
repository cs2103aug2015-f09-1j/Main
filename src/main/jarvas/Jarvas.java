package main.jarvas;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Jarvas extends Application {
	private static final String FXMLFILENAME = "sample.fxml";
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
    @Override
    public void start(Stage primaryStage){
        Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(FXMLFILENAME));
		} catch (IOException e) {
			System.err.println("invalid fxml input file " + e.getMessage());
		}
		
        primaryStage.setTitle("Jarvas");
        primaryStage.setScene(new Scene(root, 400, 420));
        primaryStage.show();
		logger.log(Level.INFO, "GUI is ready.");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
