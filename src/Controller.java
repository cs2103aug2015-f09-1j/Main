package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import java.util.Vector;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML public TreeView<String> output;
    @FXML public TextField userinput;

    @FXML
    public void initialize(java.net.URL location, ResourceBundle resources){
        Logic logic = new Logic();
        Vector<String> tasks = logic.displayTask();
        output.setRoot(new TreeItem<String>("Tasks"));
        for(String str: tasks) {
            output.getRoot().getChildren().add(new TreeItem<String>(str));
        }
    }

    @FXML
    public void getInput(ActionEvent event) throws Exception {
        Logic logic = new Logic();
        logic.getInput(userinput.getText());
        logic.execute();
        Vector<String> tasks = logic.displayTask();
        output.getRoot().getChildren().removeAll(output.getRoot().getChildren());
        for(String str: tasks) {
            System.out.println(str);
            output.getRoot().getChildren().add(new TreeItem<String>(str));
        }
        userinput.setText("");
    }

}
