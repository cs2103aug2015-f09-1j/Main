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
			indexTask = addingEvent.getIndex();
			output = addingEvent.getOutput();
		}
		else{
			AddTask addingTask = new AddTask(contentString, indexTask, tasks);
			indexTask = addingTask.getIndex();
			output = addingTask.getOutput();
		}
	}
	
	
	public String getOutput(){
		return output;
	}
	
	public int getIndexTask(){
		return indexTask;
	}
	
	public int getIndexEvent(){
		return indexEvent;
	}
	
	

	
	private boolean checkTodo(String contentStr){
		
		String tempTodo = contentStr.replace(GetSplittedString.getTask(contentStr), "");
		tempTodo = contentStr.replace("-", "");
		if(tempTodo.contains("from")){
			return true;
		}
		else{
			return false;
		}
	}
}