
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.omg.CORBA.SystemException;

import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;


public class Controller implements Initializable{
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
	@FXML public TreeView<String> output;
    @FXML public TextField userinput;

    TreeItem<String> outputTaskRoot;
    TreeItem<String> outputEventRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        Logic logic = new Logic();
        logger.log(Level.INFO, "controller class is initialised");
        Vector<TaskToDo> tasks = logic.returnNewTasks();
        outputTaskRoot = new TreeItem<String>("Tasks");
        for(int i=0; i<tasks.size();i++) {
        	outputTaskRoot.getChildren().add(new TreeItem<String>("Task name: " + 
            tasks.get(i).getName()+ "   Due Date: " + tasks.get(i).getDueDate()));
        }

        Vector<TaskEvent> events = logic.returnNewEvents();
        outputEventRoot = new TreeItem<String>("Events");
        for(int i=0; events != null && i<events.size();i++) {
        	outputEventRoot.getChildren().add(new TreeItem<String>("Event name: " + 
        			events.get(i).getName()+ "   Start Date: " + events.get(i).getStartDate()+ "   End Date: " + events.get(i).getEndDate()));
        }
        
        TreeItem<String> root = new TreeItem<String>();
        output.setRoot(root);
        output.setShowRoot(false);
        output.getRoot().getChildren().add(outputTaskRoot);
        output.getRoot().getChildren().add(outputEventRoot);
        
    }

    @FXML
    public void getInput(ActionEvent event) throws Exception {
        Logic logic = new Logic();
       // logic.getInput(userinput.getText());
        String input = userinput.getText();
        String outcome = logic.execute(input);
        printMessage(outcome);

        
        Vector<TaskToDo> tasks = logic.returnNewTasks();
        outputTaskRoot.getChildren().removeAll(outputTaskRoot.getChildren());
        for(int i=0; i<tasks.size();i++) {
        	outputTaskRoot.getChildren().add(new TreeItem<String>("task name: " + 
            tasks.get(i).getName()+ "   Due Date: " + tasks.get(i).getDueDate()));
        }
        
        Vector<TaskEvent> events = logic.returnNewEvents();
        outputEventRoot.getChildren().removeAll(outputEventRoot.getChildren());
        for(int i=0; events != null && i<events.size();i++) {
        	outputEventRoot.getChildren().add(new TreeItem<String>("Event name: " + 
        			events.get(i).getName()+ "   Start Date: " + events.get(i).getStartDate()+ "   End Date: " + events.get(i).getEndDate()));
        }
        
        
        
        userinput.setText("");
    }
    
    private void printMessage(String msg){
    	System.out.println(msg);
    }

	

}
