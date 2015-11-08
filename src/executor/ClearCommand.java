/**
 * 
 */
package executor;

import java.util.Vector;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * this class perform clear functionality for Jarvas
 *
 */
public class ClearCommand {
	private static final String MSG_TASK_CLEAR = "Events and Tasks are cleared";
	
	private String output;
	
	
	/**
	 * this method clear the content of vectors
	 * @param tasks
	 * 			is the vector of tasks
	 * @param events
	 * 			is the vector of events
	 */
	public ClearCommand(Vector<TaskToDo> tasks, Vector<TaskEvent> events){
		tasks.clear();
		events.clear();
		output = MSG_TASK_CLEAR;
	}

	
	public String getOutput(){
		return output;
	}
}
