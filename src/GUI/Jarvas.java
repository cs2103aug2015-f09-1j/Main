
package GUI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
	private Text space1;
	private Text space2;
	private Text space3;
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
        	if(tasks.get(i).getDone()){
            	alltasks.add("Y" + (i+1) + ". Task name: " + tasks.get(i).getName());
            	alltasks.add("Y" + "    Due Date: " + tasks.get(i).getStringStartDate());
        	}
        	else{
            	alltasks.add("N" + (i+1) + ". Task name: " + tasks.get(i).getName());
            	alltasks.add("N" + "    Due Date: " + tasks.get(i).getStringStartDate());
        	}
        }
        Vector<TaskEvent> events = logic.returnNewEvents();
        alltasks.add("Events");
        for(int i=0; events != null && i<events.size();i++) {
        	if(events.get(i).getDone()){
            	alltasks.add("Y" + (i+1) + ". Event name: " + events.get(i).getName());
            	alltasks.add("Y" + "    Start Date: " + events.get(i).getStartDate());
            	alltasks.add("Y" + "    End Date: " + events.get(i).getEndDate());
        	}
        	else{
            	alltasks.add("N" + (i+1) + ". Event name: " + events.get(i).getName());
            	alltasks.add("N" + "    Start Date: " + events.get(i).getStartDate());
            	alltasks.add("N" + "    End Date: " + events.get(i).getEndDate());
        	}
        }
    	alltasks.add("N");
        allTasks = new ListView<>(alltasks);
        log = new Text();
        input = new TextField();
        space1 = new Text();
        space2 = new Text();
        space3 = new Text();
        log.setFont(new Font(0));
        space1.setFont(new Font(10));
        space2.setFont(new Font(10));
        space3.setFont(new Font(10));
        inputHandler newCommand = new inputHandler();
        input.setOnAction(newCommand);
        pane = new VBox();
		vbox = new VBox();
        VBox.setVgrow(allTasks, Priority.ALWAYS);
		pane.getChildren().add(space3);
		pane.getChildren().add(input);
		pane.getChildren().add(space1);
		pane.getChildren().add(log);
		pane.getChildren().add(space2);
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
        Scene scene = new Scene(pane, 530, 500,Color.rgb(238,236,218));
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
            case "Searching Reasult:":
            	setTextFill(Color.rgb(253, 50, 50));
            	setFont(new Font("Avenir", 23));
            	setText(item);
            	//setBackground(new Background(new BackgroundFill(Color.rgb(253, 184, 186), CornerRadii.EMPTY, Insets.EMPTY)));
            	break;
            case "YEnd":
            	setTextFill(Color.rgb(253, 120, 120));
            	setFont(new Font("Avenir", 23));
            	setText(item.substring(1));
            	//setBackground(new Background(new BackgroundFill(Color.rgb(253, 184, 186), CornerRadii.EMPTY, Insets.EMPTY)));
            	break;
            case "STasks":
            case "SEvents":
            	setTextFill(Color.rgb(255, 120, 120));
            	setFont(new Font("Courier", 20));
            	setText(item.substring(1));
            	break;
            case "Tasks":
            case "Events":
            	setTextFill(Color.rgb(71, 184, 251));
            	setFont(new Font("Courier", 20));
            	setText(item);
            	break;
            default:
            	if(item.charAt(0) == 'Y'){
                	setTextFill(Color.DARKGRAY);
                	setFont(new Font("Courier", 14));
                	setText(item.substring(1));
            	}
            	else if(item.charAt(0) == 'N'){
                	setTextFill(Color.CORNFLOWERBLUE);
                	setFont(new Font("Courier", 14));
                	setText(item.substring(1));
            	}
            	else if(item.charAt(0) == 'S'){
                	if(item.charAt(1) == 'Y'){
                    	setTextFill(Color.rgb(253, 150, 50));
                    	setFont(new Font("Courier", 14));
                    	setText(item.substring(2));
                	}
                	else if(item.charAt(1) == 'N'){
                    	setTextFill(Color.rgb(253, 50, 150));
                    	setFont(new Font("Courier", 14));
                    	setText(item.substring(2));
                	}
            	}
            }
        }
    }
	

    class inputHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
        	Logic logic = new Logic();
            String Input = input.getText();
            String outcome = logic.execute(Input);
            log.setText(" " + outcome);
            log.setFont(Font.font("Courier", 12));
            Vector<TaskToDo> tasks = logic.returnNewTasks();
            Vector<TaskEvent> events = logic.returnNewEvents();
            alltasks = FXCollections.observableArrayList();
            if(logic.getIsCommandSearch()){
                Vector<TaskToDo> tasksForSearch = logic.getTasksForSearch();
                Vector<TaskEvent> eventsForSearch = logic.getEventsForSearch();
                
            	alltasks.add("Searching Reasult:");

                alltasks.add("STasks");
                for(int i=0; i<tasksForSearch.size();i++) {
                	if(tasksForSearch.get(i).getDone()){
                    	alltasks.add("SY" + (i+1) + ". Task name: " + tasksForSearch.get(i).getName());
                    	alltasks.add("SY" + "    Due Date: " + tasksForSearch.get(i).getStringStartDate());
                	}
                	else{
                    	alltasks.add("SN" + (i+1) + ". Task name: " + tasksForSearch.get(i).getName());
                    	alltasks.add("SN" + "    Due Date: " + tasksForSearch.get(i).getStringStartDate());
                	}
                }
                alltasks.add("SEvents");
                for(int i=0; eventsForSearch != null && i<eventsForSearch.size();i++) {
                	if(eventsForSearch.get(i).getDone()){
                    	alltasks.add("SY" + (i+1) + ". Event name: " + eventsForSearch.get(i).getName());
                    	alltasks.add("SY" + "    Start Date: " + eventsForSearch.get(i).getStartDate());
                    	alltasks.add("SY" + "    End Date: " + eventsForSearch.get(i).getEndDate());
                	}
                	else{
                    	alltasks.add("SN" + (i+1) + ". Event name: " + eventsForSearch.get(i).getName());
                    	alltasks.add("SN" + "    Start Date: " + eventsForSearch.get(i).getStartDate());
                    	alltasks.add("SN" + "    End Date: " + eventsForSearch.get(i).getEndDate());
                	}
                }
            	alltasks.add("Y");
            	
            }
            alltasks.add("Tasks");
            for(int i=0; i<tasks.size();i++) {
            	if(tasks.get(i).getDone()){
                	alltasks.add("Y" + (i+1) + ". Task name: " + tasks.get(i).getName());
                	alltasks.add("Y" + "    Due Date: " + tasks.get(i).getStringStartDate());
            	}
            	else{
                	alltasks.add("N" + (i+1) + ". Task name: " + tasks.get(i).getName());
                	alltasks.add("N" + "    Due Date: " + tasks.get(i).getStringStartDate());
            	}
            }
            alltasks.add("Events");
            for(int i=0; events != null && i<events.size();i++) {
            	if(events.get(i).getDone()){
                	alltasks.add("Y" + (i+1) + ". Event name: " + events.get(i).getName());
                	alltasks.add("Y" + "    Start Date: " + events.get(i).getStartDate());
                	alltasks.add("Y" + "    End Date: " + events.get(i).getEndDate());
            	}
            	else{
                	alltasks.add("N" + (i+1) + ". Event name: " + events.get(i).getName());
                	alltasks.add("N" + "    Start Date: " + events.get(i).getStartDate());
                	alltasks.add("N" + "    End Date: " + events.get(i).getEndDate());
            	}
            }
        	alltasks.add("N");
            input.setText("");
            allTasks = new ListView<>(alltasks);
            vbox.getChildren().clear();
            vbox.getChildren().add(allTasks);
    		allTasks.setCellFactory(new Callback<ListView<String>, 
    	            ListCell<String>>() {
    	                @Override 
    	                public ListCell<String> call(ListView<String> list) {
    	                    return new ColorTaskCell();
    	                }
    	            });
        }
    }
	
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
