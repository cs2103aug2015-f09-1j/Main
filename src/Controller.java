

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
        Vector<Task> tasks = logic.displayTask();
        output.setRoot(new TreeItem<String>("Tasks"));
        for(int i=0; i<tasks.size();i++) {
        	
            output.getRoot().getChildren().add(new TreeItem<String>(tasks.get(i).getTaskName()));
        }
    }

    @FXML
    public void getInput(ActionEvent event) throws Exception {
        Logic logic = new Logic();
        logic.getInput(userinput.getText());
        logic.execute();
        Vector<Task> tasks = logic.displayTask();
        output.getRoot().getChildren().removeAll(output.getRoot().getChildren());
        for(int i=0; i<tasks.size();i++) {
        	
            output.getRoot().getChildren().add(new TreeItem<String>(tasks.get(i).getTaskName()));
        }
        userinput.setText("");
    }

}
