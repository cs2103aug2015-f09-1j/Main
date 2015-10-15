
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import java.security.PublicKey;
import java.util.ResourceBundle;


public class Controller implements Initializable{
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
    @FXML public TreeView<String> output;
    @FXML public TextField userinput;

    @FXML
    public void initialize(java.net.URL location, ResourceBundle resources){
        Logic logic = new Logic();
        logger.log(Level.INFO, "controller class is initialised");
        Vector<Task> tasks = logic.returnNewTasks();
        output.setRoot(new TreeItem<String>("Tasks"));
        for(int i=0; i<tasks.size();i++) {
            output.getRoot().getChildren().add(new TreeItem<String>("task name: " + 
            tasks.get(i).getTaskName()+ "   Due Date: " + tasks.get(i).getDueDate()));
        }
    }

    @FXML
    public void getInput(ActionEvent event) throws Exception {
        Logic logic = new Logic();
       // logic.getInput(userinput.getText());
        String input = userinput.getText();
        String outcome = logic.execute(input);
        printMessage(outcome);
        Vector<Task> tasks = logic.returnNewTasks();
        assert tasks!=null :"tasks get by UI is null";
        output.getRoot().getChildren().removeAll(output.getRoot().getChildren());
        for(int i=0; i<tasks.size();i++) {
            output.getRoot().getChildren().add(new TreeItem<String>("task name: " + 
            tasks.get(i).getTaskName()+ "   Due Date: " + tasks.get(i).getDueDate()));
            
        }
        userinput.setText("");
    }
    
    private void printMessage(String msg){
    	System.out.println(msg);
    }

}
