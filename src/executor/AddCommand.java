/**
 * 
 */
package executor;

import java.util.Vector;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * This class provide adding functionality for Jarvas
 *
 */

public class AddCommand {
	
	String contentString;
	int indexTask;
	int indexEvent;
	String output;
	
	public AddCommand(String contentStr, int indexT, int indexE, Vector<TaskToDo> tasks, Vector<TaskEvent> events){
		contentString = contentStr;
		indexTask = indexT;
		indexEvent = indexE;
		if(checkTodo(contentString)){
			AddEvent addingEvent = new AddEvent(contentString, indexEvent, events);
			indexEvent = addingEvent.getIndex();
			output = addingEvent.getOutput();
		}
		else{
			AddTask addingTask = new AddTask(contentString, indexTask, tasks);
			indexTask = addingTask.getIndex();
			output = addingTask.getOutput();
		}
	}
	
	//getter methods
	public String getOutput(){
		return output;
	}
	
	public int getIndexTask(){
		return indexTask;
	}
	
	public int getIndexEvent(){
		return indexEvent;
	}
	
	

	
	/**
	 * this method check whether command is for event or task
	 * @param contentStr is the content of command entered
	 * @return
	 * 		true if it is a event command
	 */
	private boolean checkTodo(String contentStr){
		
		String tempTodo = contentStr.replace(GetSplittedString.getTask(contentStr), "");
		if(tempTodo.contains("-from") || tempTodo.contains("-f")){
			return true;
		}
		else{
			return false;
		}
	}
}
