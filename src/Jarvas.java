

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Jarvas extends Application {

    @Override
    public void start(Stage primaryStage){
        Parent root = null;
        
		try {
			root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		} catch (IOException e) {
			System.err.println("invalid input" + e.getMessage());
		}
		
        primaryStage.setTitle("Jarvas");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
