/**
 * 
 */
package executor;

import java.util.Vector;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * @author A0145381H
 *
 */
public class ClearCommand {
	private static final String MSG_TASK_CLEAR = "Events and Tasks are cleared";
	
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
