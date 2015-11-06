
package GUI;
import java.util.*;
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
 * @author A0145381H
 *
 */
public class Jarvas extends Application{
	
	
	private static final String TASK_NAME = ". Task name: ";
	private static final String TASK_DUE = "    Due Date: ";
	private static final String EVENT_NAME = ". Event name: ";
	private static final String EVENT_START = "    Start Date: ";
	private static final String EVENT_END = "    End Date: ";
	private static final String DONE = "Y";
	private static final String UNDONE = "N";
	private static final char SEARCH = 'S';
	private static final char CDONE = 'Y';
	private static final char CUNDONE = 'N';
	private static final String S_DONE = "SY";
	private static final String S_UNDONE = "SN";
	private static final String SEARCH_RESULT = "Searching Result:";
	private static final String SEARCH_END = "YEnd";
	private static final String EVENTS = "Events";
	private static final String TASKS = "Tasks";
	private static final String TASK_FOR_SEARCH = "STasks";
	private static final String EVENT_FOR_SEARCH = "SEvents";
	private static final String FONT_AVENIR = "Avenir";
	private static final String FONT_COURIER = "Courier";
	private static final String JARVAS = "Jarvas";
	private static final String EMPTY = "";
	private static final String SPACE = " ";

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
		

		Logic logic = new Logic();
        Vector<TaskToDo> tasks = logic.returnNewTasks();
        alltasks = FXCollections.observableArrayList();
        alltasks.add(TASKS);
        for(int i=0; i<tasks.size();i++) {
        	if(tasks.get(i).getDone()){
            	alltasks.add(DONE + (i+1) + TASK_NAME + tasks.get(i).getName());
            	alltasks.add(DONE + TASK_DUE + tasks.get(i).getStartDate());
        	}
        	else{
            	alltasks.add(UNDONE + (i+1) + TASK_NAME + tasks.get(i).getName());
            	alltasks.add(UNDONE + TASK_DUE + tasks.get(i).getStartDate());
        	}
        }
        Vector<TaskEvent> events = logic.returnNewEvents();
        alltasks.add(EVENTS);
        for(int i=0; events != null && i<events.size();i++) {
        	if(events.get(i).getDone()){
            	alltasks.add(DONE + (i+1) + EVENT_NAME + events.get(i).getName());
            	alltasks.add(DONE + EVENT_START + events.get(i).getStartDate());
            	alltasks.add(DONE + EVENT_END + events.get(i).getEndDate());
        	}
        	else{
            	alltasks.add(UNDONE + (i+1) + EVENT_NAME + events.get(i).getName());
            	alltasks.add(UNDONE + EVENT_START + events.get(i).getStartDate());
            	alltasks.add(UNDONE + EVENT_END + events.get(i).getEndDate());
        	}
        }
    	alltasks.add(UNDONE);
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
        primaryStage.setTitle(JARVAS);
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
            case SEARCH_RESULT:
            	setTextFill(Color.rgb(253, 50, 50));
            	setFont(new Font(FONT_AVENIR, 23));
            	setText(item);
            	//setBackground(new Background(new BackgroundFill(Color.rgb(253, 184, 186), CornerRadii.EMPTY, Insets.EMPTY)));
            	break;
            case SEARCH_END:
            	setTextFill(Color.rgb(253, 120, 120));
            	setFont(new Font(FONT_AVENIR, 23));
            	setText(item.substring(1));
            	//setBackground(new Background(new BackgroundFill(Color.rgb(253, 184, 186), CornerRadii.EMPTY, Insets.EMPTY)));
            	break;
            case TASK_FOR_SEARCH:
            case EVENT_FOR_SEARCH:
            	setTextFill(Color.rgb(255, 120, 120));
            	setFont(new Font(FONT_COURIER, 20));
            	setText(item.substring(1));
            	break;
            case TASKS:
            case EVENTS:
            	setTextFill(Color.rgb(71, 184, 251));
            	setFont(new Font(FONT_COURIER, 20));
            	setText(item);
            	break;
            default:
            	if(item.charAt(0) == CDONE){
                	setTextFill(Color.DARKGRAY);
                	setFont(new Font(FONT_COURIER, 14));
                	setText(item.substring(1));
            	}
            	else if(item.charAt(0) == CUNDONE){
                	setTextFill(Color.CORNFLOWERBLUE);
                	setFont(new Font(FONT_COURIER, 14));
                	setText(item.substring(1));
            	}
            	else if(item.charAt(0) == SEARCH){
                	if(item.charAt(1) == CDONE){
                    	setTextFill(Color.rgb(253, 150, 50));
                    	setFont(new Font(FONT_COURIER, 14));
                    	setText(item.substring(2));
                	}
                	else if(item.charAt(1) == CUNDONE){
                    	setTextFill(Color.rgb(253, 50, 150));
                    	setFont(new Font(FONT_COURIER, 14));
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
            log.setText(SPACE + outcome);
            log.setFont(Font.font(FONT_COURIER, 12));
            Vector<TaskToDo> tasks = logic.returnNewTasks();
            Vector<TaskEvent> events = logic.returnNewEvents();
            alltasks = FXCollections.observableArrayList();
            if(logic.getIsCommandSearch()){
                Vector<TaskToDo> tasksForSearch = logic.getTasksForSearch();
                Vector<TaskEvent> eventsForSearch = logic.getEventsForSearch();
                
            	alltasks.add(SEARCH_RESULT);

                alltasks.add(TASK_FOR_SEARCH);
                for(int i=0; i<tasksForSearch.size();i++) {
                	if(tasksForSearch.get(i).getDone()){
                    	alltasks.add(S_DONE + (i+1) + TASK_NAME + tasksForSearch.get(i).getName());
                    	alltasks.add(S_DONE + TASK_DUE + tasksForSearch.get(i).getStartDate());
                	}
                	else{
                    	alltasks.add(S_UNDONE + (i+1) + TASK_NAME + tasksForSearch.get(i).getName());
                    	alltasks.add(S_UNDONE + TASK_DUE + tasksForSearch.get(i).getStartDate());
                	}
                }
                alltasks.add(EVENT_FOR_SEARCH);
                for(int i=0; eventsForSearch != null && i<eventsForSearch.size();i++) {
                	if(eventsForSearch.get(i).getDone()){
                    	alltasks.add(S_DONE + (i+1) + EVENT_NAME + eventsForSearch.get(i).getName());
                    	alltasks.add(S_DONE + EVENT_START + eventsForSearch.get(i).getStartDate());
                    	alltasks.add(S_DONE + EVENT_END + eventsForSearch.get(i).getEndDate());
                	}
                	else{
                    	alltasks.add(S_UNDONE + (i+1) + EVENT_NAME + eventsForSearch.get(i).getName());
                    	alltasks.add(S_UNDONE + EVENT_START + eventsForSearch.get(i).getStartDate());
                    	alltasks.add(S_UNDONE + EVENT_END + eventsForSearch.get(i).getEndDate());
                	}
                }
            	alltasks.add(DONE);
            	
            }
            alltasks.add(TASKS);
            for(int i=0; i<tasks.size();i++) {
            	if(tasks.get(i).getDone()){
                	alltasks.add(DONE + (i+1) + TASK_NAME + tasks.get(i).getName());
                	alltasks.add(DONE + TASK_DUE + tasks.get(i).getStartDate());
            	}
            	else{
                	alltasks.add(UNDONE + (i+1) + TASK_NAME + tasks.get(i).getName());
                	alltasks.add(UNDONE + TASK_DUE + tasks.get(i).getStartDate());
            	}
            }
            alltasks.add(EVENTS);
            for(int i=0; events != null && i<events.size();i++) {
            	if(events.get(i).getDone()){
                	alltasks.add(DONE + (i+1) + EVENT_NAME + events.get(i).getName());
                	alltasks.add(DONE + EVENT_START + events.get(i).getStartDate());
                	alltasks.add(DONE + EVENT_END + events.get(i).getEndDate());
            	}
            	else{
                	alltasks.add(UNDONE + (i+1) + EVENT_NAME + events.get(i).getName());
                	alltasks.add(UNDONE + EVENT_START + events.get(i).getStartDate());
                	alltasks.add(UNDONE + EVENT_END + events.get(i).getEndDate());
            	}
            }
        	alltasks.add(UNDONE);
            input.setText(EMPTY);
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
