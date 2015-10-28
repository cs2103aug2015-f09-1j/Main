
package GUI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.jarvas.Logic;
import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;
import javafx.scene.control.*;
import javafx.scene.layout.*;
/**
 * @author Li Huiying
 *
 */
public class Jarvas extends Application{

	private final Logger logger = Logger.getLogger(Jarvas.class.getName());
	private ObservableList<String> alltasks;
	private ListView<String> allTasks;
	private Text log;
	private TextField input;
	private VBox pane;
	private VBox vbox;
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Logic logic = new Logic();
        logger.log(Level.INFO, "controller class is initialised");
        Vector<TaskToDo> tasks = logic.returnNewTasks();
        alltasks = FXCollections.observableArrayList();
        alltasks.add("Tasks");
        for(int i=0; i<tasks.size();i++) {
        	alltasks.add((i+1) + ". Task name: " + tasks.get(i).getName());
        	alltasks.add("    Due Date: " + tasks.get(i).getStringStartDate());
        }
        Vector<TaskEvent> events = logic.returnNewEvents();
        alltasks.add("Events");
        for(int i=0; events != null && i<events.size();i++) {
        	alltasks.add((i+1) + ". Event name: " + events.get(i).getName());
        	alltasks.add("    Start Date: " + events.get(i).getStartDate());
        	alltasks.add("    End Date: " + events.get(i).getEndDate());
        }
        allTasks = new ListView<>(alltasks);
        log = new Text();
        input = new TextField();
        inputHandler newCommand = new inputHandler();
        input.setOnAction(newCommand);
        pane = new VBox();
		vbox = new VBox();
        VBox.setVgrow(allTasks, Priority.ALWAYS);
		pane.getChildren().add(input);
		pane.getChildren().add(log);
		pane.getChildren().add(vbox);
		vbox.getChildren().add(allTasks);
		allTasks.setCellFactory(new Callback<ListView<String>, 
	            ListCell<String>>() {
	                @Override 
	                public ListCell<String> call(ListView<String> list) {
	                    return new ColorTaskCell();
	                }
	            }
	        );

        Scene scene = new Scene(pane, 400, 600,Color.rgb(238,236,218));
        primaryStage.setTitle("Jarvas");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	static class ColorTaskCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(item == null){
            	return;
            }
            switch(item){
            case "Tasks":
            	setTextFill(Color.rgb(71, 184, 251));
            	setText(item);
            	break;
            case "Events":
            	setTextFill(Color.rgb(71, 184, 251));
            	setText(item);
            	break;
            default:
            	setTextFill(Color.rgb(0, 0, 0));
            	setText(item);
            }
        }
    }
	
	static class ColorEventCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(item == null){
            	return;
            }
            switch(item){
            case "Events":
            	setTextFill(Color.rgb(71, 184, 251));
            	setText(item);
            	break;
            default:
            	setTextFill(Color.rgb(0, 0, 0));
            	setText(item);
            }
        }
    }
	

    class inputHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
        	Logic logic = new Logic();
            // logic.getInput(userinput.getText());
            String Input = input.getText();
            String outcome = logic.execute(Input);
            log.setText(outcome);
            Vector<TaskToDo> tasks = logic.returnNewTasks();
            Vector<TaskEvent> events = logic.returnNewEvents();
            alltasks = FXCollections.observableArrayList();
            alltasks.add("Tasks");
            for(int i=0; i<tasks.size();i++) {
            	alltasks.add((i+1) + ". Task name: " + tasks.get(i).getName());
            	alltasks.add("    Due Date: " + tasks.get(i).getStringStartDate());
            }
            alltasks.add("Events");
            for(int i=0; events != null && i<events.size();i++) {
            	alltasks.add((i+1) + ". Event name: " + events.get(i).getName());
            	alltasks.add("    Start Date: " + events.get(i).getStartDate());
            	alltasks.add("    End Date: " + events.get(i).getEndDate());
            }
            input.setText("");
            allTasks = new ListView<>(alltasks);
            vbox.getChildren().clear();
            vbox.getChildren().add(allTasks);
            
        }
    }
	
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
