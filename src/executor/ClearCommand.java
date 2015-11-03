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
public class ClearCommand {
	private static final String MSG_TASK_CLEAR = "tasks is clear";
	
	private String output;
	
	public ClearCommand(Vector<TaskToDo> tasks, Vector<TaskEvent> events){
		tasks.clear();
		events.clear();
		output = MSG_TASK_CLEAR;
	}

	
	public String getOutput(){
		return output;
	}
}
