
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;


public class Controller implements Initializable{
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
    @FXML public TreeView<String> outputTask;
    @FXML public TreeView<String> outputEvent;
    @FXML public TextField userinput;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        Logic logic = new Logic();
        logger.log(Level.INFO, "controller class is initialised");
        Vector<TaskToDo> tasks = logic.returnNewTasks();
        outputTask.setRoot(new TreeItem<String>("TasksWithDate"));
        for(int i=0; i<tasks.size();i++) {
        	outputTask.getRoot().getChildren().add(new TreeItem<String>("Task name: " + 
            tasks.get(i).getName()+ "   Due Date: " + tasks.get(i).getDueDate()));
        }

        Vector<TaskEvent> events = logic.returnNewEvents();
        outputEvent.setRoot(new TreeItem<String>("Event"));
        for(int i=0; events != null && i<events.size();i++) {
        	outputEvent.getRoot().getChildren().add(new TreeItem<String>("Event name: " + 
        			events.get(i).getName()+ "   Start Date: " + events.get(i).getStartDate()+ "   End Date: " + events.get(i).getEndDate()));
        }
    }

    @FXML
    public void getInput(ActionEvent event) throws Exception {
        Logic logic = new Logic();
       // logic.getInput(userinput.getText());
        String input = userinput.getText();
        String outcome = logic.execute(input);
        printMessage(outcome);
        
        Vector<TaskToDo> tasks = logic.returnNewTasks();
        outputTask.getRoot().getChildren().removeAll(outputTask.getRoot().getChildren());
        for(int i=0; i<tasks.size();i++) {
        	outputTask.getRoot().getChildren().add(new TreeItem<String>("task name: " + 
            tasks.get(i).getName()+ "   Due Date: " + tasks.get(i).getDueDate()));
        }
        
        
        Vector<TaskEvent> events = logic.returnNewEvents();
        outputEvent.getRoot().getChildren().removeAll(outputEvent.getRoot().getChildren());
        for(int i=0; events != null && i<events.size();i++) {
        	outputEvent.getRoot().getChildren().add(new TreeItem<String>("Event name: " + 
        			events.get(i).getName()+ "   Start Date: " + events.get(i).getStartDate()+ "   End Date: " + events.get(i).getEndDate()));
        }
        
        
        
        userinput.setText("");
    }
    
    private void printMessage(String msg){
    	System.out.println(msg);
    }

	

}
