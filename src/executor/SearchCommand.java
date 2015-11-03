/**
 * 
 */
package executor;

import java.util.Vector;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * @author Li
 *
 */
public class SearchCommand {

	private static final String MSG_SEARCH_SUCCESS = "KeyWords \"%1$s\" found";
	private static final String MSG_SEARCH_FAIL = "KeyWords \"%1$s\" not found";
	

	Vector<TaskToDo> tasks;
	Vector<TaskToDo> searchingResultTasks;
	Vector<TaskEvent> events;
	Vector<TaskEvent> searchingResultEvents;
	String contentStr;
	String output;
	boolean keyWordIsFound;
	
	public SearchCommand(Vector<TaskToDo> task, Vector<TaskEvent> event, String contentStr2){
		tasks = task;
		events = event;
		contentStr = contentStr2;
		searchingResultTasks = new Vector<>();
		searchingResultEvents = new Vector<>();
		keyWordIsFound = false;
		output = searchTask();
	}
	
	public String getOutput(){
		return output;
	}
	
	public Vector<TaskToDo> getTaskResult(){
		return searchingResultTasks;
	}
	
	public Vector<TaskEvent> getEventResult(){
		return searchingResultEvents;
	}
	

	/**
	 * This function search input by user from Task and Event 
	 * @param contentStr
	 * 				is the content to be used for the search
	 * @return add success msg
	 */
	private String searchTask(){
		// Search Task
		for(int i=0; i<tasks.size(); i++){
			if(GetSplittedString.getTask(contentStr).trim().equals(tasks.get(i).getName())){
				searchingResultTasks.addElement(tasks.elementAt(i));
				keyWordIsFound = true;
			}
		}
		// Search Event
		for(int i=0; i<events.size(); i++){
			if(GetSplittedString.getTask(contentStr).trim().equals(events.get(i).getName())){
				searchingResultEvents.addElement(events.elementAt(i));
				keyWordIsFound = true;
			}
		}
		if(keyWordIsFound){
			return String.format(MSG_SEARCH_SUCCESS, contentStr);
		}
		else{
			return String.format(MSG_SEARCH_FAIL, contentStr);
		}
	}
}
