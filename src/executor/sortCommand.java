/**
 * 
 */
package executor;

import java.util.Vector;
import java.util.Collections;
import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * this class provide sort functionality to Jarvas
 *
 */
public class sortCommand{
	
	Vector<TaskToDo> tasks;
	Vector<TaskEvent> events;
	//@@author A0134109N
	public sortCommand(Vector<TaskToDo> tasks, Vector<TaskEvent> events){
		this.tasks = tasks;
		this.events = events;
		sortAll();
	}

	
	/**
	 * this method sort all the content inside vector tasks and events
	 */
	//@@author A0134109N
	private void sortAll() {
		Collections.sort(tasks);
		Collections.sort(events);
	}

	//@@author A0134109N
	public Vector<TaskToDo> returnTasks(){
		return tasks;
	}
	//@@author A0134109N
	public Vector<TaskEvent> returnEvents(){
		return events;
	}
	
	

}